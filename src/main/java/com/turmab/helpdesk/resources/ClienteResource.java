package com.turmab.helpdesk.resources;

import com.turmab.helpdesk.domain.Cliente;
import com.turmab.helpdesk.domain.dto.ClienteDTO;
import com.turmab.helpdesk.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para a entidade Cliente.
 * Oferece endpoints para todas as operações CRUD (Criar, Ler, Atualizar, Deletar)
 * de Clientes no caminho base '/clientes'.
 * * @author Erik Holanda Pires
 * * @author Raul Ferreira Holanda
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {
    
    /** Serviço para acesso à lógica de negócio de Clientes. */
    @Autowired
    private ClienteService service;
    
    /**
     * Retorna um Cliente específico pelo seu ID.
     * * @param id O ID do Cliente a ser buscado.
     * @return ResponseEntity contendo o ClienteDTO (Status 200 OK).
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id) {
        Cliente obj = service.findById(id);
        return ResponseEntity.ok().body(new ClienteDTO(obj));
    }
    
    /**
     * Retorna uma lista com todos os Clientes cadastrados.
     * * @return ResponseEntity contendo uma lista de ClienteDTO (Status 200 OK).
     */
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> findAll() {
        List<Cliente> list = service.findAll();
        List<ClienteDTO> listDTO = list.stream()
                .map(ClienteDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }
    
    /**
     * Cria um novo Cliente no sistema.
     * * @param objDTO O ClienteDTO com os dados para criação.
     * @return ResponseEntity (Status 201 Created) com a URI para o novo recurso.
     */
    @PostMapping
    public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO objDTO) {
        Cliente newObj = service.create(objDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
    
    /**
     * Atualiza um Cliente existente pelo seu ID.
     * * @param id O ID do Cliente a ser atualizado.
     * @param objDTO O ClienteDTO com os novos dados.
     * @return ResponseEntity contendo o ClienteDTO atualizado (Status 200 OK).
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> update(@PathVariable Integer id, 
                                            @Valid @RequestBody ClienteDTO objDTO) {
        Cliente obj = service.update(id, objDTO);
        return ResponseEntity.ok().body(new ClienteDTO(obj));
    }
    
    /**
     * Deleta um Cliente existente pelo seu ID.
     * * @param id O ID do Cliente a ser deletado.
     * @return ResponseEntity (Status 204 No Content).
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}