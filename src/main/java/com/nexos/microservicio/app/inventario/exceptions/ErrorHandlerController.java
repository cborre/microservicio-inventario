package com.nexos.microservicio.app.inventario.exceptions;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class ErrorHandlerController extends ResponseEntityExceptionHandler {
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex,
			HttpHeaders headers,
			HttpStatus status,
			WebRequest request) {
		
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		String mensaje = "";
		
		if(ex.getMessage().contains("Date")) {
			mensaje = "Verifique que los campos de FECHA tengan el formato correcto.";
		}
		
		if(ex.getMessage().contains("Integer")) {
			mensaje = "Verifique que los campos de ENTEROS tengan el formato correcto.";
		}
		
		body.put("message", "Verifiquese que los datos se estan pasando correctamente." + mensaje);

		// Return
		return new ResponseEntity<Object>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}