package com.unifil.advogacia.gerenciador.security.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    @Value("${api.security.token.secret}")
    private String SECRET;

    public String criarToken(String subject, String hotspotId){
        return JWT.create().withSubject(subject).sign(Algorithm.HMAC256(SECRET));
    }

    public DecodedJWT validarToken(String token){
        if(token == null || token.isEmpty() || token.isBlank()){
            throw new JWTVerificationException("Token nao recebido");
        }
        return JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token.replaceAll("Bearer", "").strip());
    } 
}
