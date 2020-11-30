package dev.ditsche.mailo.provider;

import dev.ditsche.mailo.Mail;
import dev.ditsche.mailo.MailAddress;
import dev.ditsche.mailo.factory.TemplateMailBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SendinblueMailProviderTest {

    private final String apiKey = System.getenv("SENDINBLUE_API_KEY");

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
    public void shouldSendMailWithWorkingCredentials() {
        MailProvider mailProvider = new SendinblueMailProvider(apiKey);
        assertThat(mailProvider.send(mail)).isTrue();
    }

    @Test
    public void shouldNotSendMailWithInvalidCredentials() {
        MailProvider mailProvider = new SendinblueMailProvider("iaminvalid");
        assertThat(mailProvider.send(mail)).isFalse();
    }

}