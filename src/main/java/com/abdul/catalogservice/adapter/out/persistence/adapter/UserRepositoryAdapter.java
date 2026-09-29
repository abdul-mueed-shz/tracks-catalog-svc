package com.abdul.catalogservice.adapter.out.persistence.adapter;

import com.abdul.catalogservice.adapter.out.mapper.UserMapper;
import com.abdul.catalogservice.adapter.out.persistence.entity.User;
import com.abdul.catalogservice.adapter.out.persistence.repository.UserJpaRepository;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import com.abdul.catalogservice.domain.user.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {
    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Override
    public UserInfo getUserById(Long id) {
        Optional<User> userOptional = userJpaRepository.findById(id);
        return userOptional.map(userMapper::toDto).orElse(null);
    }

    @Override
    public UserInfo upsertUser(UserInfo userInfo) {
        return userMapper.toDto(userJpaRepository.save(userMapper.toEntity(userInfo)));
    }
}
