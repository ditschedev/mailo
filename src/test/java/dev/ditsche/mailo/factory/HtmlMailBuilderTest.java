package dev.ditsche.mailo.factory;

import dev.ditsche.mailo.Mail;
import dev.ditsche.mailo.MailAddress;
import dev.ditsche.mailo.exception.MailBuildException;
import dev.ditsche.mailo.exception.TemplateNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class HtmlMailBuilderTest {

    @Test
    public void shouldThrowExceptionWhenInvalidSubjectIsProvided() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.html().subject(null);
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.html().subject("");
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.html().subject("    ");
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.html().subject("  \n  ");
        });
    }

    @Test
    public void shouldThrowExceptionWhenInvalidRecipientIsProvided() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.html().to(null);
        });
    }

    @Test
    public void shouldThrowExceptionWhenInvalidCCIsProvided() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.html().cc(null);
        });
    }

    @Test
    public void shouldThrowExceptionWhenInvalidBCCIsProvided() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.html().bcc(null);
        });
    }

    @Test
    public void shouldThrowExceptionWhenInvalidFromIsProvided() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.html().from(null);
        });
    }

    @Test
    public void shouldThrowExceptionWhenInvalidReplyToIsProvided() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.html().replyTo(null);
        });
    }

    @Test
    public void shouldThrowExceptionWhenInvalidParamKeyIsProvided() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.html().param(null, "Test");
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.html().param("", "Test");
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.html().param("   ", "Test");
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.html().param(" \n ", "Test");
        });
    }

    @Test
    public void shouldThrowExceptionWhenInvalidParamValueIsProvided() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.html().param("name", null);
        });
    }

    @Test
    public void shouldThrowExceptionWhenInvalidHtmlTemplateIsProvided() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.html().loadTemplate(null);
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.html().loadTemplate("");
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.html().loadTemplate("    ");
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            MailBuilder.html().loadTemplate("   \n ");
        });
    }

    @Test
    public void shouldThrowExceptionWhenHtmlTemplateNotFound() {
        assertThatExceptionOfType(TemplateNotFoundException.class).isThrownBy(() -> {
            MailBuilder.html().loadTemplate("notExisting");
        });
    }

    @Test
    public void shouldThrowExceptionWhenNoRecipientIsProvided() {
        assertThatExceptionOfType(MailBuildException.class).isThrownBy(() -> {
            MailBuilder.html()
                    .subject("Test")
                    .from(new MailAddress("hello@ditsche.dev"))
                    .loadTemplate("test")
                    .build();
        });
    }

    @Test
    public void shouldThrowExceptionWhenNoSubjectIsProvided() {
        assertThatExceptionOfType(MailBuildException.class).isThrownBy(() -> {
            MailBuilder.html()
                    .to(new MailAddress("hello@ditsche.dev"))
                    .from(new MailAddress("hello@ditsche.dev"))
                    .loadTemplate("test")
                    .build();
        });
    }

    @Test
    public void shouldThrowExceptionWhenNoFromIsProvided() {
        assertThatExceptionOfType(MailBuildException.class).isThrownBy(() -> {
            MailBuilder.html()
                    .to(new MailAddress("hello@ditsche.dev"))
                    .subject("Test")
                    .loadTemplate("test")
                    .build();
        });
    }

    @Test
    public void shouldThrowExceptionWhenNoBodyIsProvided() {
        assertThatExceptionOfType(MailBuildException.class).isThrownBy(() -> {
            MailBuilder.html()
                    .to(new MailAddress("hello@ditsche.dev"))
                    .subject("Test")
                    .from(new MailAddress("hello@ditsche.dev"))
                    .build();
        });
    }

    @Test
    public void shouldSubstituteVarsUsingHtmlBody() {
        MailAddress mailAddress = new MailAddress("hello@ditsche.dev", "Toby");
        Mail mail = MailBuilder.html()
                .subject("Testsubject")
                .to(mailAddress)
                .from(mailAddress)
                .param("name", mailAddress.getName())
                .param("email", mailAddress.getEmail())
                .loadTemplate("special/vars")
                .build();
        assertThat(mail.getBody()).contains(mailAddress.getEmail());
        assertThat(mail.getBody()).contains(mailAddress.getName());
    }

    @Test
    public void shouldRenderHtmlBodyWithoutParams() {
        MailAddress mailAddress = new MailAddress("hello@ditsche.dev", "Toby");
        Mail mail = MailBuilder.html()
                .subject("Testsubject")
                .to(mailAddress)
                .from(mailAddress)
                .loadTemplate("test")
                .build();
        assertThat(mail.getBody()).contains("Hello World");
    }

    @Test
    public void shouldBuildMail() {
        Mail mail = MailBuilder.mjml()
                .subject("Testsubject")
                .to(new MailAddress("hello@ditsche.dev"))
                .cc(new MailAddress("cc@ditsche.dev"))
                .cc(new MailAddress("anothercc@ditsche.dev"))
                .bcc(new MailAddress("bcc@ditsche.dev"))
                .from(new MailAddress("from@ditsche.dev"))
                .replyTo(new MailAddress("replyto@ditsche.dev"))
                .param("name", "Toby")
                .param("email", "hello@ditsche.dev")
                .loadTemplate("special/vars")
                .build();
        assertThat(mail.getSubject()).isEqualTo("Testsubject");
        assertThat(mail.getRecipients()).hasSize(1).contains(new MailAddress("hello@ditsche.dev"));
        assertThat(mail.getCC()).hasSize(2).contains(new MailAddress("anothercc@ditsche.dev"));
        assertThat(mail.getBCC()).hasSize(1).contains(new MailAddress("bcc@ditsche.dev"));
        assertThat(mail.getFrom()).isEqualTo(new MailAddress("from@ditsche.dev"));
        assertThat(mail.getReplyTo()).isEqualTo(new MailAddress("replyto@ditsche.dev"));
        assertThat(mail.getBody()).contains("Toby").contains("hello@ditsche.dev");
    }

}