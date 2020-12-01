package dev.ditsche.mailo.provider;

import com.wildbit.java.postmark.Postmark;
import com.wildbit.java.postmark.client.ApiClient;
import com.wildbit.java.postmark.client.data.model.message.Message;
import com.wildbit.java.postmark.client.exception.InvalidAPIKeyException;
import com.wildbit.java.postmark.client.exception.PostmarkException;
import dev.ditsche.mailo.Mail;
import dev.ditsche.mailo.factory.MailBuilder;

import java.io.IOException;

/**
 * @author Tobias Dittmann
 */
public class PostmarkMailProvider extends AbstractMailProvider {

    private final ApiClient client;

    public PostmarkMailProvider(String serverToken) {
        client = Postmark.getApiClient(serverToken);
    }

    @Override
    public boolean send(MailBuilder mailBuilder) {
        return send(mailBuilder.build());
    }

    @Override
    public boolean send(Mail mail) {
        Message message = new Message();
        message.setMessageStream("outbound");
        message.setFrom(mail.getFrom().toString());
        message.setTo(addressSetToString(mail.getRecipients()));
        message.setSubject(mail.getSubject());
        if(message.getCc() != null)
            message.setCc(addressSetToString(mail.getCC()));
        if(message.getBcc() != null)
        message.setBcc(addressSetToString(mail.getBCC()));
        if(message.getReplyTo() != null)
            message.setReplyTo(mail.getReplyTo().toString());
        message.setHtmlBody(mail.getBody());

        try {
            client.deliverMessage(message);
            return true;
        } catch (PostmarkException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
