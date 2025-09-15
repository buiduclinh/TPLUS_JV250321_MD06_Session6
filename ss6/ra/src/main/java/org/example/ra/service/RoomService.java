package org.example.ra.service;

import org.example.ra.dto.OccupancyRate;
import org.example.ra.model.Room;
import org.example.ra.respone.APIDataResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;

public interface RoomService {
    APIDataResponse<Page<Room>> roomList(int page, int size, Sort sort);
    APIDataResponse<Room> createRoom(Room room);
    Room findRoomById(Long id);
    APIDataResponse<Room> updateRoom(Room room);
    void deleteRoomById(Long id);
    APIDataResponse<Page<Room>> findRoomsByTypeAndStatus(int page, int size, String type, String status);
    APIDataResponse<Void> updateRoomStatus(Long id,String status);
    APIDataResponse<OccupancyRate> occupancyRateAPIDataResponse(LocalDateTime start, LocalDateTime end);
    APIDataResponse<Double> getAverageStayDays(LocalDateTime start, LocalDateTime end);
}
