package com.example.test.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long question_id;

    private String description;

    @OneToMany(mappedBy = "question")
    private Set<Choice> choiceSet;

    @OneToOne(mappedBy = "question")
    private Activity activity;

    public Question(String description, Activity activity) {
        this.description = description;
        this.activity = activity;
    }
}