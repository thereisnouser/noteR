package com.noter.api.shared;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class UtilsTest {

    @Test
    public void isNullOrEmpty_stringIsNull_returnsTrue() {

        // Given
        final String str = null;

        // When
        final boolean result = Utils.isNullOrEmpty(str);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    public void isNullOrEmpty_emptyString_returnsTrue() {

        // Given
        final String str = "";

        // When
        final boolean result = Utils.isNullOrEmpty(str);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    public void isNullOrEmpty_stringIsNotEmpty_returnsFalse() {

        // Given
        final String str = "abc";

        // When
        final boolean result = Utils.isNullOrEmpty(str);

        // Then
        assertThat(result).isFalse();
    }
}
