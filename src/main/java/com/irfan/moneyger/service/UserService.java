package com.irfan.moneyger.service;

import com.irfan.moneyger.dto.request.UserRequest;
import com.irfan.moneyger.dto.response.UserResponse;
import com.irfan.moneyger.entity.MUser;

public interface UserService {
    UserResponse create(UserRequest userRequest);

    UserResponse getByIdDTO(String id);

    MUser getByIdEntity(String id);

    UserResponse getAll();

    UserResponse update(UserRequest userRequest);

    UserResponse updateIsActiveById(String id, Boolean isActive);

    UserResponse updateBalanceById(String id, Long balance);
}
