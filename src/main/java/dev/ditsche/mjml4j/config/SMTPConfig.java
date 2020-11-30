package dev.ditsche.mjml4j.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tobias Dittmann
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SMTPConfig {

    private String host;

    private int port;

    private String username;

    private String password;

    private boolean ssl = false;

}
