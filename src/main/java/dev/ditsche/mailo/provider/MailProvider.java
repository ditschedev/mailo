package dev.ditsche.mailo.provider;

import dev.ditsche.mailo.Mail;
import dev.ditsche.mailo.factory.MailBuilder;

public interface MailProvider {

    boolean send(MailBuilder mailBuilder);

    boolean send(Mail mail);

}
