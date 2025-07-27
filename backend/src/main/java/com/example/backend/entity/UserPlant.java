package com.example.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_plants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPlant {
    @Id
    @Column(name = "user_plant_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userPlantId;

    @NotBlank
    @Column(name = "user_plant_name", nullable = false, length = 100)
    private String userPlantName;

    @NotNull
    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @NotNull
    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinColumn(name="plant_id", nullable = false)
    private Plant plant;
}
