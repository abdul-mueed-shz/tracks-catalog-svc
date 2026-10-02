package com.abdul.catalogservice.domain.user.usecase;

import com.abdul.catalogservice.domain.common.model.PageInfo;
import com.abdul.catalogservice.domain.common.model.PaginationInfo;
import com.abdul.catalogservice.domain.common.model.SortInfo;
import com.abdul.catalogservice.domain.user.model.UserFilterInfo;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import com.abdul.catalogservice.domain.user.port.in.GetUsersUseCase;
import com.abdul.catalogservice.domain.user.port.out.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetUsersUseCaseImpl implements GetUsersUseCase {
    private final UserRepository userRepository;

    @Override
    public PageInfo<UserInfo> execute(
            UserFilterInfo filterInfo,
            PaginationInfo paginationInfo,
            SortInfo sortInfo
    ) {
        return userRepository.getAll(filterInfo, paginationInfo, sortInfo);
    }
}
