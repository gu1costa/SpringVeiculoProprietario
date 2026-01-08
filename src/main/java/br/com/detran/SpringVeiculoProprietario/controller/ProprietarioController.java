package br.com.detran.SpringVeiculoProprietario.controller;

import br.com.detran.SpringVeiculoProprietario.model.Proprietario;
import br.com.detran.SpringVeiculoProprietario.service.ProprietarioService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proprietarios")
public class ProprietarioController {

    //Injeção de dependência.
    private final ProprietarioService proprietarioService;

    public ProprietarioController(ProprietarioService proprietarioService) {
        this.proprietarioService = proprietarioService;
    }

    @GetMapping
    //Retorna uma lista de Proprietários porque podem existir vários registros no banco.
    public ResponseEntity<List<Proprietario>> findAll() {
        return ResponseEntity.ok(proprietarioService.getAll());
    }

    @GetMapping("/{id}")
    /*
    @PathVariable pega o valor do ID da URL; getById() retorna Optional para tratar se existir ou não;
    map(ResponseEntity::ok) retorna 200 OK se existir, orElse(...) retorna 404 Not Found se não existir
     */
    public ResponseEntity<Proprietario> getById(@PathVariable Long id){
        return proprietarioService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cpf/{cpfCnpj}")
    //Busca um proprietário pelo CPF/CNPJ, retorna 200 OK se encontrado, 404 se não encontrado.
    public ResponseEntity<Proprietario> getByCpfCnpj(@PathVariable String cpfCnpj){
        return proprietarioService.getByCpfCnpj(cpfCnpj)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Criar novo proprietário.
    @PostMapping
    // Recebe um JSON do Proprietário no corpo da requisição e cria o registro no banco
    public ResponseEntity<Proprietario> create(@RequestBody Proprietario proprietario){
        Proprietario criado = proprietarioService.create(proprietario);
        return ResponseEntity.ok(criado);
    }

    // Atualizar proprietário existente
    @PutMapping("/{id}")
    // Atualiza os dados do Proprietário pelo ID; retorna 200 OK com o objeto atualizado
    public ResponseEntity<Proprietario> update(@PathVariable Long id, @RequestBody Proprietario proprietario) {
        Proprietario atualizado = proprietarioService.update(id, proprietario);
        return ResponseEntity.ok(atualizado);
    }

    // Deletar proprietário
    @DeleteMapping("/{id}")
    // Deleta o Proprietário pelo ID; retorna 204 No Content se deletado com sucesso
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        proprietarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
