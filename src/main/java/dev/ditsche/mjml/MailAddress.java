package dev.ditsche.mjml;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;

/**
 * @author Tobias Dittmann
 */
@Data
@EqualsAndHashCode(exclude = "name")
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MailAddress {

    @JsonProperty("Email")
    private String email;

    @JsonProperty("Name")
    private String name;

    public MailAddress(String email) {
        this(email, null);
    }

    public InternetAddress toAddress() throws UnsupportedEncodingException {
        return new InternetAddress(this.email, this.name);
    }

}
