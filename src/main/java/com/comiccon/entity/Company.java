package com.comiccon.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    @Column(name = "company_name", nullable = false, length = 300)
    private String companyName;

    @Column(name = "area", nullable = false)
    private Short area;

    @Column(name = "number_table", nullable = false)
    private Short numberTable;

    protected Company() {}

    public Company(Artist artist, String companyName, Short area, Short numberTable) {
        this.artist = artist;
        this.companyName = companyName;
        this.area = area;
        this.numberTable = numberTable;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Artist getArtist() { return artist; }
    public void setArtist(Artist artist) { this.artist = artist; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public Short getArea() { return area; }
    public void setArea(Short area) { this.area = area; }
    public Short getNumberTable() { return numberTable; }
    public void setNumberTable(Short numberTable) { this.numberTable = numberTable; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company company)) return false;
        return Objects.equals(id, company.id);
    }

    @Override
    public int hashCode() { return Objects.hashCode(id); }

    @Override
    public String toString() {
        return String.format("Компания{id=%d, название='%s', площадь=%d, стол=%d }", id, companyName, area, numberTable);
    }
}

