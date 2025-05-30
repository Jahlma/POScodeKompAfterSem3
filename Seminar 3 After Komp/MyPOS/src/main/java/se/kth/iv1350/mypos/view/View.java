/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.mypos.view;

import java.util.Scanner;
import se.kth.iv1350.mypos.DTO.ItemDTO;
import se.kth.iv1350.mypos.DTO.SaleDTO;
import se.kth.iv1350.mypos.controller.Controller;
import se.kth.iv1350.mypos.model.Amount;
import se.kth.iv1350.mypos.model.FinalSaleInfo;

/**
 * A placeholder for the real view. It contains hardcoded executions to system operations in the controller.
 * 
 */
public class View {
    private Controller contr; 
    
    public View(Controller contr){
    this.contr = contr;
    }
    
    public void runFakeEX() {
        
        startSale();
        scanningItem(1001);
        scanningItem(1002);
        scanningItem(1004);
        scanningItem(1006);
        scanningItem(1006);
        allItemScanned();
        finalizeSale();
        
       
    
    }
    
    private void scanningItem(int itemIdentifier){
    ItemDTO itemDTO;
    itemDTO = contr.scanning(itemIdentifier);
        if(itemDTO == null){
        Scanner scanner = new Scanner(System.in);
        System.out.println("The product with identifier " + itemIdentifier + " already exsist in current sale please write the total amount of quantity of said product! ");
        int quantityAsInt = scanner.nextInt();
        Amount quantity = new Amount(quantityAsInt);
        itemDTO = contr.itemAlreadyInSale(quantity, itemIdentifier);
        
        }
    System.out.println("One scan confirmed and completed:" + " Item " + itemDTO.getName() + " has been added " + " Price: " + itemDTO.getPrice() + " VAT " + itemDTO.getVAT());
    
    } 
    
    private void allItemScanned(){
    SaleDTO sale;
    sale = contr.allItemScanned();
    System.out.println("Operator has ended sale");
    System.out.println("Total amount to pay incl. VAT: " + sale.getTotalPriceWithVAT().getAmount() + " kr");
    }
    
    private void finalizeSale(){
    int pay;
    Amount amount;
    Scanner scanner = new Scanner(System.in);
    System.out.println("Please enter how much cash to pay with");
    pay = scanner.nextInt();
    amount = new Amount(pay);   
    contr.finalizeSale(amount);
    }
    
    private void startSale(){
    contr.saleStart();
    System.out.println("A new sale has been started by operator");
    }
}
