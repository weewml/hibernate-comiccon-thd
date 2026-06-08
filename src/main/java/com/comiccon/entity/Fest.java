package com.comiccon.entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "fest")
public class Fest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private Performance performance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_id")
    private Volunteer volunteer;

    @Column(name = "address", nullable = false, length = 300)
    private String address;

    @Column(name = "date", nullable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime date;

    protected Fest() { }

    public Fest(Company company, Performance performance, Volunteer volunteer, String address, OffsetDateTime date) {
        this.company = company;
        this.performance = performance;
        this.volunteer = volunteer;
        this.address = address;
        this.date = date;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }
    public Performance getPerformance() { return performance; }
    public void setPerformance(Performance performance) { this.performance = performance; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public OffsetDateTime getDate() { return date; }
    public void setDate(OffsetDateTime date) { this.date = date; }
    public Volunteer getVolunteer() { return volunteer; }
    public void setVolunteer(Volunteer volunteer) { this.volunteer = volunteer; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fest f)) return false;
        return Objects.equals(id, f.id);
    }

    @Override
    public int hashCode() { return Objects.hashCode(id); }

    @Override
    public String toString() {
        return String.format("Фестиваль{id=%d, address='%s', date=%s}", id, address, date);
    }
}