/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myproject.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author DELL
 */
public class InvHeadDialog extends JDialog {
    private JTextField customerNameFld;
    private JTextField invDateFld;
    private JLabel customerNameLbl;
    private JLabel invDateLbl;
    private JButton okayBtn;
    private JButton cnclBtn;

    public InvHeadDialog(mainframe frame) {
        customerNameLbl = new JLabel("Customer Name:");
        customerNameFld = new JTextField(20);
        invDateLbl = new JLabel("Invoice Date:");
        invDateFld = new JTextField(20);
        okayBtn = new JButton("OK");
        cnclBtn = new JButton("Cancel");
        
        okayBtn.setActionCommand("OkayNI");
        cnclBtn.setActionCommand("CancelNI");
        
        okayBtn.addActionListener(frame.getAcListener());
        cnclBtn.addActionListener(frame.getAcListener());
        setLayout(new GridLayout(3, 2));
        setTitle("Create New Invoice");
        add(invDateLbl);
        add(invDateFld);
        add(customerNameLbl);
        add(customerNameFld);
        add(okayBtn);
        add(cnclBtn);
        
        pack();
        
    }

    public JTextField getCustomerNameFld() {
        return customerNameFld;
    }

    public JTextField getInvDateFld() {
        return invDateFld;
    }
    
}
