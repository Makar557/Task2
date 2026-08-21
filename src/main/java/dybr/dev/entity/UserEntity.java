package dybr.dev.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "users")
public class UserEntity {

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private int age;

    @Column(name = "created_at")
    private LocalDate created_at;

    public UserEntity(Long id, String name, String email, int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        created_at = LocalDate.now();
        this.age = age;
    }

    public UserEntity(String name, String email, int age) {
        this.name = name;
        this.email = email;
        created_at = LocalDate.now();
        this.age = age;
    }

    public UserEntity() {
    }

    public int getAge() {
        return age;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return  "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return age == that.age && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(created_at, that.created_at);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, age, created_at);
    }
}