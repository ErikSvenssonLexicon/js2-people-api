package se.lexicon.peopleapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import se.lexicon.peopleapi.data.PersonRepository;
import se.lexicon.peopleapi.model.Person;
import se.lexicon.peopleapi.exception.AppResourceNotFoundException;
import se.lexicon.peopleapi.model.PersonForm;
import se.lexicon.peopleapi.validation.OnPost;
import se.lexicon.peopleapi.validation.OnPut;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class PersonController {
    private final PersonRepository personRepository;

    @Autowired
    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/people")
    public ResponseEntity<List<Person>> findAll(){
        return ResponseEntity.ok(
                personRepository.findAll()
        );
    }

    @PostMapping("/people")
    public ResponseEntity<Person> create(@Validated(OnPost.class) @RequestBody PersonForm personForm){
        Person person = new Person(
                personForm.getName(),
                personForm.getEmail(),
                personForm.getBirthDate()
        );
        return ResponseEntity.status(201).body(
                personRepository.save(person)
        );
    }

    @GetMapping("/people/{id}")
    public ResponseEntity<Person> findById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(
                personRepository.findById(id)
                        .orElseThrow(() -> new AppResourceNotFoundException("Could not person with id " + id))
        );
    }

    @PutMapping("/people/{id}")
    public ResponseEntity<Person> update(@PathVariable("id") Integer id,@Validated(OnPut.class) @RequestBody PersonForm personForm){
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new AppResourceNotFoundException("Could not find person with id " + id));
        if(!id.equals(personForm.getId())){
            throw new IllegalStateException("PathVariable didn't match personForm.id");
        }
        Optional<Person> optional = personRepository.findByEmail(personForm.getEmail());
        if(optional.isPresent() && !id.equals(optional.get().getId())){
            throw new IllegalArgumentException("Email is already taken");
        }

        person.setName(personForm.getName());
        person.setEmail(personForm.getEmail());
        person.setBirthDate(personForm.getBirthDate());

        return ResponseEntity.ok(
                personRepository.save(person)
        );
    }

    @DeleteMapping("/people/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id){
        personRepository.deleteById(id);
        return personRepository.existsById(id) ? ResponseEntity.ok("Could not delete") : ResponseEntity.ok("Successfully deleted");
    }
}
