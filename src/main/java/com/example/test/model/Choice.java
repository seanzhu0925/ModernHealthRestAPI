package com.example.test.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Choice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long choice_id;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    private String description;

    public Choice(Question question, String description) {
        this.question = question;
        this.description = description;
    }
}
