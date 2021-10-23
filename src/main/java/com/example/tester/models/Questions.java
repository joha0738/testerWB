package com.example.tester.models;

import javax.persistence.*;

@Entity
public class Questions {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name = "idquestion")
    private Long id;

    private String question;
    private boolean multi;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idtest")
    private Tests tests;

    public Questions() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Tests getTests() {
        return tests;
    }

    public void setTests(Tests tests) {
        this.tests = tests;
    }

    public boolean isMulti() {
        return multi;
    }

    public void setMulti(boolean multi) {
        this.multi = multi;
    }
}
