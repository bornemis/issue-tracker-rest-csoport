package hu.elte.issuetrackerrest.repositories;
 import hu.elte.issuetrackerrest.entities.Label;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
 @Repository
public interface LabelRepository extends CrudRepository<Label, Integer> {
    
}