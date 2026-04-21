package br.gov.caixaverso.projeto.dto;

public record SignInResponse(String token, Long expiresIn) {
}
