/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.mypos.DTO;

/**
 *
 * @author jojoa
 */
public class RequestNewQuantityDTO {
     private ItemDTO itemDTO;
    private String message;

    public RequestNewQuantityDTO(ItemDTO itemDTO, String message) {
        this.itemDTO = itemDTO;
        this.message = message;
    }

    public ItemDTO getItemDTO() {
        return itemDTO;
    }

    public String getMessage() {
        return message;
    }
    
}
