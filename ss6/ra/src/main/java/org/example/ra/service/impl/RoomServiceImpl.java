package org.example.ra.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.example.ra.dto.OccupancyRate;
import org.example.ra.model.Booking;
import org.example.ra.model.Room;
import org.example.ra.repo.BookingRepository;
import org.example.ra.repo.RoomRepository;
import org.example.ra.respone.APIDataResponse;
import org.example.ra.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.example.ra.model.Status.BOOKED;

@Slf4j
@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BookingRepository bookingRepository;

    public APIDataResponse<Page<Room>> roomList(int page, int size, Sort sort) {
        Page<Room> roomList = roomRepository.findAll(PageRequest.of(page, size, sort));
        APIDataResponse<Page<Room>> apiDataResponse = new APIDataResponse<>();
        apiDataResponse.setData(roomList);
        apiDataResponse.setStatus(HttpStatus.OK);
        apiDataResponse.setMessage("success");
        apiDataResponse.setError(null);
        apiDataResponse.setSuccess(true);
        return apiDataResponse;
    }

    public Room findRoomById(Long id) {
        return roomRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public APIDataResponse<Room> createRoom(Room room) {
        Room newRoom = roomRepository.save(room);
        APIDataResponse<Room> response = new APIDataResponse<>();
        response.setData(newRoom);
        response.setSuccess(true);
        response.setMessage("Room created successfully");
        response.setError(null);
        response.setStatus(HttpStatus.CREATED);
        return response;
    }

    public APIDataResponse<Room> updateRoom(Room room) {
        Room updatedRoom = findRoomById(room.getId());
        APIDataResponse<Room> response = new APIDataResponse<>();
        response.setData(updatedRoom);
        response.setSuccess(true);
        response.setMessage("Room updated successfully");
        response.setError(null);
        response.setStatus(HttpStatus.OK);
        return response;
    }

    public void deleteRoomById(Long id) {
        if (roomRepository.existsById(id)) {
            roomRepository.deleteById(id);
            APIDataResponse<Room> response = new APIDataResponse<>();
            response.setData(findRoomById(id));
            response.setSuccess(true);
            response.setMessage("Room deleted successfully");
            response.setError(null);
            response.setStatus(HttpStatus.OK);
        }
    }

    public APIDataResponse<Page<Room>> findRoomsByTypeAndStatus(int page, int size, String type, String status) {
        Page<Room> rooms = roomRepository.findRoomByStatusAndType(PageRequest.of(page, size), status, type);
        APIDataResponse<Page<Room>> apiDataResponse = new APIDataResponse<>();
        apiDataResponse.setData(rooms);
        apiDataResponse.setStatus(HttpStatus.OK);
        apiDataResponse.setMessage("success");
        apiDataResponse.setError(null);
        apiDataResponse.setSuccess(true);
        return apiDataResponse;
    }

    public APIDataResponse<Void> updateRoomStatus(Long id, String status) {
        Room room = findRoomById(id);
        if (room.getStatus().toString().equals(BOOKED)) {
            throw new IllegalArgumentException("Room status cannot be CHANGE");
        }
        room.setStatus(status);
        roomRepository.save(room);
        APIDataResponse<Void> response = new APIDataResponse<>();
        response.setStatus(HttpStatus.OK);
        response.setMessage("Room updated successfully");
        response.setError(null);
        response.setSuccess(true);
        return response;
    }

    public APIDataResponse<OccupancyRate> occupancyRateAPIDataResponse(LocalDateTime start, LocalDateTime end) {
        long countRooms = roomRepository.count();
        long totalDays = ChronoUnit.DAYS.between(start, end) + 1;
        long totalAvailableRoomDays = countRooms * totalDays;

        List<Booking> bookingList = bookingRepository.findByCheckInDateBetween(
                start.toLocalDate().atStartOfDay(),
                end.toLocalDate().atTime(23,59,59)
        );

        long bookedRoomDays = bookingList.stream()
                .mapToLong(b -> {
                    LocalDateTime checkIn = b.getCheckInDate().toLocalDate().atStartOfDay();
                    LocalDateTime checkOut = b.getCheckOutDate().toLocalDate().atTime(23,59,59);
                    return ChronoUnit.DAYS.between(checkIn, checkOut) + 1;
                })
                .sum();

        double rate = (double) bookedRoomDays / totalAvailableRoomDays * 100;

        OccupancyRate occupancyRate = new OccupancyRate();
        occupancyRate.setCountRooms(countRooms);
        occupancyRate.setCountBookingRoomDate(bookedRoomDays);
        occupancyRate.setRate((float) rate);

        APIDataResponse<OccupancyRate> apiDataResponse = new APIDataResponse<>();
        apiDataResponse.setData(occupancyRate);
        apiDataResponse.setStatus(HttpStatus.OK);
        apiDataResponse.setMessage("success");
        apiDataResponse.setError(null);
        apiDataResponse.setSuccess(true);
        return apiDataResponse;
    }

    public APIDataResponse<Double> getAverageStayDays(LocalDateTime start, LocalDateTime end) {
        List<Booking> bookingList = bookingRepository.findByStatusInAndCheckInDateBetween(
                Arrays.asList("PENDING", "CONFIRMED"),
                start,
                end
        );

        if (bookingList.isEmpty()) {
            APIDataResponse<Double> response = new APIDataResponse<>();
            response.setStatus(HttpStatus.OK);
            response.setMessage("No bookings found");
            response.setError(null);
            response.setSuccess(false);
            response.setData(null);
            return response;
        }

        long totalDays = bookingList.stream()
                .mapToLong(b -> {
                    LocalDateTime checkIn = b.getCheckInDate().toLocalDate().atStartOfDay();
                    LocalDateTime checkOut = b.getCheckOutDate().toLocalDate().atTime(23,59,59);
                    return ChronoUnit.DAYS.between(checkIn, checkOut) + 1;
                })
                .sum();

        double averageStayDays = (double) totalDays / bookingList.size();

        APIDataResponse<Double> apiDataResponse = new APIDataResponse<>();
        apiDataResponse.setStatus(HttpStatus.OK);
        apiDataResponse.setMessage("success");
        apiDataResponse.setError(null);
        apiDataResponse.setSuccess(true);
        apiDataResponse.setData(averageStayDays);
        return apiDataResponse;
    }
}
