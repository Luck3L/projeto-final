package br.gov.caixaverso.projeto.security;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.jwt.Claims;

@ApplicationScoped
public class JwtGenerator {

    public String generateSignJwt(String nome, String role) {
        return Jwt.claim(Claims.sub, nome)
                .groups(role)
                .jws()
                .sign();
    }
}
