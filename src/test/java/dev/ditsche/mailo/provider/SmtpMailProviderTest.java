package dev.ditsche.mailo.provider;

import dev.ditsche.mailo.Mail;
import dev.ditsche.mailo.MailAddress;
import dev.ditsche.mailo.config.SmtpConfig;
import dev.ditsche.mailo.factory.TemplateMailBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SmtpMailProviderTest {

    private final Mail mail = TemplateMailBuilder.create()
            .subject("Testsubject")
            .to(new MailAddress("hello@ditsche.dev"))
            .cc(new MailAddress("hello@ditsche.dev"))
            .cc(new MailAddress("hello@ditsche.dev"))
            .bcc(new MailAddress("hello@ditsche.dev"))
            .from(new MailAddress("hello@ditsche.dev"))
            .replyTo(new MailAddress("hello@ditsche.dev"))
            .body("Testbody!")
            .build();

    private final SmtpConfig validConfig = new SmtpConfig(
            System.getenv("SMTP_HOST"),
            Integer.parseInt(System.getenv("SMTP_PORT")),
            System.getenv("SMTP_USER"),
            System.getenv("SMTP_PW"),
            false
    );

    private final SmtpConfig invalidConfig = new SmtpConfig(
            System.getenv("SMTP_HOST"),
            12345,
            System.getenv("SMTP_USER"),
            System.getenv("SMTP_PW"),
            false
    );

    @Test
    public void shouldSendMailWithWorkingCredentials() {
        MailProvider mailProvider = new SmtpMailProvider(validConfig);
        assertThat(mailProvider.send(mail)).isTrue();
    }

    @Test
    public void shouldNotSendMailWithInvalidCredentials() {
        MailProvider mailProvider = new SmtpMailProvider(invalidConfig);
        assertThat(mailProvider.send(mail)).isFalse();
    }

}