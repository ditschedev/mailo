package dev.ditsche.mjml;

import kong.unirest.Unirest;
import kong.unirest.jackson.JacksonObjectMapper;

/**
 * @author Tobias Dittmann
 */
public abstract class AbstractMailProvider implements MailProvider {

    protected MJMLConfig config;

    protected AbstractMailProvider(MJMLConfig config) {
        this.config = config;
    }

    protected String mjmlToHtml(String mjml) {
         return Unirest.spawnInstance().post("https://api.mjml.io/v1/render")
                .basicAuth(config.getAppId(), config.getAppSecret())
                .withObjectMapper(new JacksonObjectMapper())
                .header("Content-Type", "application/json")
                .body(new MjmlRequest(mjml)).asJson().getBody().getObject().getString("html");
    }

}
