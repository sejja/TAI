package com.uva.tai.controller;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.uva.tai.exception.TaiException;
import com.uva.tai.model.Concepto;
import com.uva.tai.model.Tai;
import com.uva.tai.model.Respuesta;
import com.uva.tai.model.Elemento;
import com.uva.tai.repository.ElementoRepository;
import com.uva.tai.repository.RespuestaRepository;
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

    private final TaiRepository taiRepository;
    private final RespuestaRepository respuestaRepository;
    private final ElementoRepository elemetoRepository;

    TaiController(TaiRepository taiRepository, RespuestaRepository respuestaRepository,
            ElementoRepository elemetoRepository) {
        this.taiRepository = taiRepository;
        this.respuestaRepository = respuestaRepository;
        this.elemetoRepository = elemetoRepository;
    }

    /**
     * Crea una nuevo tai apartir de una paricion POST:/tai
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
            taiRepository.saveAndFlush(newTai);
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
    private final Path root = Paths.get("apiTAI/src/main/resources/uploads");

    /**
     * Almacena una imagen apartir de una paricion POST:/tai/upload/:code
     * mediante el archivo recibido
     * 
     * @param newTai
     * @return
     */
    @PostMapping("/upload/{code}")
    public ResponseEntity<String> uploadData(@RequestParam("file") MultipartFile file, 
            @PathVariable String code) throws Exception {
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
            Files.copy(file.getInputStream(), this.root.resolve(code+file.getOriginalFilename()));
            System.out.println(this.root.resolve(code+file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }

        return new ResponseEntity<String>(originalName, HttpStatus.OK);
    }

    /**
     * Devuelve un cadigo apartir de una peticion GET:/tai/code
     * 
     * @param newTai
     * @return
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = { "/code" })
    public Tai getCode() {
        Tai newTai = new Tai();
        newTai.setCode(generateCode());
        while(taiRepository.existsByCode(newTai.getCode())){
            newTai.setCode(generateCode());
        }
        return newTai;
    }

    private String generateCode() {
        String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String code = "";
        int CODELENGH = 4;
        for (int i = 0; i < CODELENGH; i++) {
            code = code + charSet.charAt((int) (charSet.length() * Math.random()));
        }
        return code;
    }

    /**
     * Devuelve la lista de todos los tais. /tai
     * 
     * 
     * 
     * @return
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Tai> getTais() {
        List<Tai> list = taiRepository.findAll();
        return list;
    }

    /**
     * Devuelve el tai con GET: /tai/:id
     * 
     * @param id
     * @return
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{id}")
    public Optional<Tai> getTai(@PathVariable int id) {
        Optional<Tai> tai = null;
        if(taiRepository.existsById(id)){
            tai = taiRepository.findById(id);
        }
        return tai;
    }

    /**
     * Crea una nuevo respuesta apartir de una paricion POST:/tai/:id
     * mediante el json recibido
     * 
     * @param newTai
     * @return
     */
    @PostMapping("/{id}")
    public String newRespuesta(@RequestBody Respuesta newRespuesta) {

        try {
            List<Elemento> resp = newRespuesta.getResp();
            for (Elemento element : resp)
                element.setResp(newRespuesta);
            respuestaRepository.saveAndFlush(newRespuesta);
            List<Elemento> elementos = newRespuesta.getResp();
            generateResult(elementos);
            return "Nuevo registro creado";//Mejor el id de la respuesta
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

    void generateResult(List<Elemento> elementos){//Hoy que refactorizar esto

        System.out.println("\n->size " + elementos.size());

        ArrayList<Elemento> correctas = new ArrayList<>();
        ArrayList<Elemento> incorretas = new ArrayList<>();
        for (Elemento elemento : elementos) {//Clasificcación de correctas y incorrectas
            if(elemento.getCorrecta() || elemento.getTiempo() < 300 || elemento.getTiempo() > 10000){
                correctas.add(elemento);
            }else{
                incorretas.add(elemento);
            }
        }
        int mediaCorrectas = 0;
        int sumaCorrectas = 0;
        for (Elemento elemento : correctas) {//Calculo de media de correctas
            sumaCorrectas += elemento.getTiempo();
        }
        mediaCorrectas =  Math.round(sumaCorrectas / correctas.size());

        for (Elemento elemento : elementos) {//sustitucion
            if (!elemento.getCorrecta()) {
                elemento.setCorrecta(true);
                elemento.setTiempo(mediaCorrectas + 600);
            }
        }

        //Los datos ya tienen el formato correcto
        int media1 = 0;
        int media2 = 0;
        int media = 0;
        ArrayList<Elemento> test1 = new ArrayList<>();
        ArrayList<Elemento> test2 = new ArrayList<>();
        for (Elemento elemento : elementos) {
            if (elemento.getTipo().equals("Test1")) {
                test1.add(elemento);
                media1 += elemento.getTiempo();
            } else {
                test2.add(elemento);
                media2 += elemento.getTiempo();
            }
            media += elemento.getTiempo();
        }

        media1 = Math.round(media1 / test1.size());
        media2 = Math.round(media2 / test2.size());
        media = Math.round(media / elementos.size());

        int std1 = 0;
        int std2 = 0;
        int std = 0;

        for (Elemento elemento : test1) {
            std1 += Math.pow(elemento.getTiempo() - media1, 2);
        }

        for (Elemento elemento : test2) {
            std2 += Math.pow(elemento.getTiempo() - media2, 2);
        }

        for (Elemento elemento : elementos) {
            std += Math.pow(elemento.getTiempo() - media, 2);
        }

        int dif = media1 - media2;

        System.out.println("\n-> dif:"+dif+" media1:"+media1+" media2:"+media2+" media:"+media+
        " std1:"+std1+" std2:"+std2+" std:"+std);

    }



}