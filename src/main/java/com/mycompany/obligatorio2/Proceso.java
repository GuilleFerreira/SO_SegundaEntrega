/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.obligatorio2;

import java.util.Objects;

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
    public boolean esDeUsuario;
    public long intervaloES;
    public long tiempoEnES;
    
    public boolean bloqueadoPorES;
    public boolean bloqueadoPorUsuario;
    public boolean enEjecucion;
    public long tiempoCuandoSeCreo;
    
    public long tiempoTemporalEnCpu;
    public long tiempoCuandoSeBloqueoPorES;
    
    
    
    public Proceso(Integer id, int prioridad, boolean esDeUsuario, long tiempoParaFinalizar, long intervaloES, long tiempoEnES) throws Exception{
        if(prioridad < 1 || prioridad > 99){
            throw new Exception("Prioridad incorrecta");
        }else{
            this.ID = id;
            this.prioridad = prioridad;
            this.tiempoEsperando = 0;
            this.tiempoQueDebeEstarEnCPUparaFinalizar = tiempoParaFinalizar;
            this.tiempoEnES = tiempoEnES;
            this.intervaloES = intervaloES;
            this.esDeUsuario = esDeUsuario;
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Proceso other = (Proceso) obj;
        return Objects.equals(this.ID, other.ID);
    }
    
    
    
}
