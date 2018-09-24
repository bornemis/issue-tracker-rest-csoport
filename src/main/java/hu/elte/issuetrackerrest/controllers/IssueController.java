package hu.elte.issuetrackerrest.controllers;

import hu.elte.issuetrackerrest.entities.Issue;
import hu.elte.issuetrackerrest.repositories.IssueRepository;
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
@RequestMapping("/issues")
public class IssueController {
    
    @Autowired
    private IssueRepository issueRepository;
    
    @GetMapping("")
    public ResponseEntity<Iterable<Issue>> getAll() {
        return ResponseEntity.ok(issueRepository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Issue> get(@PathVariable Integer id) {
        Optional<Issue> issue = issueRepository.findById(id);
        if (issue.isPresent()) {
            return ResponseEntity.ok(issue.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("")
    public ResponseEntity<Issue> post(@RequestBody Issue issue) {
        Issue savedIssue = issueRepository.save(issue);
        return ResponseEntity.ok(savedIssue);
    }
    /*
    modositas, mi alapjan modositunk, melyik vegponton leszek
    path-ból kiolvassa az id-t, a htttp uzenetből kiolvassa az issue objectuomot,
    beadandónál a pathVariable az egyenlő-e, mint az issue id-ja, ha nem, akk badrequest hiba
    */
    @PutMapping("/{id}")
    public ResponseEntity<Issue> update(@PathVariable Integer id, @RequestBody Issue issue){
        Optional<Issue> oIssue = issueRepository.findById(id);
        if (oIssue.isPresent()) {
            issue.setId(id); //igy nem kell lekezelni, hogy a pathVariable és az issue variable-je egyenlő-e
            return ResponseEntity.ok(issueRepository.save(issue));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Issue> delete(@PathVariable Integer id){
    Optional<Issue> oIssue = issueRepository.findById(id);
        if (oIssue.isPresent()) {
            issueRepository.deleteById(id);
            return ResponseEntity.ok().build(); //ha az ok()-nak nincs paramétere, akkor build()-et kell utána írni!
        } else {
            return ResponseEntity.notFound().build();
        }
}
}
