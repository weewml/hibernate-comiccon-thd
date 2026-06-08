package com.comiccon.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "last_name", nullable = false, length = 40)
    private String lastName;

    @Column(name = "first_name", nullable = false, length = 40)
    private String firstName;

    @Column(name = "patronymic", length = 40)
    private String patronymic;

    @Column(name = "hero", nullable = false, length = 100)
    private String hero;

    @Column(name = "original_source", nullable = false, length = 200)
    private String originalSource;

    @ManyToMany(mappedBy = "members", fetch = FetchType.LAZY)
    private Set<Performance> performances = new HashSet<>();

    protected Member() {}

    public Member(String lastName, String firstName, String patronymic, String hero, String originalSource) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.hero = hero;
        this.originalSource = originalSource;
    }

    public void addPerformance(Performance performance) { performances.add(performance); }
    public void removePerformance(Performance performance) { performances.remove(performance); }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getPatronymic() { return patronymic; }
    public void setPatronymic(String patronymic) { this.patronymic = patronymic; }
    public String getHero() { return hero; }
    public void setHero(String hero) { this.hero = hero; }
    public String getOriginalSource() { return originalSource; }
    public void setOriginalSource(String originalSource) { this.originalSource = originalSource; }

    public Set<Performance> getPerformances() { return performances; }
    public void setPerformances(Set<Performance> performances) { this.performances = performances; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member member)) return false;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() { return Objects.hashCode(id) ;
    }

    @Override
    public String toString() {
        return String.format("Участник{id=%d, '%s %s %s', hero='%s', original_source='%s' }", id, lastName, firstName, patronymic, hero, originalSource);
    }
}