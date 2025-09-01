package com.turmab.helpdesk.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import com.turmab.helpdesk.domain.dto.ClienteDTO;
import com.turmab.helpdesk.domain.enums.Perfil;

@Entity
public class Cliente extends Pessoa {
    
    private static final long serialVersionUID = 1L;
    
    @OneToMany(mappedBy = "cliente")
    private List<Chamado> chamados = new ArrayList<>();
    
    public Cliente() {
        super();
        addPerfil(Perfil.CLIENTE);
    }

    public Cliente(Integer id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha);
        addPerfil(Perfil.CLIENTE);
    }

    // Construtor para DTO
    public Cliente(ClienteDTO objDTO) {
        this.id = objDTO.getId();
        this.nome = objDTO.getNome();
        this.cpf = objDTO.getCpf();
        this.email = objDTO.getEmail();
        this.senha = objDTO.getSenha();
        this.perfis = objDTO.getPerfis().stream()
                .map(Perfil::getCodigo)
                .collect(Collectors.toSet());
        addPerfil(Perfil.CLIENTE);
    }

    public List<Chamado> getChamados() {
        return chamados;
    }

    public void setChamados(List<Chamado> chamados) {
        this.chamados = chamados;
    }
}