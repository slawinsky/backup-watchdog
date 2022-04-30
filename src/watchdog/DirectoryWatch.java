package watchdog;

import java.io.IOException;
import java.nio.file.*;
import java.util.Timer;
import java.util.TimerTask;

public class DirectoryWatch {
    private Path path;
    private long interval;
    private boolean isFileCreated;

    public DirectoryWatch(String path) throws IOException, InterruptedException {
        this.path = Path.of(path);
        this.interval = 10000;
        this.isFileCreated = false;
        initTimer();
        startService();
    }

    private void initTimer() {
        System.out.println("start initTimer\n");
        Timer timer = new Timer();
        int begin = 0;
        int interval = 10000;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkIsFileCreated();
            }
        }, begin, interval);
    }

    private void checkIsFileCreated() {
        if(isFileCreated) {
            System.out.println("Backup został utworzony!");
        } else {
            System.out.println("Backup nie został utworzony!");
        }
    }

    private void startService() throws IOException, InterruptedException {
        System.out.println("start startService\n");
        WatchService watchService = FileSystems.getDefault().newWatchService();
        path.register(
                watchService,
                StandardWatchEventKinds.ENTRY_CREATE
        );

        WatchKey key;
        while((key = watchService.take()) != null) {
            for(WatchEvent<?> event : key.pollEvents()) {
                System.out.println("Event: " + event.kind() + ", wykorzystany plik: " + event.context() + ".");
                isFileCreated = true;
            }
            key.reset();
        }
    }
}
