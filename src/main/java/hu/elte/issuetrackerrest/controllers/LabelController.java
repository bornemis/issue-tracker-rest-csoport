package hu.elte.issuetrackerrest.controllers;

import hu.elte.issuetrackerrest.entities.Label;
import hu.elte.issuetrackerrest.repositories.LabelRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/labels")
public class LabelController {
    
    @Autowired
    private LabelRepository labelRepository;
    
    @GetMapping("")
    public ResponseEntity<Iterable<Label>> getAll() {
        return ResponseEntity.ok(labelRepository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Label> get(@PathVariable Integer id) {
        Optional<Label> label = labelRepository.findById(id);
        if (label.isPresent()) {
            return ResponseEntity.ok(label.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("")
    public ResponseEntity<Label> post(@RequestBody Label label) {
        Label savedLabel = labelRepository.save(label);
        return ResponseEntity.ok(savedLabel);
    }
    /*
    modositas, mi alapjan modositunk, melyik vegponton leszek
    path-ból kiolvassa az id-t, a htttp uzenetből kiolvassa az issue objectuomot,
    beadandónál a pathVariable az egyenlő-e, mint az issue id-ja, ha nem, akk badrequest hiba
    */
    @PutMapping("/{id}")
    public ResponseEntity<Label> update(@PathVariable Integer id, @RequestBody Label label){
        Optional<Label> oLabel = labelRepository.findById(id);
        if (oLabel.isPresent()) {
            label.setId(id); //igy nem kell lekezelni, hogy a pathVariable és az issue variable-je egyenlő-e
            return ResponseEntity.ok(labelRepository.save(label));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Label> delete(@PathVariable Integer id){
    Optional<Label> oLabel = labelRepository.findById(id);
        if (oLabel.isPresent()) {
            labelRepository.deleteById(id);
            return ResponseEntity.ok().build(); //ha az ok()-nak nincs paramétere, akkor build()-et kell utána írni!
        } else {
            return ResponseEntity.notFound().build();
        }
}
}
