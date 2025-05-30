/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package se.kth.iv1350.mypos.controller;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import se.kth.iv1350.mypos.DTO.ItemDTO;
import se.kth.iv1350.mypos.integration.Accounting;
import se.kth.iv1350.mypos.integration.Discount;
import se.kth.iv1350.mypos.integration.Inventory;
import se.kth.iv1350.mypos.integration.ItemIdentifier;
import se.kth.iv1350.mypos.integration.Printer;
import se.kth.iv1350.mypos.integration.Register;
import se.kth.iv1350.mypos.model.Amount;
import se.kth.iv1350.mypos.model.FinalSaleInfo;
import se.kth.iv1350.mypos.model.Item;
import se.kth.iv1350.mypos.model.Sale;
import se.kth.iv1350.mypos.model.SaleItem;
import java.io.ByteArrayInputStream;
import se.kth.iv1350.mypos.DTO.SaleDTO;
import se.kth.iv1350.mypos.model.Receipt;

/**
 *
 * @author jojoa
 */
public class ControllerTest {
    private Controller contr;
    private Sale sale;
    private ItemDTO itemDTO;
    private Inventory inv;
    public ControllerTest() {
    }

   
    
    @BeforeEach
    public void setUP(){
        
        Amount price = new Amount(56.90);
        Amount VAT = new Amount(12);
        itemDTO = new ItemDTO("Kaffe",VAT,price,1006);
        inv = new Inventory();
        sale = new Sale();
        inv.initiateList();
        Register reg = new Register(new Amount(0));
        Accounting acc = new Accounting(new Amount(0));
        Discount disc = new Discount();
        ItemIdentifier eitem = new ItemIdentifier();
        eitem.initiateList();
        Printer print = new Printer();
        contr = new Controller(eitem, print, inv, disc, reg, acc);
        
        
        
        
    }
    
    @Test
    public void testSaleStart() {
        
    }
    
    @AfterEach
    public void tearDown(){
    
    }

    @Test
    public void testNotValidItemScanning() {
        contr.saleStart();
        int itemidentifier = 90;
       
        assertThrows(IllegalArgumentException.class, ()->{contr.scanning(itemidentifier);});
        
    }

    @Test
    public void testScanning() {
        ItemDTO actuallOut;
        int itemIdentifier = 1006;
        contr.saleStart();
        actuallOut = contr.scanning(itemIdentifier);
        
        assertNotNull(actuallOut);
        
    }
    
    @Test
    public void testItemAlreadyInSale() {
       contr.saleStart();
       
       Amount quantity = new Amount(3);
       int id = 1006;
       SaleItem saleitem = contr.sale.addItemToSale(itemDTO, new Amount(1));
       SaleItem toGetQuantity = null;
       contr.sale.addSaleItemToList(saleitem);
       contr.itemAlreadyInSale(quantity, id);
       List<SaleItem> saleList = contr.sale.getSaleList();
       for(SaleItem item : saleList){
       if(item.getItemDTO().getID() == id){
          toGetQuantity = item; 
       }  
       }
       
       assertEquals(toGetQuantity.getQuantity(), quantity);
        
    }
    
    @Test
    public void testAllItemScanned() {
        contr.saleStart();
        int itemidentifier = 1006;
        contr.scanning(itemidentifier);
        SaleDTO finalsale = contr.allItemScanned();
       
        assertNotNull(finalsale);
        
    }
    
    
    @Test
    public void testPaymentChangeFinalizeSale(){
        contr.saleStart();
        contr.scanning(1003);
        contr.scanning(1002);
        Amount cash = new Amount(700);
        contr.allItemScanned();
        double expectedChange = 636.72;
        contr.finalizeSale(cash);
        assertEquals(expectedChange, contr.finalSale.getChange().getAmount(), 0.01);
    }
    
    @Test
    public void testPaymentPaidFinalizeSale(){
        contr.saleStart();
        contr.scanning(1003);
        contr.scanning(1002);
        Amount cash = new Amount(700);
        contr.allItemScanned();
        double expectedPaid = 63.28;
        contr.finalizeSale(cash);
        assertEquals(expectedPaid, contr.finalSale.getPaid().getAmount(), 00.1);
    }
    
    @Test
    public void testReceiptCreatedFinalizeSale(){
        contr.saleStart();
        contr.scanning(1003);
        contr.scanning(1002);
        Amount cash = new Amount(700);
        contr.allItemScanned();
        Receipt receipt = contr.finalSale.createReceipt(contr.finalSale);
        contr.finalizeSale(cash);
        assertNotNull(receipt);
    }
}
