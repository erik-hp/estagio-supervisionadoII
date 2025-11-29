package com.turmab.helpdesk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.turmab.helpdesk.service.DBService;

/**
 * Classe de configuração para o perfil "test" (Testes).
 * Garante que a base de dados em memória (H2) seja instanciada com dados iniciais
 * para o ambiente de testes.
 * * @author Erik Holanda Pires
 * * @author Raul Ferreira Holanda
 * @since 1.0
 */
@Configuration
@Profile("test")
public class TestConfig {
    
    /** Serviço de inicialização de dados do banco de dados. */
	@Autowired
	private DBService dbService;
	
    /**
     * Instancia o banco de dados com dados de teste.
     * Este método é executado automaticamente quando o profile 'test' está ativo.
     */
	@Bean
	public void instanciaDB() {
		this.dbService.instanciaDB();
	}
}