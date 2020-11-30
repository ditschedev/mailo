package dev.ditsche.mailo.provider;

import dev.ditsche.mailo.Mail;
import dev.ditsche.mailo.MailAddress;
import dev.ditsche.mailo.config.SmtpConfig;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

/**
 * @author Tobias Dittmann
 */
public class SmtpMailProvider extends AbstractMailProvider {

    private final Session session;

    public SmtpMailProvider(SmtpConfig smtpConfig) {
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpConfig.getHost());
        props.put("mail.smtp.port", smtpConfig.getPort());
        props.put("mail.smtp.auth", smtpConfig.getUsername() != null);
        if(smtpConfig.isSsl()) {
            props.put("mail.smtp.starttls.enable", smtpConfig.isSsl());
        } else {
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
        }

        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(smtpConfig.getUsername(), smtpConfig.getPassword());
            }
        };
        this.session = Session.getInstance(props, auth);
    }

    @Override
    public boolean send(Mail mail) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.addHeader("Content-type", "text/HTML; charset=UTF-8");
            message.addHeader("format", "flowed");
            message.addHeader("Content-Transfer-Encoding", "8bit");

            message.setFrom(mail.getFrom().toAddress());
            if(mail.getReplyTo() != null)
                message.setReplyTo(List.of(mail.getReplyTo().toAddress()).toArray(new Address[1]));
            for(MailAddress address : mail.getRecipients()) {
                message.addRecipient(Message.RecipientType.TO, address.toAddress());
            }
            for(MailAddress address : mail.getCC()) {
                message.addRecipient(Message.RecipientType.CC, address.toAddress());
            }
            for(MailAddress address : mail.getBCC()) {
                message.addRecipient(Message.RecipientType.BCC, address.toAddress());
            }
            message.setSubject(mail.getSubject(), "UTF-8");
            message.setContent(mail.getBody(), "text/html");
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
