package com.irfan.moneyger.service.impl;

import com.irfan.moneyger.dto.request.UserRequest;
import com.irfan.moneyger.dto.response.UserResponse;
import com.irfan.moneyger.entity.MUser;
import com.irfan.moneyger.repository.UserRepository;
import com.irfan.moneyger.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserResponse create(UserRequest userRequest) {
        String userId = UUID.randomUUID().toString();

        userRepository.createQuery(
            userId,
            userRequest.getFirstName(),
            userRequest.getLastName(),
                0L,
                true
        );

        MUser user = findByIdOrThrowException(userId);

        return userResponseBuilder(user);
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponse getByIdDTO(String id) {
        MUser user = findByIdOrThrowException(id);

        return userResponseBuilder(user);
    }

    @Override
    public MUser getByIdEntity(String id) {
        return findByIdOrThrowException(id);
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponse getAll() {
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserResponse update(UserRequest userRequest) {
        MUser currUser = findByIdOrThrowException(userRequest.getId());

        userRepository.updateQuery(
            userRequest.getId(),
            userRequest.getFirstName(),
            userRequest.getLastName(),
            userRequest.getBalance()
        );


        MUser updatedUser = MUser.builder()
                .id(userRequest.getId())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .balance(userRequest.getBalance())
                .isActive(currUser.getIsActive())
                .build();

        return userResponseBuilder(updatedUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserResponse updateIsActiveById(String id, Boolean isActive) {
        MUser currUser = findByIdOrThrowException(id);

        userRepository.updateIsActiveQuery(id, isActive);

        MUser updatedUser = MUser.builder()
                .id(currUser.getId())
                .firstName(currUser.getFirstName())
                .lastName(currUser.getLastName())
                .balance(currUser.getBalance())
                .isActive(isActive)
                .build();

        return userResponseBuilder(updatedUser);
    }

    @Override
    public UserResponse updateBalanceById(String id, Long balance) {
        MUser currUser = findByIdOrThrowException(id);

        userRepository.updateBalanceQuery(id, balance);

        MUser updatedUser = MUser.builder()
                .id(currUser.getId())
                .firstName(currUser.getFirstName())
                .lastName(currUser.getLastName())
                .balance(balance)
                .isActive(currUser.getIsActive())
                .build();

        return userResponseBuilder(updatedUser);
    }

    public MUser findByIdOrThrowException(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
    }

    public UserResponse userResponseBuilder(MUser user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .balance(user.getBalance())
                .isActive(user.getIsActive())
                .build();
    }
}
