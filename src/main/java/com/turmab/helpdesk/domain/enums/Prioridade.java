package com.turmab.helpdesk.domain.enums;

/**
 * Representa os níveis de prioridade de um Chamado no sistema.
 * <p>
 * Cada prioridade é definida por um código numérico e uma descrição.
 * </p>
 */
public enum Prioridade {
	/** Prioridade mais baixa. */
	BAIXA(0, "BAIXA"),
	/** Prioridade padrão/moderada. */
	MEDIA(1, "MÉDIA"),
	/** Prioridade mais alta e urgente. */
	ALTA(2, "ALTA");
	
	/** Código numérico da prioridade. */
	private Integer codigo;
    /** Descrição da prioridade. */
    private String descricao;
    
    /**
     * Construtor da enumeração Prioridade.
     * @param codigo O código numérico único.
     * @param descricao A descrição textual.
     */
    private Prioridade(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
    
    /**
     * Retorna o código numérico da prioridade.
     * @return O código.
     */
    public Integer getCodigo() {
        return codigo;
    }
    
    /**
     * Retorna a descrição da prioridade.
     * @return A descrição.
     */
    public String getDescricao() {
        return descricao;
    }
    /**
     * Converte um código numérico para o objeto {@link Prioridade} correspondente.
     * @param codigo O código numérico a ser convertido.
     * @return O objeto Prioridade.
     * @throws IllegalArgumentException Se o código for inválido.
     */
    public static Prioridade toEnum(Integer codigo) {
        if (codigo == null) {
            return null;
        }
        for (Prioridade x : Prioridade.values()) {
            if (codigo.equals(x.getCodigo())) {
                return x;
            }
        }
        throw new IllegalArgumentException("Prioridade inválida: " + codigo);
    }
}
