package com.example.test.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Section implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long section_id;

    @ManyToOne
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
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
