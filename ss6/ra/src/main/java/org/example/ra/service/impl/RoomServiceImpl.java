package org.example.ra.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.ra.model.Room;
import org.example.ra.repo.RoomRepository;
import org.example.ra.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public Page<Room> roomList(int page, int size){
        return roomRepository.findAll(PageRequest.of(page, size));
    }

    public Room createRoom(Room room){
            return roomRepository.save(room);
    }
}
