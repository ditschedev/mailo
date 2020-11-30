package dev.ditsche.mailo.provider;

import dev.ditsche.mailo.Mail;

public interface MailProvider {

    boolean send(Mail mail);

}
