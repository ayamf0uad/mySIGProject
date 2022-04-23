package com.myproject.model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class InvLineTableModel extends AbstractTableModel {
    private ArrayList<InvoiceLine> invLinesArray;
    private String[] columns = {"Item Name", "Item Price", "Count", "Invoice Total"};

    public InvLineTableModel(ArrayList<InvoiceLine> invLinesArray) {
        this.invLinesArray = invLinesArray;
    }

    @Override
    public int getRowCount() {
        return invLinesArray == null ? 0 : invLinesArray.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];

    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (invLinesArray == null)
        { return " "; }
        else {
            InvoiceLine line = invLinesArray.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return line.getItemName();
                case 1:
                    return line.getItemPrice();
                case 2:
                    return line.getCount();
                case 3:
                    return line.getInvoiceAmount();
                default:
                    return (" ");
            }
        }
    }


}
