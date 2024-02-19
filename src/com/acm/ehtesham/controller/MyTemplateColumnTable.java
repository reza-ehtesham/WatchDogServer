package com.acm.ehtesham.controller;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Created by ehtesham on 05/06/2017.
 */
public class MyTemplateColumnTable extends DefaultTableCellRenderer {
    private boolean status;
    private int rowTable;
    ImageIcon greenIcon = new ImageIcon(getClass().getResource("green.gif"));
    ImageIcon redIcon = new ImageIcon(getClass().getResource("red.gif"));

    public MyTemplateColumnTable(boolean status, int row) {
        rowTable = row;
        this.status = status;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
                row, column);

        int neededRow = rowTable; // set the needed row here in which the icon to be dispayed
        int neededColumn = 8; // set the needed column here in which the icon to be dispayed

        if (row == neededRow && column == neededColumn) {
            if (isStatus())
                setIcon(greenIcon);
            else
                setIcon(redIcon);
        }

        return this;
    }
//    JLabel lbl = new JLabel();
//   ImageIcon greenIcon = new ImageIcon(getClass().getResource("green.gif"));
//    ImageIcon redIcon = new ImageIcon(getClass().getResource("red.gif"));
//
//   @Override
//    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
//                                                   boolean hasFocus, int row, int column) {
//        row=getRow();
//        if(isStatus())
//           lbl.setIcon(greenIcon);
//        else
//           lbl.setIcon(redIcon);
//        return lbl;
//    }

    public boolean isStatus() {
        return status;
    }

    public int getRow() {
        return rowTable;
    }
}

