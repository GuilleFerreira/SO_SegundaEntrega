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
    public HashMap<Integer, Proceso> mapa = new HashMap();
    public Integer contadorParaId = 0;
    
    public Proceso buscarProceso(Integer id){
        return mapa.get(id);
    }
    
    public void agregarProceso(int prioridad, long tiempoParaFinalizar){
        try{
            Proceso procesoParaAgregar = new Proceso(contadorParaId, prioridad, tiempoParaFinalizar);
            procesoParaAgregar.tiempoCuandoSeCreo = System.currentTimeMillis();
            mapa.put(procesoParaAgregar.ID, procesoParaAgregar);
            contadorParaId++;// Hace que cada proceso tenga un id único.
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void iterarSobreProcesos(){
        Proceso procesoConMasPrioridad;
        int maxPrioridad = 99;
        Proceso procesoParaEliminar = null;
        for(Proceso p : mapa.values()){
            // Se recorre quedandose con el proceso de prioridad más alta si no está en ejecución o bloqueado.
            if((p.prioridad <= maxPrioridad) && (!p.enEjecucion) && (!p.bloqueadoPorES) && (!p.bloqueadoPorUsuario)){
                maxPrioridad = p.prioridad;
                procesoConMasPrioridad = p;
            }
            
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
                procesoParaEliminar = p;
            }
            
            //Por prioridad sacar a los procesos de estado bloqueado cuando haya pasado su tiempo.
        }
        //Elimina el proceso que haya terminado, por cada for solo se puede eliminar uno.Si se quieren eliminar varios, hacer una lista e ir agregando si cumple la condicion
        //Al final del loop, acá
        eliminarProceso(procesoParaEliminar.ID);
        //De alguna forma usar mandar al proceso a usar cpu. EN ESTA LINEA, hacer al procesador Singleton sería buena idea, sino hacer a Procesador static, o usar una clase ContenedorPrograma singleton, que contega al procesador y a este contenedor, y ahí referirme al procesador y usarCpu.
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
