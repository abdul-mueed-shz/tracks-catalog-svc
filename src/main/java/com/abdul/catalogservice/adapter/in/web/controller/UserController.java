package com.abdul.catalogservice.adapter.in.web.controller;

import com.abdul.catalogservice.adapter.in.web.dto.RegisterUserRequest;
import com.abdul.catalogservice.adapter.in.web.dto.UpdateUserNameRequest;
import com.abdul.catalogservice.adapter.in.web.mapper.UserDtoToDomainMapper;
import com.abdul.catalogservice.domain.common.model.PageInfo;
import com.abdul.catalogservice.domain.common.model.PaginationInfo;
import com.abdul.catalogservice.domain.common.model.SortInfo;
import com.abdul.catalogservice.domain.user.model.UserFilterInfo;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import com.abdul.catalogservice.domain.user.port.in.CreateUserUseCase;
import com.abdul.catalogservice.domain.user.port.in.GetUserDetailsUseCase;
import com.abdul.catalogservice.domain.user.port.in.GetUsersUseCase;
import com.abdul.catalogservice.domain.user.port.in.EditUserNameUseCase;
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
    private final EditUserNameUseCase editUserNameUseCase;
    private final UserDtoToDomainMapper userDtoToDomainMapper;

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

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfo> getUserDetails(
            @PathVariable Long userId
    ) {
        UserInfo userInfo = userDetailsUseCase.execute(userId);
        return ResponseEntity.ok(userInfo);
    }

    @PatchMapping("/{userId}/name")
    public ResponseEntity<UserInfo> editUserName(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserNameRequest request
    ) {
        return ResponseEntity.ok(editUserNameUseCase.execute(userId, request.name()));
    }
}
