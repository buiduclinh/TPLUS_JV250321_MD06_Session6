package org.example.ra.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rooms")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Can't blank the room number")
    @Column(nullable = false, unique = true)
    private String roomNumber;

    @NotBlank(message = "Can't blank this")
    @Pattern(regexp = "SINGLE|DOUBLE|SUITE",message = "type error,type only receive value: SINGLE/DOUBLE/SUITE")
    private String type;

    @NotNull(message = "Can't not blank")
    @Positive
    private Double price;

    @Positive
    @NotNull(message = "Can't not blank")
    private int capacity;

    @NotBlank(message = "Can't blank this")
    @Pattern(regexp = "AVAILABLE|BOOKED|MAINTENANCE",message = "status error,status only receive value : AVAILABLE/BOOKED/MAINTENANCE")
    private String status;

    private String image;
}