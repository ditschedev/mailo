package dev.ditsche.mjml;

import kong.unirest.Unirest;
import kong.unirest.jackson.JacksonObjectMapper;
import lombok.Getter;

/**
 * @author Tobias Dittmann
 */
@Getter
public class MJMLConfig {

    private String appId;

    private String appSecret;

    private MailAddress from;

    public MJMLConfig(String appId, String appSecret, MailAddress from) {
        this.appId = appId;
        this.appSecret = appSecret;
        this.from = from;
    }

}
