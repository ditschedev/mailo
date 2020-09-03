package dev.ditsche.mjml;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Tobias Dittmann
 */
public class SMTPMailProvider extends AbstractMailProvider {

    private final SMTPConfig config;

    private final Session session;

    public SMTPMailProvider(SMTPConfig config, String from) {
        super(from);
        this.config = config;
        Properties props = new Properties();
        props.put("mail.smtp.host", config.getHost()); //SMTP Host
        props.put("mail.smtp.port", config.getPort()); //TLS Port
        props.put("mail.smtp.auth", config.getUsername() != null); //enable authentication
        if(config.isSsl()) {
            props.put("mail.smtp.starttls.enable", config.isSsl()); //enable STARTTLS
        } else {
            props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
            props.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
        }

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(config.getUsername(), config.getPassword());
            }
        };
        this.session = Session.getInstance(props, auth);
    }

    @Override
    public boolean send(Mail mail) throws IOException {
        try {
            MimeMessage message = new MimeMessage(session);
            message.addHeader("Content-type", "text/HTML; charset=UTF-8");
            message.addHeader("format", "flowed");
            message.addHeader("Content-Transfer-Encoding", "8bit");


            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, mail.getRecipient().toAddress());

            // Set Subject: header field
            message.setSubject(mail.getSubject(), "UTF-8");

            // Now set the actual message
            message.setText(mjmlToHtml(mail.getMjml()), "UTF-8");

            // Send message
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
