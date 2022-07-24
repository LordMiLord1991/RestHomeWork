package ru.kuzmin.homework.rest.dto;

import org.hibernate.annotations.Cascade;
import ru.kuzmin.homework.rest.models.Authority;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

public class PersonDTO {

    @NotEmpty(message = "Поле не должно быть пустым")
    @Size(min = 2, max = 30, message = "Имя должно быть длиной  от 2 до 30 символов")
    private String userName;

    @Min(value = 1900, message = "Год рождения должен быть больше чем 1900")
    private int yearOfBirth;

    @Email(message = "Не валидный email")
    @NotEmpty(message = "Поле не должно быть пустым")
    private String email;

    @Column(name = "status")
    private String status;

    @ManyToMany(fetch= FetchType.EAGER)
    @JoinTable(
            name = "authority_person",
            joinColumns = @JoinColumn(name = "authority_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Authority> roles;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Authority> getRoles() {
        return roles;
    }

    public void setRoles(List<Authority> roles) {
        this.roles = roles;
    }
}
