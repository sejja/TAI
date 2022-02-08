package com.uva.tai.controller;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import com.uva.tai.exception.TaiException;
import com.uva.tai.model.Concepto;
import com.uva.tai.model.Tai;
import com.uva.tai.repository.TaiRepository;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;


import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/tai")
@CrossOrigin(origins = "*")
public class TaiController {

    private final TaiRepository pedidoRepository;
    //private final ConceptoRepository lineaPedidoRepository;

    TaiController(TaiRepository pedidoRepository /*ConceptoRepository lineaPedidoRepositor*/) {
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
            throw new TaiException("Error al crear el nuevo registro.");
        }
    }


    private static final Logger logger = Logger.getLogger(TaiController.class.getName());
    private final Path root = Paths.get("uploads");

    @PostMapping("/upload")
    public ResponseEntity<String> uploadData(@RequestParam("file") MultipartFile file) throws Exception {
        if (file == null) {
            throw new RuntimeException("You must select the a file for uploading");
        }
        InputStream inputStream = file.getInputStream();
        String originalName = file.getOriginalFilename();
        String name = file.getName();
        String contentType = file.getContentType();
        long size = file.getSize();
        logger.info("inputStream: " + inputStream);
        logger.info("originalName: " + originalName);
        logger.info("name: " + name);
        logger.info("contentType: " + contentType);
        logger.info("size: " + size);
        // Do processing with uploaded file data in Service layer

        //servicio
        if(!Files.exists(root)){
            try {
                Files.createDirectory(root);
            } catch (IOException e) {
                throw new RuntimeException("Could not initialize folder for upload!");
            }
        }

        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
            System.out.println(this.root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }

        return new ResponseEntity<String>(originalName, HttpStatus.OK);
    }

    





    /**
     * Crea una nuevo pedido apartir de una paricion POST a /orders
     * mediante el json recibido
     * 
     * @param newTai
     * @return
  
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/image")
    public String newImage(@RequestParam("file") MultipartFile file) {

        if(file.isEmpty()){
            return "Archivo no seleccionado";
        }
        try{
            uploadFileService.saveFile(file);
        }catch(IOException e){
            e.printStackTrace();
        }
        return "archivo subido";
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
        Tai pedido = pedidoRepository.findById(id).orElseThrow(() -> new TaiException("Sin resultado"));
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