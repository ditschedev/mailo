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

    private String from;

    public MJMLConfig(String appId, String appSecret, String from) {
        this.appId = appId;
        this.appSecret = appSecret;
        this.from = from;
    }

}
