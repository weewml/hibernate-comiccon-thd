package com.comiccon.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "performance")
public class Performance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "performance_member",
            joinColumns = @JoinColumn(name = "performance_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private Set<Member> members = new HashSet<>();

    @Column(name = "nomination", nullable = false, length = 30)
    private String nomination;

    @Column(name = "topic", nullable = false, length = 250)
    private String topic;

    protected Performance() { }

    public Performance(String nomination, String topic) {
        this.nomination = nomination;
        this.topic = topic;
    }

    public void addMember(Member member) { members.add(member); }
    public void removeMember(Member member) { members.remove(member); }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Set<Member> getMembers() { return members; }
    public void setMembers(Set<Member> members) { this.members = members; }
    public String getNomination() { return nomination; }
    public void setNomination(String nomination) { this.nomination = nomination; }
    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Performance that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() { return Objects.hashCode(id); }

    @Override
    public String toString() {
        return String.format("Выступление{id=%d, участников=%d, номинация='%s', тема='%s' }", id, members.size(), nomination, topic);
    }
}