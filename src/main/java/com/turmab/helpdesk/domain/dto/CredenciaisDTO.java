package com.turmab.helpdesk.domain.dto;

import java.io.Serializable;

/**
 * DTO (Data Transfer Object) utilizado para receber as credenciais de login
 * do usuário (email e senha) na requisição de autenticação.
 */
public class CredenciaisDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Email do usuário. */
    private String email;

    /** Senha do usuário. */
    private String senha;

    /** Construtor padrão. */
    public CredenciaisDTO() {
    }

    /**
     * Construtor completo.
     * @param email O email do usuário.
     * @param senha A senha do usuário.
     */
    public CredenciaisDTO(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    // --- Getters e Setters ---

    /** Retorna o email. */
    public String getEmail() {
        return email;
    }

    /** Define o email. */
    public void setEmail(String email) {
        this.email = email;
    }

    /** Retorna a senha. */
    public String getSenha() {
        return senha;
    }

    /** Define a senha. */
    public void setSenha(String senha) {
        this.senha = senha;
    }
}