package com.abdul.catalogservice.adapter.out.persistence.adapter;

import com.abdul.catalogservice.adapter.out.persistence.entity.User;
import com.abdul.catalogservice.adapter.out.persistence.mapper.UserDomainEntityMapper;
import com.abdul.catalogservice.adapter.out.persistence.repository.UserJpaRepository;
import com.abdul.catalogservice.adapter.out.persistence.specification.UserSpecification;
import com.abdul.catalogservice.adapter.out.persistence.utils.pagination.CursorPaginationSupport;
import com.abdul.catalogservice.domain.common.enums.SortProperty;
import com.abdul.catalogservice.domain.common.model.PageInfo;
import com.abdul.catalogservice.domain.common.model.PaginationInfo;
import com.abdul.catalogservice.domain.common.model.SortInfo;
import com.abdul.catalogservice.domain.user.model.UserFilterInfo;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import com.abdul.catalogservice.domain.user.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {
    private final UserJpaRepository userJpaRepository;
    private final UserDomainEntityMapper userDomainEntityMapper;
    private final UserSpecification userSpecification;
    private final CursorPaginationSupport cursorPaginationSupport;

    @Override
    public PageInfo<UserInfo> getAll(
            UserFilterInfo filterInfo,
            PaginationInfo paginationInfo,
            SortInfo sortInfo
    ) {
        return cursorPaginationSupport.execute(
                paginationInfo,
                sortInfo,
                (position, query) -> {
                    Specification<User> specification = userSpecification.filterBy(
                            filterInfo, position, query.sort()
                    );
                    return userJpaRepository.findAll(specification, query.sort(), query.limit().max());
                },
                userDomainEntityMapper::toDto,
                user -> SortProperty.CREATED_AT.getProperty().equals(sortInfo.getProperty())
                        ? user.getCreatedAt()
                        : user.getUpdatedAt(),
                User::getId
        );
    }

    @Override
    public UserInfo getUserById(Long id) {
        Optional<User> userOptional = userJpaRepository.findById(id);
        return userOptional.map(userDomainEntityMapper::toDto).orElse(null);
    }

    @Override
    public UserInfo createUser(UserInfo userInfo) {
        User user = userDomainEntityMapper.toEntity(userInfo);
        return userDomainEntityMapper.toDto(userJpaRepository.save(user));
    }

    @Override
    public UserInfo updateUser(UserInfo userInfo) {
        User user = userDomainEntityMapper.toUpdatedEntity(userInfo);
        // return userDomainEntityMapper.toDto(userJpaRepository.save(user));
        return null;
    }
}
