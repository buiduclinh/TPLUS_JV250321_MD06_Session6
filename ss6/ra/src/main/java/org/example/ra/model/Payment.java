package org.example.ra.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "payments")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
    @Positive
    private Double amount;
    @Pattern(regexp = "CASH|CARD",message = "Only get values by : CASH, CARD")
    private String method;
    private LocalDateTime paidAt;
    @OneToMany(mappedBy = "payment",cascade = CascadeType.ALL)
    private List<Booking> bookings;
}
