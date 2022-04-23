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

public class InvLnDialog extends JDialog{
    private JTextField itemNameFld;
    private JTextField itemCntFld;
    private JTextField itemPriceFld;
    private JLabel itmNameLbl;
    private JLabel itemCntLbl;
    private JLabel itmPriceLbl;
    private JButton okayBtn;
    private JButton cnclBtn;
    
    public InvLnDialog(mainframe frame) {
        itemNameFld = new JTextField(20);
        itmNameLbl = new JLabel("Item Name");
        
        itemCntFld = new JTextField(20);
        itemCntLbl = new JLabel("Item Count");
        
        itemPriceFld = new JTextField(20);
        itmPriceLbl = new JLabel("Item Price");
        
        okayBtn = new JButton("OK");
        cnclBtn = new JButton("Cancel");
        
        okayBtn.setActionCommand("OkayNL");
        cnclBtn.setActionCommand("CancelNL");
        
        okayBtn.addActionListener(frame.getAcListener());
        cnclBtn.addActionListener(frame.getAcListener());
        setLayout(new GridLayout(4, 2));
        setTitle("Add New Invoice Item");
        add(itmNameLbl);
        add(itemNameFld);
        add(itemCntLbl);
        add(itemCntFld);
        add(itmPriceLbl);
        add(itemPriceFld);
        add(okayBtn);
        add(cnclBtn);
        
        pack();
    }

    public JTextField getItemNameFld() {
        return itemNameFld;
    }

    public JTextField getItemCntFld() {
        return itemCntFld;
    }

    public JTextField getItemPriceFld() {
        return itemPriceFld;
    }
}
