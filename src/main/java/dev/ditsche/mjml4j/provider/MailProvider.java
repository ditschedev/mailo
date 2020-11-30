package dev.ditsche.mjml4j.provider;

import dev.ditsche.mjml4j.Mail;

public interface MailProvider {

    boolean send(Mail mail);

}
