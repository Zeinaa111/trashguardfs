import java.io.File;

public class TrashManager {
    private static final String TRASH_DIR = ".trash";
    private FileTracker tracker = new FileTracker();

    public TrashManager() {
        File trash = new File(TRASH_DIR);
        if (!trash.exists()) trash.mkdir();
        tracker.loadIndex();
    }

    public void trashFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("File not found.");
            return;
        }

        File dest = new File(TRASH_DIR, file.getName());
        if (file.renameTo(dest)) {
            tracker.addFile(file.getName(), file.getAbsolutePath());
            System.out.println("Moved to trash: " + file.getName());
        } else {
            System.out.println("Failed to move file.");
        }
    }

    public void recoverFile(String filename) {
        File trashFile = new File(TRASH_DIR, filename);
        String originalPath = tracker.getOriginalPath(filename);

        if (originalPath == null || !trashFile.exists()) {
            System.out.println("File not in trash.");
            return;
        }

        File original = new File(originalPath);
        if (trashFile.renameTo(original)) {
            tracker.removeFile(filename);
            System.out.println("Recovered: " + filename);
        } else {
            System.out.println("Failed to recover file.");
        }
    }

    public void purgeOldFiles(int days) {
        tracker.purgeOldFiles(days);
    }

    public void listFiles() {
        tracker.listFiles();
    }

    public void showLog() {
        utils.Logger.readLog();
    }

    public void showStats() {
        tracker.showStats();
    }
}