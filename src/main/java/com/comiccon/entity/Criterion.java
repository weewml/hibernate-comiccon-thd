package com.comiccon.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "criterion")
public class Criterion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "difficult_mark", nullable = false, precision = 4, scale = 2)
    private BigDecimal difficultMark;

    @Column(name = "artistic_mark", nullable = false, precision = 4, scale = 2)
    private BigDecimal artisticMark;

    protected Criterion() {}

    public Criterion(BigDecimal difficultMark, BigDecimal artisticMark) {
        this.difficultMark = difficultMark;
        this.artisticMark = artisticMark;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public BigDecimal getDifficultMark() { return difficultMark; }
    public void setDifficultMark(BigDecimal difficultMark) { this.difficultMark = difficultMark; }
    public BigDecimal getArtisticMark() { return artisticMark; }
    public void setArtisticMark(BigDecimal artisticMark) { this.artisticMark = artisticMark; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Criterion c)) return false;
        return Objects.equals(id, c.id);
    }

    @Override
    public int hashCode() { return Objects.hashCode(id); }

    @Override
    public String toString() {
        return String.format("Критерии оценивания{id=%d, сложность:'%s', артистизм:'%s'}", id, difficultMark, artisticMark);
    }
}
