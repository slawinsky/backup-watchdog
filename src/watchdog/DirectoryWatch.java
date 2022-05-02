package watchdog;

import javax.mail.MessagingException;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class DirectoryWatch {
    private Path path;
    private int interval;
    private boolean isFileCreated;
    private String detailsMessage;

    public DirectoryWatch(String path) throws IOException, InterruptedException {
        this.path = Path.of(path);
        this.interval = 10000;
        this.isFileCreated = false;
        this.detailsMessage = "";
        initTimer();
        startService();
    }

    //        create mailsender class, pass server and user data and send notification email
    private void sendMessage() throws MessagingException {
        MailSender mailSender = new MailSender(true, "mail.server.com", "465", "your@address.com", "password123", detailsMessage, "recipient@address.com");
        mailSender.sendMessage();
    }

    private void initTimer() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    checkIsFileCreated();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }, interval, interval);
    }

    private void checkIsFileCreated() throws MessagingException {
        sendMessage();
        clearDetails();
    }

    private void clearDetails() {
        isFileCreated = false;
        detailsMessage = "";
    }

    private void startService() throws IOException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        path.register(
                watchService,
                StandardWatchEventKinds.ENTRY_CREATE
        );

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d/MM/YYYY HH:MM");
        WatchKey key;
        while((key = watchService.take()) != null) {
            for(WatchEvent<?> event : key.pollEvents()) {
                LocalDateTime time = LocalDateTime.now();
                detailsMessage += "Created file: " + event.context() + " | date: " + dtf.format(time) + ".\r\n";
                isFileCreated = true;
            }
            key.reset();
        }
    }
}
