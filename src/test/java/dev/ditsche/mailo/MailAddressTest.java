package dev.ditsche.mailo;

import org.junit.jupiter.api.Test;

import javax.mail.internet.InternetAddress;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class MailAddressTest {

    @Test
    public void shouldNotCreateMailAddressWithEmptyEmail() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            new MailAddress("nope");
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            new MailAddress(null);
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            new MailAddress("   ");
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            new MailAddress(" \n  ");
        });
    }

    @Test
    public void shouldNotCreateMailAddressWithInvalidEmail() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            new MailAddress("hello@ditsche.");
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            new MailAddress("hell@o@ditsche.dev");
        });
    }

    @Test
    public void shouldNotCreateMailAddressWithInvalidName() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            new MailAddress("Testsubject", "");
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            new MailAddress("Testsubject", null);
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            new MailAddress("Testsubject", "   ");
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            new MailAddress("Testsubject", " \n  ");
        });
    }

    @Test
    public void shouldCreateMailAddressWithoutName() {
        MailAddress mailAddress = new MailAddress("hello@ditsche.dev");
        assertThat(mailAddress.getEmail()).isEqualTo("hello@ditsche.dev");
        assertThat(mailAddress.getName()).isNull();
    }

    @Test
    public void shouldLowercaseEmail() {
        MailAddress mailAddress = new MailAddress("Tobias.Dittmann@ditsche.dev");
        assertThat(mailAddress.getEmail()).isEqualTo("tobias.dittmann@ditsche.dev");
    }

    @Test
    public void nameShouldNotInfluenceEquality() {
        MailAddress mailAddress = new MailAddress("hello@ditsche.dev", "Tobias");
        MailAddress compare = new MailAddress("hello@ditsche.dev", "Toby");
        assertThat(mailAddress).isEqualTo(compare);
    }

    @Test
    public void shouldGenerateValidStringRepresentationWithoutName() {
        MailAddress mailAddress = new MailAddress("hello@ditsche.dev");
        assertThat(mailAddress.toString()).isEqualTo("hello@ditsche.dev");
    }

    @Test
    public void shouldGenerateValidStringRepresentationWithName() {
        MailAddress mailAddress = new MailAddress("hello@ditsche.dev", "Toby Ditsche");
        assertThat(mailAddress.toString()).isEqualTo("Toby Ditsche<hello@ditsche.dev>");
    }

    @Test
    public void shouldConvertToValidInetAddress() {
        MailAddress mailAddress = new MailAddress("hello@ditsche.dev", "Toby");
        InternetAddress inetAddress = mailAddress.toAddress();
        assertThat(inetAddress.getAddress()).isEqualTo(mailAddress.getEmail());
        assertThat(inetAddress.getPersonal()).isEqualTo(mailAddress.getName());
    }

    @Test
    public void shouldSetNameAfterwords() {
        MailAddress mailAddress = new MailAddress("hello@ditsche.dev");
        assertThat(mailAddress.getName()).isNull();
        mailAddress.setName("Toby");
        assertThat(mailAddress.getName()).isNotNull().isEqualTo("Toby");
    }

    @Test
    public void shouldSetEmailAfterwords() {
        MailAddress mailAddress = new MailAddress("hello@ditsche.dev");
        assertThat(mailAddress.getEmail()).isEqualTo("hello@ditsche.dev");
        mailAddress.setEmail("hello+new@ditsche.dev");
        assertThat(mailAddress.getEmail()).isNotNull().isEqualTo("hello+new@ditsche.dev");
    }

}