package dev.ditsche.mjml.provider;

import dev.ditsche.mjml.Mail;

public interface MailProvider {

    boolean send(Mail mail);

}
