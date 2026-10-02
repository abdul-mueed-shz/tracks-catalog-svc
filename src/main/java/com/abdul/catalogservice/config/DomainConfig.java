package com.abdul.catalogservice.config;

import com.abdul.catalogservice.domain.common.validation.UserValidator;
import com.abdul.catalogservice.domain.track.port.in.AddTrackUseCase;
import com.abdul.catalogservice.domain.track.usecase.AddTrackUseCaseImpl;
import com.abdul.catalogservice.domain.user.port.in.CreateUserUseCase;
import com.abdul.catalogservice.domain.user.port.in.GetUserDetailsUseCase;
import com.abdul.catalogservice.domain.user.port.in.GetUsersUseCase;
import com.abdul.catalogservice.domain.user.port.in.EditUserNameUseCase;
import com.abdul.catalogservice.domain.user.port.out.UserRepository;
import com.abdul.catalogservice.domain.user.usecase.CreateUserUseCaseImpl;
import com.abdul.catalogservice.domain.user.usecase.GetUserDetailsUseCaseImpl;
import com.abdul.catalogservice.domain.user.usecase.GetUsersUseCaseImpl;
import com.abdul.catalogservice.domain.user.usecase.EditUserNameUseCaseImpl;
import com.abdul.catalogservice.domain.track.port.in.GetTracksUseCase;
import com.abdul.catalogservice.domain.track.port.out.TrackRepository;
import com.abdul.catalogservice.domain.track.usecase.GetTracksUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public UserValidator userValidationHelper() {
        return new UserValidator();
    }

    @Bean
    public GetTracksUseCase getTracksByUserUseCase(TrackRepository trackRepository) {
        return new GetTracksUseCaseImpl(trackRepository);
    }

    @Bean
    public GetUserDetailsUseCase getUserDetailsUseCase(UserRepository userRepository) {
        return new GetUserDetailsUseCaseImpl(userRepository);
    }

    @Bean
    public GetUsersUseCase getUsersUseCase(UserRepository userRepository) {
        return new GetUsersUseCaseImpl(userRepository);
    }

    @Bean
    public CreateUserUseCase getCreateUserUseCase(UserRepository userRepository) {
        return new CreateUserUseCaseImpl(userRepository);
    }

    @Bean
    public EditUserNameUseCase editUserNameUseCase(UserRepository userRepository) {
        return new EditUserNameUseCaseImpl(userRepository);
    }

    @Bean
    public AddTrackUseCase addTrackUseCase(TrackRepository trackRepository,
                                           UserRepository userRepository,
                                           UserValidator userValidator) {
        return new AddTrackUseCaseImpl(trackRepository, userRepository, userValidator);
    }

}
