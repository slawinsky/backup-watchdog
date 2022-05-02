package watchdog;

import javax.mail.MessagingException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, MessagingException {
        String path = "directory_path";
        new DirectoryWatch(path);
    }
}
