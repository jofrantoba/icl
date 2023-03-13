/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gob.pe.icl.zuulone.util;

/**
 *
 * @author jonathan.torres
 */
public enum EnumError {
    ERRCONNECTREFUSA("Connection refused: connect"),
    ERRCONNECTRESET("Connection reset"),
    ERRCLIENTNOAVAILABLE("Load balancer does not have available server for client");    
    private final String value;

    private EnumError(String value) {
        this.value = value;
    }

    public static boolean contains(String strError) {

        for (EnumError error : EnumError.values()) {
            if (strError.contains(error.getValue())) {
                return true;
            }
        }
        return false;
    }

    public String getValue() {
        return value;
    }
    
    
}
