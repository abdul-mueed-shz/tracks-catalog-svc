package com.abdul.catalogservice.domain.user.usecase;

import com.abdul.catalogservice.domain.user.model.UserAliasInfo;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import com.abdul.catalogservice.domain.user.port.in.EditUserNameUseCase;
import com.abdul.catalogservice.domain.user.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Locale;

@RequiredArgsConstructor
public class EditUserNameUseCaseImpl implements EditUserNameUseCase {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserInfo execute(Long userId, String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("User name must not be blank");
        }

        UserInfo existingUser = userRepository.getUserById(userId);
        if (existingUser == null) {
            throw new IllegalArgumentException("User not found: " + userId);
        }

        String trimmedName = newName.trim();
        if (existingUser.getName().equals(trimmedName)) {
            return existingUser;
        }

        String normalizedOldName = normalize(existingUser.getName());
        ArrayList<UserAliasInfo> aliases = new ArrayList<>(existingUser.getAliases());
        boolean aliasExists = aliases.stream()
                .anyMatch(alias -> normalizedOldName.equals(alias.getNormalizedName()));

        if (!aliasExists) {
            aliases.add(UserAliasInfo.builder()
                    .aliasName(existingUser.getName())
                    .normalizedName(normalizedOldName)
                    .isPrimary(false)
                    .build());
        }

        UserInfo updatedUser = existingUser.toBuilder()
                .name(trimmedName)
                .aliases(aliases)
                .build();
        return userRepository.updateUser(updatedUser);
    }

    private String normalize(String value) {
        return Normalizer
                .normalize(value.trim()
                .toLowerCase(Locale.ROOT), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
    }
}
