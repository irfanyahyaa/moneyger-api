package com.irfan.moneyger.controller;

import com.irfan.moneyger.constant.APIUrl;
import com.irfan.moneyger.dto.request.UserRequest;
import com.irfan.moneyger.dto.response.UserResponse;
import com.irfan.moneyger.dto.response.CommonResponse;
import com.irfan.moneyger.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(APIUrl.USER)
public class UserController {
    public final UserService userService;

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<UserResponse>> createUser(
            @RequestBody UserRequest request
    ) {
        UserResponse userResponse = userService.create(request);

        CommonResponse<UserResponse> response = CommonResponse.<UserResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("user created successfully")
                .data(userResponse)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<UserResponse>> getUserById(
            @PathVariable("id") String id
    ) {
        UserResponse userResponse = userService.getById(id);

        CommonResponse<UserResponse> response = CommonResponse.<UserResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("user fetched successfully")
                .data(userResponse)
                .build();

        return ResponseEntity
                .ok(response);
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<UserResponse>> getAllUsers() {
        return null;
    }

    @PutMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<UserResponse>> updateUser(
            @RequestBody UserRequest request
    ) {
        UserResponse userResponse = userService.update(request);

        CommonResponse<UserResponse> response = CommonResponse.<UserResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("user updated successfully")
                .data(userResponse)
                .build();

        return ResponseEntity
                .ok(response);
    }

    @PutMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<UserResponse>> updateIsActive(
            @PathVariable("id") String id,
            @RequestParam("is_active") Boolean isActive
    ) {
        UserResponse userResponse = userService.updateIsActiveById(id, isActive);

        CommonResponse<UserResponse> response = CommonResponse.<UserResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("user updated successfully")
                .data(userResponse)
                .build();

        return ResponseEntity
                .ok(response);
    }
}
