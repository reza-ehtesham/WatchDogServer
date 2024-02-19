package com.acm.ehtesham.view;

import com.acm.ehtesham.controller.KPIFacadeImpl;
import com.acm.ehtesham.controller.MyTemplateColumnTable;
import com.acm.ehtesham.controller.WatchDogServer;
import com.acm.ehtesham.entity.IHeartBeat;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by ehtesham on 04/08/2017.
 */
public class WatchDogMainForm extends JFrame implements TableModelListener, IDesign {
    private static WatchDogMainForm myWatchDogMainForm;
    private TableModel tableModel;
    private Object[][] heartBeatData;
    private int index = 0;
    public static JTable heartBeatTable;
    public static Thread serverThread;
    public static WatchDogServer watchDogServer;
    String metal = "javax.swing.plaf.metal.MetalLookAndFeel";
    String motif = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
    String windows = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
    String nimbus = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";

    public WatchDogMainForm() {
        initializeControls();
    }

    @Override
    public void initializeControls() {
        heartBeatData = new Object[1000][9];
        String column[] = {"Ip", "Time", "Total_Memory", "Free_Memory", "Processores_Count", "system_Cpu_Load", "Process_Name", "Is_Alive", "Status"};
        setTableModel(new AbstractTableModel() {
            public String getColumnName(int col) {
                return column[col].toString();
            }

            public int getRowCount() {
                return getData().length;
            }

            public int getColumnCount() {
                return column.length;
            }

            public Object getValueAt(int row, int col) {
                return getData()[row][col];
            }

            public boolean isCellEditable(int row, int col) {
                return true;
            }

            public void setValueAt(Object value, int row, int col) {
                getData()[row][col] = value;
                fireTableCellUpdated(row, col);
            }
        });
        try {
            UIManager.setLookAndFeel(nimbus);
        } catch (Exception ex) {
        }
        setTitle("Watch Dog Server");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new GridLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4));
        JButton startServerButton = new JButton();
        startServerButton.setBackground(Color.black);
        startServerButton.setIcon(new ImageIcon(getClass().getResource("/com/acm/ehtesham/view/icons/enable_server.gif")));
        JButton stopServerButton = new JButton();
        stopServerButton.setBackground(Color.black);
        stopServerButton.setIcon(new ImageIcon(getClass().getResource("/com/acm/ehtesham/view/icons/desable_server.gif")));
        startServerButton.addActionListener((ActionEvent e) -> {
            watchDogServer = new WatchDogServer();
            serverThread = new Thread(watchDogServer);
            serverThread.start();
            startServerButton.setEnabled(false);
            stopServerButton.setEnabled(true);
        });

        stopServerButton.addActionListener((ActionEvent e) -> {
            serverThread.interrupt();
            try {
                throw new InterruptedException("Interruped Stop Server");
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            startServerButton.setEnabled(true);
            stopServerButton.setEnabled(false);
        });
        JButton kpiButton = new JButton();
        kpiButton.setBackground(Color.BLACK);
        kpiButton.setIcon(new ImageIcon(getClass().getResource("/com/acm/ehtesham/view/icons/performance.png")));
        kpiButton.addActionListener((ActionEvent e) -> {
            KeyPerformanceIndexForm keyPerformanceIndexForm = new KeyPerformanceIndexForm();
            keyPerformanceIndexForm.initializeControls();
            keyPerformanceIndexForm.show();
        });
        buttonPanel.add(startServerButton);
        buttonPanel.add(stopServerButton);
        buttonPanel.add(kpiButton);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        heartBeatTable = new JTable(getTableModel());
        heartBeatTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollTable = new JScrollPane(heartBeatTable);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(scrollTable, BorderLayout.CENTER);
        heartBeatTable.getModel().addTableModelListener(this);
        setVisible(true);
        getContentPane().add(mainPanel);
    }

    public static void main(String[] args) {
        setMyWatchDogMainForm(new WatchDogMainForm());
    }

    public synchronized static void receiveDataClient(IHeartBeat heartBeat, String clientIp) {
        getMyWatchDogMainForm().getTableModel().setValueAt(clientIp, getMyWatchDogMainForm().index, 0);
        getMyWatchDogMainForm().getTableModel().setValueAt(heartBeat.getTime(), getMyWatchDogMainForm().index, 1);
        getMyWatchDogMainForm().getTableModel().setValueAt(heartBeat.getTotalMemory(), getMyWatchDogMainForm().index, 2);
        getMyWatchDogMainForm().getTableModel().setValueAt(heartBeat.getFreeMemory(), getMyWatchDogMainForm().index, 3);
        getMyWatchDogMainForm().getTableModel().setValueAt(heartBeat.getProcessoresCount(), getMyWatchDogMainForm().index, 4);
        getMyWatchDogMainForm().getTableModel().setValueAt(heartBeat.getSystemCpuLoad(), getMyWatchDogMainForm().index, 5);
        getMyWatchDogMainForm().getTableModel().setValueAt(heartBeat.getProcessName(), getMyWatchDogMainForm().index, 6);
        getMyWatchDogMainForm().getTableModel().setValueAt(heartBeat.isAlive(), getMyWatchDogMainForm().index, 7);
        getMyWatchDogMainForm().getTableModel().setValueAt(new KPIFacadeImpl().checkKPI(heartBeat)
                ,getMyWatchDogMainForm().index, 8);
        getMyWatchDogMainForm().index++;
//            if((getMyWatchDogMainForm().index)==getMyWatchDogMainForm().getData().length){
//                getMyWatchDogMainForm().setData(null);
//                getMyWatchDogMainForm().setData(expandSizeArray(getMyWatchDogMainForm().getData(), getMyWatchDogMainForm().getData().length+1000));
//                getMyWatchDogMainForm().index++;
        // }
    }

    private static Object[][] expandSizeArray(Object[][] array, int size) {
        Object[][] temp = new Object[size][];
        System.arraycopy(array, 0, array, 0, array.length);
        for (int j = array.length; j < size; j++)
            temp[j] = null;
        return temp;
    }

    public static WatchDogMainForm getMyWatchDogMainForm() {
        return myWatchDogMainForm;
    }

    public static void setMyWatchDogMainForm(WatchDogMainForm myWatchDogMainForm) {
        WatchDogMainForm.myWatchDogMainForm = myWatchDogMainForm;
    }

    @Override
    public void tableChanged(TableModelEvent e) {

    }

    public TableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(TableModel tableModel) {
        this.tableModel = tableModel;
    }


    public Object[][] getData() {
        return heartBeatData;
    }

    public void setData(Object[][] data) {
        this.heartBeatData = data;
    }

}






