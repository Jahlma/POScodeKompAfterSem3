/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.mypos.integration;

import java.util.ArrayList;
import java.util.List;
import se.kth.iv1350.mypos.model.SaleItem;

/**
 * Has a list of item identifiers which represent all available items for sale.
 * 
 */
public class ItemIdentifier {
    public List<Integer> listOfIdentifiers; 
    
    
    public ItemIdentifier(){
       listOfIdentifiers = new ArrayList<>();
       
    }
    
    /**
    * Method looks trough sale list and returns a boolean value if its found or not.
    * @param itemIdentifier identifier that identifies specific items.
    */
    public boolean lookupItem(int itemIdentifier){
        for(Integer id : listOfIdentifiers){
            if(id.intValue() == itemIdentifier){
                return true;
                }
            }
        return false; 
    }
    
    public List<Integer> initiateList(){
        listOfIdentifiers.add(1001);
        listOfIdentifiers.add(1002);
        listOfIdentifiers.add(1003);
        listOfIdentifiers.add(1004);
        listOfIdentifiers.add(1005);
        listOfIdentifiers.add(1006);
        listOfIdentifiers.add(1007);
        listOfIdentifiers.add(1008);
        listOfIdentifiers.add(1009);
        listOfIdentifiers.add(1010);
        return listOfIdentifiers;
    }
}
