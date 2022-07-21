package ru.kuzmin.homework.rest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    @NotEmpty(message = "Поле не должно быть пустым")
    @Size(min = 2, max = 30, message = "Имя должно быть длиной  от 2 до 30 символов")
    private String userName;

    @Column(name = "year_of_birth")
    @Min(value = 1900, message = "Год рождения должен быть больше чем 1900")
    private int yearOfBirth;

    @Column(name = "email")
    @Email(message = "Не валидный email")
    @NotEmpty(message = "Поле не должно быть пустым")
    private String email;

    @Column(name = "status")
    private String status;

    @Column(name = "status_change_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date statusChangeAt;

    @OneToOne(mappedBy = "owner")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JsonIgnore
    private Image image;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    public Person() {
    }

    public Person(String userName, int yearOfBirth, String email) {
        this.userName = userName;
        this.yearOfBirth = yearOfBirth;
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
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

    public Date getStatusChangeAt() {
        return statusChangeAt;
    }

    public void setStatusChangeAt(Date statusChangeAt) {
        this.statusChangeAt = statusChangeAt;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Person{" +
                "userName='" + userName + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                ", email='" + email + '\'' +
                '}';
    }
}
