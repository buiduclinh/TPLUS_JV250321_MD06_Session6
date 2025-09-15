package org.example.ra.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.ra.model.Room;
import org.example.ra.model.User;
import org.example.ra.repo.UserRepository;
import org.example.ra.respone.APIDataResponse;
import org.example.ra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    public APIDataResponse<Page<User>> roomList(int page, int size, Sort sort) {
        Page<User> users = userRepository.findAll(PageRequest.of(page, size,sort));
        APIDataResponse<Page<User>> apiDataResponse = new APIDataResponse<>();
        apiDataResponse.setData(users);
        apiDataResponse.setSuccess(true);
        apiDataResponse.setMessage("<UNK>");
        apiDataResponse.setStatus(HttpStatus.OK);
        apiDataResponse.setError(null);
        return apiDataResponse;
    }

    public APIDataResponse<User> getUser(Long id){
        User user =  userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("User not found"));
        APIDataResponse<User> apiDataResponse = new APIDataResponse<>();
        apiDataResponse.setData(user);
        apiDataResponse.setSuccess(true);
        apiDataResponse.setMessage("<UNK>");
        apiDataResponse.setStatus(HttpStatus.OK);
        apiDataResponse.setError(null);
        return apiDataResponse;
    }

    public APIDataResponse<User> createUser(User user) {
        User u = userRepository.save(user);
        APIDataResponse<User> apiDataResponse = new APIDataResponse<>();
        apiDataResponse.setData(u);
        apiDataResponse.setSuccess(true);
        apiDataResponse.setMessage("<UNK>");
        apiDataResponse.setStatus(HttpStatus.OK);
        apiDataResponse.setError(null);
        return apiDataResponse;
    }

}
