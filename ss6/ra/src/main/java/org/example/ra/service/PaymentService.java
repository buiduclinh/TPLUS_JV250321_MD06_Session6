package org.example.ra.service;

import org.example.ra.dto.BookingRevenueDTO;
import org.example.ra.model.Payment;
import org.example.ra.respone.APIDataResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;

public interface PaymentService {
    APIDataResponse<Payment> createPayment(Long roomId, Long bookingId, Payment payment);
    APIDataResponse<Page<BookingRevenueDTO>> getBookingRevenue(int page, int size, Sort sort, LocalDateTime paidAt);
}
