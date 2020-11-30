package dev.ditsche.mailo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * @author Tobias Dittmann
 */
@Data
@EqualsAndHashCode(exclude = "name")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MailAddress {

    @JsonProperty("Email")
    private String email;

    @JsonProperty("Name")
    private String name;

    public MailAddress(String email) {
        this(email, null);
    }

    public MailAddress(String email, String name) {
        if(email == null || email.trim().isEmpty())
            throw new IllegalArgumentException("Field \"email\" can not be null or empty");
        if(name != null && name.trim().isEmpty())
            throw new IllegalArgumentException("Field \"name\" can not be empty");
        if(!isValid(email))
            throw new IllegalArgumentException(String.format("\"%s\" is not a valid email adress", email));
        this.email = email.trim().toLowerCase();
        this.name = name != null ? name.trim() : null;
    }

    private boolean isValid(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    @SneakyThrows
    public InternetAddress toAddress() {
        return new InternetAddress(this.email, this.name);
    }

    public String toString() {
        if(this.name != null)
            return this.name + "<" + this.email + ">";
        return this.email;
    }

}
