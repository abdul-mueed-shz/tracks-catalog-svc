package com.abdul.catalogservice.adapter.out.persistence.utils.codec;

import org.springframework.data.domain.KeysetScrollPosition;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CursorCodec {
    public KeysetScrollPosition decodeCursor(String cursor) {
        if (cursor == null || cursor.isBlank()) {
            return ScrollPosition.keyset();
        }
        try {
            String decoded = new String(
                    Base64.getUrlDecoder().decode(cursor),
                    StandardCharsets.UTF_8
            );
            Map<String, Object> keys = new LinkedHashMap<>();
            for (String value : decoded.split("&")) {
                String[] entry = value.split("=", 2);
                if (entry.length != 2) {
                    throw new IllegalArgumentException("Invalid cursor");
                }
                keys.put(entry[0], decodeValue(entry[0], entry[1]));
            }
            return ScrollPosition.forward(keys);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Invalid cursor", exception);
        }
    }

    public String encodeCursor(ScrollPosition position) {
        KeysetScrollPosition keysetPosition = (KeysetScrollPosition) position;
        String value = keysetPosition.getKeys().entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .reduce((left, right) -> left + "&" + right)
                .orElseThrow(() -> new IllegalArgumentException("Cannot encode empty cursor"));
        return Base64.getUrlEncoder().withoutPadding().encodeToString(
                value.getBytes(StandardCharsets.UTF_8)
        );
    }

    private Object decodeValue(String key, String value) {
        if ("id".equals(key)) {
            return Long.parseLong(value);
        }
        if ("createdAt".equals(key) || "updatedAt".equals(key)) {
            return LocalDateTime.parse(value);
        }
        throw new IllegalArgumentException("Unsupported cursor key: " + key);
    }
}
