/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.mypos.model;

import se.kth.iv1350.mypos.DTO.SaleDTO;

/**
 * Stores and calculates information for the final sale and its attributes. 
 * 
 */
public class FinalSaleInfo {
    Sale sale; 
    Amount change;
    Amount paidAmount;
    
    /**
    * Creates a new instance of a final sale.
    * @param SaleDTO is the final version of the completed sale with all information to it.
    */
    public FinalSaleInfo(Sale sale){
        this.sale = sale;
    }
    
    /**
    * Method calculates how much change the customer is eligible to.
    * @param cash represents cash given by customer to pay for sale.
    */
    public Amount calculateChange(Amount cash){
    Amount amountPaid;
    amountPaid = cash.sub(sale.getTotalPriceWithVAT());
    return amountPaid;
    }
    
    /**
    * Method updates how much customer has paid and what the change is.
    * @param paid represents the customers payment.
    */
    public void pay(Payment paid){
        change = calculateChange(paid.getCash());
        paidAmount = sale.getTotalPriceWithVAT();
        
    }
    
    public Amount getTotalAmountToPay(FinalSaleInfo finalSale){
        return sale.getTotalPriceWithVAT();
    }
    
    /**
    * Method creates a receipt 
    * @param finalSale represents the final sale and its information.
    */
    public Receipt createReceipt(FinalSaleInfo finalSale){
    return new Receipt(sale.getSaleTime(), paidAmount, change, sale.getTotalVAT(), sale.getSaleList());
    }
    
    public Amount getChange(){
    return change;
    }
    
    public Amount getPaid(){
    return sale.getTotalPriceWithVAT();
    }
    
    public SaleDTO getSaleDTO(){
    return sale.toSaleDTO();
    }
    
   
}
