package watchdog;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class DirectoryWatch {
    private Path path;

    public DirectoryWatch(String path) throws IOException, InterruptedException {
        this.path = Paths.get(System.getProperty(path));
        startService();
    }

    private void startService() throws IOException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        path.register(
                watchService,
                StandardWatchEventKinds.ENTRY_CREATE
        );

        WatchKey key;
        while((key = watchService.take()) != null) {
            for(WatchEvent<?> event : key.pollEvents()) {
                System.out.println("Event: " + event.kind() + ", wykorzystany plik: " + event.context() + ".");
            }
            key.reset();
        }
    }
}
