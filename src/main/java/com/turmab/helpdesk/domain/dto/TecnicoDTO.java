package com.turmab.helpdesk.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.turmab.helpdesk.domain.Tecnico;
import com.turmab.helpdesk.domain.enums.Perfil;

import javax.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class TecnicoDTO {
    
    private Integer id;
    
    @NotEmpty(message = "O campo NOME é requerido")
    private String nome;
    
    @NotEmpty(message = "O campo CPF é requerido")
    private String cpf;
    
    @NotEmpty(message = "O campo EMAIL é requerido")
    private String email;
    
    @NotEmpty(message = "O campo SENHA é requerido")
    private String senha;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataCriacao;
    
    private Set<Integer> perfis;
    
    public TecnicoDTO() {
    }
    
    public TecnicoDTO(Tecnico obj) {
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
    
    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    
    public Set<Perfil> getPerfis() {
        return perfis.stream()
                .map(Perfil::toEnum)
                .collect(Collectors.toSet());
    }
    
    public void setPerfis(Set<Integer> perfis) {
        this.perfis = perfis;
    }
}