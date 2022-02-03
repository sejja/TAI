package com.uva.orders.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class PedidoException extends RuntimeException {
    //Crea una excepcion con el mensaje especificado
    public PedidoException(String mensaje) {
        super(mensaje);
    }
}
