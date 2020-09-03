package dev.ditsche.mjml;

import kong.unirest.Unirest;

/**
 * @author Tobias Dittmann
 */
public abstract class AbstractMailProvider implements MailProvider {

    protected String from;

    protected AbstractMailProvider(String from) {
        this.from = from;
    }

    protected String mjmlToHtml(String mjml) {
         return Unirest.post("/render")
                .header("Content-Type", "application/json")
                .body(new MjmlRequest(mjml)).asJson().getBody().getObject().getString("html");
    }

}
