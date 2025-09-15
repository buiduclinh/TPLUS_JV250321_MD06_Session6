package org.example.ra.controller;

import jakarta.validation.Valid;
import org.example.ra.dto.OccupancyRate;
import org.example.ra.model.Room;
import org.example.ra.respone.APIDataResponse;
import org.example.ra.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping
    public ResponseEntity<APIDataResponse<Page<Room>>> getAllRoom(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        APIDataResponse<Page<Room>> roomList = roomService.roomList(page,size, Sort.by(Sort.Direction.ASC,"id"));
        return ResponseEntity.ok(roomList);
    }

    @PostMapping
    public ResponseEntity<APIDataResponse<Room>> createRoom(@Valid @RequestBody Room room){
        APIDataResponse<Room> room1 = roomService.createRoom(room);
        return ResponseEntity.ok(room1);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIDataResponse<Room>> updateRoom(@Valid @RequestBody Room room, @PathVariable Long id){
        room.setId(id);
        APIDataResponse<Room> updateRoom = roomService.updateRoom(room);
        return ResponseEntity.ok(updateRoom);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id){
        roomService.deleteRoomById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findRoomsByTypeAndStatus")
    public ResponseEntity<APIDataResponse<Page<Room>>> findRoomsByTypeAndStatus(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam String type, @RequestParam String status){
        APIDataResponse<Page<Room>> roomList = roomService.findRoomsByTypeAndStatus(page, size, type, status);
        return ResponseEntity.ok(roomList);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<APIDataResponse<Void>> updateStatusRoom(@PathVariable Long id, @RequestParam String status){
        roomService.updateRoomStatus(id,status);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/occupancyRate")
    public ResponseEntity<APIDataResponse<OccupancyRate>> occupancyRateAPIDataResponse(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end){
        return ResponseEntity.ok(roomService.occupancyRateAPIDataResponse(start, end));
    }

    @GetMapping("/averageStayDays")
    public ResponseEntity<APIDataResponse<Double>> getAverageStayDays(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end){
        return ResponseEntity.ok(roomService.getAverageStayDays(start, end));
    }
}
