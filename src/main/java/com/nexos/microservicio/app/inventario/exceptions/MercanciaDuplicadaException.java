package com.nexos.microservicio.app.inventario.exceptions;

public class MercanciaDuplicadaException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public MercanciaDuplicadaException(String message) {
        super(message);
    }

    public MercanciaDuplicadaException(String message, Throwable cause) {
        super(message, cause);
    }

}