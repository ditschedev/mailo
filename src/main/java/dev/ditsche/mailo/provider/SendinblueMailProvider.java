package dev.ditsche.mailo.provider;

import dev.ditsche.mailo.Mail;
import dev.ditsche.mailo.factory.MailBuilder;
import sendinblue.ApiClient;
import sendinblue.ApiException;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.SmtpApi;
import sibModel.*;

import java.util.stream.Collectors;

/**
 * @author Tobias Dittmann
 */
public class SendinblueMailProvider extends AbstractMailProvider {

    private final SmtpApi sendApi;

    public SendinblueMailProvider(String apiKey) {
        ApiClient apiClient = Configuration.getDefaultApiClient();
        ApiKeyAuth key = (ApiKeyAuth) apiClient.getAuthentication("api-key");
        key.setApiKey(apiKey);
        sendApi = new SmtpApi();
    }

    @Override
    public boolean send(MailBuilder mailBuilder) {
        return send(mailBuilder.build());
    }

    @Override
    public boolean send(Mail mail) {
        SendSmtpEmail email = new SendSmtpEmail();
        email.setTo(mail.getRecipients().stream().map(ma -> new SendSmtpEmailTo().email(ma.getEmail()).name(ma.getName())).collect(Collectors.toList()));
        email.subject(mail.getSubject());
        email.setSender(new SendSmtpEmailSender().email(mail.getFrom().getEmail()).name(mail.getFrom().getName()));
        email.setHtmlContent(mail.getBody());
        if(mail.getCC() != null)
            email.setCc(mail.getCC().stream().map(ma -> new SendSmtpEmailCc().email(ma.getEmail()).name(ma.getName())).collect(Collectors.toList()));
        if(mail.getBCC() != null)
            email.setBcc(mail.getBCC().stream().map(ma -> new SendSmtpEmailBcc().email(ma.getEmail()).name(ma.getName())).collect(Collectors.toList()));
        if(mail.getReplyTo() != null)
            email.setReplyTo(new SendSmtpEmailReplyTo().email(mail.getReplyTo().getEmail()).name(mail.getReplyTo().getName()));

        try {
            sendApi.sendTransacEmail(email);
            return true;
        } catch (ApiException e) {
            e.printStackTrace();
            return false;
        }
    }

}
