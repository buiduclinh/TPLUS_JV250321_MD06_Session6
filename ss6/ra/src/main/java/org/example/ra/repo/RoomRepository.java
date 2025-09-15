package org.example.ra.repo;

import org.example.ra.model.Booking;
import org.example.ra.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {
    @Query("SELECT r.id FROM Room r WHERE r.status = :status AND r.type = :type GROUP BY r.id ORDER BY r.status DESC")
    Page<Room> findRoomByStatusAndType(Pageable pageable, @Param("status") String status, @Param("type") String type);
    @Query("UPDATE Room r SET r.status = :status WHERE r.id = :id")
    void updateRoomStatus(@Param("id") Long id, @Param("status") String status);
    @Query("SELECT b FROM Booking b " +
            "WHERE b.status IN ('PENDING','CONFIRMED') " +
            "AND b.checkOutDate >= :startDate " +
            "AND b.checkInDate <= :endDate")
    List<Booking> findValidBookings(@Param("startDate") LocalDateTime start,
                                    @Param("endDate") LocalDateTime end);
}
