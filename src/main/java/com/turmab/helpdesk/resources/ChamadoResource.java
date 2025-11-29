package com.turmab.helpdesk.resources;

import com.turmab.helpdesk.domain.Chamado;
import com.turmab.helpdesk.domain.dto.ChamadoDTO;
import com.turmab.helpdesk.service.ChamadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para a entidade Chamado.
 * Oferece endpoints para todas as operações CRUD (Criar, Ler, Atualizar, Deletar)
 * de Chamados no caminho base '/chamados'.
 * * @author Erik Holanda Pires
 * * @author Raul Ferreira Holanda
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/chamados")
public class ChamadoResource {
    
    /** Serviço para acesso à lógica de negócio de Chamados. */
    @Autowired
    private ChamadoService service;
    
    /**
     * Retorna um Chamado específico pelo seu ID.
     * * @param id O ID do Chamado a ser buscado.
     * @return ResponseEntity contendo o ChamadoDTO (Status 200 OK).
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<ChamadoDTO> findById(@PathVariable Integer id) {
        Chamado obj = service.findById(id);
        return ResponseEntity.ok().body(new ChamadoDTO(obj));
    }
    
    /**
     * Retorna uma lista com todos os Chamados cadastrados.
     * * @return ResponseEntity contendo uma lista de ChamadoDTO (Status 200 OK).
     */
    @GetMapping
    public ResponseEntity<List<ChamadoDTO>> findAll() {
        List<Chamado> list = service.findAll();
        List<ChamadoDTO> listDTO = list.stream()
                .map(ChamadoDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }
    
    /**
     * Cria um novo Chamado no sistema.
     * * @param objDTO O ChamadoDTO com os dados para criação.
     * @return ResponseEntity (Status 201 Created) com a URI para o novo recurso.
     */
    @PostMapping
    public ResponseEntity<ChamadoDTO> create(@Valid @RequestBody ChamadoDTO objDTO) {
        Chamado newObj = service.create(objDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
    
    /**
     * Atualiza um Chamado existente pelo seu ID.
     * * @param id O ID do Chamado a ser atualizado.
     * @param objDTO O ChamadoDTO com os novos dados.
     * @return ResponseEntity contendo o ChamadoDTO atualizado (Status 200 OK).
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<ChamadoDTO> update(@PathVariable Integer id, 
                                            @Valid @RequestBody ChamadoDTO objDTO) {
        Chamado obj = service.update(id, objDTO);
        return ResponseEntity.ok().body(new ChamadoDTO(obj));
    }
    
    /**
     * Deleta um Chamado existente pelo seu ID.
     * * @param id O ID do Chamado a ser deletado.
     * @return ResponseEntity (Status 204 No Content).
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}