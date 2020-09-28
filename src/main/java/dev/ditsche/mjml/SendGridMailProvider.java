package dev.ditsche.mjml;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;

/**
 * @author Tobias Dittmann
 */
public class SendGridMailProvider extends AbstractMailProvider {

    private final SendGrid client;

    public SendGridMailProvider(MJMLConfig config, String apiKey) {
        super(config);

        client = new SendGrid(apiKey);
    }

    @Override
    public boolean send(Mail mail) {
        com.sendgrid.helpers.mail.Mail send = new com.sendgrid.helpers.mail.Mail(
                mailAddressToEmail(this.config.getFrom()),
                mail.getSubject(),
                mailAddressToEmail(mail.getRecipient()),
                new Content("text/html", mjmlToHtml(mail.getMjml()))
        );
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(send.build());
            Response response = client.api(request);
            return response.getStatusCode() == 202;
        } catch (IOException ex) {
            return false;
        }
    }

    private Email mailAddressToEmail(MailAddress mailAddress) {
        return new Email(mailAddress.getEmail(), mailAddress.getName());
    }
}
