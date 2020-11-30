package dev.ditsche.mailo.provider;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import dev.ditsche.mailo.Mail;
import dev.ditsche.mailo.MailAddress;

import java.io.IOException;

/**
 * @author Tobias Dittmann
 */
public class SendGridMailProvider extends AbstractMailProvider {

    private final SendGrid client;

    public SendGridMailProvider(String apiKey) {
        client = new SendGrid(apiKey);
    }

    @Override
    public boolean send(Mail mail) {
        com.sendgrid.helpers.mail.Mail send = new com.sendgrid.helpers.mail.Mail();

        send.setFrom(mailAddressToEmail(mail.getFrom()));
        send.setSubject(mail.getSubject());
        send.addContent(new Content("text/html", mail.getBody()));
        Personalization personalization = new Personalization();

        for(MailAddress address : mail.getRecipients()) {
            personalization.addTo(mailAddressToEmail(address));
        }

        for(MailAddress address : mail.getCC()) {
            personalization.addCc(mailAddressToEmail(address));
        }

        for(MailAddress address : mail.getBCC()) {
            personalization.addBcc(mailAddressToEmail(address));
        }

        send.addPersonalization(personalization);

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
