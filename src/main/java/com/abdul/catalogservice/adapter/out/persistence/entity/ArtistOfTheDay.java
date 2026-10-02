package com.abdul.catalogservice.adapter.out.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "artist_of_the_day")
public class ArtistOfTheDay {
    @Id
    private LocalDate day;

    @ManyToOne(optional = false)
    @JoinColumn(name = "artist_id", nullable = false)
    private User artist;
}
