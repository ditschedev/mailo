package dev.ditsche.mailo.factory;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import dev.ditsche.mailo.Mail;
import dev.ditsche.mailo.MailAddress;
import dev.ditsche.mailo.config.MailoConfig;
import dev.ditsche.mailo.exception.MailBuildException;
import dev.ditsche.mailo.exception.TemplateNotFoundException;
import dev.ditsche.mailo.mjml.MjmlClient;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TemplateMailBuilder implements MailBuilder {

    private final MjmlClient mjmlClient = new MjmlClient();

    private String subject;

    private final Set<MailAddress> toList = new HashSet<>();

    private final Set<MailAddress> ccList = new HashSet<>();

    private final Set<MailAddress> bccList = new HashSet<>();

    private MailAddress from;

    private MailAddress replyTo;

    private HashMap<String, Object> params;

    private boolean isMjml;

    private String body;

    private TemplateMailBuilder() {
        this.params = new HashMap<>();
    }

    public static TemplateMailBuilder create() {
        return new TemplateMailBuilder();
    }

    public TemplateMailBuilder subject(String subject) {
        if(subject == null || subject.trim().isEmpty())
            throw new IllegalArgumentException("Subject can not be null or empty");
        this.subject = subject.trim();
        return this;
    }

    public TemplateMailBuilder to(MailAddress recipient) {
        if(recipient == null)
            throw new IllegalArgumentException("Recipient can not be null");
        this.toList.add(recipient);
        return this;
    }

    public TemplateMailBuilder cc(MailAddress recipient) {
        if(recipient == null)
            throw new IllegalArgumentException("Recipient can not be null");
        this.ccList.add(recipient);
        return this;
    }

    public TemplateMailBuilder bcc(MailAddress recipient) {
        if(recipient == null)
            throw new IllegalArgumentException("Recipient can not be null");
        this.bccList.add(recipient);
        return this;
    }

    public TemplateMailBuilder from(MailAddress from) {
        if(from == null)
            throw new IllegalArgumentException("Sender can not be null");
        this.from = from;
        return this;
    }

    public TemplateMailBuilder replyTo(MailAddress replyTo) {
        if(replyTo == null)
            throw new IllegalArgumentException("Reply To can not be null");
        this.replyTo = replyTo;
        return this;
    }

    public TemplateMailBuilder param(String key, Object value) {
        if(key == null || key.trim().isEmpty())
            throw new IllegalArgumentException("Parameter key can not be null or empty");
        if(value == null)
            throw new IllegalArgumentException("Parameter value can not be null");
        this.params.put(key.trim(), value);
        return this;
    }

    public MailBuilder mjmlTemplate(String templatePath) {
        if(templatePath == null || templatePath.trim().isEmpty())
            throw new IllegalArgumentException("Template path can not be null or empty");
        if(!templatePath.endsWith(".mjml")) templatePath += ".mjml";
        this.body = renderMustacheTemplate(templatePath);
        this.isMjml = true;
        return this;
    }

    public MailBuilder htmlTemplate(String templatePath) {
        if(templatePath == null || templatePath.trim().isEmpty())
            throw new IllegalArgumentException("Template path can not be null or empty");
        if(!templatePath.endsWith(".html")) templatePath += ".html";
        this.body = renderMustacheTemplate(templatePath);
        this.isMjml = false;
        return this;
    }

    public MailBuilder body(String body) {
        if(body == null || body.trim().isEmpty())
            throw new IllegalArgumentException("Body can not be null or empty");
        this.body = renderMustacheString(body.trim());
        this.isMjml = false;
        return this;
    }

    private String renderMustacheTemplate(String templatePath) {
        MailoConfig config = MailoConfig.get();
        MustacheFactory mf = new DefaultMustacheFactory();
        templatePath = config.getTemplateDirectory() + templatePath;
        String templateFile = TemplateMailBuilder.class.getResource(templatePath) == null ? null : TemplateMailBuilder.class.getResource(templatePath).getFile();
        if(templateFile == null)
            throw new TemplateNotFoundException("Template file \"" + templatePath + "\" can not be found in classpath");
        Mustache mustache = mf.compile(templateFile);
        StringWriter stringWriter = new StringWriter();
        mustache.execute(stringWriter, this.params);
        return stringWriter.toString();
    }

    private String renderMustacheString(String body) {
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(new StringReader(body), "inline");
        StringWriter stringWriter = new StringWriter();
        mustache.execute(stringWriter, this.params);
        return stringWriter.toString();
    }

    @Override
    public Mail build() {
        MailoConfig config = MailoConfig.get();

        if(subject == null || subject.isEmpty())
            throw new MailBuildException("The subject of the mail can not be null or empty");

        if(toList.size() == 0)
            throw new MailBuildException("The mail needs to have at least one recipient");

        if(from == null && config.getDefaultSender() == null)
            throw new MailBuildException("The mail needs to have a from address defined");

        if(body == null || body.isEmpty())
            throw new MailBuildException("The mail needs to have a non empty body");

        Mail mail = new Mail(this.subject);
        this.toList.forEach(mail::addRecipient);
        this.ccList.forEach(mail::addCC);
        this.bccList.forEach(mail::addBCC);
        mail.setFrom(from != null ? from : config.getDefaultSender());
        mail.setReplyTo(replyTo != null ? replyTo : config.getDefaultReplyTo());
        mail.setBody(isMjml ? mjmlClient.render(body) : body);

        return mail;
    }
}