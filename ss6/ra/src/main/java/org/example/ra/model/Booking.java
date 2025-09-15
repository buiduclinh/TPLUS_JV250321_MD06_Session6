package org.example.ra.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "booking")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    @Pattern(regexp = "PENDING|CONFIRMED|CANCELLED",message = "status only get values: PENDING, CONFIRMED, CANCELLED")
    private String status;
    private LocalDateTime createdAt = LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;
}
