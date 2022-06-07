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
    
    public void usarCpu(Proceso p){
        procesoActual = p;
        procesoActual.enEjecucion = true;
        procesoActual.tiempoEnCpu = System.currentTimeMillis();//Cuando inicia
    }
    /*
    Para sacarlo del cpu hay dos opciones, fijaerme en el contenedorProcesos cuando está iterando, 
    si el proceso, pasó x tiempo en cpu.
    
    Sino crear un método acá que tenga que correr a cada rato en un hilo a parte o adentro de un while en una clase principal que corra todo.
    
    El método o la funcionalidad que se encargue de verificar cuando sacarlo, tiene que chequear que el tiempoEnCpu sea igual o mayor al tiempoParaFinalizar.
    Para que cuando lo saque, si pasó más de ese tiempo, también sea eliminado del contenedor de procesos.
    */
}
