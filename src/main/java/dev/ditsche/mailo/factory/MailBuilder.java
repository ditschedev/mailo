package dev.ditsche.mailo.factory;

import dev.ditsche.mailo.Mail;

/**
 * @author Tobias Dittmann
 */
public interface MailBuilder {

    Mail build();

    static TemplateMailBuilder mjml() {
        return new MjmlMailBuilder();
    }

    static TemplateMailBuilder html() {
        return new HtmlMailBuilder();
    }

    static StringMailBuilder plain() {
        return new PlainMailBuilder();
    }

}
