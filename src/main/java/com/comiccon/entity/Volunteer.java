package com.comiccon.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "volunteer")
public class Volunteer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "last_name", nullable = false, length = 40)
    private String lastName;

    @Column(name = "first_name", nullable = false, length = 40)
    private String firstName;

    @Column(name = "patronymic", length = 40)
    private String patronymic;

    @Column(name = "task", nullable = false)
    private String task;

    protected Volunteer() {}

    public Volunteer(String lastName, String firstName, String patronymic, String task) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.task = task;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getPatronymic() { return patronymic; }
    public void setPatronymic(String patronymic) { this.patronymic = patronymic; }
    public String getTask() { return task; }
    public void setTask(String task) { this.task = task; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Volunteer volunteer)) return false;
        return Objects.equals(id, volunteer.id);
    }

    @Override
    public int hashCode() { return Objects.hashCode(id); }

    @Override
    public String toString() {
        return String.format("Волонтер{id=%d, '%s %s %s', task: '%s'}", id, lastName, firstName, patronymic, task);
    }
}