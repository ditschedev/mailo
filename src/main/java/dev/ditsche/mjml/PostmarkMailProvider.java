package dev.ditsche.mjml;

import com.wildbit.java.postmark.Postmark;
import com.wildbit.java.postmark.client.ApiClient;
import com.wildbit.java.postmark.client.data.model.message.Message;
import com.wildbit.java.postmark.client.data.model.message.MessageResponse;
import com.wildbit.java.postmark.client.exception.PostmarkException;

import java.io.IOException;
import java.util.stream.Collectors;

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

        message.setFrom(config.getFrom());
        message.setTo(mail.getRecipient().getEmail());
        message.setSubject(mail.getSubject());
        message.setCc(mail.getCC().stream().map(MailAddress::getEmail).collect(Collectors.toList()));
        message.setBcc(mail.getBCC().stream().map(MailAddress::getEmail).collect(Collectors.toList()));
        message.setReplyTo(mail.getReplyTo().getEmail());
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
