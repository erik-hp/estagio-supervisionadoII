package com.turmab.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.turmab.helpdesk.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente,Integer>{

}
