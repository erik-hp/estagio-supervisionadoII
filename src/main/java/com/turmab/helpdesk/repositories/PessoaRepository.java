package com.turmab.helpdesk.repositories;

import com.turmab.helpdesk.domain.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
    
    Optional<Pessoa> findByEmail(String email);
    
    Optional<Pessoa> findByCpf(String cpf);
}