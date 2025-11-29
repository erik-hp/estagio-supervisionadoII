package com.turmab.helpdesk.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import com.turmab.helpdesk.domain.enums.Prioridade;
import com.turmab.helpdesk.domain.enums.Status;


/**
 * Entidade principal que representa um Chamado.
 * <p>
 * Um chamado possui informações sobre prioridade, status, título, observações,
 * e a associação obrigatória com um {@link Tecnico} e um {@link Cliente}.
 * </p>
 */
@Entity
public class Chamado implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /** ID único do Chamado. Chave primária. */
    private Integer id;
    /** A prioridade do Chamado (Baixa, Média, Alta). */
    private Prioridade prioridade;
    /** O status atual do Chamado (Aberto, Em Andamento, Encerrado). */
    private Status status;
    /** O título breve que resume o problema. */
    private String titulo;
    /** Descrição detalhada do problema/solicitação. */
    private String observacoes;
    
    /**
     * O Técnico responsável por resolver o Chamado.
     * <p>
     * Relacionamento ManyToOne.
     * </p>
     */
    
    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private Tecnico tecnico;
    
    /**
     * O Cliente que abriu o Chamado.
     * <p>
     * Relacionamento ManyToOne.
     * </p>
     */
    
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    
    /**
     * Data e hora em que o Chamado foi aberto.
     * <p>
     * É inicializada automaticamente no momento da criação.
     * </p>
     */
    
    private LocalDate dataAbertura = LocalDate.now();

    /** Construtor padrão. */
    public Chamado() {
    }
    /**
     * Construtor completo para inicialização de Chamado.
     * @param id ID do Chamado.
     * @param prioridade A prioridade do Chamado (Enum).
     * @param status O status do Chamado (Enum).
     * @param titulo O título do Chamado.
     * @param observacoes As observações/descrição do Chamado.
     * @param tecnico O {@link Tecnico} responsável.
     * @param cliente O {@link Cliente} solicitante.
     */
    public Chamado(Integer id, Prioridade prioridade, Status status, String titulo, 
                  String observacoes, Tecnico tecnico, Cliente cliente) {
        this.id = id;
        this.prioridade = prioridade;
        this.status = status;
        this.titulo = titulo;
        this.observacoes = observacoes;
        this.tecnico = tecnico;
        this.cliente = cliente;
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public Prioridade getPrioridade() { return prioridade; }
    public void setPrioridade(Prioridade prioridade) { this.prioridade = prioridade; }
    
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
    
    public Tecnico getTecnico() { return tecnico; }
    public void setTecnico(Tecnico tecnico) { this.tecnico = tecnico; }
    
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    
    public LocalDate getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(LocalDate dataAbertura) { this.dataAbertura = dataAbertura; }
}