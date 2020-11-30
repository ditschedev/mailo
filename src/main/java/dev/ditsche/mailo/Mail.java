package dev.ditsche.mailo;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a mail.
 *
 * @author Tobias Dittmann
 */
public class Mail {

    @Getter
    @Setter
    private String subject;

    @Getter
    @Setter
    private MailAddress from;

    private final Set<MailAddress> recipients;

    private final Set<MailAddress> cc;

    private final Set<MailAddress> bcc;

    @Getter
    @Setter
    private MailAddress replyTo;

    @Getter
    @Setter
    private String body;

    public Mail(String subject) {
        this(subject, new MailAddress[0]);
    }

    public Mail(String subject, MailAddress ...to) {
        if(subject == null || subject.trim().isEmpty())
            throw new IllegalArgumentException("The mails \"subject\" can not be null or empty");
        subject = subject.trim();
        this.subject = subject;
        this.recipients = new HashSet<>();
        if(to != null)
            this.recipients.addAll(Arrays.asList(to.clone()));
        this.cc = new HashSet<>();
        this.bcc = new HashSet<>();
        this.body = null;
    }

    /**
     * Adds one or more address to the mails recipients.
     *
     * @param mailAddress Email addresses that should be added.
     */
    public void addRecipient(MailAddress ...mailAddress) {
        if(mailAddress == null || mailAddress.length == 0)
            throw new IllegalArgumentException("At least one MailAddress must be provided");
        this.recipients.addAll(Arrays.asList(mailAddress));
    }

    /**
     * Adds one or more address to the mails bcc addresses.
     *
     * @param mailAddress Email addresses that should be added.
     */
    public void addCC(MailAddress ...mailAddress) {
        if(mailAddress == null || mailAddress.length == 0)
            throw new IllegalArgumentException("At least one MailAddress must be provided");
        this.cc.addAll(Arrays.asList(mailAddress));
    }

    /**
     * Adds one or more address to the mails bcc addresses.
     *
     * @param mailAddress Email addresses that should be added.
     */
    public void addBCC(MailAddress ...mailAddress) {
        if(mailAddress == null || mailAddress.length == 0)
            throw new IllegalArgumentException("At least one MailAddress must be provided");
        this.bcc.addAll(Arrays.asList(mailAddress));
    }

    /**
     * Gets an immutable collection of the current recipients.
     *
     * @return A immutable set of the current recipient list.
     */
    public Set<MailAddress> getRecipients() {
        return Collections.unmodifiableSet(this.recipients);
    }

    /**
     * Gets an immutable collection of the current cc's.
     *
     * @return A immutable set of the current cc list.
     */
    public Set<MailAddress> getCC() {
        return Collections.unmodifiableSet(this.cc);
    }

    /**
     * Gets an immutable collection of the current bcc's.
     *
     * @return A immutable set of the current bcc list.
     */
    public Set<MailAddress> getBCC() {
        return Collections.unmodifiableSet(this.bcc);
    }
}
