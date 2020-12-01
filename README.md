# Mailo - Java Mail Library

This is a client for sending emails easily from java applications :heart:.

It is extendable, as you can add custom mail providers and comes with a SMTP and some mail service providers out of the box.

## Installation
Add the dependency to your maven dependencies:

```xml
<dependency>
    <groupId>dev.ditsche</groupId>
    <artifactId>mailo</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Configuration

### Environment Variables
Configuration is easy using the predefined environment variables. See below for more details:

##### `MAILO_APP_ID`
Sets the MJML application id obtained by requesting api keys from [mjml.io](mjml.io).

##### `MAILO_APP_SECRET`
Sets the MJML application secret obtained by requesting api keys from [mjml.io](mjml.io).

##### `MAILO_TEMPLATE_DIR`
Sets the root template directory in the classpath where all mail templates are located. 
Defaults to `/templates/`.

### Config Object
The libary is designed to be configured through environment variables, but you can use the config object too. Before sending or
building mails, get the current config by using:

```java
MailoConfig config = MailoConfig.get();
```

Now you can set the values as you need them to be programmatically.


## Usage

### Creating templates
Write your template with mjml and place them in your template directory inside of your Java Application. By default the library
searches `/templates/**` in your classpath resources.

### Sending mails
To send a mail using smtp, adapt the following code snippet.

```java
// Create a smtp config for our mail provider
SmtpConfig config = new SmtpConfig();
config.setHost("smtp.example.com");
config.setPort(465);
config.setUsername("test@example.com");
config.setPassword("test123!");

// Create a mail provider
MailProvider mailProvider = new SmtpMailProvider(config);

// Build a mail
Mail mail = MailBuilder.mjml()
    .subject("Email Subject")
    .from(new MailAddress("sender@test.com", "Sender name"))
    .to(new MailAddress("test@test.com", "Test Name"))
    .params("url", "http://example.com")
    .params("name", "John Doe")
    .template("path/to/template.mjml") // .mjml extension is optional
    .build();

// Send the mail
mailProvider.send(mail);
```

You can send mails using some big mail providers as well, if you don't like to use smtp directly. For example Postmark:
```java
// Create a mail provider
MailProvider mailProvider = new PostmarkMailProvider("YOUR_POSTMARK_SERVER_TOKEN");

// Build a mail
Mail mail = MailBuilder.create()
    .subject("Email Subject")
    .from(new MailAddress("sender@test.com", "Sender name"))
    .to(new MailAddress("test@test.com", "Test Name"))
    .params("url", "http://example.com")
    .params("name", "John Doe")
    .template("path/to/template.mjml")
    .build();

// Send the mail
mailProvider.send(mail);
```

Currently available are:
* Postmark
* SendGrid
* Sendinblue
* ~~Mailgun~~ (coming soon)
* ~~Mailjet~~ (coming soon)