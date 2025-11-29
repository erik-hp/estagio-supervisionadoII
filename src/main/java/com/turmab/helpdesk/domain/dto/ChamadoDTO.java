package com.turmab.helpdesk.domain.dto;

import com.turmab.helpdesk.domain.Chamado;
import com.turmab.helpdesk.domain.enums.Prioridade;
import com.turmab.helpdesk.domain.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) para a entidade {@link Chamado}.
 * <p>
 * Utilizado para receber dados da API (Criação/Atualização) e para retornar uma
 * representação simples do Chamado, incluindo apenas os IDs e nomes das entidades relacionadas.
 * </p>
 */
public class ChamadoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID do chamado. Não é preenchido na criação. */
    private Integer id;
    
    /** Data de abertura do chamado. Retornada pela API, mas não necessária na entrada. */
    private LocalDate dataAbertura;
    /**
     * Prioridade do chamado (0=BAIXA, 1=MÉDIA, 2=ALTA).
     * O campo é {@code @NotNull} e representa o código numérico do Enum.
     */
    @NotNull(message = "O campo PRIORIDADE é requerido")
    private Integer prioridade;

    /**
     * Status do chamado (0=ABERTO, 1=ANDAMENTO, 2=ENCERRADO).
     * O campo é {@code @NotNull} e representa o código numérico do Enum.
     */
    @NotNull(message = "O campo STATUS é requerido")
    private Integer status;

    /**
     * Título breve que resume o problema.
     * O campo é {@code @NotEmpty}.
     */
    @NotEmpty(message = "O campo TÍTULO é requerido")
    private String titulo;

    /**
     * Descrição detalhada do problema/solicitação.
     * O campo é {@code @NotEmpty}.
     */
    @NotEmpty(message = "O campo OBSERVAÇÕES é requerido")
    private String observacoes;

    /**
     * ID do técnico responsável pelo chamado.
     * O campo é {@code @NotNull}.
     */
    @NotNull(message = "O campo TÉCNICO é requerido")
    private Integer tecnico;

    /**
     * ID do cliente que abriu o chamado.
     * O campo é {@code @NotNull}.
     */
    @NotNull(message = "O campo CLIENTE é requerido")
    private Integer cliente;

    /** Nome do técnico. Campo apenas para retorno (leitura). */
    private String nomeTecnico;

    /** Nome do cliente. Campo apenas para retorno (leitura). */
    private String nomeCliente;

    /** Construtor padrão. */
    public ChamadoDTO() {
        super();
    }

    /**
     * Construtor para converter a entidade {@link Chamado} para o DTO.
     * <p>
     * Converte os campos de Enum para Integer.
     * </p>
     * @param obj A entidade Chamado a ser convertida.
     */
    public ChamadoDTO(Chamado obj) {
        super();
        this.id = obj.getId();
        this.dataAbertura = obj.getDataAbertura();
        // Converte o Enum.getCodigo() para Integer
        this.prioridade = obj.getPrioridade().getCodigo();
        this.status = obj.getStatus().getState();
        this.titulo = obj.getTitulo();
        this.observacoes = obj.getObservacoes();
        this.tecnico = obj.getTecnico().getId();
        this.cliente = obj.getCliente().getId();
        this.nomeTecnico = obj.getTecnico().getNome();
        this.nomeCliente = obj.getCliente().getNome();
    }

    // --- Getters e Setters ---

    /** Retorna o ID do chamado. */
    public Integer getId() { return id; }
    /** Define o ID do chamado. */
    public void setId(Integer id) { this.id = id; }

    /** Retorna a data de abertura. */
    public LocalDate getDataAbertura() { return dataAbertura; }
    /** Define a data de abertura. */
    public void setDataAbertura(LocalDate dataAbertura) { this.dataAbertura = dataAbertura; }

    /**
     * Retorna o objeto {@link Prioridade} correspondente ao código.
     * @return A Prioridade.
     */
    public Prioridade getPrioridade() {
        return Prioridade.toEnum(this.prioridade);
    }
    /** Define a prioridade pelo código numérico. */
    public void setPrioridade(Integer prioridade) { this.prioridade = prioridade; }

    /**
     * Retorna o objeto {@link Status} correspondente ao código.
     * @return O Status.
     */
    public Status getStatus() {
        return Status.toEnum(this.status);
    }
    /** Define o status pelo código numérico. */
    public void setStatus(Integer status) { this.status = status; }

    /** Retorna o título do chamado. */
    public String getTitulo() { return titulo; }
    /** Define o título do chamado. */
    public void setTitulo(String titulo) { this.titulo = titulo; }

    /** Retorna as observações/descrição. */
    public String getObservacoes() { return observacoes; }
    /** Define as observações/descrição. */
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    /** Retorna o ID do técnico. */
    public Integer getTecnico() { return tecnico; }
    /** Define o ID do técnico. */
    public void setTecnico(Integer tecnico) { this.tecnico = tecnico; }

    /** Retorna o ID do cliente. */
    public Integer getCliente() { return cliente; }
    /** Define o ID do cliente. */
    public void setCliente(Integer cliente) { this.cliente = cliente; }

    /** Retorna o nome do técnico (apenas leitura). */
    public String getNomeTecnico() { return nomeTecnico; }
    /** Define o nome do técnico (apenas leitura). */
    public void setNomeTecnico(String nomeTecnico) { this.nomeTecnico = nomeTecnico; }

    /** Retorna o nome do cliente (apenas leitura). */
    public String getNomeCliente() { return nomeCliente; }
    /** Define o nome do cliente (apenas leitura). */
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }
}