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
            //System.out.println(p.ID + " " + p.tiempoEnCpu + " " + p.tiempoTemporalEnCpu);
            // Se recorre quedandose con el proceso de prioridad más alta si no está en ejecución o bloqueado.
            if((p.prioridad <= maxPrioridad) && (!p.enEjecucion) && (!p.bloqueadoPorES) && (!p.bloqueadoPorUsuario)){
                maxPrioridad = p.prioridad;
                procesoConMasPrioridad = p;
            }
            
            //Envcejecimiento
            //Me aseguro que solo cuente el tiempo esperando cuando NO está bloqueado NI en ejecución.
            //Ya que si tiene mucha E/S siempre será prioritario si no hiciesemos esto.
            if(!p.bloqueadoPorES && !p.bloqueadoPorUsuario && !p.enEjecucion){
                //Cuidado con overflow del float.
                //El tiempo esperando es el tiempo que lleva el programa corriendo.
                p.tiempoEsperando = System.currentTimeMillis() - p.tiempoCuandoSeCreo;//el tiempo que está esperando un proceso a su tiempo que realmente lleva esperando.
            }
            
            /*ModificarPrioridad
            no tiene que estar en ejecución o en bloqueado si quiero hacer hilos que hagan diferentes cosas
            ya que pueden pasar cositas si accedo al mismo proceso desde 2 hilos y modifico algo
            Capaz no, pero just in case.
            */
            if((p.tiempoEsperando > 1000) && (p.prioridad > 1) && (!p.enEjecucion) && (!p.bloqueadoPorES) && (!p.bloqueadoPorUsuario)){
                p.prioridad--;//Se disminuye ya que la prioridad más alta es 1;
                p.tiempoCuandoSeCreo = System.currentTimeMillis();
                p.tiempoEsperando = 0;//Se resetea el tiempo esperando asi siempre que pasen x tiempo, entra a este if, sino se deberían hacer muchos if para cada intervalo de tiempo.
            }
            
            //Cuando un proceso ya terminó según su tiempo para finalizar.
            if(p.tiempoEnCpu >= p.tiempoQueDebeEstarEnCPUparaFinalizar){
                p.enEjecucion = false;
                this.CPU.dejarCpu();
                procesoParaEliminar = p;
                //System.out.println("TIEMPO EN CPU " + p.tiempoEnCpu);
                //System.out.println(p.ID + " A ELIMINAR");
            }
            
            //System.out.println(p.ID + "-tiempo t " + p.tiempoTemporalEnCpu + " Tiempo aux: " + p.tiempoTemporalEnCpuAuxiliar + " intervalo: " + p.intervaloES);
            //Entrada y salida
            if(p.enEjecucion && (p.tiempoTemporalEnCpuAuxiliar + p.tiempoTemporalEnCpu >= p.intervaloES)){
                //System.out.println("Te lo bloqueo y sale de cpu " + p.ID);
                p.tiempoTemporalEnCpuAuxiliar = 0;//Importante para que si vuelve a pasar, funcione igual.
                this.CPU.dejarCpu();
                p.tiempoCuandoSeBloqueoPorES = System.currentTimeMillis();
                System.out.println("Se bloquea " + p.ID);
                p.bloqueadoPorES = true;
                if(!p.equals(procesoConMasPrioridad)){
                    if (this.CPU.libre && procesoConMasPrioridad != null){
                        this.CPU.usarCpu(procesoConMasPrioridad);
                    }
                }
            }
            
            //Respeta tiempo cpu
            if (p.enEjecucion && (p.tiempoTemporalEnCpu >= this.CPU.tiempoPorProceso)){
                p.enEjecucion = false;
                //Para evaluar el bloqueo por ES, ya que tiempoTemporal pasaria a ser 0 en las siguientes lineas
                //Entonces si sale de cpu por que estuvo el tiempo max del cpu, me guardo en tiempoTemporalEnCpuAuxiliar, el tiempo que estuvo en cpu
                //Sino nunca entraría a un bloqueo por ES porque el temporalEnCpu siempre será 0 si se salió del cpu por tiempo límite en cpu.
                p.tiempoTemporalEnCpuAuxiliar = p.tiempoTemporalEnCpu; 
                this.CPU.dejarCpu();
                p.tiempoTemporalEnCpu = 0;
            }

            if(p.bloqueadoPorES){
                //System.out.println(p.ID + "Bloqueado");
                //Si el tiempo que estuvo en entrada salida es mayor o igual al que debe estar en ES, lo desbloqueo
                long tiempoQueEstuvoBloqueadoPorES = System.currentTimeMillis() - p.tiempoCuandoSeBloqueoPorES;
                if(tiempoQueEstuvoBloqueadoPorES >= p.tiempoEnES){
                    System.out.println("DESBLOQUEO " + p.ID + " ya que estuvo " + tiempoQueEstuvoBloqueadoPorES);
                    p.bloqueadoPorES = false;
                    p.tiempoTemporalEnCpu = 0;
                }  
            }
            
            
            if(p.ID.equals(procesoParaBloquearID)){
                p.bloqueadoPorUsuario = true;
            }
            
            if(p.ID.equals(procesoParaDesbloquearID)){
                p.bloqueadoPorUsuario = false;
            }
            
            if(p.enEjecucion){
                p.tiempoTemporalEnCpu = System.currentTimeMillis() - CPU.tiempoActualCuandoEntraProcesoEnCpu;
                //Mejorado, cada vez que el proceso sale de cpu, tiempoActualCuandoEntra, es 0, entonces, p.tiempoEnCpuAux, guarda el tiempoEnCpu, en el metodo dejarCpu
                //Para poder llevar bien la cuenta de tiempo.
                p.tiempoEnCpu = System.currentTimeMillis() - CPU.tiempoActualCuandoEntraProcesoEnCpu + p.tiempoEnCpuAux; 
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
                System.out.println("Se elimina " + procesoParaEliminar.ID);
                eliminarProceso(procesoParaEliminar.ID);
                procesoParaEliminar = null;
            }
            agregarProcesos();
    }
    
    public void eliminarProceso(Integer id){
        mapa.remove(id);
    }
    
    public void modifiarPrioridadProceso(Integer id, int nuevaPrioridad){
        if((nuevaPrioridad >= 1) && (nuevaPrioridad <= 99)){
           Proceso p = mapa.get(id);
            p.prioridad = nuevaPrioridad; 
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
        arrayRetornadoBloqueados = (ArrayList)arrayBloq.clone();
        arrayRetornadoBloqueados.removeIf(proceso2 -> (proceso2.enEjecucion) == true);
        //arrayRetornadoBloqueados.removeIf(proceso2 -> (proceso2.bloqueadoPorES) == false);
        //arrayRetornadoBloqueados.removeIf(proceso2 -> (proceso2.bloqueadoPorUsuario) == false);
        Collections.sort(arrayRetornadoBloqueados, new Comparator<Proceso>() {
            @Override
            public int compare(Proceso p11, Proceso p22) {
                return p11.prioridad - p22.prioridad;
            }
        });
    }
}
