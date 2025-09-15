package org.example.ra.service;

import org.example.ra.model.Booking;
import org.example.ra.respone.APIDataResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface BookingService {
    APIDataResponse<Booking> createBooking(Booking booking);
    APIDataResponse<Booking> cancelBooking(Long userId,Long roomId ,Booking booking);
    APIDataResponse<Page<Booking>> getBookings(Long userId,int page,int size,Sort sort);
    Booking findBooking(Long id);
}
