package com.abdul.catalogservice.unit.domain.user.usecase;

import com.abdul.catalogservice.domain.common.model.PageInfo;
import com.abdul.catalogservice.domain.common.model.PaginationInfo;
import com.abdul.catalogservice.domain.common.model.SortInfo;
import com.abdul.catalogservice.domain.user.model.UserFilterInfo;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import com.abdul.catalogservice.domain.user.port.out.UserRepository;
import com.abdul.catalogservice.domain.user.usecase.GetUsersUseCaseImpl;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class GetUsersUseCaseImplTest {
    private final UserRepository userRepository = mock(UserRepository.class);
    private final GetUsersUseCaseImpl useCase = new GetUsersUseCaseImpl(userRepository);

    @Test
    void getsUsersWithFilterPaginationAndSort() {
        UserFilterInfo filter = UserFilterInfo.builder().isArtist(true).build();
        PaginationInfo pagination = new PaginationInfo();
        SortInfo sort = new SortInfo();
        PageInfo<UserInfo> page = PageInfo.<UserInfo>builder().size(1).build();
        when(userRepository.getAll(filter, pagination, sort)).thenReturn(page);

        assertThat(useCase.execute(filter, pagination, sort)).isSameAs(page);
        verify(userRepository).getAll(filter, pagination, sort);
    }
}
