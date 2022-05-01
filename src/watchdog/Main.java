package watchdog;

import javax.mail.MessagingException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, MessagingException {
//        System.out.println("App start!\n");

//        String path = "C:\\Users\\Kuba\\Desktop\\backup-tests";
//        new DirectoryWatch(path);

        MailSender mailSender = new MailSender(true, "smtp.dpoczta.pl", "587", "jakub@exabytes.pl", "7zzQ4eJk7U", "Testowa wiadomość", "jakub@exabytes.pl");
        mailSender.sendMessage();
    }
}
