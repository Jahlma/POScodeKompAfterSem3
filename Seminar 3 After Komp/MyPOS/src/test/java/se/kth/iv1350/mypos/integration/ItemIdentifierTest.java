/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package se.kth.iv1350.mypos.integration;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author jojoa
 */
public class ItemIdentifierTest {
    
    public ItemIdentifierTest() {
    }

   

    @Test
    public void testLookupItem() {
        ItemIdentifier eitem = new ItemIdentifier();
        eitem.initiateList();
        boolean found = eitem.lookupItem(1001);
        boolean notFound = eitem.lookupItem(1);
        assertTrue(found);
        assertFalse(notFound);
    }    
        
        
    
    
}
    

