package com.example.test.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Activity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long activity_id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    @Lob
    private String htmlContent;

    public Activity(String htmlContent, Section section) {
        this.htmlContent = htmlContent;
        this.section = section;
    }

    public Activity(Question question, Section section) {
        this.question = question;
        this.section = section;
    }
}
