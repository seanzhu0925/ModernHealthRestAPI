package com.example.test.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long section_id;

    @ManyToOne
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @OneToMany(mappedBy = "section")
    private Set<Activity> activitySet;

    @NotNull
    @Size(max = 128)
    private String name;

    @NotNull
    @Size(max = 128)
    private String description;

    @Lob
    @NotNull
    private byte[] imageContent;

    @NotNull
    private int order_index;

    public Section(String description, byte[] imageContent, String name, int order_index, Program program) {
        this.program = program;
        this.name = name;
        this.description = description;
        this.imageContent = imageContent;
        this.order_index = order_index;
    }
}
