package org.example.ra.controller;

import jakarta.validation.Valid;
import org.example.ra.dto.BookingRevenueDTO;
import org.example.ra.model.Booking;
import org.example.ra.model.Payment;
import org.example.ra.respone.APIDataResponse;
import org.example.ra.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<APIDataResponse<Payment>> createBooking(@RequestParam Long roomId, @RequestParam Long bookingId, @Valid @RequestBody Payment payment) {
        APIDataResponse<Payment> response = paymentService.createPayment(roomId,bookingId,payment);
        return ResponseEntity.created(URI.create("/payments/"+response.getData().getId())).body(response);
    }

    @GetMapping("/getBookingRevenue")
    public ResponseEntity<APIDataResponse<Page<BookingRevenueDTO>>>  getBookingRevenue(int page, int size, Sort sort, LocalDateTime paidAt) {
        APIDataResponse<Page<BookingRevenueDTO>>  response = paymentService.getBookingRevenue(page, size, sort, paidAt);
        return ResponseEntity.ok(response);
    }
}
