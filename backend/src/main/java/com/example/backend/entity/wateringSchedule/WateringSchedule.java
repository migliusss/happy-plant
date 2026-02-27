package com.example.backend.entity.wateringSchedule;

import com.example.backend.entity.userPlant.UserPlant;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "watering_schedule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WateringSchedule {
    @Id
    @Column(name = "watering_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer wateringId;

    @Column(name = "last_watering")
    private LocalDate lastWatering;

    @Column(name = "next_watering")
    private LocalDate nextWatering;

    @NotNull
    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinColumn(name="user_plant_id", nullable = false)
    private UserPlant userPlant;

    public static WateringSchedule newWateringSchedule(LocalDate lastWatering, LocalDate nextWatering, UserPlant userPlant) {
        return new WateringSchedule(null, lastWatering, nextWatering, userPlant);
    }
}
