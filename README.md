# Java mjml-client

This is a client for sending mjml emails from java applications :heart:.

It is extendable, as you can add custom mail providers and comes with a SMTP and Mailjet provider out of the box.

## Installation
Add the dependency to your maven dependencies:

```xml
<dependency>
    <groupId>dev.ditsche</groupId>
    <artifactId>mjml-client</artifactId>
    <version>1.2.2</version>
</dependency>
```

## Usage

### Creating templates
Place your mjml-mail templates at `resources/templates`

### Sending mails
To send a mail, adapt the following code snippet.

```java
// Create a config
SMTPConfig config = new SMTPConfig();
config.setHost("smtp.example.com");
config.setPort(465);
config.setUsername("test@example.com");
config.setPassword("test123!");

MJMLConfig mjmlConfig = new MJMLConfig("appId", "appSecret", "from@example.com");

// Init mail provider
MailProvider mailProvider = new SMTPMailProvider(mjmlConfig, config);

// Build a mail
Mail mail = MailBuilder.create("Email Subject")
    .to(new MailAddress("test@test.com", "Test Name"))
    .params("url", "http://example.com")
    .params("name", "John Doe")
    .template("path/to/template.mjml");

// Send the mail
mailProvider.send(mail);
```