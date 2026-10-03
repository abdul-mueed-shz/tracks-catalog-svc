package com.abdul.catalogservice.adapter.in.web.controller;

import com.abdul.catalogservice.domain.user.model.UserAliasInfo;
import com.abdul.catalogservice.domain.user.port.in.GetUserAliasUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/aliases")
@RequiredArgsConstructor
public class UserAliasController {
    private final GetUserAliasUseCase getUserAliasUseCase;

    @GetMapping
    public ResponseEntity<List<UserAliasInfo>> getUserAliases(
            @PathVariable("userId") Long userId
    ) {
        return ResponseEntity.ok(getUserAliasUseCase.getAll(userId));
    }
}
