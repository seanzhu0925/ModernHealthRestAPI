package com.example.test.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long activity_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;

    @Lob
    private String htmlContent;

    public Activity(String htmlContent, Section section){
        this.htmlContent = htmlContent;
        this.section = section;
    }

    public Activity(Question question, Section section){
        this.question = question;
        this.section = section;
    }
}
