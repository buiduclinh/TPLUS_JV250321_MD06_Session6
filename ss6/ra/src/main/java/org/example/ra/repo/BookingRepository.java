package org.example.ra.repo;

import org.example.ra.model.Booking;
import org.example.ra.respone.APIDataResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
   Page<Booking> findBookingsByUser_Id(Long userId, Pageable pageable);

    List<Booking> findByCheckInDateBetween(LocalDateTime start, LocalDateTime end);

    List<Booking> findByStatusInAndCheckInDateBetween(List<String> list, LocalDateTime start, LocalDateTime end);
}
