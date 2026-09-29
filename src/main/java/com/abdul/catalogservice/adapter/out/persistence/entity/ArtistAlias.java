package com.abdul.catalogservice.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "artist_aliases",
        indexes = @Index(name = "idx_artist_aliases_normalized_name", columnList = "normalized_name"),
        uniqueConstraints = @UniqueConstraint(name = "uq_artist_alias", columnNames = {"artist_id", "normalized_name"})
)
public class ArtistAlias extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    @Column(name = "alias_name", nullable = false)
    private String aliasName;

    @Column(name = "normalized_name", nullable = false)
    private String normalizedName;

    @Column(name = "is_primary")
    private Boolean isPrimary;
}
