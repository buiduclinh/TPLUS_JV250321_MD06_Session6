package org.example.ra.repo;

import org.example.ra.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT SUM(p.amount) FROM Payment p JOIN p.bookings b WHERE p.paidAt = :paidAt  GROUP BY b.id")
    Page<Object[]> findAmountByPaidAt(Pageable pageable, @Param("paidAt") LocalDateTime paidAt);
}
