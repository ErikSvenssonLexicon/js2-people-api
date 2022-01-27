package se.lexicon.peopleapi.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.lexicon.peopleapi.model.Person;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    @Query("SELECT p FROM Person p WHERE UPPER(p.email) = UPPER(:email)")
    Optional<Person> findByEmail(@Param("email") String email);

}
