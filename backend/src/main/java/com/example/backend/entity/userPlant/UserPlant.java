package com.example.backend.entity.userPlant;

import com.example.backend.entity.plant.Plant;
import com.example.backend.entity.user.User;
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

    public static UserPlant newUserPlant (String userPlantName, User user, Plant plant) {
        return new UserPlant(null, userPlantName, user, plant);
    }
}
