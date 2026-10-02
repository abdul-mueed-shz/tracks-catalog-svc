package com.abdul.catalogservice.adapter.out.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "artist_rotation")
public class ArtistRotation {
    @Id
    private Long id;
    private Long lastArtistId;

    public void advanceTo(Long artistId) {
        this.lastArtistId = artistId;
    }
}
