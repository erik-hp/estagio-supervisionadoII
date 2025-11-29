package com.turmab.helpdesk.service;

import com.turmab.helpdesk.domain.Tecnico;
import com.turmab.helpdesk.domain.dto.TecnicoDTO;
import com.turmab.helpdesk.repositories.TecnicoRepository;
import com.turmab.helpdesk.service.exceptions.DataIntegrityViolationException;
import com.turmab.helpdesk.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pela lógica de negócio da entidade Técnico.
 * Realiza as operações de CRUD, validações de CPF/Email e criptografia da senha.
 * * @author Erik Holanda Pires
 * * @author Raul Ferreira Holanda
 * @since 1.0
 */
@Service
public class TecnicoService {
    
    /** Componente para criptografia de senhas. */
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
    /** Repositório para acesso aos dados da entidade Técnico. */
    @Autowired
    private TecnicoRepository repository;
    
    /**
     * Busca um Técnico pelo seu ID.
     * * @param id O ID do Técnico que se deseja buscar.
     * @return O objeto Técnico encontrado.
     * @throws ObjectNotFoundException Caso o ID do técnico não seja encontrado.
     */
    public Tecnico findById(Integer id) {
        Optional<Tecnico> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
            "Objeto não encontrado! Id: " + id + ", Tipo: " + Tecnico.class.getName()));
    }
    
    /**
     * Retorna uma lista com todos os Técnicos cadastrados.
     * * @return Uma lista de objetos Técnico.
     */
    public List<Tecnico> findAll() {
        return repository.findAll();
    }
    
    /**
     * Cria um novo Técnico no sistema, criptografando a senha antes de salvar.
     * * @param objDTO O DTO contendo os dados do novo Técnico.
     * @return O objeto Técnico persistido na base de dados.
     * @throws DataIntegrityViolationException Se o CPF ou Email já estiverem cadastrados.
     */
    public Tecnico create(TecnicoDTO objDTO) {
        objDTO.setId(null);
        validaPorCpfEEmail(objDTO);
        Tecnico newObj = new Tecnico(objDTO);
        newObj.setSenha(bCryptPasswordEncoder.encode(objDTO.getSenha()));
        return repository.save(newObj);
    }
    
    /**
     * Atualiza os dados de um Técnico existente.
     * * @param id O ID do Técnico a ser atualizado.
     * @param objDTO O DTO com os novos dados do Técnico.
     * @return O objeto Técnico atualizado.
     * @throws ObjectNotFoundException Se o ID do técnico não for encontrado.
     * @throws DataIntegrityViolationException Se a atualização violar a unicidade de CPF ou Email.
     */
    public Tecnico update(Integer id, TecnicoDTO objDTO) {
        objDTO.setId(id);
        Tecnico oldObj = findById(id);
        validaPorCpfEEmail(objDTO);
     // Verifica se a senha foi alterada
        if (!objDTO.getSenha().equals(oldObj.getSenha()) && 
            !objDTO.getSenha().startsWith("$2a$")) {
            // Se a senha é nova (não criptografada), criptografa
            String senhaCriptografada = bCryptPasswordEncoder.encode(objDTO.getSenha());
            objDTO.setSenha(senhaCriptografada);
        }
        oldObj = new Tecnico(objDTO);
        return repository.save(oldObj);
    }
    
    /**
     * Deleta um Técnico pelo seu ID.
     * * @param id O ID do Técnico a ser deletado.
     * @throws ObjectNotFoundException Se o Técnico não for encontrado.
     * @throws DataIntegrityViolationException Se o Técnico possuir ordens de serviço associadas.
     */
    public void delete(Integer id) {
        Tecnico obj = findById(id);
        try {
            repository.deleteById(id);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(
                "Técnico possui ordens de serviço e não pode ser deletado!");
        }
    }
    
    /**
     * Verifica se o CPF e o Email fornecidos no DTO já existem na base de dados
     * e garante que não haja duplicidade ao tentar criar ou atualizar um Técnico.
     * * @param objDTO O DTO do Técnico com os dados a serem validados.
     * @throws DataIntegrityViolationException Se o CPF ou Email já estiverem cadastrados por outro usuário.
     */
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