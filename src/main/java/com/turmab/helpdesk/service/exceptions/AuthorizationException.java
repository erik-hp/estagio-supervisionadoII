package com.turmab.helpdesk.service.exceptions;

/**
 * Exceção lançada quando um usuário não tem permissão para executar uma ação
 * (Ex: Deletar um recurso de nível superior).
 * Deve ser tratada com o status HTTP 403 Forbidden.
 */
public class AuthorizationException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
}