package com.irfan.moneyger.controller;

import com.irfan.moneyger.constant.APIUrl;
import com.irfan.moneyger.dto.request.UserRequest;
import com.irfan.moneyger.dto.response.CommonResponse;
import com.irfan.moneyger.dto.response.PagingResponse;
import com.irfan.moneyger.dto.response.UserResponse;
import com.irfan.moneyger.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        UserResponse userResponse = userService.getByIdDTO(id);

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
    public ResponseEntity<CommonResponse<List<UserResponse>>> getAllUsers(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "firstName") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @RequestParam(name = "firstName", required = false) String firstName

    ) {
        UserRequest userRequest = UserRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .firstName(firstName)
                .build();
        Page<UserResponse> user = userService.getAll(userRequest);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(user.getTotalPages())
                .totalElement(user.getTotalElements())
                .page(user.getPageable().getPageNumber() + 1)
                .size(user.getPageable().getPageSize())
                .hasNext(user.hasNext())
                .hasPrevious(user.hasPrevious())
                .build();

        CommonResponse<List<UserResponse>> response = CommonResponse.<List<UserResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("users fetched successfully")
                .data(user.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity
                .ok(response);
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
