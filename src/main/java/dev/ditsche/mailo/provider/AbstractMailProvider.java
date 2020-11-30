package dev.ditsche.mailo.provider;

import dev.ditsche.mailo.MailAddress;
import dev.ditsche.mailo.config.MailoConfig;

import java.util.Iterator;
import java.util.Set;

/**
 * @author Tobias Dittmann
 */
public abstract class AbstractMailProvider implements MailProvider {

    protected final MailoConfig config = MailoConfig.get();

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
