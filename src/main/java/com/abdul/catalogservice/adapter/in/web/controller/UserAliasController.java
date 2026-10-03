package com.abdul.catalogservice.adapter.in.web.controller;

import com.abdul.catalogservice.adapter.in.web.dto.UserAliasResponse;
import com.abdul.catalogservice.adapter.in.web.mapper.UserDtoToDomainMapper;
import com.abdul.catalogservice.domain.user.port.in.GetUserAliasUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users/{userId}/aliases")
@RequiredArgsConstructor
public class UserAliasController {
    private final GetUserAliasUseCase getUserAliasUseCase;
    private final UserDtoToDomainMapper userDtoToDomainMapper;

    @GetMapping
    public ResponseEntity<List<UserAliasResponse>> getUserAliases(
            @PathVariable("userId") UUID userId
    ) {
        return ResponseEntity.ok(userDtoToDomainMapper.toUserAliasResponseList(getUserAliasUseCase.getAll(userId)));
    }
}
