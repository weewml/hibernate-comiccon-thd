package com.comiccon.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "mark")
public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jury_id", nullable = false)
    private Jury jury;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criterion_id", nullable = false)
    private Criterion criterion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id", nullable = false)
    private Performance performance;


    protected Mark() { }

    public Mark(Jury jury, Criterion criterion, Performance performance) {
        this.jury = jury;
        this.criterion = criterion;
        this.performance = performance;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Jury getJury() { return jury; }
    public void setJury(Jury jury) { this.jury = jury; }
    public Criterion getCriterion() { return criterion; }
    public void setCriterion(Criterion criterion) { this.criterion = criterion; }
    public Performance getPerformance() { return performance; }
    public void setPerformance(Performance performance) { this.performance = performance; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mark mark)) return false;
        return Objects.equals(id, mark.id);
    }

    @Override
    public int hashCode() { return Objects.hashCode(id); }

    @Override
    public String toString() {
        return String.format("Mark{id=%d, jury=%d, criterion=%d, performance=%d}",
                id, jury != null ? jury.getId() : null,
                criterion != null ? criterion.getId() : null,
                performance != null ? performance.getId() : null);
    }
}
