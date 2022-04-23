package com.myproject.model;

import com.myproject.view.mainframe;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;


public class InvHeadTableModel extends AbstractTableModel{
    
    private ArrayList<InvoiceHeader> invoicesArray;
    private String[] columns = {"Invoice Num", "Invoice Date", "Customer Name", "Invoice Total"};

public InvHeadTableModel(ArrayList<InvoiceHeader> invoicesArray){
        this.invoicesArray = invoicesArray;
    }
    
    

    @Override
    public int getRowCount() {
        return invoicesArray.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    InvoiceHeader inv = invoicesArray.get(rowIndex);
        switch (columnIndex) {
            case 0: return inv.getNumber();
            case 1: return mainframe.dateFormat.format(inv.getInvoiceDate());
            case 2: return inv.getCustomer();
            case 3: return inv.getInvoicesTotal();
        }
        return "";    }
    
    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public int getColumnCount() {
    return columns.length;    }
    
    
}
