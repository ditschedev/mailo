package dev.ditsche.mailo.config;

import dev.ditsche.mailo.MailAddress;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class MailoConfigTest {

    @Test
    public void shouldInitializeWithDefaultValues() {
        MailoConfig config = MailoConfig.get();
        assertThat(config.getTemplateDirectory()).isEqualTo("/templates/");
        assertThat(config.getMjmlAppId()).isNotNull();
        assertThat(config.getMjmlAppSecret()).isNotNull();
        assertThat(config.getDefaultSender()).isNull();
        assertThat(config.getDefaultReplyTo()).isNull();
    }

    @Test
    public void shouldStoreConfig() {
        MailoConfig config = MailoConfig.get();
        String newTemplateDir = "/test/";
        assertThat(config.getTemplateDirectory()).isEqualTo("/templates/");
        config.setTemplateDirectory(newTemplateDir);
        assertThat(config.getTemplateDirectory()).isEqualTo(newTemplateDir);
        config.setDefaultSender(new MailAddress("hello@ditsche.dev"));
        config.setDefaultReplyTo(new MailAddress("hello@ditsche.dev"));

        MailoConfig updatedConfig = MailoConfig.get();
        assertThat(updatedConfig.getTemplateDirectory()).isEqualTo(newTemplateDir);
        assertThat(updatedConfig.getDefaultSender()).isEqualTo(new MailAddress("hello@ditsche.dev"));
        assertThat(updatedConfig.getDefaultReplyTo()).isEqualTo(new MailAddress("hello@ditsche.dev"));
    }

    @Test
    public void shouldTrimValues() {
        MailoConfig config = MailoConfig.get();
        config.setMjmlAppId("   \n ");
        assertThat(config.getMjmlAppId()).isEmpty();
    }

    @Test
    public void shouldNotAllowNullValues() {
        MailoConfig config = MailoConfig.get();
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            config.setMjmlAppSecret(null);
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            config.setMjmlAppId(null);
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            config.setTemplateDirectory(null);
        });
    }

    @AfterEach
    public void resetConfig() {
        MailoConfig.get().reset();
    }

}