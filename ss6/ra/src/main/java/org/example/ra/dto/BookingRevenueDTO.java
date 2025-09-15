package org.example.ra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingRevenueDTO {
    private Long bookingId;
    private Double revenue;
}
