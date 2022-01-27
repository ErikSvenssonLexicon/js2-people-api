package se.lexicon.peopleapi.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.lexicon.peopleapi.data.PersonRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final PersonRepository personRepository;

    @Autowired
    public UniqueEmailValidator(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null){
            return true;
        }
        return !personRepository.findByEmail(value.trim()).isPresent();
    }
}
