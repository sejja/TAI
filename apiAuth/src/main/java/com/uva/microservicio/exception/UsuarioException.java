package com.uva.microservicio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class UsuarioException extends RuntimeException {
    //Crea una excepcion con el mensaje especificado
    public UsuarioException(String mensaje) {
        super(mensaje);
    }
}
