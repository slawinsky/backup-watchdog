package watchdog;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("App start!\n");

        String path = "C:\\Users\\Kuba\\Desktop\\backup-tests";
        new DirectoryWatch(path);
    }
}
