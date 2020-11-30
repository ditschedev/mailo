package dev.ditsche.mailo.factory;

import dev.ditsche.mailo.Mail;
import dev.ditsche.mailo.MailAddress;
import dev.ditsche.mailo.exception.MailBuildException;
import dev.ditsche.mailo.exception.TemplateNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class TemplateMailBuilderTest {

    @Test
    public void shouldThrowExceptionWhenInvalidSubjectIsProvided() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            TemplateMailBuilder.create().subject(null);
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            TemplateMailBuilder.create().subject("");
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            TemplateMailBuilder.create().subject("    ");
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            TemplateMailBuilder.create().subject("  \n  ");
        });
    }

    @Test
    public void shouldThrowExceptionWhenInvalidRecipientIsProvided() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            TemplateMailBuilder.create().to(null);
        });
    }

    @Test
    public void shouldThrowExceptionWhenInvalidCCIsProvided() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            TemplateMailBuilder.create().cc(null);
        });
    }

    @Test
    public void shouldThrowExceptionWhenInvalidBCCIsProvided() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            TemplateMailBuilder.create().bcc(null);
        });
    }

    @Test
    public void shouldThrowExceptionWhenInvalidFromIsProvided() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            TemplateMailBuilder.create().from(null);
        });
    }

    @Test
    public void shouldThrowExceptionWhenInvalidReplyToIsProvided() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            TemplateMailBuilder.create().replyTo(null);
        });
    }

    @Test
    public void shouldThrowExceptionWhenInvalidParamKeyIsProvided() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            TemplateMailBuilder.create().param(null, "Test");
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            TemplateMailBuilder.create().param("", "Test");
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            TemplateMailBuilder.create().param("   ", "Test");
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            TemplateMailBuilder.create().param(" \n ", "Test");
        });
    }

    @Test
    public void shouldThrowExceptionWhenInvalidParamValueIsProvided() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            TemplateMailBuilder.create().param("name", null);
        });
    }

    @Test
    public void shouldThrowExceptionWhenInvalidBodyIsProvided() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            TemplateMailBuilder.create().body(null);
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            TemplateMailBuilder.create().body("");
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            TemplateMailBuilder.create().body("    ");
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            TemplateMailBuilder.create().body("   \n ");
        });
    }

    @Test
    public void shouldThrowExceptionWhenInvalidHtmlTemplateIsProvided() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            TemplateMailBuilder.create().htmlTemplate(null);
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            TemplateMailBuilder.create().htmlTemplate("");
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            TemplateMailBuilder.create().htmlTemplate("    ");
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            TemplateMailBuilder.create().htmlTemplate("   \n ");
        });
    }

    @Test
    public void shouldThrowExceptionWhenInvalidMjmlTemplateIsProvided() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            TemplateMailBuilder.create().mjmlTemplate(null);
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            TemplateMailBuilder.create().mjmlTemplate("");
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            TemplateMailBuilder.create().mjmlTemplate("    ");
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            TemplateMailBuilder.create().mjmlTemplate("   \n ");
        });
    }

    @Test
    public void shouldThrowExceptionWhenHtmlTemplateNotFound() {
        assertThatExceptionOfType(TemplateNotFoundException.class).isThrownBy(() -> {
            TemplateMailBuilder.create().htmlTemplate("notExisting");
        });
    }

    @Test
    public void shouldThrowExceptionWhenMjmlTemplateNotFound() {
        assertThatExceptionOfType(TemplateNotFoundException.class).isThrownBy(() -> {
            TemplateMailBuilder.create().mjmlTemplate("notExisting");
        });
    }

    @Test
    public void shouldThrowExceptionWhenNoRecipientIsProvided() {
        assertThatExceptionOfType(MailBuildException.class).isThrownBy(() -> {
            TemplateMailBuilder.create()
                    .subject("Test")
                    .from(new MailAddress("hello@ditsche.dev"))
                    .body("test")
                    .build();
        });
    }

    @Test
    public void shouldThrowExceptionWhenNoSubjectIsProvided() {
        assertThatExceptionOfType(MailBuildException.class).isThrownBy(() -> {
            TemplateMailBuilder.create()
                    .to(new MailAddress("hello@ditsche.dev"))
                    .from(new MailAddress("hello@ditsche.dev"))
                    .body("test")
                    .build();
        });
    }

    @Test
    public void shouldThrowExceptionWhenNoFromIsProvided() {
        assertThatExceptionOfType(MailBuildException.class).isThrownBy(() -> {
            TemplateMailBuilder.create()
                    .to(new MailAddress("hello@ditsche.dev"))
                    .subject("Test")
                    .body("test")
                    .build();
        });
    }

    @Test
    public void shouldThrowExceptionWhenNoBodyIsProvided() {
        assertThatExceptionOfType(MailBuildException.class).isThrownBy(() -> {
            TemplateMailBuilder.create()
                    .to(new MailAddress("hello@ditsche.dev"))
                    .subject("Test")
                    .from(new MailAddress("hello@ditsche.dev"))
                    .build();
        });
    }

    @Test
    public void shouldSubstituteVarsUsingMjmlBody() {
        MailAddress mailAddress = new MailAddress("hello@ditsche.dev", "Toby");
        Mail mail = TemplateMailBuilder.create()
                .subject("Testsubject")
                .to(mailAddress)
                .from(mailAddress)
                .param("name", mailAddress.getName())
                .param("email", mailAddress.getEmail())
                .mjmlTemplate("special/vars")
                .build();
        assertThat(mail.getBody()).contains(mailAddress.getEmail());
        assertThat(mail.getBody()).contains(mailAddress.getName());
    }

    @Test
    public void shouldSubstituteVarsUsingHtmlBody() {
        MailAddress mailAddress = new MailAddress("hello@ditsche.dev", "Toby");
        Mail mail = TemplateMailBuilder.create()
                .subject("Testsubject")
                .to(mailAddress)
                .from(mailAddress)
                .param("name", mailAddress.getName())
                .param("email", mailAddress.getEmail())
                .htmlTemplate("special/vars")
                .build();
        assertThat(mail.getBody()).contains(mailAddress.getEmail());
        assertThat(mail.getBody()).contains(mailAddress.getName());
    }

    @Test
    public void shouldRenderMjmlBodyWithoutParams() {
        MailAddress mailAddress = new MailAddress("hello@ditsche.dev", "Toby");
        Mail mail = TemplateMailBuilder.create()
                .subject("Testsubject")
                .to(mailAddress)
                .from(mailAddress)
                .mjmlTemplate("test")
                .build();
        assertThat(mail.getBody()).contains("Hello World");
    }

    @Test
    public void shouldRenderHtmlBodyWithoutParams() {
        MailAddress mailAddress = new MailAddress("hello@ditsche.dev", "Toby");
        Mail mail = TemplateMailBuilder.create()
                .subject("Testsubject")
                .to(mailAddress)
                .from(mailAddress)
                .htmlTemplate("test")
                .build();
        assertThat(mail.getBody()).contains("Hello World");
    }

    @Test
    public void shouldSubstituteVarsUsingStringBody() {
        Random random = new Random(100);
        int test = random.nextInt();
        Mail mail = TemplateMailBuilder.create()
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
        Mail mail = TemplateMailBuilder.create()
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