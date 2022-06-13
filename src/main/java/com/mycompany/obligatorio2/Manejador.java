/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.obligatorio2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author guille
 */
public class Manejador extends javax.swing.JFrame {

    SistemaOperativo SO;
    DefaultListModel ListaAgregar = new DefaultListModel();
    String[] columnColaCPU = {"ID","Prioridad","Tipo"};
    String[] columnColaBloqueados = {"ID","Prioridad","Tipo"};
    DefaultTableModel ModeloTablaColaCPU = new DefaultTableModel(columnColaCPU,1);
    DefaultTableModel ModeloTablaColaBloqueados = new DefaultTableModel(columnColaBloqueados,1);
    public final static int INTERVAL = 10;
    public final static int INTERVAL2 = 1;
    public long tiempo;
    
    /**
     * Creates new form Manejador
     * @param tiempo
     */
    public Manejador(long tiempo) {
        this.tiempo = tiempo;
        initComponents();
        SO = new SistemaOperativo(tiempo);
        timerIniciar.start();
        timer.start();
        timerColaCPU.start();
        timerColaBloqueados.start();
        ListaAgregar.clear();
        jList1.setModel(ListaAgregar);
        TablaEnColaCPU.setModel(ModeloTablaColaCPU);
        TablaBloqueados.setModel(ModeloTablaColaBloqueados);
    }

    Timer timerColaCPU = new Timer(INTERVAL, new ActionListener() {
    public void actionPerformed(ActionEvent evt) {
        SO.SiguienteEnCPU();
        ArrayList<Proceso> cola = SO.Cola();
        ModeloTablaColaCPU.setRowCount(0);
        TablaEnColaCPU.setModel(ModeloTablaColaCPU);
        for (Proceso proc : cola){
            String[] data = {proc.ID.toString(),proc.ID.toString(),proc.ID.toString()};
            ModeloTablaColaCPU.insertRow(cola.indexOf(proc),data);
            TablaEnColaCPU.setModel(ModeloTablaColaCPU);
        }
        }    
    });
    
    Timer timerColaBloqueados = new Timer(INTERVAL, new ActionListener() {
    public void actionPerformed(ActionEvent evt) {
        SO.SiguienteEnCPU();
        ArrayList<Proceso> cola2 = SO.Cola();
        ModeloTablaColaBloqueados.setRowCount(0);
        TablaBloqueados.setModel(ModeloTablaColaBloqueados);
        for (Proceso proc2 : cola2){
            String[] data2 = {proc2.ID.toString(),proc2.ID.toString(),proc2.ID.toString()};
            ModeloTablaColaBloqueados.insertRow(cola2.indexOf(proc2),data2);
            TablaBloqueados.setModel(ModeloTablaColaBloqueados);
        }
        }    
    });
    
    Timer timer = new Timer(INTERVAL, new ActionListener() {
    public void actionPerformed(ActionEvent evt) {
        String[] info = SO.enCPU();
        PAid.setText(info[0]);
        PAprioridad.setText(info[1]);
        PAtipo.setText(info[2]);
        PAtiempoencpu.setText(info[3]);
        PAintervalo.setText(info[4]);
        PAtiempoES.setText(info[5]);
        String p = info[6];
        int porcentaje = Integer.parseInt(p);
        //System.out.println("porcentaje " + porcentaje);
        jProgressBar1.setValue(porcentaje);
        }    
    });
    
    Timer timerIniciar = new Timer(INTERVAL2, new ActionListener() {
    public void actionPerformed(ActionEvent evt) {
        SO.Iniciar();
        }    
    });
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelVerde = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        BotonCargarProcesos = new javax.swing.JButton();
        PidProceso = new javax.swing.JTextField();
        BotonCrearProceso = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        Texto_ID = new javax.swing.JLabel();
        PPrioridad = new javax.swing.JComboBox<>();
        Texto_Prioridad = new javax.swing.JLabel();
        Texto_TipoProceso = new javax.swing.JLabel();
        PTipoProceso = new javax.swing.JComboBox<>();
        Texto_TiempoDeEjecucion = new javax.swing.JLabel();
        PTFinalizar = new javax.swing.JTextField();
        Texto_IntervaloES = new javax.swing.JLabel();
        PIntervaloES = new javax.swing.JTextField();
        Texto_TiempoES = new javax.swing.JLabel();
        PTiempoES = new javax.swing.JTextField();
        PIntervaloES1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        enCPU2 = new javax.swing.JLabel();
        PAid = new javax.swing.JLabel();
        PAprioridad = new javax.swing.JLabel();
        enCPU3 = new javax.swing.JLabel();
        PAtipo = new javax.swing.JLabel();
        enCPU4 = new javax.swing.JLabel();
        PAtiempoencpu = new javax.swing.JLabel();
        enCPU5 = new javax.swing.JLabel();
        PAintervalo = new javax.swing.JLabel();
        enCPU6 = new javax.swing.JLabel();
        PAtiempoES = new javax.swing.JLabel();
        enCPU7 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaBloqueados = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        TablaEnColaCPU = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanelVerde.setBackground(new java.awt.Color(3, 68, 46));
        PanelVerde.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("HP Simplified Hans", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Creador de Procesos");
        PanelVerde.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, -1, -1));

        jList1.setBackground(new java.awt.Color(3, 100, 46));
        jList1.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jList1.setForeground(new java.awt.Color(255, 255, 255));
        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        PanelVerde.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 330, 250, -1));

        BotonCargarProcesos.setBackground(new java.awt.Color(3, 100, 46));
        BotonCargarProcesos.setFont(new java.awt.Font("HP Simplified Hans", 0, 12)); // NOI18N
        BotonCargarProcesos.setForeground(new java.awt.Color(255, 255, 255));
        BotonCargarProcesos.setText("Cargar Procesos");
        BotonCargarProcesos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BotonCargarProcesos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonCargarProcesosActionPerformed(evt);
            }
        });
        PanelVerde.add(BotonCargarProcesos, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 490, 120, 30));

        PidProceso.setBackground(new java.awt.Color(3, 100, 46));
        PidProceso.setForeground(new java.awt.Color(255, 255, 255));
        PidProceso.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        PidProceso.setText("1");
        PidProceso.setBorder(null);
        PanelVerde.add(PidProceso, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 110, 30));

        BotonCrearProceso.setBackground(new java.awt.Color(3, 100, 46));
        BotonCrearProceso.setFont(new java.awt.Font("HP Simplified Hans", 0, 12)); // NOI18N
        BotonCrearProceso.setForeground(new java.awt.Color(255, 255, 255));
        BotonCrearProceso.setText("CREAR");
        BotonCrearProceso.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BotonCrearProceso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonCrearProcesoActionPerformed(evt);
            }
        });
        PanelVerde.add(BotonCrearProceso, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 270, 130, 30));

        jLabel3.setFont(new java.awt.Font("HP Simplified Hans", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Procesos Creados:");
        PanelVerde.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 310, -1, -1));

        Texto_ID.setFont(new java.awt.Font("HP Simplified Hans", 0, 14)); // NOI18N
        Texto_ID.setForeground(new java.awt.Color(255, 255, 255));
        Texto_ID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Texto_ID.setText("ID:");
        Texto_ID.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        PanelVerde.add(Texto_ID, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 110, 30));

        PPrioridad.setBackground(new java.awt.Color(3, 100, 46));
        PPrioridad.setForeground(new java.awt.Color(255, 255, 255));
        PPrioridad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99" }));
        PanelVerde.add(PPrioridad, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 80, 110, 30));

        Texto_Prioridad.setFont(new java.awt.Font("HP Simplified Hans", 0, 14)); // NOI18N
        Texto_Prioridad.setForeground(new java.awt.Color(255, 255, 255));
        Texto_Prioridad.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Texto_Prioridad.setText("Prioridad:");
        PanelVerde.add(Texto_Prioridad, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 110, 30));

        Texto_TipoProceso.setFont(new java.awt.Font("HP Simplified Hans", 0, 14)); // NOI18N
        Texto_TipoProceso.setForeground(new java.awt.Color(255, 255, 255));
        Texto_TipoProceso.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Texto_TipoProceso.setText("Tipo de proceso:");
        PanelVerde.add(Texto_TipoProceso, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, 110, 30));

        PTipoProceso.setBackground(new java.awt.Color(3, 100, 46));
        PTipoProceso.setForeground(new java.awt.Color(255, 255, 255));
        PTipoProceso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Usuario", "SO" }));
        PanelVerde.add(PTipoProceso, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, 110, 30));

        Texto_TiempoDeEjecucion.setFont(new java.awt.Font("HP Simplified Hans", 0, 14)); // NOI18N
        Texto_TiempoDeEjecucion.setForeground(new java.awt.Color(255, 255, 255));
        Texto_TiempoDeEjecucion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Texto_TiempoDeEjecucion.setText("Tiempo de ejec:");
        PanelVerde.add(Texto_TiempoDeEjecucion, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 120, 110, 30));

        PTFinalizar.setBackground(new java.awt.Color(3, 100, 46));
        PTFinalizar.setForeground(new java.awt.Color(255, 255, 255));
        PTFinalizar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        PTFinalizar.setText("10000");
        PTFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PTFinalizarActionPerformed(evt);
            }
        });
        PanelVerde.add(PTFinalizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 150, 110, 30));

        Texto_IntervaloES.setFont(new java.awt.Font("HP Simplified Hans", 0, 14)); // NOI18N
        Texto_IntervaloES.setForeground(new java.awt.Color(255, 255, 255));
        Texto_IntervaloES.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Texto_IntervaloES.setText("Intervalo E/S:");
        PanelVerde.add(Texto_IntervaloES, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, 110, 30));

        PIntervaloES.setBackground(new java.awt.Color(153, 153, 153));
        PIntervaloES.setForeground(new java.awt.Color(0, 0, 0));
        PIntervaloES.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        PIntervaloES.setText("ID");
        PIntervaloES.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        PanelVerde.add(PIntervaloES, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 540, 100, 20));

        Texto_TiempoES.setFont(new java.awt.Font("HP Simplified Hans", 0, 14)); // NOI18N
        Texto_TiempoES.setForeground(new java.awt.Color(255, 255, 255));
        Texto_TiempoES.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Texto_TiempoES.setText("Tiempo E/S:");
        PanelVerde.add(Texto_TiempoES, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 190, 110, 30));

        PTiempoES.setBackground(new java.awt.Color(3, 100, 46));
        PTiempoES.setForeground(new java.awt.Color(255, 255, 255));
        PTiempoES.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        PTiempoES.setText("1000");
        PanelVerde.add(PTiempoES, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 220, 110, 30));

        PIntervaloES1.setBackground(new java.awt.Color(3, 100, 46));
        PIntervaloES1.setForeground(new java.awt.Color(255, 255, 255));
        PIntervaloES1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        PIntervaloES1.setText("8000");
        PanelVerde.add(PIntervaloES1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, 110, 30));

        jButton1.setBackground(new java.awt.Color(255, 51, 51));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Bloquear");
        jButton1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PanelVerde.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 540, 110, 20));

        jButton2.setBackground(new java.awt.Color(255, 51, 51));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Desbloquear");
        jButton2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PanelVerde.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 540, 100, 20));

        getContentPane().add(PanelVerde, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 0, 350, 580));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        enCPU2.setFont(new java.awt.Font("HP Simplified Hans", 1, 24)); // NOI18N
        enCPU2.setForeground(new java.awt.Color(0, 0, 0));
        enCPU2.setText("PROCESO ACTUAL:");
        jPanel3.add(enCPU2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        PAid.setFont(new java.awt.Font("HP Simplified Hans Light", 0, 24)); // NOI18N
        PAid.setForeground(new java.awt.Color(0, 0, 0));
        PAid.setText("jLabel1");
        jPanel3.add(PAid, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, 270, 30));

        PAprioridad.setFont(new java.awt.Font("HP Simplified Hans Light", 0, 18)); // NOI18N
        PAprioridad.setForeground(new java.awt.Color(0, 0, 0));
        PAprioridad.setText("jLabel1");
        jPanel3.add(PAprioridad, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 230, 30));

        enCPU3.setFont(new java.awt.Font("HP Simplified Hans", 0, 18)); // NOI18N
        enCPU3.setForeground(new java.awt.Color(0, 0, 0));
        enCPU3.setText("Prioridad:");
        jPanel3.add(enCPU3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, 30));

        PAtipo.setFont(new java.awt.Font("HP Simplified Hans Light", 0, 18)); // NOI18N
        PAtipo.setForeground(new java.awt.Color(0, 0, 0));
        PAtipo.setText("jLabel1");
        jPanel3.add(PAtipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 210, 30));

        enCPU4.setFont(new java.awt.Font("HP Simplified Hans", 0, 18)); // NOI18N
        enCPU4.setForeground(new java.awt.Color(0, 0, 0));
        enCPU4.setText("Tipo de proceso:");
        jPanel3.add(enCPU4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, 30));

        PAtiempoencpu.setFont(new java.awt.Font("HP Simplified Hans Light", 0, 18)); // NOI18N
        PAtiempoencpu.setForeground(new java.awt.Color(0, 0, 0));
        PAtiempoencpu.setText("jLabel1");
        jPanel3.add(PAtiempoencpu, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, 240, 30));

        enCPU5.setFont(new java.awt.Font("HP Simplified Hans", 0, 18)); // NOI18N
        enCPU5.setForeground(new java.awt.Color(0, 0, 0));
        enCPU5.setText("Tiempo en CPU:");
        jPanel3.add(enCPU5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, 30));

        PAintervalo.setFont(new java.awt.Font("HP Simplified Hans Light", 0, 18)); // NOI18N
        PAintervalo.setForeground(new java.awt.Color(0, 0, 0));
        PAintervalo.setText("jLabel1");
        jPanel3.add(PAintervalo, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 140, 230, 30));

        enCPU6.setFont(new java.awt.Font("HP Simplified Hans", 0, 18)); // NOI18N
        enCPU6.setForeground(new java.awt.Color(0, 0, 0));
        enCPU6.setText("Intervalo E/S:");
        jPanel3.add(enCPU6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, 30));

        PAtiempoES.setFont(new java.awt.Font("HP Simplified Hans Light", 0, 18)); // NOI18N
        PAtiempoES.setForeground(new java.awt.Color(0, 0, 0));
        PAtiempoES.setText("jLabel1");
        jPanel3.add(PAtiempoES, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, 240, 30));

        enCPU7.setFont(new java.awt.Font("HP Simplified Hans", 0, 18)); // NOI18N
        enCPU7.setForeground(new java.awt.Color(0, 0, 0));
        enCPU7.setText("Tiempo E/S:");
        jPanel3.add(enCPU7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, 30));

        jProgressBar1.setBackground(new java.awt.Color(153, 153, 153));
        jProgressBar1.setForeground(new java.awt.Color(0, 102, 0));
        jPanel3.add(jProgressBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 290, 30));

        TablaBloqueados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Prioridad", "Tipo Bloqueo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(TablaBloqueados);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 290, 300, 260));

        TablaEnColaCPU.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(TablaEnColaCPU);

        jPanel3.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 300, 260));
        jPanel3.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 620, -1));

        jLabel1.setFont(new java.awt.Font("HP Simplified Hans", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Procesos bloqueados");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 260, -1, -1));

        jLabel4.setFont(new java.awt.Font("HP Simplified Hans", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Procesos en cola");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 260, -1, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 680, 580));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BotonCrearProcesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonCrearProcesoActionPerformed
        SO.crearProceso(PidProceso.getText(), PPrioridad.getSelectedItem().toString(), PTipoProceso.getSelectedItem().toString(), PTFinalizar.getText(), PIntervaloES1.getText(), PTiempoES.getText());
        ListaAgregar.addElement(PidProceso.getText());
        jList1.setModel(ListaAgregar);
    }//GEN-LAST:event_BotonCrearProcesoActionPerformed

    private void BotonCargarProcesosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonCargarProcesosActionPerformed
        SO.cargarProcesos();
        timer.start();
        ListaAgregar.clear();
        jList1.setModel(ListaAgregar);
    }//GEN-LAST:event_BotonCargarProcesosActionPerformed

    private void PTFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PTFinalizarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PTFinalizarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Manejador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Manejador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Manejador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Manejador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Manejador(1).setVisible(true);
            }
        });
    }
    
    public void Setup(){
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotonCargarProcesos;
    private javax.swing.JButton BotonCrearProceso;
    private javax.swing.JLabel PAid;
    private javax.swing.JLabel PAintervalo;
    private javax.swing.JLabel PAprioridad;
    private javax.swing.JLabel PAtiempoES;
    private javax.swing.JLabel PAtiempoencpu;
    private javax.swing.JLabel PAtipo;
    private javax.swing.JTextField PIntervaloES;
    private javax.swing.JTextField PIntervaloES1;
    private javax.swing.JComboBox<String> PPrioridad;
    private javax.swing.JTextField PTFinalizar;
    private javax.swing.JTextField PTiempoES;
    private javax.swing.JComboBox<String> PTipoProceso;
    private javax.swing.JPanel PanelVerde;
    private javax.swing.JTextField PidProceso;
    private javax.swing.JTable TablaBloqueados;
    private javax.swing.JTable TablaEnColaCPU;
    private javax.swing.JLabel Texto_ID;
    private javax.swing.JLabel Texto_IntervaloES;
    private javax.swing.JLabel Texto_Prioridad;
    private javax.swing.JLabel Texto_TiempoDeEjecucion;
    private javax.swing.JLabel Texto_TiempoES;
    private javax.swing.JLabel Texto_TipoProceso;
    private javax.swing.JLabel enCPU2;
    private javax.swing.JLabel enCPU3;
    private javax.swing.JLabel enCPU4;
    private javax.swing.JLabel enCPU5;
    private javax.swing.JLabel enCPU6;
    private javax.swing.JLabel enCPU7;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
