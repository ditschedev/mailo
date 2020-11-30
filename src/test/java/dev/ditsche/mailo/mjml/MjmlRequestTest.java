package dev.ditsche.mailo.mjml;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MjmlRequestTest {

    @Test
    public void shouldCreateRequestObject() {
        MjmlRequest request = new MjmlRequest();
        request.setMjml("test");
        assertThat(request.getMjml()).isEqualTo("test");
    }

}