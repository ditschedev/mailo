package dev.ditsche.mjml;

/**
 * @author Tobias Dittmann
 */
public class MailgunProvider extends AbstractMailProvider {

    public MailgunProvider(MJMLConfig config) {
        super(config);
    }

    @Override
    public boolean send(Mail mail) {
        return false;
    }
}
