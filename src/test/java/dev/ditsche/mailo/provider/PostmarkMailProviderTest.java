package dev.ditsche.mailo.provider;

import dev.ditsche.mailo.Mail;
import dev.ditsche.mailo.MailAddress;
import dev.ditsche.mailo.factory.MailBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

import static org.assertj.core.api.Assertions.assertThat;

public class PostmarkMailProviderTest {

    private final String serverToken = System.getenv("POSTMARK_SERVER_TOKEN");

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

    @Test
    @DisabledIfEnvironmentVariable(named = "CI", matches = "true")
    public void shouldSendMailWithWorkingCredentials() {
        if(serverToken == null)
            return;
        MailProvider mailProvider = new PostmarkMailProvider(serverToken);
        assertThat(mailProvider.send(mail)).isTrue();
    }

    @Test
    public void shouldNotSendMailWithInvalidCredentials() {
        MailProvider mailProvider = new PostmarkMailProvider("iaminvalid");
        assertThat(mailProvider.send(mail)).isFalse();
    }

}