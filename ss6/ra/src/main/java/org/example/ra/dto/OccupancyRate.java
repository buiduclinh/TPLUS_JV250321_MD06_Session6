package org.example.ra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OccupancyRate {
    Long countRooms;
    Long countBookingRoomDate;
    float rate;
}
