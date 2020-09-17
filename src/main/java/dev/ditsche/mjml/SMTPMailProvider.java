package dev.ditsche.mjml;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

/**
 * @author Tobias Dittmann
 */
public class SMTPMailProvider extends AbstractMailProvider {

    private final SMTPConfig smtpConfig;

    private final Session session;

    public SMTPMailProvider(MJMLConfig config, SMTPConfig smtpConfig) {
        super(config);
        this.smtpConfig = smtpConfig;
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpConfig.getHost()); //SMTP Host
        props.put("mail.smtp.port", smtpConfig.getPort()); //TLS Port
        props.put("mail.smtp.auth", smtpConfig.getUsername() != null); //enable authentication
        if(smtpConfig.isSsl()) {
            props.put("mail.smtp.starttls.enable", smtpConfig.isSsl()); //enable STARTTLS
        } else {
            props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
            props.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
        }

        //create Authenticator object to pass in Session.getInstance argument
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

            message.setFrom(new InternetAddress(this.config.getFrom()));
            if(mail.getReplyTo() != null)
                message.setReplyTo(List.of(mail.getReplyTo().toAddress()).toArray(new Address[1]));
            message.addRecipient(Message.RecipientType.TO, mail.getRecipient().toAddress());
            message.setSubject(mail.getSubject(), "UTF-8");
            message.setContent(mjmlToHtml(mail.getMjml()), "text/html");
            Transport.send(message);
            return true;
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
