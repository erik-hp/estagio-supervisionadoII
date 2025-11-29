package com.turmab.helpdesk.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.turmab.helpdesk.domain.Chamado;
import com.turmab.helpdesk.domain.Cliente;
import com.turmab.helpdesk.domain.Tecnico;
import com.turmab.helpdesk.domain.enums.Perfil;
import com.turmab.helpdesk.domain.enums.Prioridade;
import com.turmab.helpdesk.domain.enums.Status;
import com.turmab.helpdesk.repositories.ChamadoRepository;
import com.turmab.helpdesk.repositories.ClienteRepository;
import com.turmab.helpdesk.repositories.TecnicoRepository;

@Service
public class DBService {
	
	@Autowired
    private BCryptPasswordEncoder encoder;
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ChamadoRepository chamadoRepository;
	
	public void instanciaDB() {
		Tecnico tec1 = new Tecnico(null,"Bill Gates", "765045777093","bill@mail.com",encoder.encode("123"));
		tec1.addPerfil(Perfil.ADMIN);
		
		Cliente cli1 = new Cliente(null,"Linus Torvalds", "70511744013","linus@mail.com",encoder.encode("123"));
		Chamado cha1 = new Chamado(null,Prioridade.MEDIA, Status.ANDAMENTO,"Chamado 01","primeiro chamado", tec1, cli1);
		
		tecnicoRepository.saveAll(Arrays.asList(tec1));
		clienteRepository.saveAll(Arrays.asList(cli1));
		chamadoRepository.saveAll(Arrays.asList(cha1));
	}
}
