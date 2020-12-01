package dev.ditsche.mailo.provider;

import dev.ditsche.mailo.Mail;
import dev.ditsche.mailo.MailAddress;
import dev.ditsche.mailo.config.SmtpConfig;
import dev.ditsche.mailo.factory.MailBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

import static org.assertj.core.api.Assertions.assertThat;

public class SmtpMailProviderTest {

    private final Mail mail = MailBuilder.mjml()
            .subject("Testsubject")
            .to(new MailAddress("hello@ditsche.dev"))
            .cc(new MailAddress("cc@ditsche.dev"))
            .cc(new MailAddress("anothercc@ditsche.dev"))
            .bcc(new MailAddress("bcc@ditsche.dev"))
            .from(new MailAddress("hello@ditsche.dev"))
            .replyTo(new MailAddress("replyto@ditsche.dev"))
            .loadTemplate("test")
            .build();

    private final SmtpConfig validConfig = new SmtpConfig(
            System.getenv("SMTP_HOST"),
            Integer.parseInt(System.getenv("SMTP_PORT")),
            System.getenv("SMTP_USER"),
            System.getenv("SMTP_PW"),
            false
    );

    private final SmtpConfig invalidConfig = new SmtpConfig(
            "",
            0,
            "",
            "",
            false
    );

    @Test
    @DisabledIfEnvironmentVariable(named = "CI", matches = "true")
    public void shouldSendMailWithWorkingCredentials() {
        if(System.getenv("SMTP_HOST") == null)
            return;
        MailProvider mailProvider = new SmtpMailProvider(validConfig);
        assertThat(mailProvider.send(mail)).isTrue();
    }

    @Test
    public void shouldNotSendMailWithInvalidCredentials() {
        MailProvider mailProvider = new SmtpMailProvider(invalidConfig);
        assertThat(mailProvider.send(mail)).isFalse();
    }

}