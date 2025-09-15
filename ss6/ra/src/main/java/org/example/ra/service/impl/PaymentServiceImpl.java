package org.example.ra.service.impl;

import org.example.ra.dto.BookingRevenueDTO;
import org.example.ra.model.Booking;
import org.example.ra.model.Payment;
import org.example.ra.model.Room;
import org.example.ra.repo.PaymentRepository;
import org.example.ra.respone.APIDataResponse;
import org.example.ra.service.BookingService;
import org.example.ra.service.PaymentService;
import org.example.ra.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.ra.model.Status.CONFIRMED;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoomService roomService;

    public APIDataResponse<Payment> createPayment(Long roomId, Long bookingId, Payment payment) {
        Booking booking = bookingService.findBooking(bookingId);
        if (booking.getStatus().toString().equals(CONFIRMED)) {
            Room room = roomService.findRoomById(roomId);
            payment.setBooking(booking);
            int bookingDays = booking.getCheckOutDate().getDayOfMonth() - booking.getCheckInDate().getDayOfMonth();
            Double Amount = room.getPrice() * bookingDays;
            payment.setAmount(payment.getAmount() + Amount);
            paymentRepository.save(payment);
            APIDataResponse<Payment> response = new APIDataResponse<>();
            response.setSuccess(true);
            response.setError(null);
            response.setStatus(HttpStatus.CREATED);
            response.setMessage("Payment created successfully");
            response.setData(payment);
            return response;
        } else {
            APIDataResponse<Payment> response = new APIDataResponse<>();
            response.setSuccess(false);
            response.setError(null);
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage("Payment status only get values: CONFIRMED");
            response.setData(payment);
            return response;
        }
    }

    public APIDataResponse<Page<BookingRevenueDTO>> getBookingRevenue(int page, int size, Sort sort, LocalDateTime paidAt) {
        Page<Object[]> result = paymentRepository.findAmountByPaidAt(PageRequest.of(page, size, sort), paidAt);
        Page<BookingRevenueDTO> bookingRevenueDTOPage = result.map(row -> new BookingRevenueDTO(
                ((Number) row[0]).longValue(),
                ((Number) row[1]).doubleValue()
        ));
        APIDataResponse<Page<BookingRevenueDTO>> response = new APIDataResponse<>();
        response.setData(bookingRevenueDTOPage);
        response.setSuccess(true);
        response.setError(null);
        response.setStatus(HttpStatus.OK);
        response.setMessage("Bookings revenue");
        response.setData(bookingRevenueDTOPage);
        return response;
    }
}
