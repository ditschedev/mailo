package dev.ditsche.mjml;

import com.wildbit.java.postmark.Postmark;
import com.wildbit.java.postmark.client.ApiClient;
import com.wildbit.java.postmark.client.data.model.message.Message;
import com.wildbit.java.postmark.client.exception.PostmarkException;

import java.io.IOException;

/**
 * @author Tobias Dittmann
 */
public class PostmarkMailProvider extends AbstractMailProvider {

    private final ApiClient client;

    public PostmarkMailProvider(MJMLConfig config, String serverToken) {
        super(config);
        client = Postmark.getApiClient(serverToken);
    }

    @Override
    public boolean send(Mail mail) {
        Message message = new Message();

        message.setFrom(config.getFrom().toString());
        message.setTo(addressSetToString(mail.getRecipients()));
        message.setSubject(mail.getSubject());
        if(message.getCc() != null)
            message.setCc(addressSetToString(mail.getCC()));
        if(message.getBcc() != null)
        message.setBcc(addressSetToString(mail.getBCC()));
        if(message.getReplyTo() != null)
            message.setReplyTo(mail.getReplyTo().toString());
        message.setHtmlBody(mjmlToHtml(mail.getMjml()));

        try {
            client.deliverMessage(message);
            return true;
        } catch (PostmarkException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
