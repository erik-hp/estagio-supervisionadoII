package com.turmab.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.turmab.helpdesk.domain.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico,Integer>{

}
