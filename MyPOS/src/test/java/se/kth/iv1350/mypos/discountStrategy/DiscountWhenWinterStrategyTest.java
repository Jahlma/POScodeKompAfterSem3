/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package se.kth.iv1350.mypos.discountStrategy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import se.kth.iv1350.mypos.model.Amount;
import se.kth.iv1350.mypos.model.CustomerID;
import se.kth.iv1350.mypos.model.Sale;

/**
 *
 * @author jojoa
 */
public class DiscountWhenWinterStrategyTest {
    
    public DiscountWhenWinterStrategyTest() {
    }

    @Test
    public void testDiscount() {
       Sale sale = new Sale(); 
        sale.totalPriceWithVAT = new Amount(400); 
        Amount expected = new Amount(140);
        CustomerID id = new CustomerID("John Guidetti", new Amount(1992));
        DiscountWhenWinterStrategy instance = new DiscountWhenWinterStrategy();
        Amount actuall = instance.discount(sale.totalPriceWithVAT, id);
       
        assertEquals(expected.getAmount(), actuall.getAmount());
    
    }
    
    @Test
    public void testCalculateDiscount() {
      DiscountWhenWinterStrategy instance = new DiscountWhenWinterStrategy();
      Amount saleValue = new Amount(500);
      Amount percentage = new Amount(0.5);
      Amount expected = new Amount(250); 
      Amount discount = instance.calculateDiscount(saleValue, percentage);
     
      assertEquals(expected.getAmount(), discount.getAmount());
      
    }
    
}

    
