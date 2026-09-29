package com.abdul.catalogservice.adapter.in.web.controller;

import com.abdul.catalogservice.domain.user.model.UserInfo;
import com.abdul.catalogservice.domain.user.port.in.CreateUserUseCase;
import com.abdul.catalogservice.domain.user.port.in.GetUserDetailsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final GetUserDetailsUseCase userDetailsUseCase;
    private final CreateUserUseCase createUserUseCase;

    @PostMapping
    public ResponseEntity<UserInfo> registerUser(
            @RequestBody UserInfo userInfo
    ) {
        UserInfo createdUser = createUserUseCase.execute(userInfo);
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
}
