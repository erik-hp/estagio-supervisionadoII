package com.turmab.helpdesk.domain.enums;

/**
 * Representa os estados possíveis para um Chamado no sistema (Ciclo de Vida).
 * <p>
 * Cada status é definido por um estado numérico e uma descrição.
 * </p>
 */
public enum Status {
	
	/** Chamado recém-criado e aguardando atribuição. */
	ABERTO(0,"ABERTO"),
	
	/** Chamado atribuído a um técnico e em processo de solução. */
	ANDAMENTO(1,"ANDAMENTO"),
	
	/** Chamado finalizado e resolvido. */
	ENCERRADO(2,"ENCERRADO");
	
	/** Estado numérico do status. */
	private Integer state;
	
	/** Descrição textual do status. */
	private String stateDescription;
	
	/**
     * Construtor da enumeração Status.
     * @param state O código numérico único.
     * @param stateDescription A descrição textual.
     */
	private Status(Integer state, String stateDescription) {
		this.state = state;
		this.stateDescription = stateDescription;
	}
	
	/**
     * Retorna o código numérico do status.
     * @return O código do status.
     */
	public Integer getState() {
		return state;
	}
	
	/**
     * Retorna a descrição do status.
     * @return A descrição do status.
     */
	public String getStateDescription() {
		return stateDescription;
	}
	
	/**
     * Converte um código numérico para o objeto {@link Status} correspondente.
     * @param state O código numérico a ser convertido.
     * @return O objeto Status.
     * @throws IllegalArgumentException Se o código for inválido.
     */
	public static Status toEnum(Integer state) {
        if (state == null) {
            return null;
        }
        for (Status x : Status.values()) {
            if (state.equals(x.getState())) {
                return x;
            }
        }
        throw new IllegalArgumentException("Status inválido: " + state);
    }
}