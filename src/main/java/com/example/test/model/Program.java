package com.example.test.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Program implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long program_id;

    @NotNull
    @Column(unique = true)
    @Size(max = 60, message = "Name can't be more than 60 character")
    private String name;

    @NotNull
    @Size(max = 128)
    private String description;

    @NotNull
    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Section> sectionSet;

    public Program(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
