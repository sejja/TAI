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
import com.uva.tai.model.Resultado;
import com.uva.tai.model.Elemento;
import com.uva.tai.repository.ElementoRepository;
import com.uva.tai.repository.RespuestaRepository;
import com.uva.tai.repository.ResultadoRepository;
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
    private final ResultadoRepository resultadoRepository;

    TaiController(TaiRepository taiRepository, RespuestaRepository respuestaRepository,
            ElementoRepository elemetoRepository, ResultadoRepository resultadoRepository) {
        this.taiRepository = taiRepository;
        this.respuestaRepository = respuestaRepository;
        this.elemetoRepository = elemetoRepository;
        this.resultadoRepository = resultadoRepository;
    }

    /**
     * Crea una nuevo tai apartir de una peticion POST:/tai
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
     * Almacena una imagen apartir de una peticion POST:/tai/upload/:code
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
     * Devuelve un codigo apartir de una peticion GET:/tai/code
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

    /**
     * Genera un codigo de 4 caracteres en mayusculas
     * @return
     */

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
     * Devuelve una lista de todos los tais. 
     * Mediante la peticion GET:/tai
     * @return
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Tai> getTais() {
        List<Tai> list = taiRepository.findAll();
        return list;
    }

    /**
     * Devuelve el tai con GET:/tai/:id
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
     * Crea una nueva respuesta apartir de una peticion POST:/tai/:id
     * mediante el json recibido ademas calcula los resultados de esa 
     * respuesta y los almacena ademas devuelve el id de la respuesta
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
            Respuesta respuesta = respuestaRepository.findTopByOrderByIdDesc();
            generateResult(respuesta);
            return respuesta.getId().toString();//devualve el id de la respuesta
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

    /**
     * Calculo de resultados
     * @param newRespuesta
     */
    void generateResult(Respuesta newRespuesta){//Hay que refactorizar esto

        List<Elemento> elementos = newRespuesta.getResp();

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

        std1 = (int) Math.round(Math.sqrt(std1 / test1.size()));
        std2 = (int) Math.round(Math.sqrt(std2 / test2.size()));
        std = (int) Math.round(Math.sqrt(std / elementos.size()));

        System.out.println("\n-> dif:"+dif+" media1:"+media1+" media2:"+media2+" media:"+media+
        " std1:"+std1+" std2:"+std2+" std:"+std);

        Resultado resultado = new Resultado(newRespuesta.getId(), newRespuesta.getIdTai(), "Test1",
         media1, std1, "Test2", media2, std2, media, std);

        resultadoRepository.saveAndFlush(resultado);

    }

    /**
     * Devuelve una lista con los resultados del tai con id especificada
     * @param id
     * @return
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{id}/resultados")  
    public List<Resultado> getResultados(@PathVariable int id) {
        return resultadoRepository.findByIdTai(id);
    }

    /**
     * Divierve el resultado del tai id de la respuesta id2
     * @param id
     * @param id2
     * @return
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{id}/resultados/{id2}")
    public Optional<Resultado> getResultados(@PathVariable int id, @PathVariable int id2) {
        return resultadoRepository.findByIdResp(id2);
    }

}