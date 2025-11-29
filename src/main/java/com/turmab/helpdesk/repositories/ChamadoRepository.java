package com.turmab.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.turmab.helpdesk.domain.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado,Integer>{
	
}
