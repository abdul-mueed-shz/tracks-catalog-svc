package com.abdul.catalogservice.unit.domain.track.usecase;

import com.abdul.catalogservice.domain.common.model.PageInfo;
import com.abdul.catalogservice.domain.common.model.PaginationInfo;
import com.abdul.catalogservice.domain.common.model.SortInfo;
import com.abdul.catalogservice.domain.track.model.TrackFilterInfo;
import com.abdul.catalogservice.domain.track.model.TrackInfo;
import com.abdul.catalogservice.domain.track.port.out.TrackRepository;
import com.abdul.catalogservice.domain.track.usecase.GetTracksUseCaseImpl;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class GetTracksUseCaseImplTest {
    private final TrackRepository trackRepository = mock(TrackRepository.class);
    private final GetTracksUseCaseImpl useCase = new GetTracksUseCaseImpl(trackRepository);

    @Test
    void getsTracksWithFilterPaginationAndSort() {
        TrackFilterInfo filter = TrackFilterInfo.builder().userId(7L).build();
        PaginationInfo pagination = new PaginationInfo();
        SortInfo sort = new SortInfo();
        PageInfo<TrackInfo> page = PageInfo.<TrackInfo>builder().size(1).build();
        when(trackRepository.getAll(filter, pagination, sort)).thenReturn(page);

        assertThat(useCase.execute(filter, pagination, sort)).isSameAs(page);
        verify(trackRepository).getAll(filter, pagination, sort);
    }
}
