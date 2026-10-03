package com.abdul.catalogservice.unit.domain.artistofday.model;

import com.abdul.catalogservice.domain.artist.model.ArtistInfo;
import com.abdul.catalogservice.domain.artistofday.model.ArtistRotationInfo;
import com.abdul.catalogservice.domain.artistofday.port.out.ArtistCatalog;
import com.abdul.catalogservice.domain.common.exception.NotFoundException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class ArtistRotationInfoTest {

    private final ArtistCatalog artistCatalog = mock(ArtistCatalog.class);

    @Test
    void selectsFirstArtistWhenLastArtistIdIsNull() {
        ArtistRotationInfo rotation = ArtistRotationInfo.builder().id(1L).build();
        ArtistInfo firstArtist = new ArtistInfo(10L, "First");
        when(artistCatalog.findFirstArtist()).thenReturn(firstArtist);

        ArtistInfo selected = rotation.selectNextArtist(artistCatalog);

        assertThat(selected).isEqualTo(firstArtist);
        assertThat(rotation.getLastArtistId()).isEqualTo(10L);
        verify(artistCatalog).findFirstArtist();
        verify(artistCatalog, never()).findArtistAfterId(any());
    }

    @Test
    void selectsNextArtistWhenLastArtistIdExists() {
        ArtistRotationInfo rotation = ArtistRotationInfo.builder().id(1L).lastArtistId(10L).build();
        ArtistInfo nextArtist = new ArtistInfo(20L, "Second");
        when(artistCatalog.findArtistAfterId(10L)).thenReturn(nextArtist);

        ArtistInfo selected = rotation.selectNextArtist(artistCatalog);

        assertThat(selected).isEqualTo(nextArtist);
        assertThat(rotation.getLastArtistId()).isEqualTo(20L);
        verify(artistCatalog).findArtistAfterId(10L);
        verify(artistCatalog, never()).findFirstArtist();
    }

    @Test
    void wrapsAroundToFirstArtistWhenNoArtistAfterLastArtistId() {
        ArtistRotationInfo rotation = ArtistRotationInfo.builder().id(1L).lastArtistId(30L).build();
        ArtistInfo firstArtist = new ArtistInfo(10L, "First");
        when(artistCatalog.findArtistAfterId(30L)).thenReturn(null);
        when(artistCatalog.findFirstArtist()).thenReturn(firstArtist);

        ArtistInfo selected = rotation.selectNextArtist(artistCatalog);

        assertThat(selected).isEqualTo(firstArtist);
        assertThat(rotation.getLastArtistId()).isEqualTo(10L);
        verify(artistCatalog).findArtistAfterId(30L);
        verify(artistCatalog).findFirstArtist();
    }

    @Test
    void throwsNotFoundExceptionWhenNoArtistFound() {
        ArtistRotationInfo rotation = ArtistRotationInfo.builder().id(1L).build();
        when(artistCatalog.findFirstArtist()).thenReturn(null);

        assertThatThrownBy(() -> rotation.selectNextArtist(artistCatalog))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("No artist found for the day");
    }

    @Test
    void advancesLastArtistId() {
        ArtistRotationInfo rotation = ArtistRotationInfo.builder().id(1L).build();
        rotation.advanceTo(42L);

        assertThat(rotation.getLastArtistId()).isEqualTo(42L);
    }
}
