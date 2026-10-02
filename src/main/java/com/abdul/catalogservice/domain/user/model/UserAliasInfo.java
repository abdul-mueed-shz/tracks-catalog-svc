package com.abdul.catalogservice.domain.user.model;

import com.abdul.catalogservice.domain.common.model.BaseInfo;
import java.text.Normalizer;
import java.util.Locale;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class UserAliasInfo extends BaseInfo {
    private Long userId;
    private String aliasName;
    private String normalizedName;

    public static UserAliasInfo create(Long userId, String aliasName) {
        if (aliasName == null || aliasName.isBlank()) {
            throw new IllegalArgumentException("Alias name is required.");
        }
        String trimmedName = aliasName.trim();
        return UserAliasInfo.builder()
                .userId(userId)
                .aliasName(trimmedName)
                .normalizedName(normalize(trimmedName))
                .build();
    }

    private static String normalize(String value) {
        return Normalizer.normalize(value.toLowerCase(Locale.ROOT), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
    }
}
