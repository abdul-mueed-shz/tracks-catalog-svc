package com.abdul.catalogservice.adapter.in.web.controller;

import com.abdul.catalogservice.adapter.in.web.dto.RegisterUserRequest;
import com.abdul.catalogservice.adapter.in.web.dto.UpdateUserRequest;
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
    public ResponseEntity<UserInfo> registerUser(
            @Valid @RequestBody RegisterUserRequest request
    ) {
        UserInfo createdUser = createUserUseCase.execute(userDtoToDomainMapper.registerUserRequestToUserInfo(request));
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{userId}")
                        .buildAndExpand(createdUser.getId())
                        .toUri()
        ).body(createdUser);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserInfo> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserRequest request
    ) {
        return ResponseEntity.ok(updateUserUseCase.execute(userId, userDtoToDomainMapper.updateUserRequestToUserInfo(request)));
    }

    @GetMapping
    public ResponseEntity<PageInfo<UserInfo>> getUsers(
            @ModelAttribute UserFilterInfo filterInfo,
            @ModelAttribute PaginationInfo paginationInfo,
            @ModelAttribute SortInfo userSortInfo
    ) {
        return ResponseEntity.ok(getUsersUseCase.execute(
                filterInfo,
                paginationInfo,
                userSortInfo
        ));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfo> getUserDetails(
            @PathVariable Long userId
    ) {
        UserInfo userInfo = userDetailsUseCase.execute(userId);
        return ResponseEntity.ok(userInfo);
    }
}
