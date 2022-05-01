package watchdog;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class MailSender {
    private Properties prop;
    private boolean smtpAuth;
    private String mailServer;
    private String port;
    private String username;
    private String password;
    private String content;
    private String recipient;

    public MailSender(boolean smtpAuth, String mailServer, String port, String username, String password, String content, String recipient) {
        prop = new Properties();
        this.smtpAuth = smtpAuth;
        this.mailServer = mailServer;
        this.port = port;
        this.username = username;
        this.password = password;
        this.content = content;
        this.recipient = recipient;
        setProperties();
    }

    private void setProperties() {
        prop.put("mail.smtp.auth", smtpAuth);
        prop.put("mail.smtp.host", mailServer);
        prop.put("mail.smtp.port", port);
        prop.put("mail.smtp.ssl.trust", mailServer);
    }

    public void sendMessage() throws MessagingException {
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(recipient));
        message.setSubject("Backup alert!");

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(content, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);
        Transport.send(message);
    }
}
