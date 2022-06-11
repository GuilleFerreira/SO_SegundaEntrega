/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.obligatorio2;

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
            if((p.tiempoEsperando > 10) && (p.prioridad > 1) && (!p.enEjecucion) && (!p.bloqueadoPorES) && (!p.bloqueadoPorUsuario)){
                p.prioridad--;//Se disminuye ya que la prioridad más alta es 1;
                p.tiempoEsperando = 0;//Se resetea el tiempo esperando asi siempre que pasen x tiempo, entra a este if, sino se deberían hacer muchos if para cada intervalo de tiempo.
            }
            
            //Cuando un proceso ya terminó según su tiempo para finalizar.
            if(p.tiempoEnCpu >= p.tiempoQueDebeEstarEnCPUparaFinalizar){
                p.enEjecucion = false;
                this.CPU.dejarCpu();
                procesoParaEliminar = p;
                System.out.println("TIEMPO EN CPU " + p.tiempoEnCpu);
                System.out.println(p.ID + " A ELIMINAR");
            }
            
            System.out.println(p.ID + "-tiempo t " + p.tiempoTemporalEnCpu + " intervalo: " + p.intervaloES);
            //Entrada y salida
            if(p.enEjecucion && (p.tiempoTemporalEnCpu >= p.intervaloES)){
                System.out.println("Te lo bloqueo y sale de cpu " + p.ID);
                p.tiempoEnCpu += p.tiempoTemporalEnCpu;
                if(procesoConMasPrioridad != null){
                    System.out.println(procesoConMasPrioridad.ID + " PRIORITARIO");
                }
                this.CPU.dejarCpu();
                p.tiempoCuandoSeBloqueoPorES = System.currentTimeMillis();
                p.bloqueadoPorES = true;
                if(!p.equals(procesoConMasPrioridad)){
                    if (this.CPU.libre && procesoConMasPrioridad != null){
                        this.CPU.usarCpu(procesoConMasPrioridad);
                    }
                }
            }
            
            //Respeta tiempo cpu
            if (p.enEjecucion && (p.tiempoTemporalEnCpu >= this.CPU.tiempoPorProceso)){
                p.tiempoEnCpu += p.tiempoTemporalEnCpu;
                p.enEjecucion = false;
                this.CPU.dejarCpu();
                p.tiempoTemporalEnCpu = 0;
            }

            if(p.bloqueadoPorES){
                System.out.println(p.ID + "Bloqueado");
                //Si el tiempo que estuvo en entrada salida es mayor o igual al que debe estar en ES, lo desbloqueo
                long tiempoQueEstuvoBloqueadoPorES = System.currentTimeMillis() - p.tiempoCuandoSeBloqueoPorES;
                if(tiempoQueEstuvoBloqueadoPorES >= p.tiempoEnES){
                    System.out.println("DESBLOQUEO " + p.ID + " ya que estuvo " + tiempoQueEstuvoBloqueadoPorES);
                    p.bloqueadoPorES = false;
                    p.tiempoTemporalEnCpu = 0;
                }  
            }
            
            
            if(p.ID == procesoParaBloquearID){
                p.bloqueadoPorUsuario = true;
            }
            
            if(p.ID == procesoParaDesbloquearID){
                p.bloqueadoPorUsuario = false;
            }
            
            if(p.enEjecucion){
                p.tiempoTemporalEnCpu = System.currentTimeMillis() - CPU.tiempoActualCuandoEntraProcesoEnCpu;
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
}
