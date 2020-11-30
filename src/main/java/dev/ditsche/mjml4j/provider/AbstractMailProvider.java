package dev.ditsche.mjml4j.provider;

import dev.ditsche.mjml4j.config.MJMLConfig;
import dev.ditsche.mjml4j.MailAddress;
import dev.ditsche.mjml4j.MjmlRequest;
import kong.unirest.Unirest;
import kong.unirest.jackson.JacksonObjectMapper;

import java.util.Iterator;
import java.util.Set;

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

    protected String addressSetToString(Set<MailAddress> mailAddresses) {
        if(mailAddresses.size() == 0)
            return null;
        if(mailAddresses.size() == 1)
            return mailAddresses.toArray()[0].toString();
        StringBuilder sb = new StringBuilder();
        Iterator<MailAddress> iterator = mailAddresses.iterator();
        while(iterator.hasNext()) {
            MailAddress address = iterator.next();
            sb.append(address.toString());
            if(iterator.hasNext())
                sb.append(", ");
        }
        return sb.toString();
    }

}
