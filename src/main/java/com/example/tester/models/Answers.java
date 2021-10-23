package com.example.tester.models;

import javax.persistence.*;

@Entity
public class Answers {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "idanswer")
    private Long id;

    private String answer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idquestion")
    private Questions questions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public com.example.tester.models.Questions getQuestions() {
        return questions;
    }

    public void setQuestions(com.example.tester.models.Questions questions) {
        this.questions = questions;
    }
}
