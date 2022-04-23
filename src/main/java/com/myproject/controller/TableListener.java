
package com.myproject.controller;

import com.myproject.model.InvLineTableModel;
import com.myproject.model.InvoiceHeader;
import com.myproject.model.InvoiceLine;
import com.myproject.view.mainframe;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;


public class TableListener implements ListSelectionListener {

    private mainframe frame;

    public TableListener(mainframe frame) {
        this.frame = frame;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int sInvoiceIndx = frame.getInvoiceHeaderTbl().getSelectedRow();
        System.out.println("Invoice selected: " + sInvoiceIndx);
        if (sInvoiceIndx != -1) {
            InvoiceHeader selectedInvoice = frame.getInvoicesArray().get(sInvoiceIndx);
            ArrayList<InvoiceLine> lines = selectedInvoice.getLines();
            InvLineTableModel lineTblModel = new InvLineTableModel(lines);
            frame.setInvLinesArray(lines);
            frame.getInvoiceLineTbl().setModel(lineTblModel);
            frame.getCustomerNameLbl().setText(selectedInvoice.getCustomer());
            frame.getInvoiceNumberLbl().setText(String.valueOf(selectedInvoice.getNumber()));
            frame.getInvoiceDateLbl().setText(String.valueOf(mainframe.dateFormat.format(selectedInvoice.getInvoiceDate())));
            frame.getInvoiceTotalLbl().setText(String.valueOf(selectedInvoice.getInvoicesTotal()));
        }
    }
    
    
}
