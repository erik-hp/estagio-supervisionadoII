package com.turmab.helpdesk.resources;

import com.turmab.helpdesk.domain.Tecnico;
import com.turmab.helpdesk.domain.dto.TecnicoDTO;
import com.turmab.helpdesk.service.TecnicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para a entidade Técnico.
 * Oferece endpoints para todas as operações CRUD (Criar, Ler, Atualizar, Deletar)
 * de Técnicos no caminho base '/tecnicos'.
 * * @author Erik Holanda Pires
 * * @author Raul Ferreira Holanda
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoResource {
    
    /** Serviço para acesso à lógica de negócio de Técnicos. */
    @Autowired
    private TecnicoService service;
    
    /**
     * Retorna um Técnico específico pelo seu ID.
     * * @param id O ID do Técnico a ser buscado.
     * @return ResponseEntity contendo o TecnicoDTO (Status 200 OK).
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {
        Tecnico obj = service.findById(id);
        return ResponseEntity.ok().body(new TecnicoDTO(obj));
    }
    
    /**
     * Retorna uma lista com todos os Técnicos cadastrados.
     * * @return ResponseEntity contendo uma lista de TecnicoDTO (Status 200 OK).
     */
    @GetMapping
    public ResponseEntity<List<TecnicoDTO>> findAll() {
        List<Tecnico> list = service.findAll();
        List<TecnicoDTO> listDTO = list.stream()
                .map(TecnicoDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }
    
    /**
     * Cria um novo Técnico no sistema.
     * * @param objDTO O TecnicoDTO com os dados para criação.
     * @return ResponseEntity (Status 201 Created) com a URI para o novo recurso.
     */
    @PostMapping
    public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO objDTO) {
        Tecnico newObj = service.create(objDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
    
    /**
     * Atualiza um Técnico existente pelo seu ID.
     * * @param id O ID do Técnico a ser atualizado.
     * @param objDTO O TecnicoDTO com os novos dados.
     * @return ResponseEntity contendo o TecnicoDTO atualizado (Status 200 OK).
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, 
                                            @Valid @RequestBody TecnicoDTO objDTO) {
        Tecnico obj = service.update(id, objDTO);
        return ResponseEntity.ok().body(new TecnicoDTO(obj));
    }
    
    /**
     * Deleta um Técnico existente pelo seu ID.
     * * @param id O ID do Técnico a ser deletado.
     * @return ResponseEntity (Status 204 No Content).
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}