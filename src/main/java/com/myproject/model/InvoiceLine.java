
package com.myproject.model;


public class InvoiceLine {
    private InvoiceHeader header;
    private String itemName;
    private double itemPrice;
    private int count;

    public InvoiceLine() {
    }

    public InvoiceLine(InvoiceHeader header, String itemName, double itemPrice, int count) {
        this.header = header;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public InvoiceHeader getHeader() {
        return header;
    }

    public void setHeader(InvoiceHeader header) {
        this.header = header;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }
    
    public double getInvoiceAmount(){
        return itemPrice * count;
    }

    @Override
    public String toString() {
        return  header.getNumber() + "," + itemName + "," + itemPrice + "," + count;
    }
    
    
    
    
}
