package com.turmab.helpdesk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.turmab.helpdesk.service.DBService;

/**
 * Classe de configuração para o perfil "Dev" (Desenvolvimento).
 * Responsável por instanciar a base de dados em memória (H2)
 * se a propriedade 'spring.jpa.hibernate.ddl-auto' estiver configurada como 'create'.
 * * @author Erik Holanda Pires
 * * @author Raul Ferreira Holanda
 * @since 1.0
 */
@Configuration
@Profile("Dev")
public class DevConfig {
    
    /** Serviço de inicialização de dados do banco de dados. */
	@Autowired
	private DBService dbService;
	
    /** Valor da propriedade 'spring.jpa.hibernate.ddl-auto' do ambiente. */
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String value;
	
    /**
     * Instancia o banco de dados se a propriedade 'ddl-auto' for 'create'.
     * * @return false, indicando a conclusão da operação.
     */
	@Bean
	public boolean instanciaDB() {
		if(value.equals("create")) {
			this.dbService.instanciaDB();			
		}
		return false;
	}
}