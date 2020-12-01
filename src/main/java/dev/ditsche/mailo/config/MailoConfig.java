package dev.ditsche.mailo.config;

import dev.ditsche.mailo.MailAddress;
import lombok.Getter;
import lombok.Setter;

public final class MailoConfig {

    private static MailoConfig instance;

    @Getter
    private String templateDirectory;

    @Getter
    private String mjmlAppId;

    @Getter
    private String mjmlAppSecret;

    @Getter
    @Setter
    private MailAddress defaultSender;

    @Getter
    @Setter
    private MailAddress defaultReplyTo;

    private MailoConfig() {
        init();
    }

    private void init() {
        templateDirectory = System.getenv("MAILO_TEMPLATE_DIR") != null ? System.getenv("MAILO_APP_ID") : "/templates/";
        mjmlAppId = System.getenv("MAILO_APP_ID");
        mjmlAppSecret = System.getenv("MAILO_APP_SECRET");
        defaultSender = null;
        defaultReplyTo = null;
    }

    public static MailoConfig get() {
        if(instance == null)
            instance = new MailoConfig();
        return instance;
    }

    public void setTemplateDirectory(String templateDirectory) {
        if(templateDirectory == null)
            throw new IllegalArgumentException("templateDirectory can not be null");
        this.templateDirectory = templateDirectory.trim();
    }

    public void setMjmlAppId(String mjmlAppId) {
        if(mjmlAppId == null)
            throw new IllegalArgumentException("mjmlAppId can not be null");
        this.mjmlAppId = mjmlAppId.trim();
    }

    public void setMjmlAppSecret(String mjmlAppSecret) {
        if(mjmlAppSecret == null)
            throw new IllegalArgumentException("mjmlAppSecret can not be null");
        this.mjmlAppSecret = mjmlAppSecret.trim();
    }

    public void reset() {
        init();
    }

}
