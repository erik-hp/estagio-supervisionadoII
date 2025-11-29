package com.turmab.helpdesk.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.turmab.helpdesk.domain.Cliente;
import com.turmab.helpdesk.domain.enums.Perfil;

import javax.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * DTO (Data Transfer Object) para a entidade {@link Cliente}.
 * <p>
 * Representa os dados essenciais para criação e atualização de um Cliente,
 * incluindo validações básicas como {@code @NotEmpty}.
 * </p>
 */
public class ClienteDTO {
    
	/** ID do Cliente.*/
    private Integer id;
    
    /** Nome do Cliente. */
    @NotEmpty(message = "O campo NOME é requerido")
    private String nome;
    
    /** CPF do Cliente*/
    @NotEmpty(message = "O campo CPF é requerido")
    private String cpf;
    
    /** Email do Cliente. */
    @NotEmpty(message = "O campo EMAIL é requerido")
    private String email;
    
    /** Senha do Cliente.*/
    @NotEmpty(message = "O campo SENHA é requerido")
    private String senha;
    
    /** Conjunto de perfis (códigos) do Cliente (ex: 2 para CLIENTE). */
    private Set<Integer> perfis;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataCriacao;
    
    /** Construtor padrão. */
    public ClienteDTO() {
    }
    /**
     * Construtor para converter a entidade {@link Cliente} para o DTO.
     * <p>
     * Converte o conjunto de {@link Perfil} para um conjunto de códigos numéricos.
     * </p>
     * @param obj A entidade Cliente a ser convertida.
     */
    public ClienteDTO(Cliente obj) {
        this.id = obj.getId();
        this.nome = obj.getNome();
        this.cpf = obj.getCpf();
        this.email = obj.getEmail();
        this.senha = obj.getSenha();
        this.dataCriacao = obj.getDataCriacao();
        this.perfis = obj.getPerfis().stream()
                .map(Perfil::getCodigo)
                .collect(Collectors.toSet());
    }
    
 // --- Getters e Setters ---

    /** Retorna o ID do cliente. */
    public Integer getId() { return id; }
    /** Define o ID do cliente. */
    public void setId(Integer id) { this.id = id; }

    /** Retorna o nome do cliente. */
    public String getNome() { return nome; }
    /** Define o nome do cliente. */
    public void setNome(String nome) { this.nome = nome; }

    /** Retorna o CPF do cliente. */
    public String getCpf() { return cpf; }
    /** Define o CPF do cliente. */
    public void setCpf(String cpf) { this.cpf = cpf; }

    /** Retorna o email do cliente. */
    public String getEmail() { return email; }
    /** Define o email do cliente. */
    public void setEmail(String email) { this.email = email; }
    
    /** Retorna a senha do cliente. */
    public String getSenha() { return senha; }
    /** Define a senha do cliente. */
    public void setSenha(String senha) { this.senha = senha; }
    
    
    public LocalDate getDataCriacao() { return dataCriacao; }
    /**
     * Retorna o conjunto de códigos de perfis.
     * @return Um {@link Set} de Integer.
     */
    
    public void setDataCriacao(LocalDate dataCriacao) { this.dataCriacao = dataCriacao; }
    public Set<Perfil> getPerfis() {
        return perfis.stream()
                .map(Perfil::toEnum)
                .collect(Collectors.toSet());
    }
    
    /** Define o conjunto de códigos de perfis. */
    public void setPerfis(Set<Integer> perfis) {
    	this.perfis = perfis;
    }
}	