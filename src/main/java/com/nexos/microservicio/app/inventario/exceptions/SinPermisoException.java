package com.nexos.microservicio.app.inventario.exceptions;

public class SinPermisoException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public SinPermisoException(String message) {
        super(message);
    }

    public SinPermisoException(String message, Throwable cause) {
        super(message, cause);
    }

}