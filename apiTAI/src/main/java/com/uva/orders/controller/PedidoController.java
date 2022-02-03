package com.uva.orders.controller;

import java.util.List;
import java.util.Optional;

import com.uva.orders.exception.PedidoException;
import com.uva.orders.model.Estado;
import com.uva.orders.model.LineaPedido;
import com.uva.orders.model.Pedido;
import com.uva.orders.repository.LineaPedidoRepository;
import com.uva.orders.repository.PedidoRepository;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final LineaPedidoRepository lineaPedidoRepository;

    PedidoController(PedidoRepository pedidoRepository, LineaPedidoRepository lineaPedidoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.lineaPedidoRepository = lineaPedidoRepository;
    }

    /**
     * Crea una nuevo pedido apartir de una paricion POST a /orders
     * mediante el json recibido
     * 
     * @param newPedido
     * @return
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String newPedido(@RequestBody Pedido newPedido) {
        try {
            List<LineaPedido> products = newPedido.getProducts();
            for (LineaPedido product : products) product.setPedido(newPedido);
            pedidoRepository.saveAndFlush(newPedido);
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
    public Pedido getPedidoById(@PathVariable int id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new PedidoException("Sin resultado"));
        return pedido;
    }

    /**
     * Edita el pedido con id param{id} apartir de una peticion PUT a
     * /orders/{id} mediante el json recibido
     * 
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public String putPedido(@PathVariable("id") Integer identificador, @RequestBody Pedido pedido) {
        Optional<Pedido> existePedido = pedidoRepository.findById(identificador);
        if (!existePedido.isPresent())
            throw new PedidoException("No exite el pedido modificado");
        
        Pedido pedidoModificable = existePedido.get();
        switch(pedidoModificable.getStatus()){
            case Delivered:
            case In_transit:
                break;
            case Ordered:
                List<LineaPedido> products = lineaPedidoRepository.findByPedidoId(identificador);
                try {
                    lineaPedidoRepository.deleteAll(products);
                    List<LineaPedido> newProducts = pedido.getProducts();
                    for (LineaPedido product : newProducts) product.setPedido(pedidoModificable);
                    lineaPedidoRepository.saveAll(newProducts);
                } catch (Exception e){
                    lineaPedidoRepository.saveAll(products);
                    throw new PedidoException("Error al modificar los nuevos productos.");
                }
                pedidoModificable.setProducts(pedido.getProducts());
            case Processing:
                pedidoModificable.setDeliveryAddress(pedido.getDeliveryAddress());
                pedidoModificable.setDeliveryCity(pedido.getDeliveryCity());
                pedidoModificable.setDeliveryPerson(pedido.getDeliveryPerson());
                pedidoModificable.setDeliveryPostalCode(pedido.getDeliveryPostalCode());
                break;
        }
        pedidoModificable.setStatus(pedido.getStatus());
        try {
            pedidoRepository.save(pedidoModificable);
            return "Registro modificado";
        } catch (Exception e) {
            throw new PedidoException("Error al modificar el registro.");
        }
    }

    /**
     * Borra el pedido con id param{id} apartir de
     * una peticion DELETE a /orders/{id}
     * 
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public String deletePedido(@PathVariable Integer id) {
        try {
            if(pedidoRepository.existsById(id)){
                Optional<Pedido> pedido = pedidoRepository.findById(id);
                if(pedido.get().getStatus().equals(Estado.Ordered)){
                    List<LineaPedido> products = lineaPedidoRepository.findByPedidoId(id);
                    lineaPedidoRepository.deleteAll(products);
                    pedidoRepository.deleteById(id);
                    return "Pedido eliminado: " + id;
                }else{
                    return "Pedido no se puede eliminar: " + id;
                }
            }else{
                return "Pedido con id: " + id + " no existe";
            }
        } catch (Exception e) {
            throw new PedidoException("Error al elimiar el registro.");
        }
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
    public List<Pedido> getPedidos() {
        List<Pedido> list = pedidoRepository.findAll();
        return list;
    }
}