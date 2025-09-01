package com.turmab.helpdesk.service;

import com.turmab.helpdesk.domain.Cliente;
import com.turmab.helpdesk.domain.dto.ClienteDTO;
import com.turmab.helpdesk.repositories.ClienteRepository;
import com.turmab.helpdesk.service.exceptions.DataIntegrityViolationException;
import com.turmab.helpdesk.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository repository;
    
    public Cliente findById(Integer id) {
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }
    
    public List<Cliente> findAll() {
        return repository.findAll();
    }
    
    public Cliente create(ClienteDTO objDTO) {
        objDTO.setId(null);
        validaPorCpfEEmail(objDTO);
        Cliente newObj = new Cliente(objDTO);
        return repository.save(newObj);
    }
    
    public Cliente update(Integer id, ClienteDTO objDTO) {
        objDTO.setId(id);
        Cliente oldObj = findById(id);
        validaPorCpfEEmail(objDTO);
        oldObj = new Cliente(objDTO);
        return repository.save(oldObj);
    }
    
    public void delete(Integer id) {
        Cliente obj = findById(id);
        try {
            repository.deleteById(id);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(
                "Cliente possui chamados e não pode ser deletado!");
        }
    }
    
    private void validaPorCpfEEmail(ClienteDTO objDTO) {  // CORREÇÃO: Use ClienteDTO aqui
        Optional<Cliente> obj = repository.findByCpf(objDTO.getCpf());
        
        if (obj.isPresent() && !obj.get().getId().equals(objDTO.getId())) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
        }
        
        obj = repository.findByEmail(objDTO.getEmail());
        
        if (obj.isPresent() && !obj.get().getId().equals(objDTO.getId())) {
            throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
        }
    }
}