package com.martintonts.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sector {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String val;

    private String name;

    private int level;

    private int pos;

    @ManyToMany
    @JsonBackReference
    @JoinColumn(name = "userSectors_id")
    private List<UserSectors> userSectors;
}
