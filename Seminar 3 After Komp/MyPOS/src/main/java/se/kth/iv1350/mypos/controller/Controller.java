/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.mypos.controller;


import java.util.List;
import java.util.Scanner;
import se.kth.iv1350.mypos.DTO.ItemDTO;
import se.kth.iv1350.mypos.DTO.SaleDTO;
import se.kth.iv1350.mypos.integration.Accounting;
import se.kth.iv1350.mypos.integration.Discount;
import se.kth.iv1350.mypos.integration.Inventory;
import se.kth.iv1350.mypos.integration.ItemIdentifier;
import se.kth.iv1350.mypos.integration.Printer;
import se.kth.iv1350.mypos.integration.Register;
import se.kth.iv1350.mypos.model.Amount;
import se.kth.iv1350.mypos.model.FinalSaleInfo;
import se.kth.iv1350.mypos.model.Item;
import se.kth.iv1350.mypos.model.Payment;
import se.kth.iv1350.mypos.model.Receipt;
import se.kth.iv1350.mypos.model.Sale;
import se.kth.iv1350.mypos.model.SaleItem;


/**
 * Gets called by view and its purpose is to delegate and activate other parts of the project.
 * 
 */
public class Controller {
    private ItemIdentifier eitem;           
    private Printer print;
    private Inventory inv;
    private Discount disc;
    private Register reg;
    public Sale sale;
    private Accounting acc;
    FinalSaleInfo finalSale;
    
    
    
    /**
    * Creates a new instance of a controller.
    * @param eitem database for item identifiers of available items for sale.
    * @param print external printer.
    * @param inv represents external inventory system.
    * @param disc represents discount database.
    * @param reg register that is used for collecting change.
    * @param acc database for accounting numbers.
    */
    public Controller(ItemIdentifier eitem, Printer print, Inventory inv, Discount disc, Register reg, Accounting acc){
        this.eitem = eitem;    
        this.print = print;
        this.inv = inv;
        this.disc = disc;
        this.reg = reg;
        this.acc = acc;
    }   
        
        
    /**
    * Creates a new and only one sale 
    * 
    */    
    public void saleStart() {
      sale = new Sale();
  
    }
    
    /**
    * Represents one item scanned, if item already exist in sale then a quantity is asked for and set, otherwise is set to 1
    * item is fetched from inventory and converted to an DTO. ItemDTO and quantity creates an saleItem which 
    * is used to store in sale.
    * @param itemidentifier Shows which item that is scanned. In current program it is manually called,
    * should appear from actual scan with machine.
    */   
    public ItemDTO scanning(int itemidentifier){
        boolean foundAsID = false;
        boolean foundInSale = false;
        ItemDTO itemDTO;
        Item item;
        SaleItem saleItem;
        Amount quantity;
        int quantityAsInt;
  
        foundAsID = eitem.lookupItem(itemidentifier);
        if(foundAsID == true){
           itemDTO = inv.getInfoOfItem(itemidentifier).toDTO();
           foundInSale = sale.lookupItem(itemDTO);
           if(foundInSale == true){
               return null;
        }
           else{
                quantityAsInt = 1;
                quantity = new Amount(quantityAsInt);
                saleItem = sale.addItemToSale(itemDTO, quantity);
                sale.addSaleItemToList(saleItem);
                return itemDTO;
            }
        }
        else{
            throw new IllegalArgumentException("Scannad vara exsisterar inte i systemet");
        }       
    }
    
    public ItemDTO itemAlreadyInSale(Amount quantity, int identifier){
        List<SaleItem> salelist = sale.getSaleList();
        for(SaleItem item : salelist){
            if(item.getItemDTO().getID() == identifier){
                item.quantity = quantity; 
                return item.getItemDTO();
            }
        }
        
        return null;
    }
    
    
    /**
    * Method called when all items included in sale is scanned and completed. The sale information is converted
    * to a DTO. Method print out total amount paid including VAT, it also creates an object 
    * for finalSaleinfo which is returned.
    * 
    */   
    public SaleDTO allItemScanned(){ 
       SaleDTO saleFinal; 
       Sale saleToUse;
       saleToUse = sale.calculateInfo();
       saleFinal = saleToUse.toSaleDTO();
       finalSale = new FinalSaleInfo(saleToUse);
       return saleFinal;
       
    }
    
    
    /**
    * When sale is completed method first handles payment by creating a new object. Then calls 
    * for a new receipt and other methods for updating databases. Also calls method for printing receipt
    * @param finalSale holds all relevent information for the sale made.
    * @param cash represents payment made by customer.
    */   
    public void finalizeSale(Amount cash){
      Payment paid;
      Receipt receipt;
      finalSale = new FinalSaleInfo(sale);
      paid = new Payment(cash); 
      paid.getToPay(finalSale);
      finalSale.pay(paid);
      reg.AddRegister(finalSale);
      receipt = finalSale.createReceipt(finalSale);
      inv.updateInventoryInfo(finalSale.getSaleDTO());
      acc.updateAccountingInfo(finalSale.getSaleDTO());
      print.printReceipt(receipt);
    }
    
    public Sale getSale(){
    return sale;
    }
   
    
}
