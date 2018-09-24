/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.issuetrackerrest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    @NotNull
    private String text;
    @Column(updatable=false)
    @CreationTimestamp
    private LocalDateTime created_at;
    @Column
    @UpdateTimestamp
    private LocalDateTime updated_at;
    //több message tartozik egy issue-hoz
   /* @ManyToOne
    //2 tábla közötti kapcsolatot biztosítja
    @JoinColumn
    @JsonIgnore //issue oldalról, ha lekérem a dolgokat, akkor az issue-hoz tartozó message-ek is jönnek, fordítva viszont nem!
    private Issue issue;*/
}
