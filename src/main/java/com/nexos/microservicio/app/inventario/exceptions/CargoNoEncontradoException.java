package com.nexos.microservicio.app.inventario.exceptions;

public class CargoNoEncontradoException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public CargoNoEncontradoException(String message) {
        super(message);
    }

    public CargoNoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }

}