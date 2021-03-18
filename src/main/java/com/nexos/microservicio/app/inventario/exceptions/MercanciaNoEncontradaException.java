package com.nexos.microservicio.app.inventario.exceptions;

public class MercanciaNoEncontradaException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public MercanciaNoEncontradaException(String message) {
        super(message);
    }

    public MercanciaNoEncontradaException(String message, Throwable cause) {
        super(message, cause);
    }

}