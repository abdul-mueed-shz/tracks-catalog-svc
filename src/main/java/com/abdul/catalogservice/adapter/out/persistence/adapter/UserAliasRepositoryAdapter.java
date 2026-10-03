package com.abdul.catalogservice.adapter.out.persistence.adapter;

import com.abdul.catalogservice.adapter.out.persistence.entity.User;
import com.abdul.catalogservice.adapter.out.persistence.mapper.UserAliasDomainEntityMapper;
import com.abdul.catalogservice.adapter.out.persistence.repository.UserAliasJpaRepository;
import com.abdul.catalogservice.adapter.out.persistence.repository.UserJpaRepository;
import com.abdul.catalogservice.domain.common.exception.NotFoundException;
import com.abdul.catalogservice.domain.user.model.UserAliasInfo;
import com.abdul.catalogservice.domain.user.port.out.UserAliasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserAliasRepositoryAdapter implements UserAliasRepository {
    private final UserJpaRepository userJpaRepository;
    private final UserAliasDomainEntityMapper mapper;
    private final UserAliasJpaRepository userAliasJpaRepository;

    @Override
    public boolean existsByUserIdAndNormalizedName(Long userId, String normalizedName) {
        return userAliasJpaRepository.existsByUser_IdAndNormalizedName(userId, normalizedName);
    }

    @Override
    public UserAliasInfo create(UserAliasInfo aliasInfo) {
        User user = userJpaRepository.findById(aliasInfo.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found."));
        return mapper.toDto(userAliasJpaRepository.save(mapper.toEntity(aliasInfo, user)));
    }

    @Override
    public List<UserAliasInfo> getAllByUserId(Long userId) {
        return userAliasJpaRepository.findAllByUser_IdOrderByIdAsc(userId).stream()
                .map(mapper::toDto)
                .toList();
    }
}
