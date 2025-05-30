/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.mypos.integration;


import se.kth.iv1350.mypos.discountStrategy.DiscountStrategy;
import se.kth.iv1350.mypos.discountStrategy.DiscountWhenSummerStrategy;
import se.kth.iv1350.mypos.discountStrategy.DiscountWhenWinterStrategy;
import se.kth.iv1350.mypos.model.Amount;
import se.kth.iv1350.mypos.model.CustomerID;
import se.kth.iv1350.mypos.model.Sale;


/**
 * Represents available discount for active buyer.   
 * 
 */
public class Discount {
    private static final Discount INSTANCE = new Discount(); 
    DiscountStrategy strat;
    DiscountStrategy summer = new DiscountWhenSummerStrategy();
    DiscountStrategy winter = new DiscountWhenWinterStrategy();
    Amount month = new Amount(10);
    
    private Discount(){
        
        if(month.getAmount() >= 6){
         strat = winter;
        }
        
        if(month.getAmount() < 6){
         strat = summer;
        }
         
    }
    
    /**
    * calculates what discount customer is eligible for.   
    * @param sale current sale used to calculate discount.
    * @param want boolean value that represents if customer wants discount or not.
    * @param id represents customers identity.
    */
    public Sale calculateDiscountValue(Sale sale, CustomerID id){
        Amount totalValue = sale.getTotalPriceWithVAT();
        Amount discountPrice = strat.discount(totalValue, id);
        sale.totalPriceToPay = discountPrice; 
        return sale; 
    }
    
    public static Discount getDiscount(){
    return INSTANCE;
    }
    
    
  
    
}
