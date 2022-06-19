/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.obligatorio2;

import java.util.ArrayList;

/**
 *
 * @author inazu
 */
public class SistemaOperativo {

    public Procesador procesador;
    public ContenedorProcesosHashMap contenedor;

    public SistemaOperativo(long tiempoCPU) {
        this.procesador = new Procesador(tiempoCPU);
        this.contenedor = new ContenedorProcesosHashMap(procesador);
    }

    //boton crear Proceso
    public void crearProceso(String id, String prioridad, String DeUsuario, String tiempoParaFinalizar, String intervaloES, String tiempoEnES) throws Exception{
        Integer ID = Integer.parseInt(id);
        int prioridadInt = Integer.parseInt(prioridad);
        boolean esDeUsuario = (DeUsuario.compareTo("Usuario") == 0);
        long tiempoParaFinalizarLong = Long.parseLong(tiempoParaFinalizar);
        long intervaloESlong = Long.parseLong(intervaloES);
        long tiempoEnESlong = Long.parseLong(tiempoEnES);
        if (contenedor.mapa.containsKey(ID)){
            throw new Exception("ID ya existente");
        }
        try {
            if (esDeUsuario) {
                if (prioridadInt < 20) {
                    prioridadInt = 20;
                } 
            }    
            Proceso procesoNuevo = new Proceso(ID, prioridadInt, esDeUsuario, tiempoParaFinalizarLong, intervaloESlong, tiempoEnESlong);
            this.contenedor.procesosParaInsertar.add(procesoNuevo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    //boton cargar Procesos
    public void cargarProcesos() {
        contenedor.cargarProcesos = true;
    }

    //boton bloquear
    public void bloquearProceso(Integer ID) throws Exception{
        if (!contenedor.mapa.containsKey(ID)){
            throw new Exception("ID No existente");
        }else{
            this.contenedor.procesoParaBloquearID = ID;
        }
    }

    //boton desbloquear
    public void desbloquearProceso(Integer ID) throws Exception{
        if (!contenedor.mapa.containsKey(ID)){
            throw new Exception("ID No existente");
        }else{
            this.contenedor.procesoParaDesbloquearID = ID;
        }
    }

    public String[] enCPU() {
        String InfoProceso[] = new String[7];
        if (procesador.procesoActual != null) {
            long porcentaje = (long) ((float) procesador.procesoActual.tiempoEnCpu / procesador.procesoActual.tiempoQueDebeEstarEnCPUparaFinalizar * 100);
            String procesoTiempoEnCPU = String.valueOf(procesador.procesoActual.tiempoEnCpu) + "/" + String.valueOf(procesador.procesoActual.tiempoQueDebeEstarEnCPUparaFinalizar);
            String procesoPorcentaje = porcentaje + "";

            InfoProceso[0] = procesador.procesoActual.getID();
            InfoProceso[1] = procesador.procesoActual.getPrioridad();
            InfoProceso[2] = procesador.procesoActual.getEsDeUsuario();
            InfoProceso[3] = procesoTiempoEnCPU;
            InfoProceso[4] = procesador.procesoActual.getIntervaloES();
            InfoProceso[5] = procesador.procesoActual.getTiempoES();
            InfoProceso[6] = procesoPorcentaje;
            return InfoProceso;
        } else {
            InfoProceso[0] = "No hay procesos en CPU";
            InfoProceso[1] = "No hay procesos en CPU";
            InfoProceso[2] = "No hay procesos en CPU";
            InfoProceso[3] = "No hay procesos en CPU";
            InfoProceso[4] = "No hay procesos en CPU";
            InfoProceso[5] = "No hay procesos en CPU";
            InfoProceso[6] = "0";
            return InfoProceso;
        }
    }

    public void Iniciar() {
        this.contenedor.iterarSobreProcesos();
    }

    public void SiguienteEnCPU() {
        this.contenedor.listaSiguienteEnCPU();
    }

    public void SiguienteBloqueado() {
        this.contenedor.listaSiguienteBloqueado();
    }

    public ArrayList<Proceso> Cola() {
        return this.contenedor.arrayRetornado;
    }

    public ArrayList<Proceso> ColaBloqueados() {
        return this.contenedor.arrayRetornadoBloqueados;
    }

    public void modifiarPrioridadProceso(Integer ID, int prioridad) throws Exception{
        if (!contenedor.mapa.containsKey(ID)){
            throw new Exception("ID No existente");
        }else{
            this.contenedor.modifiarPrioridadProceso(ID, prioridad);
        }
    }
}
