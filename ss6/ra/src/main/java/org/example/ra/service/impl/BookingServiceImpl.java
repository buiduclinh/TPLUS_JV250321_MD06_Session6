package org.example.ra.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.ra.model.Booking;
import org.example.ra.model.Room;
import org.example.ra.model.User;
import org.example.ra.repo.BookingRepository;
import org.example.ra.respone.APIDataResponse;
import org.example.ra.service.BookingService;
import org.example.ra.service.RoomService;
import org.example.ra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.example.ra.model.Status.CANCELLED;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RoomService roomService;

    public APIDataResponse<Booking> createBooking(Booking booking){
        Booking booking1 = bookingRepository.save(booking);
        APIDataResponse<Booking> response = new APIDataResponse<>();
        response.setData(booking1);
        response.setStatus(HttpStatus.CREATED);
        response.setMessage("booking created");
        response.setError(null);
        response.setSuccess(true);
        return response;
    }

    public APIDataResponse<Booking> cancelBooking(Long userId,Long roomId ,Booking booking){
        Booking updatedBooking = findBooking(booking.getId());
        User user = userService.getUser(userId).getData();
        Room room = roomService.findRoomById(roomId);
        updatedBooking.setStatus(CANCELLED.toString());
        updatedBooking.setUser(user);
        updatedBooking.setRoom(room);
        bookingRepository.save(updatedBooking);
        APIDataResponse<Booking> response = new APIDataResponse<>();
        response.setData(booking);
        response.setStatus(HttpStatus.OK);
        response.setMessage("booking cancelled");
        response.setError(null);
        response.setSuccess(true);
        return response;
    }

    public Booking findBooking(Long id){
        return bookingRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Booking not found"));
    }

    public APIDataResponse<Page<Booking>> getBookings(Long userId,int page,int size,Sort sort){
        Page<Booking> bookingList = bookingRepository.findBookingsByUser_Id(userId,PageRequest.of(page,size,sort));
        APIDataResponse<Page<Booking>> response = new APIDataResponse<>();
        response.setData(bookingList);
        response.setStatus(HttpStatus.OK);
        response.setMessage("bookings found");
        response.setError(null);
        response.setSuccess(true);
        return response;
    }
}
