package org.example.ra.controller;

import jakarta.validation.Valid;
import org.example.ra.model.Booking;
import org.example.ra.respone.APIDataResponse;
import org.example.ra.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingService  bookingService;

    @PostMapping
    public ResponseEntity<APIDataResponse<Booking>> createBooking(@Valid @RequestBody Booking booking) {
        APIDataResponse<Booking> response = bookingService.createBooking(booking);
        return ResponseEntity.created(URI.create("/booking")).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIDataResponse<Booking>> cancelBooking(@RequestParam Long userId,@RequestParam Long roomId,@Valid @RequestBody Booking booking) {
        APIDataResponse<Booking> response = bookingService.cancelBooking(userId,roomId,booking);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getBookingListByUserId")
    public ResponseEntity<APIDataResponse<Page<Booking>>> getBookingListByUserId(@RequestParam Long userId,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        APIDataResponse<Page<Booking>> response = bookingService.getBookings(userId,page,size,Sort.by("id").ascending());
        return ResponseEntity.ok(response);
    }
}
