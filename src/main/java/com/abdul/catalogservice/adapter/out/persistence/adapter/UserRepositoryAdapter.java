package com.abdul.catalogservice.adapter.out.persistence.adapter;

import com.abdul.catalogservice.adapter.out.persistence.entity.User;
import com.abdul.catalogservice.adapter.out.persistence.entity.UserAlias;
import com.abdul.catalogservice.adapter.out.persistence.mapper.UserAliasMapper;
import com.abdul.catalogservice.adapter.out.persistence.mapper.UserMapper;
import com.abdul.catalogservice.adapter.out.persistence.repository.UserJpaRepository;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import com.abdul.catalogservice.domain.user.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {
    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;
    private final UserAliasMapper userAliasMapper;

    @Override
    public UserInfo getUserById(Long id) {
        Optional<User> userOptional = userJpaRepository.findById(id);
        return userOptional.map(userMapper::toDto).orElse(null);
    }

    @Override
    public UserInfo createUser(UserInfo userInfo) {
        User user = userMapper.toEntity(userInfo);
        return userMapper.toDto(userJpaRepository.save(user));
    }

    @Override
    public UserInfo updateUser(UserInfo userInfo) {
        User user = userJpaRepository.findById(userInfo.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userInfo.getId()));
        userMapper.updateEntity(userInfo, user);
        addNewAliases(userInfo, user);
        return userMapper.toDto(userJpaRepository.save(user));
    }

    private void addNewAliases(UserInfo userInfo, User user) {
        Set<String> existingNames = new HashSet<>(user.getAliases().stream()
                .map(UserAlias::getNormalizedName)
                .toList());

        userInfo.getAliases().stream()
                .filter(alias -> alias.getId() == null)
                .filter(alias -> existingNames.add(alias.getNormalizedName()))
                .map(alias -> userAliasMapper.toEntity(alias, user))
                .forEach(alias -> {
                    user.getAliases().add(alias);
                });
    }
}
