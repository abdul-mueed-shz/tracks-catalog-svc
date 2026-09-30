package com.abdul.catalogservice.adapter.out.persistence.utils;

import org.springframework.data.domain.KeysetScrollPosition;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Component
public class CursorCodec {
    public KeysetScrollPosition decodeCursor(String cursor) {
        if (cursor == null || cursor.isBlank()) {
            return ScrollPosition.keyset();
        }
        try {
            long id = Long.parseLong(new String(
                    Base64.getUrlDecoder().decode(cursor),
                    StandardCharsets.UTF_8
            ));
            return ScrollPosition.forward(Map.of("id", id));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Invalid cursor", exception);
        }
    }

    public String encodeCursor(ScrollPosition position) {
        KeysetScrollPosition keysetPosition = (KeysetScrollPosition) position;
        Object id = keysetPosition.getKeys().get("id");
        return Base64.getUrlEncoder().withoutPadding().encodeToString(
                String.valueOf(id).getBytes(StandardCharsets.UTF_8)
        );
    }
}
