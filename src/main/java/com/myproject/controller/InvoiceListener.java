
 package com.myproject.controller;

import com.myproject.model.InvHeadTableModel;
import com.myproject.model.InvLineTableModel;
import com.myproject.model.InvoiceHeader;
import com.myproject.model.InvoiceLine;
import com.myproject.view.InvHeadDialog;
import com.myproject.view.InvLnDialog;
import com.myproject.view.mainframe;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class InvoiceListener implements ActionListener{
    
    private mainframe frame;
    private InvHeadDialog headDialog;
    private InvLnDialog lnDialog;

    public InvoiceListener(mainframe frame){
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        switch(e.getActionCommand()){
            case "Load Files":
                if (frame.getInvoicesArray() != null){
                    int a = JOptionPane.showConfirmDialog(frame, "Loading a New File Might Cause Losing of Unsaved Changes\nPlease, Make sure you save your current files first\nAre you sure you would like to continue?");
                    if(a == JOptionPane.YES_OPTION){
                        loadFiles();
                    }


                }
                else {
                    loadFiles();
                }
                break;
            case "OkayNI":
                dsplyNIDlg();
                break;
            case "CancelNI":
                cnclNIDlg();
                break;
            case "OkayNL":
                nLnOk();
                break;
            case "CancelNL":
                cnclNLn();
                break;
            case "Save Files":
                saveFiles();
                break;
            case "Create New Invoice":
                createInvoice();
                break;
            case "Delete Invoice":
                deleteInvoice();
                break;
            case "New Line":
                addNewLine();
                break;
            case "Delete Line":
                deleteLine();
                break;
 
        }
        
    }

    private void cnclNLn() {
        lnDialog.setVisible(true);
        lnDialog.dispose();
        lnDialog = null;
    }

    private void nLnOk() {
        lnDialog.setVisible(true);

        String namestr = lnDialog.getItemNameFld().getText();
        String countstr = lnDialog.getItemCntFld().getText();
        String pricestr = lnDialog.getItemPriceFld().getText();
        int cnt = 1;
        double prc = 1;
        try {
            cnt = Integer.parseInt(countstr);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "invalid input","wrong number format",JOptionPane.ERROR_MESSAGE);
        }
        try {
            prc = Double.parseDouble(pricestr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "invalid input","wrong number format",JOptionPane.ERROR_MESSAGE);
        }
        int sInvoiceH = frame.getInvoiceHeaderTbl().getSelectedRow();
        if (sInvoiceH != -1){
            InvoiceHeader invoiceHeader = frame.getInvoicesArray().get(sInvoiceH);
            InvoiceLine ln = new InvoiceLine(invoiceHeader, namestr, prc,cnt);
            //invoiceHeader.getLines().add(ln);
            frame.getInvLinesArray().add(ln);
            InvLineTableModel lnTblModel = (InvLineTableModel) frame.getInvoiceLineTbl().getModel();
            lnTblModel.fireTableDataChanged();
            frame.getInvHeadTableModel().fireTableDataChanged();
        }
        frame.getInvoiceHeaderTbl().setRowSelectionInterval(sInvoiceH, sInvoiceH);

        lnDialog.dispose();
        lnDialog = null;
    }


    private void loadFiles() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Load Invoice File(s)");
        try {
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            System.out.println("User Clicked on Load Option");
            System.out.println("*************************************");
           File headFile = fileChooser.getSelectedFile();
           Path headPath = Paths.get(headFile.getAbsolutePath());
           List<String> headLines = Files.readAllLines(headPath);
           ArrayList<InvoiceHeader> invoiceHeaders = new ArrayList<>();
           for (String headLine : headLines){
               String[] elem = headLine.split(",");
               String s1 = elem[0];
               String s2 = elem[1];
               String s3 = elem[2];
               int num = Integer.parseInt(s1);
               Date invDate = mainframe.dateFormat.parse(s2);
               InvoiceHeader header = new InvoiceHeader(num, s3, invDate);
               System.out.println("Loaded Invoice File: " + header);
               invoiceHeaders.add(header);

           }
            System.out.println("*************************************");
        frame.setInvoicesArray(invoiceHeaders);
        
        result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File linesFile = fileChooser.getSelectedFile();
            Path linesPath = Paths.get(linesFile.getAbsolutePath());
            List<String> lineLines = Files.readAllLines(linesPath);
            ArrayList<InvoiceLine> invoiceLines = new ArrayList<>();
            for (String lineLine : lineLines ) {
                String[] elem = lineLine.split(",");
                String s1 = elem[0];
                String s2 = elem[1];
                String s3 = elem[2];
                String s4 = elem[3];
                int invNum = Integer.parseInt(s1);
                double price = Double.parseDouble(s3);
                int count = Integer.parseInt(s4);
                InvoiceHeader inv = frame.getInvObj(invNum);
                InvoiceLine line = new InvoiceLine(inv, s2, price, count);
                System.out.println("Loaded Invoice Details: " + line);
                inv.getLines().add(line);

            }
            System.out.println("*************************************");
        }
        InvHeadTableModel headerTblModel = new InvHeadTableModel(invoiceHeaders);
        frame.setInvHeadTableModel(headerTblModel);
        frame.getInvoiceHeaderTbl().setModel(headerTblModel);
        }
        
        }  catch (IOException ex){
            JOptionPane.showMessageDialog(frame, ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex){
            JOptionPane.showMessageDialog(frame, ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
        }

    }


    private void saveFiles() {
        ArrayList<InvoiceHeader> invArr = frame.getInvoicesArray();
        JFileChooser fileC = new JFileChooser();
        fileC.setDialogTitle("Save Main Invoice File");
        try {
            int res = fileC.showSaveDialog(frame);
            if (res == JFileChooser.APPROVE_OPTION) {
                File headF = fileC.getSelectedFile();
                FileWriter fileW = new FileWriter(headF);
                String head = "";
                String details = "";
                for (InvoiceHeader invoice : invArr) {
                    head += invoice.toString();
                    head += "\n";
                    for (InvoiceLine line : invoice.getLines()) {
                        details += line.toString();
                        details += "\n";
                    }
                }
                head = head.substring(0, head.length()-1);
                details = details.substring(0,details.length()-1);
                res = fileC.showSaveDialog(frame);
                File detailsFile = fileC.getSelectedFile();
                FileWriter detailsFW = new FileWriter(detailsFile);
                fileW.write(head);
                detailsFW.write(details);
                fileW.close();
                detailsFW.close();

            }
        } catch(IOException ex){
            JOptionPane.showMessageDialog(frame, ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void createInvoice() {
        int sInvoiceIndx = frame.getInvoiceHeaderTbl().getSelectedRow();

        //if (frame.getInvoicesArray() == null) { JOptionPane.showMessageDialog(frame,"Can't Create Invoice Without Invoices File Loaded", "File Missing", JOptionPane.ERROR_MESSAGE);}
        //else {
            headDialog = new InvHeadDialog(frame);
            headDialog.setVisible(true);
        //}
    }

    private void deleteInvoice() {
        int sInvoiceIndx = frame.getInvoiceHeaderTbl().getSelectedRow();
        if (sInvoiceIndx != -1) {
            frame.getInvoicesArray().remove(sInvoiceIndx);
            frame.getInvHeadTableModel().fireTableDataChanged();

            frame.getInvoiceLineTbl().setModel(new InvLineTableModel(new ArrayList<InvoiceLine>()));
            frame.setInvLinesArray(null);
            frame.getCustomerNameLbl().setText("");
            frame.getInvoiceNumberLbl().setText("");
            frame.getInvoiceDateLbl().setText("");
            frame.getInvoiceTotalLbl().setText(" ");
        }
    }

    private void cnclNIDlg() {
        headDialog.setVisible(false);
        headDialog.dispose();
        headDialog = null;
    }
    private void dsplyNIDlg() {
        headDialog.setVisible(false);
        String namestr = headDialog.getCustomerNameFld().getText();
        String datestr = headDialog.getInvDateFld().getText();
        Date d = new Date();
        try {
        d = mainframe.dateFormat.parse(datestr); } catch (ParseException ex) {
            JOptionPane.showMessageDialog(frame, "Can't Parse Date, will be today's date", "wrong date format", JOptionPane.ERROR_MESSAGE);
        }

        if(frame.getInvoicesArray() == null){

            ArrayList<InvoiceHeader> invoiceHeaders = new ArrayList<>();
            InvoiceHeader addedInvoice = new InvoiceHeader(1, namestr, d);
            invoiceHeaders.add(addedInvoice);
            frame.setInvoicesArray(invoiceHeaders);
            InvHeadTableModel headerTblModel = new InvHeadTableModel(invoiceHeaders);
            frame.setInvHeadTableModel(headerTblModel);
            frame.getInvoiceHeaderTbl().setModel(headerTblModel);

        }
        else {
            int invNumber = 0;
            try {
                for (InvoiceHeader inv : frame.getInvoicesArray()) {

                    if (inv.getNumber() > invNumber) {
                        invNumber = inv.getNumber();
                    }
                }
            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(frame, "Please load Required Files to Create Invoice", "Files Required", JOptionPane.ERROR_MESSAGE);
            }
            invNumber++;

            InvoiceHeader addedInvoice = new InvoiceHeader(invNumber, namestr, d);
            frame.getInvoicesArray().add(addedInvoice);
            frame.getInvHeadTableModel().fireTableDataChanged();
        }

        headDialog.dispose();
        headDialog = null;
    }

    private void addNewLine() {
        int sInvoiceIndx = frame.getInvoiceHeaderTbl().getSelectedRow();
        if (frame.getInvoicesArray() == null) { JOptionPane.showMessageDialog(frame,"Can't Add Invoice Details Without A File Loaded", "File Missing", JOptionPane.ERROR_MESSAGE);}
        else if (sInvoiceIndx == -1) { JOptionPane.showMessageDialog(frame,"Can't Add Invoice Details Without Selecting An Invoice", "File Missing", JOptionPane.ERROR_MESSAGE);}
        else {
            lnDialog = new InvLnDialog(frame);
            lnDialog.setVisible(true);
        }
    }

    private void deleteLine() {
        int sLnIndex = frame.getInvoiceLineTbl().getSelectedRow();
        int sInvIndex = frame.getInvoiceHeaderTbl().getSelectedRow();
        if (sLnIndex != -1){
            frame.getInvLinesArray().remove(sLnIndex);
            InvLineTableModel lnTblModel = (InvLineTableModel) frame.getInvoiceLineTbl().getModel();
            lnTblModel.fireTableDataChanged();
            frame.getInvoiceTotalLbl().setText(String.valueOf(frame.getInvoicesArray().get(sInvIndex).getInvoicesTotal()));
            frame.getInvHeadTableModel().fireTableDataChanged();
            frame.getInvoiceHeaderTbl().setRowSelectionInterval(sInvIndex,sInvIndex);


        }
    }
    
}
