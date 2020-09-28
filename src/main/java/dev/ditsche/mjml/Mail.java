package dev.ditsche.mjml;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Represents a mail.
 *
 * @author Tobias Dittmann
 */
@NoArgsConstructor
public class Mail {

    @Getter
    @Setter
    private String subject;

    private Set<MailAddress> recipients;

    private Set<MailAddress> cc;

    private Set<MailAddress> bcc;

    @Getter
    @Setter
    private MailAddress replyTo;

    @Getter
    @Setter
    private String mjml;

    public Mail(String subject) {
        this(subject, null);
    }

    public Mail(String subject, MailAddress to) {
        this.subject = subject;
        this.recipients = new HashSet<>();
        this.recipients.add(to);
        this.cc = new HashSet<>();
        this.bcc = new HashSet<>();
        this.mjml = "";
    }

    /**
     * Adds one or more address to the mails bcc addresses.
     *
     * @param mailAddress Email addresses that should be added.
     */
    public void addCC(MailAddress ...mailAddress) {
        this.cc.addAll(Arrays.asList(mailAddress));
    }

    /**
     * Adds one or more address to the mails bcc addresses.
     *
     * @param mailAddress Email addresses that should be added.
     */
    public void addBCC(MailAddress ...mailAddress) {
        this.bcc.addAll(Arrays.asList(mailAddress));
    }

    /**
     * Gets an immutable collection of the current cc's.
     *
     * @return A immutable set of the current cc list.
     */
    public Set<MailAddress> getCC() {
        return Collections.unmodifiableSet(cc);
    }

    /**
     * Gets an immutable collection of the current bcc's.
     *
     * @return A immutable set of the current bcc list.
     */
    public Set<MailAddress> getBCC() {
        return Collections.unmodifiableSet(bcc);
    }

    /**
     * Delegates with a null value for properties if there are no variables
     * that should be parsed.
     *
     * @see #fromTemplate(String, String, Map)
     *
     * @param subject The subject of the mail.
     * @param template The name of the template including subfolders.
     * @return The parsed Mail object with a ready to send html body.
     */
    public static Mail fromTemplate(String subject, String template) {
        return fromTemplate(subject, template, null);
    }

    /**
     * Creates a Mail object from a template stored in the projects classpath.
     * Takes in a Map of properties that will be used to parse the mails body and
     * replace the variables by the maps keys.
     *
     * @param subject The subject of the mail.
     * @param template The name of the template including subfolders.
     * @param properties A Map containing the variables that should be replaced.
     * @return The parsed Mail object with a ready to send html body.
     */
    public static Mail fromTemplate(String subject, String template, Map<String, Object> properties) {
        if(!template.endsWith(".mjml")) template += ".mjml";
        if(properties == null) properties = new HashMap<>();
        MustacheFactory mf = new DefaultMustacheFactory();
        if(Mail.class.getResource("/templates/" + template) == null)
            throw new TemplateNotFoundException();
        Mustache mustache = mf.compile("templates/" + template);
        StringWriter stringWriter = new StringWriter();
        mustache.execute(stringWriter, properties);
        Mail mail = new Mail(subject);
        mail.setMjml(stringWriter.toString());
        return mail;
    }
}
