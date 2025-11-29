package com.turmab.helpdesk.service;

import com.turmab.helpdesk.domain.Chamado;
import com.turmab.helpdesk.domain.Cliente;
import com.turmab.helpdesk.domain.Tecnico;
import com.turmab.helpdesk.domain.dto.ChamadoDTO;
import com.turmab.helpdesk.repositories.ChamadoRepository;
import com.turmab.helpdesk.service.exceptions.DataIntegrityViolationException;
import com.turmab.helpdesk.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pela lógica de negócio da entidade Chamado.
 * Realiza as operações de CRUD, buscando e manipulando dados de chamados,
 * garantindo a integridade dos vínculos com Cliente e Técnico.
 * * @author Erik Holanda Pires
 * * @author Raul Ferreira Holanda
 * @since 1.0
 */
@Service
public class ChamadoService {
    
    /** Repositório para acesso aos dados da entidade Chamado. */
    @Autowired
    private ChamadoRepository repository;
    
    /** Serviço para buscar dados da entidade Tecnico. */
    @Autowired
    private TecnicoService tecnicoService;
    
    /** Serviço para buscar dados da entidade Cliente. */
    @Autowired
    private ClienteService clienteService;
    
    /**
     * Busca um Chamado pelo seu ID.
     * * @param id O ID do Chamado que se deseja buscar.
     * @return O objeto Chamado encontrado.
     * @throws ObjectNotFoundException Se o ID do chamado não for encontrado na base de dados.
     */
    public Chamado findById(Integer id) {
        Optional<Chamado> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
            "Chamado não encontrado! Id: " + id + ", Tipo: " + Chamado.class.getName()));
    }
    
    /**
     * Retorna uma lista com todos os Chamados cadastrados.
     * * @return Uma lista de objetos Chamado.
     */
    public List<Chamado> findAll() {
        return repository.findAll();
    }
    
    /**
     * Cria um novo Chamado no sistema.
     * * @param objDTO O DTO (Data Transfer Object) contendo os dados do novo Chamado.
     * @return O objeto Chamado persistido na base de dados.
     */
    public Chamado create(ChamadoDTO objDTO) {
        objDTO.setId(null);
        return repository.save(fromDTO(objDTO));
    }
    
    /**
     * Atualiza os dados de um Chamado existente.
     * * @param id O ID do Chamado a ser atualizado.
     * @param objDTO O DTO com os novos dados do Chamado.
     * @return O objeto Chamado atualizado.
     * @throws ObjectNotFoundException Se o ID do chamado não for encontrado.
     */
    public Chamado update(Integer id, ChamadoDTO objDTO) {
        objDTO.setId(id);
        Chamado oldObj = findById(id);
        oldObj = fromDTO(objDTO);
        return repository.save(oldObj);
    }
    /**
     * Deleta um Chamado existente pelo seu ID.
     * * <p>
     * O método primeiro verifica a existência do chamado usando {@code findById(id)}.
     * Se encontrado, tenta realizar a delação.
     * </p>
     * * @param id O ID do Chamado a ser deletado.
     * @throws ObjectNotFoundException Se o Chamado com o ID fornecido não for encontrado.
     * @throws DataIntegrityViolationException Se houver uma violação de integridade referencial 
     * (embora incomum para Chamados) que impeça a deleção.
     */
    public void delete(Integer id) {
        findById(id); // Verifica se o chamado existe
        try {
            repository.deleteById(id);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
             throw new DataIntegrityViolationException(
                "Chamado não pode ser deletado, pois possui vínculos (embora seja improvável)!");
        }
    }
    
    /**
     * Converte um ChamadoDTO em um objeto Chamado (Entidade).
     * Resolve os IDs de Cliente e Técnico para as respectivas Entidades.
     * * @param objDTO O objeto DTO a ser convertido.
     * @return O objeto Chamado preenchido com as Entidades referenciadas.
     */
    private Chamado fromDTO(ChamadoDTO objDTO) {
        Tecnico tecnico = tecnicoService.findById(objDTO.getTecnico());
        Cliente cliente = clienteService.findById(objDTO.getCliente());
        
        Chamado chamado = new Chamado();
        
        if (objDTO.getId() != null) {
            chamado.setId(objDTO.getId());
        }
        
        // Configura prioridade e status usando as enums
        chamado.setPrioridade(objDTO.getPrioridade());
        chamado.setStatus(objDTO.getStatus());
        
        chamado.setTitulo(objDTO.getTitulo());
        chamado.setObservacoes(objDTO.getObservacoes());
        chamado.setTecnico(tecnico);
        chamado.setCliente(cliente);
        
        // Mantém a data de abertura original ou define nova
        if (objDTO.getDataAbertura() != null) {
            chamado.setDataAbertura(objDTO.getDataAbertura());
        } else {
            chamado.setDataAbertura(LocalDate.now());
        }
        
        return chamado;
    }
}