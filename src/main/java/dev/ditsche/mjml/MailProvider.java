package dev.ditsche.mjml;

import java.io.IOException;

public interface MailProvider {

    boolean send(Mail mail);

}
