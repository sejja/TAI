package com.uva.microservicio.controller;

import com.uva.microservicio.repository.UsuarioRepository;
import com.uva.microservicio.model.Usuario;
import com.uva.microservicio.exception.UsuarioException;

import java.sql.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class ControllerUsers {

    private final UsuarioRepository repository;
    private Usuario defaulUser = new Usuario("root", "root", "root", "root@root.com", "root", true,
            Date.valueOf("2021-11-28"), Date.valueOf("2021-11-28"));

    ControllerUsers(UsuarioRepository repository) {
        this.repository = repository;
        System.out.println("Antes de añadir defecto");
        this.postUsuario(defaulUser);// Añadir usuario por defecto
        System.out.println("Antes de añadir defecto:");
    }

    /**
     * Crea una nuevo usuario apartir de una paricion POST a /users
     * mediante el json recibido
     * 
     * @param newUsuario
     * @return
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String newUsuario(@RequestBody Usuario newUsuario) {
        return postUsuario(newUsuario);
    }

    public String postUsuario(Usuario newUsuario) {
        try {
            if (!repository.existsUsuarioByEmail(newUsuario.getEmail())) {
                newUsuario.setPassword(String.valueOf(newUsuario.getPassword().hashCode()));
                repository.save(newUsuario);
                return "Nuevo registro creado";
            } else {
                return "El email ya esta en uso";
            }
        } catch (Exception e) {
            // Se deja esta parte comentada como alternativa a la gestion de errores
            // propuesta
            // throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Error al
            // crear el nuevo registro.");
            // Se usa este sistema de gestión de errores porque es mas sencillo hacer que el
            // cliente reciba el mensaje de error
            throw new UsuarioException("Error al crear el nuevo registro.");
        }
    }

    /**
     * Devuelve el usuario con id param{id} apartir de una
     * peticion GET a /users/{id}
     * 
     * @param id
     * @return
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = { "/{id}" })
    public Usuario getUsuarioById(@PathVariable int id) {
        Usuario usuario = repository.findById(id).orElseThrow(() -> new UsuarioException("Sin resultado"));
        return usuario;
    }

    /**
     * Edita el usuario con id param{id} apartir de una peticion PUT a
     * /users/{id} mediante el json recibido
     * 
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public String putUsuario(@PathVariable("id") Integer identificador, @RequestBody Usuario usuario) {
        if (!repository.existsById(identificador))
            throw new UsuarioException("No exite el usuario modificado");
        if (!repository.existsUsuarioByEmail(usuario.getEmail()) ||
                repository.findByEmail(usuario.getEmail()).get(0).getId() == identificador) {// Comprobacion de email no
                                                                                             // esta en uso
            try {
                usuario.setPassword(String.valueOf(usuario.getPassword().hashCode()));
                repository.save(usuario);
                return "Registro modificado";
            } catch (Exception e) {
                throw new UsuarioException("Error al modificar el registro.");
            }
        }
        throw new UsuarioException("Email ya en uso.");
    }

    /**
     * Borra el usuario con id param{id} apartir de
     * una peticion DELETE a /users/{id}
     * 
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public String deleteUsuario(@PathVariable Integer id) {
        try {
            repository.deleteById(id);
            return "Usuario eliminado: " + id;
        } catch (Exception e) {
            throw new UsuarioException("Error al crear el nuevo registro.");
        }
    }

    /**
     * Devuelve la lista de todos los usuarios si no se especifica parametro. Con
     * parametro enable param{enable} debolverá la lista de los usuarios activo o
     * inavtios en funcion del valor de enable: true o false GET a GET /users y GET
     * /users?enable={enable} respectivamente. Si el parametro es email
     * param{email} debolverá una lista vacia si no existe y una lista con el
     * usuario
     * con el mais espacifico en caso de existir
     * 
     * @param enable
     * @param email
     * @return
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Usuario> getUsuarios(@RequestParam(required = false) Boolean enable,
            @RequestParam(required = false) String email) {
        if (enable == null && email == null) {
            return repository.findAll();
        } else if (enable != null) {
            return repository.findByEnabled(enable);
        } else {
            return repository.findByEmail(email);
        }
    }

    public Usuario getByEmail(String email) {
        List<Usuario> list = repository.findByEmail(email);
        if (list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    /**
     * Edita los usuario con id contenido en la lista de enteros param{user_id}
     * apartir de una peticion PUT a /users/enable?user_id={user_id} mosdificando
     * el estado si esta activo seguirá activo si estaba inavtivo pasará a activo
     * 
     * @param user_id
     * @return
     */
    @PutMapping(value = "/enable", produces = MediaType.APPLICATION_JSON_VALUE)
    public String setUsuariosDisponibles(@RequestParam List<Integer> user_id) {
        for (Integer id : user_id) {
            Usuario usuario = repository.findById(id).orElseThrow(() -> new UsuarioException("Sin resultado"));
            usuario.setEnabled(true);
            repository.save(usuario);
        }
        return "Lista de usuarios actualizada";
    }

}