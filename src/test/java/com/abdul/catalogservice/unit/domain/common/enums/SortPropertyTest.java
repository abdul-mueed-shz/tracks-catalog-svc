package com.abdul.catalogservice.unit.domain.common.enums;

import com.abdul.catalogservice.domain.common.enums.SortProperty;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SortPropertyTest {
    @Test
    void normalizesSupportedSortValues() {
        assertThat(SortProperty.fromValue("created_at")).isEqualTo(SortProperty.CREATED_AT);
        assertThat(SortProperty.fromValue(" createdAt ")).isEqualTo(SortProperty.CREATED_AT);
        assertThat(SortProperty.fromValue("updated_at")).isEqualTo(SortProperty.UPDATED_AT);
    }

    @Test
    void defaultsBlankSortValueToUpdatedAt() {
        assertThat(SortProperty.fromValue(null)).isEqualTo(SortProperty.UPDATED_AT);
        assertThat(SortProperty.fromValue(" ")).isEqualTo(SortProperty.UPDATED_AT);
    }

    @Test
    void rejectsUnsupportedSortValue() {
        assertThatThrownBy(() -> SortProperty.fromValue("name"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Unsupported sort property: name");
    }
}
