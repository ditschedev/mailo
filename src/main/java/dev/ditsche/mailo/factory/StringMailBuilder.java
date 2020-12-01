package dev.ditsche.mailo.factory;

import dev.ditsche.mailo.MailAddress;

public interface StringMailBuilder extends MailBuilder {

    StringMailBuilder subject(String subject);

    StringMailBuilder to(MailAddress recipient);

    StringMailBuilder cc(MailAddress recipient);

    StringMailBuilder bcc(MailAddress recipient);

    StringMailBuilder from(MailAddress from);

    StringMailBuilder replyTo(MailAddress replyTo);

    StringMailBuilder param(String key, Object value);

    MailBuilder body(String body);

}
