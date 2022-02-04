package com.uva.orders.controller;

import java.util.List;


import com.uva.orders.exception.PedidoException;

import com.uva.orders.model.Concepto;
import com.uva.orders.model.Tai;
///import com.uva.orders.repository.ConceptoRepository;
import com.uva.orders.repository.TaiRepository;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/tai")
@CrossOrigin(origins = "*")
public class PedidoController {

    private final TaiRepository pedidoRepository;
    //private final ConceptoRepository lineaPedidoRepository;

    PedidoController(TaiRepository pedidoRepository /*ConceptoRepository lineaPedidoRepositor*/) {
        this.pedidoRepository = pedidoRepository;
       // this.lineaPedidoRepository = lineaPedidoRepository;
    }

    /**
     * Crea una nuevo pedido apartir de una paricion POST a /orders
     * mediante el json recibido
     * 
     * @param newTai
     * @return
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String newTai(@RequestBody Tai newTai) {

        try {
            List<Concepto> concepts = newTai.getConcepts();
            for (Concepto concept : concepts) concept.setTai(newTai);
            System.out.print(concepts.size()+" "+concepts.get(0).getStatus());
            pedidoRepository.saveAndFlush(newTai);
            return "Nuevo registro creado";
        } catch (Exception e) {
            // Se deja esta parte comentada como alternativa a la gestion de errores
            // propuesta
            // throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Error al
            // crear el nuevo registro.");
            // Se usa este sistema de gestión de errores porque es mas sencillo hacer que el
            // cliente reciba el mensaje de error
            e.printStackTrace();
            throw new PedidoException("Error al crear el nuevo registro.");
        }
    }
    
    /**
     * Devuelve el pedido con id param{id} apartir de una
     * peticion GET a /orders/{id}
     * 
     * @param id
     * @return
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = { "/{id}" })
    public Tai getPedidoById(@PathVariable int id) {
        Tai pedido = pedidoRepository.findById(id).orElseThrow(() -> new PedidoException("Sin resultado"));
        return pedido;
    }

    /**
     * Devuelve la lista de todos los usuarios si no se especifica parametro. Con
     * parametro enable param{enable} debolverá la lista de los usuarios activo o
     * inavtios en funcion del valor de enable: true o false GET a GET /orders
     * 
     * @param enable
     * @return
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Tai> getPedidos() {
        List<Tai> list = pedidoRepository.findAll();
        return list;
    }
}