package com.zzszxyy.appointment.conmon;

public class ServiceException extends Exception {
    
    private static final long serialVersionUID = -760739769632990672L;

    public ServiceException() {

    }
    
    public ServiceException(String message) {
        super(message);
    }
    
    
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
