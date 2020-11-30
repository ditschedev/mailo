package dev.ditsche.mailo.provider;

import dev.ditsche.mailo.Mail;
import dev.ditsche.mailo.MailAddress;
import dev.ditsche.mailo.factory.TemplateMailBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

import static org.assertj.core.api.Assertions.assertThat;

public class SendGridMailProviderTest {

    private final String apiKey = System.getenv("SENDGRID_API_KEY");

    private final Mail mail = TemplateMailBuilder.create()
            .subject("Testsubject")
            .to(new MailAddress("hello@ditsche.dev"))
            .cc(new MailAddress("cc@ditsche.dev"))
            .cc(new MailAddress("anothercc@ditsche.dev"))
            .bcc(new MailAddress("bcc@ditsche.dev"))
            .from(new MailAddress("hello@ditsche.dev"))
            .replyTo(new MailAddress("replyto@ditsche.dev"))
            .body("Testbody!")
            .build();

    @Test
    @DisabledIfEnvironmentVariable(named = "CI", matches = "true")
    public void shouldSendMailWithWorkingCredentials() {
        if(apiKey == null)
            return;
        MailProvider mailProvider = new SendGridMailProvider(apiKey);
        assertThat(mailProvider.send(mail)).isTrue();
    }

    @Test
    public void shouldNotSendMailWithInvalidCredentials() {
        MailProvider mailProvider = new SendGridMailProvider("iaminvalid");
        assertThat(mailProvider.send(mail)).isFalse();
    }

}