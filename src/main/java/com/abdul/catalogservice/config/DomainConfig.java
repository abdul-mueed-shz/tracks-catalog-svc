package com.abdul.catalogservice.config;

import com.abdul.catalogservice.domain.common.validation.UserValidator;
import com.abdul.catalogservice.domain.track.port.in.AddTrackUseCase;
import com.abdul.catalogservice.domain.track.usecase.AddTrackUseCaseImpl;
import com.abdul.catalogservice.domain.user.port.in.CreateUserUseCase;
import com.abdul.catalogservice.domain.user.port.in.GetUserDetailsUseCase;
import com.abdul.catalogservice.domain.user.port.in.EditUserNameUseCase;
import com.abdul.catalogservice.domain.user.port.out.UserRepository;
import com.abdul.catalogservice.domain.user.usecase.CreateUserUseCaseImpl;
import com.abdul.catalogservice.domain.user.usecase.GetUserDetailsUseCaseImpl;
import com.abdul.catalogservice.domain.user.usecase.EditUserNameUseCaseImpl;
import com.abdul.catalogservice.domain.track.port.in.GetTracksByUserUseCase;
import com.abdul.catalogservice.domain.track.port.out.TrackRepository;
import com.abdul.catalogservice.domain.track.usecase.GetTracksByUserUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public UserValidator userValidationHelper() {
        return new UserValidator();
    }

    @Bean
    public GetTracksByUserUseCase getTracksByUserUseCase(TrackRepository trackRepository,
                                                         UserRepository userRepository,
                                                         UserValidator userValidator) {
        return new GetTracksByUserUseCaseImpl(trackRepository, userRepository, userValidator);
    }

    @Bean
    public GetUserDetailsUseCase getUserDetailsUseCase(UserRepository userRepository) {
        return new GetUserDetailsUseCaseImpl(userRepository);
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
