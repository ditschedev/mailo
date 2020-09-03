package dev.ditsche.mjml;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.resource.Emailv31;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author Tobias Dittmann
 */
public class MailJetMailProvider extends AbstractMailProvider {

    private MailjetClient mailjetClient;

    public MailJetMailProvider(String publicKey, String secretKey, String from) {
        super(from);
        this.mailjetClient = new MailjetClient(publicKey, secretKey, new ClientOptions("v3.1"));
    }

    @SneakyThrows
    @Override
    public boolean send(Mail mail) throws IOException {
        MailjetRequest request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", this.from))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", mail.getRecipient().getEmail())
                                                .put("Name", mail.getRecipient().getName())))
                                .put(Emailv31.Message.SUBJECT, mail.getSubject())
                                .put(Emailv31.Message.HTMLPART, mjmlToHtml(mail.getMjml()))
                                .put(Emailv31.Message.CUSTOMID, "se-lv")));
        MailjetResponse response = mailjetClient.post(request);
        return response.getStatus() == 200;
    }
}
