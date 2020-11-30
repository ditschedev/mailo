package dev.ditsche.mailo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class MailTest {

    @Test
    public void shouldNotCreateMailWithInvalidSubject() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            new Mail(null);
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            new Mail("");
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            new Mail("   ");
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            new Mail("  \n ");
        });
    }

    @Test
    public void shouldCreateMail() {
        Mail mail = new Mail("Testsubject");
        assertThat(mail.getSubject()).isEqualTo("Testsubject");
    }

    @Test
    public void shouldUpdateSubject() {
        Mail mail = new Mail("Testsubject");
        assertThat(mail.getSubject()).isEqualTo("Testsubject");
        mail.setSubject("[Test] Subject");
        assertThat(mail.getSubject()).isEqualTo("[Test] Subject");
    }

    @Test
    public void shouldUpdateFrom() {
        Mail mail = new Mail("Testsubject");
        assertThat(mail.getFrom()).isNull();
        MailAddress from = new MailAddress("hello@ditsche.dev");
        mail.setFrom(from);
        assertThat(mail.getFrom()).isNotNull().isEqualTo(from);
    }

    @Test
    public void shouldUpdateReplyTo() {
        Mail mail = new Mail("Testsubject");
        assertThat(mail.getReplyTo()).isNull();
        MailAddress replyTo = new MailAddress("hello@ditsche.dev");
        mail.setReplyTo(replyTo);
        assertThat(mail.getReplyTo()).isNotNull().isEqualTo(replyTo);
    }

    @Test
    public void shouldUpdateBody() {
        Mail mail = new Mail("Testsubject");
        assertThat(mail.getBody()).isNull();
        String body = "Hello world!!";
        mail.setBody(body);
        assertThat(mail.getBody()).isNotNull().isEqualTo(body);
    }

    @Test
    public void shouldAddRecipientToEmptyMail() {
        Mail mail = new Mail("Testsubject");
        MailAddress added = new MailAddress("hello@ditsche.dev", "Tobias");
        mail.addRecipient(added);
        assertThat(mail.getRecipients().size()).isEqualTo(1);
        assertThat(mail.getRecipients()).contains(added);
    }

    @Test
    public void shouldAddRecipientToNonEmptyMail() {
        Mail mail = new Mail("Testsubject", new MailAddress("hello@ditsche.dev", "Tobias"));
        MailAddress added = new MailAddress("hello2@ditsche.dev", "Tobias");
        mail.addRecipient(added);
        assertThat(mail.getRecipients().size()).isEqualTo(2);
        assertThat(mail.getRecipients()).contains(added);
    }

    @Test
    public void shouldNotAddRecipientIfAlreadyPresent() {
        Mail mail = new Mail("Testsubject", new MailAddress("hello@ditsche.dev", "Tobias"));
        assertThat(mail.getRecipients().size()).isEqualTo(1);
        mail.addRecipient(new MailAddress("hello@ditsche.dev", "Tobias"));
        assertThat(mail.getRecipients().size()).isEqualTo(1);
    }

    @Test
    public void shouldThrowExceptionWhenAddingEmptyRecipient() {
        Mail mail = new Mail("Testsubject", new MailAddress("hello@ditsche.dev", "Tobias"));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            mail.addRecipient();
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            mail.addRecipient(null);
        });
    }

    @Test
    public void shouldAddCCToEmptyCCList() {
        Mail mail = new Mail("Testsubject");
        MailAddress added = new MailAddress("hello@ditsche.dev", "Tobias");
        mail.addCC(added);
        assertThat(mail.getCC().size()).isEqualTo(1);
        assertThat(mail.getCC()).contains(added);
    }

    @Test
    public void shouldAddCCToNonEmptyCCList() {
        Mail mail = new Mail("Testsubject");
        mail.addCC(new MailAddress("hello@ditsche.dev", "Tobias"));
        MailAddress added = new MailAddress("hello2@ditsche.dev", "Tobias");
        mail.addCC(added);
        assertThat(mail.getCC().size()).isEqualTo(2);
        assertThat(mail.getCC()).contains(added);
    }

    @Test
    public void shouldNotAddCCIfAlreadyPresent() {
        Mail mail = new Mail("Testsubject");
        mail.addCC(new MailAddress("hello@ditsche.dev", "Tobias"));
        assertThat(mail.getCC().size()).isEqualTo(1);
        mail.addCC(new MailAddress("hello@ditsche.dev"));
        assertThat(mail.getCC().size()).isEqualTo(1);
    }

    @Test
    public void shouldThrowExceptionWhenAddingEmptyCC() {
        Mail mail = new Mail("Testsubject", new MailAddress("hello@ditsche.dev", "Tobias"));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            mail.addCC();
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            mail.addCC(null);
        });
    }

    @Test
    public void shouldAddBCCToEmptyBCCList() {
        Mail mail = new Mail("Testsubject");
        MailAddress added = new MailAddress("hello@ditsche.dev", "Tobias");
        mail.addBCC(added);
        assertThat(mail.getBCC().size()).isEqualTo(1);
        assertThat(mail.getBCC()).contains(added);
    }

    @Test
    public void shouldAddBCCToNonEmptyBCCList() {
        Mail mail = new Mail("Testsubject");
        mail.addBCC(new MailAddress("hello@ditsche.dev", "Tobias"));
        MailAddress added = new MailAddress("hello2@ditsche.dev", "Tobias");
        mail.addBCC(added);
        assertThat(mail.getBCC().size()).isEqualTo(2);
        assertThat(mail.getBCC()).contains(added);
    }

    @Test
    public void shouldNotAddBCCIfAlreadyPresent() {
        Mail mail = new Mail("Testsubject");
        mail.addBCC(new MailAddress("hello@ditsche.dev", "Tobias"));
        assertThat(mail.getBCC().size()).isEqualTo(1);
        mail.addBCC(new MailAddress("hello@ditsche.dev"));
        assertThat(mail.getBCC().size()).isEqualTo(1);
    }

    @Test
    public void shouldThrowExceptionWhenAddingEmptyBCC() {
        Mail mail = new Mail("Testsubject", new MailAddress("hello@ditsche.dev", "Tobias"));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            mail.addBCC();
        });
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            mail.addBCC(null);
        });
    }

}