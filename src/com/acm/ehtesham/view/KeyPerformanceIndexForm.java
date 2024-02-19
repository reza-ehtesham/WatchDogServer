package com.acm.ehtesham.view;

import com.acm.ehtesham.controller.KPIFacadeImpl;
import com.acm.ehtesham.entity.KPIFacade;
import com.acm.ehtesham.entity.KeyPerfomanceIndexEntity;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by ehtesham on 1/23/2017.
 */
public class KeyPerformanceIndexForm extends JFrame implements IDesign {
    JLabel formTitle = new JLabel("Enter KPI (Key Performance Index):");
    JLabel totalMemoryLabel = new JLabel("Total-Memory:");
    JTextField totalMemoryText = new JTextField(15);
    JLabel freeMemoryLabel = new JLabel("Free-Memory:");
    JTextField freeMemoryText = new JTextField(15);
    JLabel Processores_CountLabel = new JLabel("Processores_Count:");
    JTextField Processores_CountText = new JTextField(15);
    JLabel cpuLoadLabel = new JLabel("System-Cpu-Load:");
    JTextField cpuLoadText = new JTextField(15);
    JButton saveButton = new JButton("Save");
    JButton clearButton = new JButton("Clear");
    private EntityManager em;

    public static void main(String[] args) {
        new KeyPerformanceIndexForm().initializeControls();
    }

    @Override
    public void initializeControls() {
        String metal = "javax.swing.plaf.metal.MetalLookAndFeel";
        String motif = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
        String windows = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        String nimbus = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
        try {
            UIManager.setLookAndFeel(nimbus);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JPanel panel1 = new JPanel();
        panel1.add(formTitle);
        panel1.add(formTitle);

        JPanel panel2 = new JPanel();
        panel2.add(totalMemoryLabel);
        panel2.add(totalMemoryText);

        JPanel panel3 = new JPanel();
        panel3.add(freeMemoryLabel);
        panel3.add(freeMemoryText);

        JPanel panel4 = new JPanel();
        panel4.add(Processores_CountLabel);
        panel4.add(Processores_CountText);

        JPanel panel5 = new JPanel();
        panel5.add(cpuLoadLabel);
        panel5.add(cpuLoadText);

        JPanel panel6 = new JPanel();
        panel6.add(saveButton);
        panel6.add(clearButton);

        saveButton.addActionListener((ActionEvent e) -> {
            KeyPerfomanceIndexEntity kpiEntity = new KeyPerfomanceIndexEntity();
            kpiEntity.setTotalMemory(Long.valueOf(totalMemoryText.getText()));
            kpiEntity.setFreeMemory(Long.valueOf(freeMemoryText.getText()));
            kpiEntity.setSystemCpuLoad(Double.valueOf(cpuLoadText.getText()));
            kpiEntity.setProcessorsCount(Integer.valueOf(Processores_CountText.getText()));
            KPIFacade kpiFacade = new KPIFacadeImpl();
            kpiFacade.saveKPI(kpiEntity);

        });

        clearButton.addActionListener((ActionEvent e) -> {
            totalMemoryText.setText(null);
            freeMemoryText.setText(null);
            cpuLoadText.setText(null);
            Processores_CountText.setText(null);
        });

        Container container = this.getContentPane();
        container.add(panel1);
        container.add(panel2);
        container.add(panel3);
        container.add(panel4);
        container.add(panel5);
        container.add(panel6);
        setTitle("KeyPerformanceIndexForm");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(container, 3));
        setSize(400, 300);
        setResizable(false);
        setVisible(true); // the last thing to do!

    }
}
