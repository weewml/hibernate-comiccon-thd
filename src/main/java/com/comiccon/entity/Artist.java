package com.comiccon.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "artist")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "last_name", nullable = false, length = 40)
    private String lastName;

    @Column(name = "first_name", nullable = false, length = 40)
    private String firstName;

    @Column(name = "patronymic", length = 40)
    private String patronymic;

    protected Artist() {}

    public Artist(String lastName, String firstName, String patronymic) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getPatronymic() { return patronymic; }
    public void setPatronymic(String patronymic) { this.patronymic = patronymic; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artist a)) return false;
        return Objects.equals(id, a.id);
    }

    @Override
    public int hashCode() { return Objects.hashCode(id); }

    @Override
    public String toString() {
        return String.format("Художник{id=%d, '%s %s %s'}", id, lastName, firstName, patronymic);
    }
}
