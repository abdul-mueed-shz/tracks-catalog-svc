package com.abdul.catalogservice.adapter.in.web.controller;

import com.abdul.catalogservice.adapter.in.web.dto.RegisterUserRequest;
import com.abdul.catalogservice.adapter.in.web.dto.UpdateUserRequest;
import com.abdul.catalogservice.adapter.in.web.dto.UserResponse;
import com.abdul.catalogservice.adapter.in.web.mapper.UserDtoToDomainMapper;
import com.abdul.catalogservice.domain.common.model.PageInfo;
import com.abdul.catalogservice.domain.common.model.PaginationInfo;
import com.abdul.catalogservice.domain.common.model.SortInfo;
import com.abdul.catalogservice.domain.user.model.UserFilterInfo;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import com.abdul.catalogservice.domain.user.port.in.CreateUserUseCase;
import com.abdul.catalogservice.domain.user.port.in.GetUserDetailsUseCase;
import com.abdul.catalogservice.domain.user.port.in.GetUsersUseCase;
import com.abdul.catalogservice.domain.user.port.in.UpdateUserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final GetUserDetailsUseCase userDetailsUseCase;
    private final GetUsersUseCase getUsersUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final UserDtoToDomainMapper userDtoToDomainMapper;

    @PostMapping
    public ResponseEntity<UserResponse> registerUser(
            @Valid @RequestBody RegisterUserRequest request
    ) {
        UserInfo createdUser = createUserUseCase.execute(userDtoToDomainMapper.registerUserRequestToUserInfo(request));
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{userId}")
                        .buildAndExpand(createdUser.getUuid())
                        .toUri()
        ).body(userDtoToDomainMapper.toUserResponse(createdUser));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable UUID userId,
            @Valid @RequestBody UpdateUserRequest request
    ) {
        UserInfo updatedUser = updateUserUseCase.execute(userId, userDtoToDomainMapper.updateUserRequestToUserInfo(request));
        return ResponseEntity.ok(userDtoToDomainMapper.toUserResponse(updatedUser));
    }

    @GetMapping
    public ResponseEntity<PageInfo<UserResponse>> getUsers(
            @ModelAttribute UserFilterInfo filterInfo,
            @ModelAttribute PaginationInfo paginationInfo,
            @ModelAttribute SortInfo userSortInfo
    ) {
        PageInfo<UserInfo> usersPage = getUsersUseCase.execute(
                filterInfo,
                paginationInfo,
                userSortInfo
        );
        return ResponseEntity.ok(usersPage.map(userDtoToDomainMapper::toUserResponse));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserDetails(
            @PathVariable UUID userId
    ) {
        UserInfo userInfo = userDetailsUseCase.execute(userId);
        return ResponseEntity.ok(userDtoToDomainMapper.toUserResponse(userInfo));
    }
}
