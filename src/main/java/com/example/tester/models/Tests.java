package com.example.tester.models;


import javax.persistence.*;

@Entity
public class Tests {

    @Id
    @GeneratedValue ( strategy = GenerationType.AUTO)
    @Column(name = "idtest")
    private Long id;

    private String title;

    public Tests() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
