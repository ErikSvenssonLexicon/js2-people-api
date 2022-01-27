package se.lexicon.peopleapi.model;

import org.springframework.validation.annotation.Validated;
import se.lexicon.peopleapi.validation.OnPost;
import se.lexicon.peopleapi.validation.OnPut;
import se.lexicon.peopleapi.validation.UniqueEmail;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Validated
public class PersonForm {

    @NotNull(message = "Id is required", groups = OnPut.class)
    private Integer id;
    @NotBlank(message = "This field is required", groups = {OnPut.class, OnPost.class})
    @Size(max = 60, message = "This field can only hold 60 letters", groups = {OnPut.class, OnPost.class})
    private String name;
    @NotBlank(message = "This field is required", groups = {OnPut.class, OnPost.class})
    @Size(max = 64, message = "This field can only hold 64 letters", groups = {OnPut.class, OnPost.class})
    @UniqueEmail(message = "Email already taken", groups = OnPost.class)
    private String email;
    @NotNull(message = "This field is required", groups = {OnPut.class, OnPost.class})
    private LocalDate birthDate;

    public PersonForm() {
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
}
