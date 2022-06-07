/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.obligatorio2;

/**
 *
 * @author inazu
 */
public class SistemaOperativo {
    public Procesador procesador;
    public ContenedorProcesosHashMap contenedor;
    
    public SistemaOperativo(){
        this.procesador = new Procesador();
        this.contenedor = new ContenedorProcesosHashMap();
    }
    
    public void agregarProceso(String prioridad, String tiempoParaFinalizar){
        int prioridadInt = Integer.parseInt(prioridad);
        long tiempoParaFinalizarLong = Long.parseLong(tiempoParaFinalizar);
        this.contenedor.agregarProceso(prioridadInt, tiempoParaFinalizarLong);
    }
}
