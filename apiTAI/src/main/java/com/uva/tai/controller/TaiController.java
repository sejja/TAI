package com.uva.tai.controller;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.uva.tai.exception.TaiException;
import com.uva.tai.model.Concepto;
import com.uva.tai.model.Tai;
import com.uva.tai.model.Respuesta;
import com.uva.tai.model.Resultado;
import com.uva.tai.model.Elemento;
import com.uva.tai.repository.ConceptoRepository;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;


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
    private final ElementoRepository elementoRepository;
    private final ResultadoRepository resultadoRepository;
    private final ConceptoRepository conceptoRepository;

    private static final Logger logger = Logger.getLogger(TaiController.class.getName());
    String url = "uploads";
    private final Path root = Paths.get(url);

    TaiController(TaiRepository taiRepository, RespuestaRepository respuestaRepository,
            ElementoRepository elementoRepository, ResultadoRepository resultadoRepository,
            ConceptoRepository conceptoRepository) {

        this.taiRepository = taiRepository;
        this.respuestaRepository = respuestaRepository;
        this.elementoRepository = elementoRepository;
        this.resultadoRepository = resultadoRepository;
        this.conceptoRepository = conceptoRepository;

        System.out.println("Ruta: " + root.toString());
        
        if (!Files.exists(root)) {
            try {
                Files.createDirectory(root);
            } catch (IOException e) {
                throw new RuntimeException("Could not initialize folder for upload!");
            }
        }
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

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/{id}/enable")
    public String enableTai(@RequestBody Boolean enable, @PathVariable int id) {

        Tai tai = this.getTai(id).get();
        tai.setEnable(enable);
        taiRepository.saveAndFlush(tai);

        return id+":"+tai.getEnable();
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
            
            if(getTai(newRespuesta.getIdTai()).get().getEnable()){
                List<Elemento> resp = newRespuesta.getResp();
                for (Elemento element : resp)
                    element.setResp(newRespuesta);
                respuestaRepository.saveAndFlush(newRespuesta);
                Respuesta respuesta = respuestaRepository.findTopByOrderByIdDesc();
                generateResult(respuesta);
                return respuesta.getId().toString();// devualve el id de la respuesta
            }else {
                return "tai no activo";
            }
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
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Bloque3", 0);
        map.put("Bloque4", 0);
        map.put("Bloque6", 0);
        map.put("Bloque7", 0);

        ArrayList<Elemento> correctas = new ArrayList<>();
        ArrayList<Elemento> incorretas = new ArrayList<>();
        for (Elemento elemento : elementos) {//Clasificcación de correctas y incorrectas
            
            if(elemento.getCorrecta() && elemento.getTiempo() > 300 && elemento.getTiempo() < 10000){
                correctas.add(elemento);
                map.put(elemento.getTipo(), map.get(elemento.getTipo()) + 1);
            }else{
                incorretas.add(elemento);
            }
        }
        
        if(correctas.size() > Math.round(elementos.size() * 0.4)){

            int mediaCorrectas = 0;
            for (Elemento elemento : correctas) {//Calculo de media de correctas
                mediaCorrectas += elemento.getTiempo();
            }
            mediaCorrectas = Math.round(mediaCorrectas / correctas.size());
            

            for (Elemento elemento : elementos) {//sustitucion
                if (!elemento.getCorrecta()) {
                    elemento.setCorrecta(true);
                    elemento.setTiempo(mediaCorrectas + 600);
                }
            }

            //Los datos ya tienen el formato correcto
            int mb3 = 0;
            int mb4 = 0;
            int mb6 = 0;
            int mb7 = 0;
            ArrayList<Elemento> b3 = new ArrayList<>();
            ArrayList<Elemento> b4 = new ArrayList<>();
            ArrayList<Elemento> b6 = new ArrayList<>();
            ArrayList<Elemento> b7 = new ArrayList<>();
            for (Elemento elemento : elementos) {
                if (elemento.getTipo().equals("Bloque3")) {
                    b3.add(elemento);
                    mb3 += elemento.getTiempo();
                } else if (elemento.getTipo().equals("Bloque4")) {
                    b4.add(elemento);
                    mb4 += elemento.getTiempo();
                } else if (elemento.getTipo().equals("Bloque6")) {
                    b6.add(elemento);
                    mb6 += elemento.getTiempo();
                } else if (elemento.getTipo().equals("Bloque7")) {
                    b7.add(elemento);
                    mb7 += elemento.getTiempo();
                }
                
            }

            if(map.get("Bloque3") > Math.round(b3.size() * 0.4)
            && map.get("Bloque4") > Math.round(b4.size() * 0.4)
            && map.get("Bloque6") > Math.round(b6.size() * 0.4)
            && map.get("Bloque7") > Math.round(b7.size() * 0.4)){

                mb3 = Math.round(mb3 / b3.size());
                mb4 = Math.round(mb4 / b4.size());
                mb6 = Math.round(mb6 / b6.size());
                mb7 = Math.round(mb7 / b7.size());


                int mb36 = Math.round((mb3 * b3.size() + mb6 * b6.size()) / (b3.size() + b6.size()));
                int mb47 = Math.round((mb4 * b4.size() + mb7 * b7.size()) / (b4.size() + b7.size()));

                int std3 = 0;
                int std4 = 0;
                int std6 = 0;
                int std7 = 0;

                int std36 = 0;
                int std47 = 0;

                for (Elemento elemento : b3) {
                    std3 += Math.pow(elemento.getTiempo() - mb3, 2);
                    std36 += Math.pow(elemento.getTiempo() - mb36, 2);
                } 
                std3 = (int) Math.round(Math.sqrt(std3 / b3.size()));

                for (Elemento elemento : b4) {
                    std4 += Math.pow(elemento.getTiempo() - mb4, 2);
                    std47 += Math.pow(elemento.getTiempo() - mb47, 2);
                }
                std4 = (int) Math.round(Math.sqrt(std4 / b4.size()));

                for (Elemento elemento : b6) {
                    std6 += Math.pow(elemento.getTiempo() - mb6, 2);
                    std36 += Math.pow(elemento.getTiempo() - mb36, 2);
                }
                std6 = (int) Math.round(Math.sqrt(std6 / b6.size()));
                for (Elemento elemento : b7) {
                    std7 += Math.pow(elemento.getTiempo() - mb7, 2);
                    std47 += Math.pow(elemento.getTiempo() - mb47, 2);
                }
                std7 = (int) Math.round(Math.sqrt(std7 / b7.size()));

                std36 = (int) Math.round(Math.sqrt(std36 / (b3.size() + b6.size())));
                std47 = (int) Math.round(Math.sqrt(std47 / (b4.size() + b7.size())));

                int diff63 = mb6 - mb3;
                int diff74 = mb7 - mb4;

                float dscore36 = diff63 * 1f / std36;
                float dscore47 = diff74 * 1f / std47;


                System.out.println("\n-> medias: " + mb3 + ", " + mb4 + ", " + mb6 + ", " + mb7 + ".\n"+
                                    " desvest: " + std3 + ", " + std4 + ", " + std6 + ", " + std7 + ".\n" +
                                    " scores:3 y 6: " + dscore36 + ", scores:4 y 7: " + dscore47);

                Resultado resultado = new Resultado(newRespuesta.getId(), newRespuesta.getIdTai(),  mb3,  mb4,  mb6,  mb7, mb36,  mb47, 
                    std3, std4, std6, std7, std36, std47);

                resultadoRepository.saveAndFlush(resultado);
            }
        }
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


    @DeleteMapping("/{id}")
    public String deleteTai(@PathVariable int id) {
        Tai tai = taiRepository.findById(id).get();

        // eliminar las imagenes
        eliminarPorPrefijo(url, tai.getCode());

        List<Concepto> conceptos = tai.getConcepts();
        List<Respuesta> respuestas = respuestaRepository.findByIdTai(id);
        List<Resultado> resultados = resultadoRepository.findByIdTai(id);
        try {
            for (Resultado resultado : resultados) {
                resultadoRepository.deleteById(resultado.getId());
            }
            for (Respuesta respuesta : respuestas) {
                List<Elemento> elementos = respuesta.getResp();
                for (Elemento elemento : elementos) {
                    elementoRepository.deleteById(elemento.getId());
                }
                respuestaRepository.deleteById(respuesta.getId());
            }

            for (Concepto concepto : conceptos) {
                conceptoRepository.deleteById(concepto.getSku());
            }
            taiRepository.deleteById(id);
            return "Tai eliminado: " + id;
        } catch (Exception e) {
            throw new TaiException("Error al eliminar el nuevo registro.");
        }
    }

    /**
     * Elimina los archivos con un determinado prefijo de una carpeta
     * 
     * @param path   Carpeta de la cual eliminar los archivos
     * @param prefix Prefijo de los archivos a eliminar
     */
    public static void eliminarPorPrefijo(String path, final String prefix){
        File[] archivos = new File(path).listFiles(new FileFilter() {
            public boolean accept(File archivo) {
                if (archivo.isFile())
                    return archivo.getName().startsWith(prefix);
                return false;
            }
        });
        for (File archivo : archivos)
            archivo.delete();
    }

    public static File[] getPorPrefijo(String path, final String prefix){
        File[] archivos = new File(path).listFiles(new FileFilter() {
            public boolean accept(File archivo) {
                if (archivo.isFile())
                    return archivo.getName().startsWith(prefix);
                return false;
            }
        });
        return archivos;
    }



    @PostMapping("/{id}/clone")
    public String cloneTai(@PathVariable int id, @RequestBody String name) {
        Tai tai = taiRepository.findById(id).get();
        String code = getCode().getCode();
        File[] files = getPorPrefijo(url, tai.getCode());
        System.out.println("Numero de fortos: "+files.length);
        for (File file : files) {
            try {
                FileInputStream input = new FileInputStream(file);
                String fileName = file.getName().substring(4);
                System.out.println("file name: "+code+fileName);
                Files.copy(input, this.root.resolve(code+fileName));
                System.out.println(this.root.resolve(code+fileName));
            } catch (Exception e) {
                throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
            }
        }

        try {
            tai.setName(name);
            tai.setCode(code);
            tai.setConcepts(null);
            tai.setId(0);
            taiRepository.saveAndFlush(tai);
            Tai taiClone = taiRepository.findTopByOrderByIdDesc();
            List<Concepto> concepts = conceptoRepository.findByTaiId(id);
            for (Concepto concept : concepts) {
                concept.setSku(0);
                concept.setTai(taiClone);
            }
            taiClone.setConcepts(concepts);
            taiRepository.saveAndFlush(taiClone);
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

}