package dev.ditsche.mailo.factory;

import dev.ditsche.mailo.MailAddress;

public interface TemplateMailBuilder extends MailBuilder {

    TemplateMailBuilder subject(String subject);

    TemplateMailBuilder to(MailAddress recipient);

    TemplateMailBuilder cc(MailAddress recipient);

    TemplateMailBuilder bcc(MailAddress recipient);

    TemplateMailBuilder from(MailAddress from);

    TemplateMailBuilder replyTo(MailAddress replyTo);

    TemplateMailBuilder param(String key, Object value);

    MailBuilder loadTemplate(String templatePath);

}
