package com.nexos.microservicio.app.inventario.exceptions;

public class UsuarioNoEncontradoException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public UsuarioNoEncontradoException(String message) {
        super(message);
    }

    public UsuarioNoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }

}