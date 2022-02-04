package com.uva.tai.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class TaiException extends RuntimeException {
    //Crea una excepcion con el mensaje especificado
    public TaiException(String mensaje) {
        super(mensaje);
    }
}
