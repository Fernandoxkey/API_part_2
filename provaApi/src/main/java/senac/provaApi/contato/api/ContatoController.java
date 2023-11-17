package senac.provaApi.contato.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import senac.provaApi.contato.dominio.ContatoRepository;
import senac.provaApi.contato.dominio.Contato;

@RestController
@RequestMapping("/contatos")
public class ContatoController {

    private ContatoRepository repository;

    public ContatoController(ContatoRepository contatoRepository){
        this.repository = contatoRepository;

    }
    @GetMapping
    public ResponseEntity<Page<Contato>> listarContatos(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanhoPagina){

        Pageable pageable = PageRequest.of(pagina, tamanhoPagina);
        Page<Contato> contatos = repository.findAll(pageable);

        return ResponseEntity.ok(contatos);
    }
    @GetMapping(path = "/id")
    public ResponseEntity<Contato> findById(@PathVariable long id){
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public Contato create(@RequestBody Contato contact){
        return repository.save(contact);
    }
    @PutMapping(value="/id")
    public ResponseEntity update(@PathVariable("id") long id,
                                 @RequestBody Contato contato) {
        return repository.findById(id)
                .map(record -> {
                    record.setNome(contato.getNome());
                    record.setTelefone(contato.getTelefone());
                    record.setEmail(contato.getEmail());
                    record.setDataInicio(contato.getDataInicio());
                    record.setEstagio(contato.getEstagio());
                    record.setTarefa(contato.getTarefa());
                    record.setAnotacoes(contato.getAnotacoes());
                    Contato updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping(path ="/id")
    public ResponseEntity <?> delete(@PathVariable long id) {
        return repository.findById(id)
                .map(record -> {
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }



}
