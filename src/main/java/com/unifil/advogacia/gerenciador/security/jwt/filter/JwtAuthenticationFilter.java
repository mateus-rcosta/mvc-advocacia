package com.unifil.advogacia.gerenciador.security.jwt.filter;

import com.unifil.advogacia.gerenciador.exception.ForbiddenException;
import com.unifil.advogacia.gerenciador.security.jwt.service.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import java.util.List;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    private static final List<String> EXCLUDE_URLS = List.of(
            "/api/auth/login");

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = request.getMethod();

        if ("OPTIONS".equalsIgnoreCase(method)) {
            return true;
        }

        // Ignora rotas públicas
        return EXCLUDE_URLS.stream().anyMatch(path::equals);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Iniciado filtro de jwt na rota: {}", request.getRequestURL());
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new ForbiddenException("Token ausente ou inválido");
            }
            jwtService.validarToken(authHeader);
            filterChain.doFilter(request, response);
        } catch (ForbiddenException ex) {
            log.warn("Acesso negado: {}", ex.getMessage());
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().flush();
        }
    }
}