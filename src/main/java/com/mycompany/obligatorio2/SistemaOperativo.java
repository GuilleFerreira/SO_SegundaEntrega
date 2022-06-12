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
    
    public SistemaOperativo(long tiempoCPU){
        this.procesador = new Procesador(tiempoCPU);
        this.contenedor = new ContenedorProcesosHashMap(procesador);
    }
    
    
    //boton crear Proceso
    public void crearProceso(String id, String prioridad, String DeUsuario, String tiempoParaFinalizar, String intervaloES, String tiempoEnES){
        Integer ID = Integer.parseInt(id);
        int prioridadInt = Integer.parseInt(prioridad);
        boolean esDeUsuario = (DeUsuario.compareTo("Usuario") == 0);
        long tiempoParaFinalizarLong = Long.parseLong(tiempoParaFinalizar);
        long intervaloESlong = Long.parseLong(intervaloES);
        long tiempoEnESlong = Long.parseLong(tiempoEnES);
        try{
            Proceso procesoNuevo = new Proceso(ID, prioridadInt, esDeUsuario, tiempoParaFinalizarLong, intervaloESlong, tiempoEnESlong);
            this.contenedor.procesosParaInsertar.add(procesoNuevo);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
    }
    
    //boton cargar Procesos
    public void cargarProcesos(){
        contenedor.cargarProcesos = true;
    }
    
    //boton bloquear
    public void bloquearProceso(String id){
        Integer ID = Integer.parseInt(id);
        contenedor.procesoParaBloquearID = ID;
    }
    
    //boton desbloquear
    public void desbloquearProceso(String id){
        Integer ID = Integer.parseInt(id);
        contenedor.procesoParaDesbloquearID = ID;
    }
    
    public String[] enCPU(){
        String InfoProceso[] = new String[7];
        if (procesador.procesoActual != null){
            String procesoID = procesador.procesoActual.ID.toString();
            String procesoPrioridad = procesador.procesoActual.prioridad + "";
            String procesoSOoUser = "SO";
            if (procesador.procesoActual.esDeUsuario == true){
                procesoSOoUser = "Usuario";
            }
            long porcentaje = (long)((float) procesador.procesoActual.tiempoEnCpu / procesador.procesoActual.tiempoQueDebeEstarEnCPUparaFinalizar * 100);
            String procesoTiempoEnCPU = String.valueOf(procesador.procesoActual.tiempoEnCpu) + "/" + String.valueOf(procesador.procesoActual.tiempoQueDebeEstarEnCPUparaFinalizar);
            String procesoIntervaloES = String.valueOf(procesador.procesoActual.intervaloES);
            String procesoTiempoES = String.valueOf(procesador.procesoActual.tiempoEnES);
            String procesoPorcentaje = porcentaje + "";
            
            InfoProceso[0] = procesoID;
            InfoProceso[1] = procesoPrioridad;
            InfoProceso[2] = procesoSOoUser;
            InfoProceso[3] = procesoTiempoEnCPU;
            InfoProceso[4] = procesoIntervaloES;
            InfoProceso[5] = procesoTiempoES;
            InfoProceso[6] = procesoPorcentaje;
            return InfoProceso;
        }else{
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
    
    
    //PORCENTAJE DE TIEMPO, ESTO SERIA PARA LA GUI EL PORCENTAJE
    
    /*
    public long porcentajeProcesoEnCPU(){
        long porcentaje;
        long division = procesador.procesoActual.tiempoEnCpu / procesador.procesoActual.tiempoQueDebeEstarEnCPUparaFinalizar;
        porcentaje = division;
        return porcentaje;
    }
    */
    public void Iniciar(){
        this.contenedor.iterarSobreProcesos();
    }
}
