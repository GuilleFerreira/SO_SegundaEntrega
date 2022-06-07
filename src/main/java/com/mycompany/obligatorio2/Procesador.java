/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.obligatorio2;

/**
 *
 * @author inazu
 */
public class Procesador {
    public Proceso procesoActual;
    public long tiempoCorriendoUnProceso;
    public long tiempoActualCuandoEntraProcesoEnCpu;
    public long tiempoActualCuandoDejaProcesoACpu;
    
    public void usarCpu(Proceso p){
        procesoActual = p;
        procesoActual.enEjecucion = true;
        this.tiempoActualCuandoEntraProcesoEnCpu = System.currentTimeMillis();
    }
    
    public void dejarCpu(){
        this.tiempoActualCuandoDejaProcesoACpu = System.currentTimeMillis();
        procesoActual.tiempoEnCpu += this.tiempoActualCuandoDejaProcesoACpu - this.tiempoActualCuandoEntraProcesoEnCpu;//El tiempo que estuvo el proceso en cpu, *se usa += ya que sino no se guardaria el tiempo anterior que estuvo en cpu.*
        //Se setean a 0 los tiempos de la clase Procesador
        this.tiempoActualCuandoDejaProcesoACpu = 0;
        this.tiempoActualCuandoEntraProcesoEnCpu = 0;
    }
    /*
    Para sacarlo del cpu hay dos opciones, fijaerme en el contenedorProcesos cuando está iterando, 
    si el proceso, pasó x tiempo en cpu.
    
    Sino crear un método acá que tenga que correr a cada rato en un hilo a parte o adentro de un while en una clase principal que corra todo.
    
    El método o la funcionalidad que se encargue de verificar cuando sacarlo, tiene que chequear que el tiempoEnCpu sea igual o mayor al tiempoParaFinalizar.
    Para que cuando lo saque, si pasó más de ese tiempo, también sea eliminado del contenedor de procesos.
    */
}
