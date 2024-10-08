package com.uva.tai.controller;

import java.util.stream.Collectors;

import com.uva.tai.exception.AuthException;
import com.uva.tai.model.Auth;
import com.uva.tai.model.Usuario;
import com.uva.tai.repository.UsuarioRepository;

import java.util.List;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.sql.Date;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
class ControllerAuth {

    private final UsuarioRepository repository;

    private final String PREFIX = "Bearer ";
    private final String SECRET = "asdfhdjskalskdjfhalduiweuhdlajadhaiuehdljdhiqouehfjlajsdhfuhofqhqehdjahldjhfiqeu";

    ControllerAuth(UsuarioRepository repository) {
        this.repository = repository;
    }

    /**
     * Recibe una autenticacion con los compos email, password y token, este ultimo vacio y devuelve la misma aultentacion sin contraseña
     * pero con el tocken jwt que se uasará para autneticarse en el sistema
     * @param auth
     * @return
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Auth newUsuario(@RequestBody Auth auth) {
        if (repository.existsUsuarioByEmail(auth.getEmail())) {
            Usuario usuario = repository.getByEmail(auth.getEmail());
            
            if(usuario.getPassword().equals(String.valueOf(auth.getPassword().hashCode()))){
                // We need a signing key, so we'll create one just for this example. Usually
                // the key would be read from your application configuration instead.
                Auth respon = new Auth();
                respon.setEmail(usuario.getEmail());
                respon.setToken(getJWTToken(usuario.getEmail()));
                return respon;
            }
        }//FAlta error 403
        throw new AuthException("Error al autentificar.");
    }


    /**
     * Devuelve un token jwt valido para la autenticacion
     * @param username
     * @return
     */
    private String getJWTToken(String username) {
        String secretKey = SECRET;
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))// Entes 10 min de sesion 600000
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes())
                .compact();

        return PREFIX + token;
    }

}