package com.abdul.catalogservice.config;

import com.abdul.catalogservice.domain.artistofday.usecase.GetArtistOfTheDayUseCaseImpl;
import com.abdul.catalogservice.domain.artistofday.port.out.ArtistOfTheDayRepository;
import com.abdul.catalogservice.domain.common.validation.UserValidator;
import com.abdul.catalogservice.domain.track.port.in.AddTrackUseCase;
import com.abdul.catalogservice.domain.track.port.in.GetTracksUseCase;
import com.abdul.catalogservice.domain.track.port.out.TrackRepository;
import com.abdul.catalogservice.domain.track.usecase.AddTrackUseCaseImpl;
import com.abdul.catalogservice.domain.track.usecase.GetTracksUseCaseImpl;
import com.abdul.catalogservice.domain.user.port.in.GetUserDetailsUseCase;
import com.abdul.catalogservice.domain.user.port.in.GetUserAliasUseCase;
import com.abdul.catalogservice.domain.user.port.in.GetUsersUseCase;
import com.abdul.catalogservice.domain.user.port.out.UserAliasRepository;
import com.abdul.catalogservice.domain.user.port.out.UserRepository;
import com.abdul.catalogservice.domain.user.usecase.CreateUserUseCaseImpl;
import com.abdul.catalogservice.domain.user.usecase.GetUserDetailsUseCaseImpl;
import com.abdul.catalogservice.domain.user.usecase.GetUserAliasUseCaseImpl;
import com.abdul.catalogservice.domain.user.usecase.GetUsersUseCaseImpl;
import com.abdul.catalogservice.domain.user.usecase.UpdateUserUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.ZoneOffset;

@Configuration
public class DomainConfig {

    @Bean
    public GetArtistOfTheDayUseCaseImpl artistOfTheDayUseCase(
            UserRepository userRepository,
            ArtistOfTheDayRepository artistOfTheDayRepository,
            Clock artistOfTheDayClock
    ) {
        return new GetArtistOfTheDayUseCaseImpl(artistOfTheDayRepository, userRepository, artistOfTheDayClock);
    }

    @Bean
    public Clock artistOfTheDayClock() {
        return Clock.system(ZoneOffset.UTC);
    }

    @Bean
    public UserValidator userValidationHelper() {
        return new UserValidator();
    }

    @Bean
    public GetTracksUseCase getTracksByUserUseCase(TrackRepository trackRepository) {
        return new GetTracksUseCaseImpl(trackRepository);
    }

    @Bean
    public GetUserDetailsUseCase getUserDetailsUseCase(UserRepository userRepository, UserValidator userValidator) {
        return new GetUserDetailsUseCaseImpl(userRepository, userValidator);
    }

    @Bean
    public GetUserAliasUseCase getUserAliasUseCase(
            UserRepository userRepository,
            UserAliasRepository userAliasRepository,
            UserValidator userValidator
    ) {
        return new GetUserAliasUseCaseImpl(userRepository, userAliasRepository, userValidator);
    }

    @Bean
    public GetUsersUseCase getUsersUseCase(UserRepository userRepository) {
        return new GetUsersUseCaseImpl(userRepository);
    }

    @Bean
    public CreateUserUseCaseImpl createUserUseCase(
            UserRepository userRepository,
            UserAliasRepository userAliasRepository
    ) {
        return new CreateUserUseCaseImpl(userRepository, userAliasRepository);
    }

    @Bean
    public UpdateUserUseCaseImpl editUserNameUseCase(UserRepository userRepository,
                                                     UserAliasRepository userAliasRepository,
                                                     UserValidator userValidator) {
        return new UpdateUserUseCaseImpl(userRepository, userAliasRepository, userValidator);
    }

    @Bean
    public AddTrackUseCase addTrackUseCase(TrackRepository trackRepository,
                                           UserRepository userRepository,
                                           UserValidator userValidator) {
        return new AddTrackUseCaseImpl(trackRepository, userRepository, userValidator);
    }

}
