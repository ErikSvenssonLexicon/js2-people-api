package se.lexicon.peopleapi.model;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "people")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, name = "id")
    private Integer id;
    @Column(name = "name", length = 60)
    private String name;
    @Column(name = "email", length = 64, unique = true)
    private String email;
    @Column(name = "birth_date")
    private LocalDate birthDate;

    public Person(String name, String email, LocalDate birthDate) {
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
    }

    public Person() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @JsonGetter
    public Integer getAge(){
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
