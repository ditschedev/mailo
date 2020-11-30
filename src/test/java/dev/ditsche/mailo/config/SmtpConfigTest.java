package dev.ditsche.mailo.config;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SmtpConfigTest {

    @Test
    public void shouldCreateSmtpConfigWithAllArgs() {
        SmtpConfig config = new SmtpConfig("host", 25, "username", "password", true);
        assertThat(config.getHost()).isEqualTo("host");
        assertThat(config.getPort()).isEqualTo(25);
        assertThat(config.getUsername()).isEqualTo("username");
        assertThat(config.getPassword()).isEqualTo("password");
        assertThat(config.isSsl()).isEqualTo(true);
    }

    @Test
    public void shouldCreateSmtpConfigWithNoArgs() {
        SmtpConfig config = new SmtpConfig();
        config.setHost("host");
        config.setPort(25);
        config.setUsername("username");
        config.setPassword("password");
        config.setSsl(true);

        assertThat(config.getHost()).isEqualTo("host");
        assertThat(config.getPort()).isEqualTo(25);
        assertThat(config.getUsername()).isEqualTo("username");
        assertThat(config.getPassword()).isEqualTo("password");
        assertThat(config.isSsl()).isEqualTo(true);
    }

}