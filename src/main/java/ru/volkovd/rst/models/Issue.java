package ru.volkovd.rst.models;

import javax.persistence.*;

@Entity
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long number;

    @ManyToOne
    @JoinColumn(name = "release_id", nullable = true)
    private Release release;

    public Issue() {
    }

    public Issue(Long number) {
        this.number = number;
    }

    public Issue(Long number, Release release) {
        this.number = number;
        this.release = release;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Release getRelease() {
        return release;
    }

    public void setRelease(Release release) {
        this.release = release;
    }
}
