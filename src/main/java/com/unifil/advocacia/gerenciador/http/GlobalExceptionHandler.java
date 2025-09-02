package com.unifil.advocacia.gerenciador.http;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.unifil.advocacia.gerenciador.exception.BadRequestException;
import com.unifil.advocacia.gerenciador.exception.ForbiddenException;
import com.unifil.advocacia.gerenciador.exception.NotFoundException;
import com.unifil.advocacia.gerenciador.exception.UnauthorizedException;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;



@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGlobalException(Exception ex) {
        log.error("Erro interno: {}", ex.getMessage());
        ApiResponse<String> resposta = new ApiResponse<>(
            "Erro interno no servidor",
            500,
            LocalDateTime.now(),
            null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resposta);
    }

    // Captura erros de validação do @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, Boolean>>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, Boolean> erros = new HashMap<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            erros.put(fieldError.getField(), true);
        }
        log.info("Usuário fez requisicao porem nao completou os campos obrigatorios: {}", erros);
        ApiResponse<Map<String, Boolean>> resposta = new ApiResponse<Map<String, Boolean>>(
            "Erro de validação de campo",
            400,
            LocalDateTime.now(),
            erros
        );

        return ResponseEntity.badRequest().body(resposta);
    }
    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Map<String, Boolean>>> handleNotFoundException(NotFoundException ex){
        log.info("Não encontrado: \n{}", ex.getRecurso());
        ApiResponse<Map<String, Boolean>> resposta = new ApiResponse<Map<String, Boolean>>(
            ex.getMessage(),
            404,
            LocalDateTime.now(),
            ex.getRecurso()
        );

        return ResponseEntity.status(404).body(resposta);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Map<String, Boolean>>> handleBadRequestException(BadRequestException ex){
        log.info("Inválido: \n{}", ex.getRecurso());
        ApiResponse<Map<String, Boolean>> resposta = new ApiResponse<Map<String, Boolean>>(
            ex.getMessage(),
            400,
            LocalDateTime.now(),
            ex.getRecurso()
        );

        return ResponseEntity.status(400).body(resposta);
    }

    @ExceptionHandler(SignatureVerificationException.class)
    public ResponseEntity<Void> handleSignatureVerificationException(SignatureVerificationException ex){
        log.info("Tentativa de acesso com chave assinada com outra secret.");
        return ResponseEntity.status(403).build();
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ApiResponse<String>> handleTokenExpiredException(TokenExpiredException ex){
        log.info("Tentativa de acesso com token expirado.");
        ApiResponse<String> resposta = new ApiResponse<>(
            "Token expirado",
            401,
            LocalDateTime.now(),
            null
        );

        return ResponseEntity.status(401).body(resposta);
    }

    @ExceptionHandler(AlgorithmMismatchException.class)
    public ResponseEntity<ApiResponse<String>> handleAlgorithmMismatchException(AlgorithmMismatchException ex){
        log.info("Tentativa de decodificação com algoritmo errado");
        return ResponseEntity.status(403).build();
    }

    @ExceptionHandler(JWTDecodeException.class)
    public ResponseEntity<ApiResponse<String>> handleJWTDecodeException(JWTDecodeException ex){
        log.info("Tentativa de decodificação com algoritmo errado");
        return ResponseEntity.status(403).build();
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiResponse<String>> handleForbiddenException(ForbiddenException ex){
        log.info("{}", ex.getMessage());
        return ResponseEntity.status(403).build();
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<String>> handleUnauthorizedException(UnauthorizedException ex){
        log.info("{}", ex.getMessage());
        return ResponseEntity.status(401).build();
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<ApiResponse<String>> handleJWTVerificationException(JWTVerificationException ex){
        log.info("Erro na verificação de token: {}", ex.getMessage());
        ApiResponse<String> resposta = new ApiResponse<>(
            ex.getMessage(),
            401,
            LocalDateTime.now(),
            null
        );

        return ResponseEntity.status(401).body(resposta);
    }
}