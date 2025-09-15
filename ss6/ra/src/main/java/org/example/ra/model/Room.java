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

    @NotNull(message = "Can't blank this")
    @Pattern(regexp = "SINGLE|DOUBLE|SUITE",message = "type error")
    @Enumerated(EnumType.STRING)
    private Type type;

    @NotNull(message = "Can't not blank")
    @Positive
    private Double price;

    @Positive
    @NotNull(message = "Can't not blank")
    private int capacity;

    @Pattern(regexp = "AVAILABLE|BOOKED|MAINTENANCE",message = "status error")
    @Enumerated(EnumType.STRING)

    private Status status;

    private String image;
}