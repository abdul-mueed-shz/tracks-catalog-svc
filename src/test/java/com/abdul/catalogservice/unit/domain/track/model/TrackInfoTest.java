package com.abdul.catalogservice.unit.domain.track.model;

import com.abdul.catalogservice.domain.track.model.TrackInfo;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TrackInfoTest {
    @Test
    void assignsTrackToUserWithoutMutatingOriginal() {
        TrackInfo track = TrackInfo.builder().title("Track").build();
        UserInfo artist = UserInfo.builder().id(1L).isArtist(true).build();

        TrackInfo assigned = track.assignTo(artist);

        assertThat(track.getUser()).isNull();
        assertThat(assigned.getUser()).isSameAs(artist);
        assertThat(assigned.getTitle()).isEqualTo("Track");
    }
}
