package com.turmab.helpdesk.service;

import com.turmab.helpdesk.domain.Tecnico;
import com.turmab.helpdesk.domain.dto.TecnicoDTO;
import com.turmab.helpdesk.repositories.TecnicoRepository;
import com.turmab.helpdesk.service.exceptions.DataIntegrityViolationException;
import com.turmab.helpdesk.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TecnicoService {
    
    @Autowired
    private TecnicoRepository repository;
    
    public Tecnico findById(Integer id) {
        Optional<Tecnico> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! Id: " + id + ", Tipo: " + Tecnico.class.getName()));
    }
    
    public List<Tecnico> findAll() {
        return repository.findAll();
    }
    
    public Tecnico create(TecnicoDTO objDTO) {
        objDTO.setId(null); // Garante que é uma criação
        validaPorCpfEEmail(objDTO);
        Tecnico newObj = new Tecnico(objDTO);
        return repository.save(newObj);
    }
    
    public Tecnico update(Integer id, TecnicoDTO objDTO) {
        objDTO.setId(id);
        Tecnico oldObj = findById(id);
        validaPorCpfEEmail(objDTO);
        oldObj = new Tecnico(objDTO);
        return repository.save(oldObj);
    }
    
    public void delete(Integer id) {
        Tecnico obj = findById(id);
        try {
            repository.deleteById(id);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(
                "Técnico possui ordens de serviço e não pode ser deletado!");
        }
    }
    
    private void validaPorCpfEEmail(TecnicoDTO objDTO) {
        Optional<Tecnico> obj = repository.findByCpf(objDTO.getCpf());
        
        // Verifica se já existe um técnico com o mesmo CPF
        if (obj.isPresent() && !obj.get().getId().equals(objDTO.getId())) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
        }
        
        obj = repository.findByEmail(objDTO.getEmail());
        
        // Verifica se já existe um técnico com o mesmo email
        if (obj.isPresent() && !obj.get().getId().equals(objDTO.getId())) {
            throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
        }
    }
}