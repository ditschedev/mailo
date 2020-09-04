package dev.ditsche.mjml;

import java.util.HashMap;

/**
 * @author Tobias Dittmann
 */
public final class MailBuilder {

    private String subject;

    private MailAddress recipient;

    private HashMap<String, Object> params;

    private MailBuilder(String subject) {
        this.params = new HashMap<>();
        this.subject = subject;
    }

    public static MailBuilder create(String subject) {
        return new MailBuilder(subject);
    }

    public MailBuilder to(MailAddress recipient) {
        this.recipient = recipient;
        return this;
    }

    public MailBuilder param(String key, Object value) {
        this.params.put(key, value);
        return this;
    }

    public Mail template(String path) {
        Mail mail = Mail.fromTemplate(this.subject, path, this.params);
        mail.setRecipient(this.recipient);
        return mail;
    }

}
