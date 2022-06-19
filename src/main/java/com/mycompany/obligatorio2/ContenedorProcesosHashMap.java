/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.obligatorio2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author inazu
 */
public class ContenedorProcesosHashMap {
    public HashMap<Integer, Proceso> mapa;
    public Integer contadorParaId;
    public LinkedList<Proceso> procesosParaInsertar;
    public boolean cargarProcesos;
    public Integer procesoParaBloquearID;
    public Integer procesoParaDesbloquearID;
    public Procesador CPU;
    
    public ArrayList<Proceso> arrayRetornado = new ArrayList<>();
    public ArrayList<Proceso> arrayRetornadoBloqueados = new ArrayList<>();
    
    
    public ContenedorProcesosHashMap(Procesador cpu){
        this.procesosParaInsertar = new LinkedList();
        this.contadorParaId = 0;
        this.mapa = new HashMap();
        this.cargarProcesos = false;
        this.CPU = cpu;
    }
    
    public Proceso buscarProceso(Integer id){
        System.out.println(mapa);
        return mapa.get(id);
    }
    
    public void agregarProcesos(){
        if(cargarProcesos){
            for(Proceso p : procesosParaInsertar){
                mapa.put(p.ID, p);
            }
            cargarProcesos = false;
            procesosParaInsertar = new LinkedList();
        }
    }
    
    
    public void iterarSobreProcesos(){
        Proceso procesoConMasPrioridad = null;
        int maxPrioridad = 99;
        Proceso procesoParaEliminar = null;
        for(Proceso p : mapa.values()){
            // Se recorre quedandose con el proceso de prioridad más alta si no está en ejecución o bloqueado.
            
            /** Busqueda del proceso con mas prioridad
             *  Se guarda el proceso con prioridad mas alta si no está en ejecución o bloqueado (Por E/S o Usuario).
             */ 
            if((p.prioridad <= maxPrioridad) && (!p.enEjecucion) && (!p.bloqueadoPorES) && (!p.bloqueadoPorUsuario)){
                maxPrioridad = p.prioridad;
                procesoConMasPrioridad = p;
            }
            
            /** Envcejecimiento
             * Guardamos el tiempo que lleva esperando para entrar a CPU
             * Nos aseguramos que solo cuente el tiempo si NO esta en ejecucion y NO esta bloqueado
             * Ya que si tiene mucha E/S siempre será prioritario si no hiciesemos esto.
            */
            if(!p.bloqueadoPorES && !p.bloqueadoPorUsuario && !p.enEjecucion){
                p.tiempoEsperando = System.currentTimeMillis() - p.tiempoCuandoSeCreo;
            }
            
            /** ModificarPrioridad SO (Por Envejecimiento)
             * Si un proceso de SO lleva mas de (x) tiempo esperando se le baja la prioridad
             * Se verifica que el proceso no este en ejecucion ni bloqueado
             * Se resetea el tiempo cuando fue creado y se pone en 0 el tiempo esperando.
            */
            if((p.tiempoEsperando > 1000) && (!p.esDeUsuario) && (p.prioridad > 1) && (!p.enEjecucion) && (!p.bloqueadoPorES) && (!p.bloqueadoPorUsuario)){
                p.prioridad--;
                p.tiempoCuandoSeCreo = System.currentTimeMillis();
                p.tiempoEsperando = 0;
            }
            
            /**ModificarPrioridad USUARIO (Por Envejecimiento)
             * Si un proceso de Usuario lleva mas de (x) tiempo esperando se le baja la prioridad
             * Se verifica que el proceso no este en ejecucion ni bloqueado
             * Se resetea el tiempo cuando fue creado y se pone en 0 el tiempo esperando.
            */
            if((p.tiempoEsperando > 1000) && (p.esDeUsuario) && (p.prioridad > 20) && (!p.enEjecucion) && (!p.bloqueadoPorES) && (!p.bloqueadoPorUsuario)){
                p.prioridad--;
                p.tiempoCuandoSeCreo = System.currentTimeMillis();
                p.tiempoEsperando = 0;
            }
            
            /** Verificamos que el tiempo en ejecucion no exceda el intervalo entre E/S
             * Si el proceso esta en ejecucion y el tiempo en CPU es igual o mayor al intervalo en E/S el proceso
             * se bloquea por E/S
             * 
             * Se resetea el tiempo por Auxiliar en CPU y se guarda el momento cuando se bloqueo.
             */
            
            if(p.enEjecucion && (p.tiempoTemporalEnCpuAuxiliar + p.tiempoTemporalEnCpu >= p.intervaloES)){
                p.tiempoTemporalEnCpuAuxiliar = 0;
                this.CPU.dejarCpu();
                p.tiempoCuandoSeBloqueoPorES = System.currentTimeMillis();
                //System.out.println("Se bloquea " + p.ID);
                p.bloqueadoPorES = true;
                p.tiempoQueFaltaParaSerDesbloqueado = p.tiempoEnES;
                p.cuandoSeBloqueo = System.currentTimeMillis();
                if(!p.equals(procesoConMasPrioridad)){
                    if (this.CPU.libre && procesoConMasPrioridad != null){
                        this.CPU.usarCpu(procesoConMasPrioridad);
                    }
                }
            }
            
            /** Si el proceso actual esta en ejecucion y excede el tiempo por proceso establecido por el CPU
             * lo sacamos de CPU, dejandolo libre
             * 
             * Guardamos el tiempo temporal en CPU en tiempo temporal en CPU auxiliar.
             * 
             * Reseteamos el tiempo temporal en CPU
             */
            if (p.enEjecucion && (p.tiempoTemporalEnCpu >= this.CPU.tiempoPorProceso)){
                p.enEjecucion = false;
                p.tiempoTemporalEnCpuAuxiliar = p.tiempoTemporalEnCpu; 
                this.CPU.dejarCpu();
                p.tiempoTemporalEnCpu = 0;
            }

            /** Si el proceso esta bloqueado vamos incrementado el tiempo que lleva bloqueado
             * 
             * Si el proceso ya paso el tiempo que deberia estar bloqueado se desbloquea.
             * 
             * Reseteamos su tiempo temporal en CPU, cuando se bloqueo, tiempo que falta para ser bloqueado y tiempo que lleva bloqueado
             */
            if(p.bloqueadoPorES){
                //System.out.println(p.ID + "Bloqueado");
                long tiempoQueEstuvoBloqueadoPorES = System.currentTimeMillis() - p.tiempoCuandoSeBloqueoPorES;
                if(tiempoQueEstuvoBloqueadoPorES >= p.tiempoEnES){
                    p.bloqueadoPorES = false;
                    p.tiempoTemporalEnCpu = 0;
                    p.cuandoSeBloqueo = 0;
                    p.tiempoQueFaltaParaSerDesbloqueado = p.tiempoEnES;
                    p.tiempoQueLlevaBloqueado = 0;
                }  
            }
            
            /** Si el proceso es el que se ingreso para ser bloqueado lo bloqueamos y liberamos el CPU
             * 
             * Reseteamos el proceso que debe ser bloqueado.
             */
            if(p.ID.equals(procesoParaBloquearID)){
                p.bloqueadoPorUsuario = true;
                if (p.enEjecucion){
                    CPU.dejarCpu();
                }
                procesoParaBloquearID = null;
            }
            
            /** Si el proceso es el que se ingreso para ser desbloqueado lo bloqueamos y liberamos el CPU
             * 
             * Reseteamos el proceso que debe ser desbloqueado.
             */
            if(p.ID.equals(procesoParaDesbloquearID)){
                p.bloqueadoPorUsuario = false;
                p.tiempoQueFaltaParaSerDesbloqueado = 0;
                procesoParaDesbloquearID = null;
            }
            
            /** Si el proceso esta en ejecucion incrementamos el tiempo que lleva ejecutandose.
             * 
             * Tambien incrementamos el tiempo temporal
             */
            if(p.enEjecucion){
                p.tiempoTemporalEnCpu = System.currentTimeMillis() - CPU.tiempoActualCuandoEntraProcesoEnCpu;
                p.tiempoEnCpu = System.currentTimeMillis() - CPU.tiempoActualCuandoEntraProcesoEnCpu + p.tiempoEnCpuAux; 
            }
            
            /** Si el proceso ya cumplio su tiempo en CPU lo eliminamos y liberamos el CPU.
             */
            if(p.tiempoEnCpu >= p.tiempoQueDebeEstarEnCPUparaFinalizar){
                p.enEjecucion = false;
                this.CPU.dejarCpu();
                procesoParaEliminar = p;
                //System.out.println("TIEMPO EN CPU " + p.tiempoEnCpu);
                //System.out.println(p.ID + " A ELIMINAR");
            }
            
            /** Si el proceso esta bloqueado por ES
             *  Actualizamos el tiempo que lleva bloqueado y el tiempo que falta para ser desbloqueado.
             */
            if(p.bloqueadoPorES){
                p.tiempoQueLlevaBloqueado = Math.subtractExact(System.currentTimeMillis(), p.cuandoSeBloqueo);
                p.tiempoQueFaltaParaSerDesbloqueado = Math.subtractExact(p.tiempoEnES, p.tiempoQueLlevaBloqueado);
                //System.out.println(p.ID + " " + p.tiempoQueFaltaParaSerDesbloqueado);
            }
            
            /** Si el proceso esta bloqueado por Usuario
             * Ponemos el valor máximo de tiempo que falta para ser desbloqueado.
             */
            if(p.bloqueadoPorUsuario){
                p.tiempoQueFaltaParaSerDesbloqueado = 2147483600;
                //System.out.println("USER " + p.getIntTiempoQUeFalta());
            }
            
            
            
            //Por prioridad sacar a los procesos de estado bloqueado cuando haya pasado su tiempo.
        }
        
        
            if(procesoConMasPrioridad != null){
                if (this.CPU.libre){
                    this.CPU.usarCpu(procesoConMasPrioridad);
                }
            }
            //Elimina el proceso que haya terminado, por cada for solo se puede eliminar uno.Si se quieren eliminar varios, hacer una lista e ir agregando si cumple la condicion
            //Al final del loop, acá, eliminar los procesos de esa lista.
            if(procesoParaEliminar != null){
                //System.out.println("Se elimina " + procesoParaEliminar.ID);
                eliminarProceso(procesoParaEliminar.ID);
                procesoParaEliminar = null;
            }
            agregarProcesos();
    }
    
    public void eliminarProceso(Integer id){
        mapa.remove(id);
    }
    
    public void modifiarPrioridadProceso(Integer id, int nuevaPrioridad){
        
        try{
            mapa.containsKey(id);
            Proceso p = (Proceso)mapa.get(id);
            if (p.esDeUsuario){
                if (nuevaPrioridad < 20){
                    p.prioridad = 20; 
                }else{
                    p.prioridad = nuevaPrioridad;
                }
            }else{
                p.prioridad = nuevaPrioridad;
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void listaSiguienteEnCPU(){
        ArrayList<Proceso> array2 = new ArrayList<>(mapa.values());
        arrayRetornado.clear();
        arrayRetornado = (ArrayList)array2.clone();
        arrayRetornado.removeIf(proceso -> (proceso.enEjecucion) == true);
        arrayRetornado.removeIf(proceso -> (proceso.bloqueadoPorES) == true);
        arrayRetornado.removeIf(proceso -> (proceso.bloqueadoPorUsuario) == true);
        Collections.sort(arrayRetornado, new Comparator<Proceso>() {
            @Override
            public int compare(Proceso p1, Proceso p2) {
                return p1.prioridad - p2.prioridad;
            }
        });
    }
    
    public void listaSiguienteBloqueado(){
        ArrayList<Proceso> arrayBloq = new ArrayList<>(mapa.values());
        arrayRetornadoBloqueados.clear();
        for(Proceso p : arrayBloq){
            if(!p.enEjecucion &&  (p.bloqueadoPorES || p.bloqueadoPorUsuario)){
                try{
                    Proceso copiaDeP = new Proceso(p.ID, p.prioridad, p.esDeUsuario, p.tiempoQueDebeEstarEnCPUparaFinalizar, p.intervaloES, p.tiempoEnES);
                    copiaDeP.tiempoQueFaltaParaSerDesbloqueado = p.tiempoQueFaltaParaSerDesbloqueado;
                    arrayRetornadoBloqueados.add(copiaDeP);
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }
        Collections.sort(arrayRetornadoBloqueados, new Comparator<Proceso>() {
            @Override
            public int compare(Proceso p11, Proceso p22) {
                return p11.getIntTiempoQUeFalta() < p22.getIntTiempoQUeFalta() ? -1 : p11.getIntTiempoQUeFalta() == p22.getIntTiempoQUeFalta() ? 0 : 1;
            }
        });
    }
}
