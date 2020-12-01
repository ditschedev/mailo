package dev.ditsche.mailo.factory;

import dev.ditsche.mailo.Mail;
import dev.ditsche.mailo.MailAddress;
import dev.ditsche.mailo.exception.MailBuildException;
import dev.ditsche.mailo.exception.TemplateNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class PlainMailBuilderTest {

    @Test
    public void shouldThrowExceptionWhenInvalidSubjectIsProvided() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.plain().subject(null);
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.plain().subject("");
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.plain().subject("    ");
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.plain().subject("  \n  ");
        });
    }

    @Test
    public void shouldThrowExceptionWhenInvalidRecipientIsProvided() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.plain().to(null);
        });
    }

    @Test
    public void shouldThrowExceptionWhenInvalidCCIsProvided() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.plain().cc(null);
        });
    }

    @Test
    public void shouldThrowExceptionWhenInvalidBCCIsProvided() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.plain().bcc(null);
        });
    }

    @Test
    public void shouldThrowExceptionWhenInvalidFromIsProvided() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.plain().from(null);
        });
    }

    @Test
    public void shouldThrowExceptionWhenInvalidReplyToIsProvided() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.plain().replyTo(null);
        });
    }

    @Test
    public void shouldThrowExceptionWhenInvalidParamKeyIsProvided() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.plain().param(null, "Test");
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.plain().param("", "Test");
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.plain().param("   ", "Test");
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.plain().param(" \n ", "Test");
        });
    }

    @Test
    public void shouldThrowExceptionWhenInvalidParamValueIsProvided() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.plain().param("name", null);
        });
    }

    @Test
    public void shouldThrowExceptionWhenInvalidBodyIsProvided() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.plain().body(null);
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.plain().body("");
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.plain().body("    ");
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.plain().body("   \n ");
        });
    }

    @Test
    public void shouldThrowExceptionWhenNoRecipientIsProvided() {
        assertThatExceptionOfType(MailBuildException.class).isThrownBy(() -> {
            MailBuilder.plain()
                    .subject("Test")
                    .from(new MailAddress("hello@ditsche.dev"))
                    .body("test")
                    .build();
        });
    }

    @Test
    public void shouldThrowExceptionWhenNoSubjectIsProvided() {
        assertThatExceptionOfType(MailBuildException.class).isThrownBy(() -> {
            MailBuilder.plain()
                    .to(new MailAddress("hello@ditsche.dev"))
                    .from(new MailAddress("hello@ditsche.dev"))
                    .body("test")
                    .build();
        });
    }

    @Test
    public void shouldThrowExceptionWhenNoFromIsProvided() {
        assertThatExceptionOfType(MailBuildException.class).isThrownBy(() -> {
            MailBuilder.plain()
                    .to(new MailAddress("hello@ditsche.dev"))
                    .subject("Test")
                    .body("test")
                    .build();
        });
    }

    @Test
    public void shouldThrowExceptionWhenNoBodyIsProvided() {
        assertThatExceptionOfType(MailBuildException.class).isThrownBy(() -> {
            MailBuilder.plain()
                    .to(new MailAddress("hello@ditsche.dev"))
                    .subject("Test")
                    .from(new MailAddress("hello@ditsche.dev"))
                    .build();
        });
    }

    @Test
    public void shouldSubstituteVarsUsingStringBody() {
        Random random = new Random(100);
        int test = random.nextInt();
        Mail mail = MailBuilder.plain()
                .subject("Testsubject")
                .to(new MailAddress("hello@ditsche.dev"))
                .from(new MailAddress("hello@ditsche.dev"))
                .param("test", test)
                .body("The number is: {{test}}")
                .build();
        assertThat(mail.getBody()).isEqualTo("The number is: " + test);
    }

    @Test
    public void shouldBuildMail() {
        Mail mail = MailBuilder.plain()
                .subject("Testsubject")
                .to(new MailAddress("hello@ditsche.dev"))
                .cc(new MailAddress("cc@ditsche.dev"))
                .cc(new MailAddress("anothercc@ditsche.dev"))
                .bcc(new MailAddress("bcc@ditsche.dev"))
                .from(new MailAddress("from@ditsche.dev"))
                .replyTo(new MailAddress("replyto@ditsche.dev"))
                .body("Testbody!")
                .build();
        assertThat(mail.getSubject()).isEqualTo("Testsubject");
        assertThat(mail.getRecipients()).hasSize(1).contains(new MailAddress("hello@ditsche.dev"));
        assertThat(mail.getCC()).hasSize(2).contains(new MailAddress("anothercc@ditsche.dev"));
        assertThat(mail.getBCC()).hasSize(1).contains(new MailAddress("bcc@ditsche.dev"));
        assertThat(mail.getFrom()).isEqualTo(new MailAddress("from@ditsche.dev"));
        assertThat(mail.getReplyTo()).isEqualTo(new MailAddress("replyto@ditsche.dev"));
        assertThat(mail.getBody()).isEqualTo("Testbody!");
    }

}