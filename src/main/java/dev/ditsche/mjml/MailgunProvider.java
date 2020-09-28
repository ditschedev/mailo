package dev.ditsche.mjml;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

/**
 * @author Tobias Dittmann
 */
public class MailgunProvider extends AbstractMailProvider {

    private final String url;

    private final String apiKey;

    public MailgunProvider(MJMLConfig config, String apiKey, String domain) {
        this(config, apiKey, domain, "https://api.mailgun.net/v3/");
    }

    public MailgunProvider(MJMLConfig config, String apiKey, String domain, String endpoint) {
        super(config);

        this.url = endpoint + domain + "/messages";
        this.apiKey = apiKey;
    }

    @Override
    public boolean send(Mail mail) {
        HttpResponse<JsonNode> request = Unirest.spawnInstance().post(url)
                .basicAuth("api", apiKey)
                .field("from", config.getFrom().toString())
                .field("to", addressSetToString(mail.getRecipients()))
                .field("subject", mail.getSubject())
                .field("html", mjmlToHtml(mail.getMjml()))
                .field("cc", addressSetToString(mail.getCC()))
                .field("bcc", addressSetToString(mail.getBCC()))
                .asJson();

        return request.isSuccess();
    }
}
