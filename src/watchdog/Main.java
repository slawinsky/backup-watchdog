package watchdog;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("App start!\n");
        new DirectoryWatch("C:\\Users\\Kuba\\Desktop");
    }
}
