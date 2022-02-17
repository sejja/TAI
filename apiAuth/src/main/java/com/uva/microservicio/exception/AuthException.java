package com.uva.microservicio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class AuthException extends RuntimeException {
    //Crea una excepcion con el mensaje especificado
    public AuthException(String mensaje) {
        super(mensaje);
    }
}
