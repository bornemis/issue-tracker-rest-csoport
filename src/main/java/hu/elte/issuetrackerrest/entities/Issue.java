package hu.elte.issuetrackerrest.entities;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotNull
    private String title;

    @Column
    private String description;

    @Column
    @NotNull
    private String place;

    @Column(updatable=false)
    @CreationTimestamp
    private LocalDateTime created_at;

    @Column
    @UpdateTimestamp
    private LocalDateTime updated_at;
    
    //egy Issue-hoz több Message tartozik
    //egyik entitásban mi referál vissza ránk
    @OneToMany(mappedBy="issue") //message-ben megjelenik az issue_id oszlop
    private List<Message> messages;
    //sok sok kapcsolat a label es az issue kozott
    @ManyToMany
    @JoinTable //testre lehetne szabni, hogy mi legyen a neve, kapcsoló mezők stb., alapból issue_labels lesz
    private List<Label> labels;
    
}
