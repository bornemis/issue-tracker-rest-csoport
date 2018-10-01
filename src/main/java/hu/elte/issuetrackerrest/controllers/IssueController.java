package hu.elte.issuetrackerrest.controllers;

import hu.elte.issuetrackerrest.entities.Issue;
import hu.elte.issuetrackerrest.entities.Label;
import hu.elte.issuetrackerrest.entities.Message;
import hu.elte.issuetrackerrest.repositories.IssueRepository;
import hu.elte.issuetrackerrest.repositories.LabelRepository;
import hu.elte.issuetrackerrest.repositories.MessageRepository;
import java.util.List;
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
    
    @Autowired
    private MessageRepository messageRepository;
     @Autowired
     private LabelRepository labelRepository;
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
    @GetMapping("/{id}/messages")
    //Iterable, mert több Message-et várok
    public ResponseEntity<Iterable<Message>> messages(@PathVariable Integer id){
        Optional<Issue> issue = issueRepository.findById(id);
        if (issue.isPresent()) {
            return ResponseEntity.ok(issue.get().getMessages()); //lombok létrehozza automatikusan a gettereket, settereket az entity-knek
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/{id}/messages")
    //Iterable, mert több Message-et várok
    //issues végpontról megközelítve új message-et akarok bementeni
    public ResponseEntity<Message> insertMessage(@PathVariable Integer id, @RequestBody Message message){
        Optional<Issue> oIssue = issueRepository.findById(id);
        if (oIssue.isPresent()) {
            Issue issue=oIssue.get(); //ehhez kell felvenni a message-et, a message be van parse-olva a body-ból
            message.setIssue(issue); //igy a JPA ki fogja tölteni ezt az issue-t
            return ResponseEntity.ok(messageRepository.save(message)); //lombok létrehozza automatikusan a gettereket, settereket az entity-knek
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{id}/labels") //ez egy redundans, mert az issues is ezt csinalja
    public ResponseEntity<Iterable<Label>> labels(@PathVariable Integer id){
        Optional<Issue> oIssue = issueRepository.findById(id);
        if (oIssue.isPresent()) {
            return ResponseEntity.ok(oIssue.get().getLabels()); //visszaadja az issue-hot tartozo labeleket
        } else {
            return ResponseEntity.notFound().build();
        }
    }
//1 label-t varok post-kent es azt szeretnem felvenni
    @PostMapping("/{id}/labels")
    //melyik issue-hoz adjuk hozzá, milyen labelt
    public ResponseEntity<Label> insertLabel(@PathVariable Integer id, @RequestBody Label label){
        Optional<Issue> oIssue = issueRepository.findById(id);
        if (oIssue.isPresent()) {
            Issue issue=oIssue.get();
            Label newLabel=labelRepository.save(label); //elmentjuk a label tablaba, visszaadja az elmentett label objektumot (a kapcsolt tablaba nem kerul be)
            issue.getLabels().add(newLabel);//uj cimke megjelenik az issue cimkei kozott
            issueRepository.save(issue); //kapcsolt tablaba szur be, ezt azon az oldalon kell megcsinalni, ahol a jointtable van, mivel az issue-nal van, ezert itt kell save-elni
            return ResponseEntity.ok(newLabel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    //labelek listajat felkuldjuk, hogy azt modositsa (tombot adunk meg az arc body-jaba a put-nal)
    @PutMapping("/{id}/labels")
    public ResponseEntity<Iterable<Label>> modifyLabels(@PathVariable Integer id, @RequestBody List<Label> labels){
            Optional<Issue> oIssue = issueRepository.findById(id);
        if (oIssue.isPresent()) {
            Issue issue=oIssue.get();
            //ha uj label-t is hozza akarunk adni a listaba
            for(Label label: labels){
                if(label.getId()==null){
                    labelRepository.save(label);
                }
            }
            issue.setLabels(labels);
            issueRepository.save(issue);
            return ResponseEntity.ok(labels);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
}
