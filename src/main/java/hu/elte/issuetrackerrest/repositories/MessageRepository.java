package hu.elte.issuetrackerrest.repositories;


import hu.elte.issuetrackerrest.entities.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {    
}
