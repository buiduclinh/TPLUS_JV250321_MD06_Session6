package org.example.ra.service;

import org.example.ra.model.User;
import org.example.ra.respone.APIDataResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface UserService {
    APIDataResponse<Page<User>> roomList(int page, int size, Sort sort);
    APIDataResponse<User> createUser(User user);
    APIDataResponse<User> getUser(Long id);
}
