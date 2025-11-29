package com.turmab.helpdesk.service;

import com.turmab.helpdesk.domain.Cliente;
import com.turmab.helpdesk.domain.dto.ClienteDTO;
import com.turmab.helpdesk.domain.enums.Perfil;
import com.turmab.helpdesk.repositories.ClienteRepository;
import com.turmab.helpdesk.security.UserSS;
import com.turmab.helpdesk.service.exceptions.AuthorizationException;
import com.turmab.helpdesk.service.exceptions.DataIntegrityViolationException;
import com.turmab.helpdesk.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pela lógica de negócio da entidade Cliente.
 * Realiza as operações de CRUD, além de garantir a unicidade do CPF e Email
 * e a integridade referencial dos dados.
 * * @author Erik Holanda Pires
 * * @author Raul Ferreira Holanda
 * @since 1.0
 */
@Service
public class ClienteService {
    
    /** Repositório para acesso aos dados da entidade Cliente. */
    @Autowired
    private ClienteRepository repository;
    
    @Autowired
    private BCryptPasswordEncoder BCryptPasswordEncoder;
    
    /**
     * Busca um Cliente pelo seu ID.
     * * @param id O ID do Cliente que se deseja buscar.
     * @return O objeto Cliente encontrado.
     * @throws ObjectNotFoundException Caso o ID do cliente não seja encontrado.
     */
    public Cliente findById(Integer id) {
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }
    
    /**
     * Retorna uma lista com todos os Clientes cadastrados.
     * * @return Uma lista de objetos Cliente.
     */
    public List<Cliente> findAll() {
        return repository.findAll();
    }
    
    /**
     * Cria um novo Cliente no sistema.
     * * @param objDTO O DTO contendo os dados do novo Cliente.
     * @return O objeto Cliente persistido na base de dados.
     * @throws DataIntegrityViolationException Se o CPF ou Email já estiverem cadastrados.
     */
    public Cliente create(ClienteDTO objDTO) {
        objDTO.setId(null);
        validaPorCpfEEmail(objDTO);
        Cliente newObj = new Cliente(objDTO);
        newObj.setSenha(BCryptPasswordEncoder.encode(objDTO.getSenha()));
        return repository.save(newObj);
    }
    
    /**
     * Atualiza os dados de um Cliente existente.
     * * @param id O ID do Cliente a ser atualizado.
     * @param objDTO O DTO com os novos dados do Cliente.
     * @return O objeto Cliente atualizado.
     * @throws ObjectNotFoundException Se o ID do cliente não for encontrado.
     * @throws DataIntegrityViolationException Se a atualização violar a unicidade de CPF ou Email.
     */
    public Cliente update(Integer id, ClienteDTO objDTO) {
        objDTO.setId(id);
        Cliente oldObj = findById(id);
        validaPorCpfEEmail(objDTO);
     // Verifica se a senha foi alterada
        if (!objDTO.getSenha().equals(oldObj.getSenha()) && 
            !objDTO.getSenha().startsWith("$2a$")) {
            // Se a senha é nova (não criptografada), criptografa
            String senhaCriptografada = BCryptPasswordEncoder.encode(objDTO.getSenha());
            objDTO.setSenha(senhaCriptografada);
        }
        oldObj = new Cliente(objDTO);
        return repository.save(oldObj);
    }
    
    /**
     * Deleta um Cliente pelo seu ID.
     * * @param id O ID do Cliente a ser deletado.
     * @throws ObjectNotFoundException Se o Cliente não for encontrado.
     * @throws DataIntegrityViolationException Se o Cliente possuir chamados associados.
     */
    public void delete(Integer id) {
  
        Cliente obj = findById(id);
        
        try {
            repository.deleteById(id);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(
                "Cliente possui chamados e não pode ser deletado!");
        }
    }
    
    /**
     * Verifica se o CPF e o Email fornecidos no DTO já existem na base de dados
     * e garante que não haja duplicidade ao tentar criar ou atualizar um Cliente.
     * * @param objDTO O DTO do Cliente com os dados a serem validados.
     * @throws DataIntegrityViolationException Se o CPF ou Email já estiverem cadastrados por outro usuário.
     */
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