package com.irfan.moneyger.service;

import com.irfan.moneyger.dto.request.UserRequest;
import com.irfan.moneyger.dto.response.UserResponse;

public interface UserService {
    UserResponse create(UserRequest userRequest);
    UserResponse getById(String id);
    UserResponse getAll();
    UserResponse update(UserRequest userRequest);
    UserResponse updateIsActiveById(String id, Boolean isActive);
}
