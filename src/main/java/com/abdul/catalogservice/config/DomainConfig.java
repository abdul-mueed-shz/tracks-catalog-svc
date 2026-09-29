package com.abdul.catalogservice.config;

import com.abdul.catalogservice.domain.artist.port.in.CreateArtistUseCase;
import com.abdul.catalogservice.domain.artist.port.in.GetArtistDetailsUseCase;
import com.abdul.catalogservice.domain.artist.port.out.ArtistRepository;
import com.abdul.catalogservice.domain.artist.usecase.CreateArtistUseCaseImpl;
import com.abdul.catalogservice.domain.artist.usecase.GetArtistDetailsUseCaseImpl;
import com.abdul.catalogservice.domain.track.port.in.GetTracksByArtistUseCase;
import com.abdul.catalogservice.domain.track.port.out.TrackRepository;
import com.abdul.catalogservice.domain.track.usecase.GetTracksByArtistUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {
    @Bean
    public GetTracksByArtistUseCase getTracksByArtistUseCase(TrackRepository trackRepository) {
        return new GetTracksByArtistUseCaseImpl(trackRepository);
    }

    @Bean
    public GetArtistDetailsUseCase getArtistDetailsUseCase(ArtistRepository artistRepository) {
        return new GetArtistDetailsUseCaseImpl(artistRepository);
    }

    @Bean
    public CreateArtistUseCase getCreateArtistUseCase(ArtistRepository artistRepository) {
        return new CreateArtistUseCaseImpl(artistRepository);
    }
}
