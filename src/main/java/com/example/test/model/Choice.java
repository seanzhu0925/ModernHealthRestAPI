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
public class Choice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long choice_id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private String description;

    public Choice(Question question, String description) {
        this.question = question;
        this.description = description;
    }
}
