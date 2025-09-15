package org.example.ra.controller;

import jakarta.validation.Valid;
import org.example.ra.model.User;
import org.example.ra.respone.APIDataResponse;
import org.example.ra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<APIDataResponse<Page<User>>> userList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        APIDataResponse<Page<User>> userList = userService.roomList(page, size, Sort.by(Sort.Direction.ASC, "id"));
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIDataResponse<User>> getUser(@PathVariable Long id){
        APIDataResponse<User> user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<APIDataResponse<User>> createUser(@Valid @RequestBody User user){
        APIDataResponse<User> userResponse = userService.createUser(user);
        return ResponseEntity.ok(userResponse);
    }

}
