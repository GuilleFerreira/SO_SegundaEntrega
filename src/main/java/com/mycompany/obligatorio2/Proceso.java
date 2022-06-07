/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.obligatorio2;

/**
 *
 * @author inazu
 */
public class Proceso {
    public long tiempoEnCpu;
 
    /*
    Empeiza cuando se crea el proceso, cada vez que se recorre la lista de procesos,
    este se actualiza.
    */
    public long tiempoEsperando;
    public long tiempoQueDebeEstarEnCPUparaFinalizar;
    
    public int prioridad;
    public Integer ID;
    public boolean bloqueadoPorES;
    public boolean bloqueadoPorUsuario;
    public boolean enEjecucion;
    
    public Proceso(Integer id, int prioridad, long tiempoParaFinalizar) throws Exception{
        if(prioridad < 1 || prioridad > 99){
            throw new Exception("Prioridad incorrecta");
        }else{
            this.prioridad = prioridad;
            this.tiempoEsperando = 0;
            this.tiempoQueDebeEstarEnCPUparaFinalizar = tiempoParaFinalizar;
        }
    }
    
    
    
}
