import java.io.*;
import java.util.*;
import java.text.*;

public class FileTracker {
    private static final String INDEX_FILE = "trash_index.dat";
    private Map<String, FileMeta> index = new HashMap<>();

    public void loadIndex() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(INDEX_FILE))) {
            index = (Map<String, FileMeta>) in.readObject();
        } catch (Exception e) {
            index = new HashMap<>();
        }
    }

    public void saveIndex() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(INDEX_FILE))) {
            out.writeObject(index);
        } catch (IOException e) {
            System.err.println("Failed to save index.");
        }
    }

    public void addFile(String name, String originalPath) {
        index.put(name, new FileMeta(originalPath, new Date()));
        saveIndex();
        utils.Logger.log("Trashed: " + name);
    }

    public void removeFile(String name) {
        index.remove(name);
        saveIndex();
        utils.Logger.log("Recovered: " + name);
    }

    public String getOriginalPath(String name) {
        FileMeta meta = index.get(name);
        return meta != null ? meta.originalPath : null;
    }

    public void purgeOldFiles(int days) {
        Date now = new Date();
        Iterator<Map.Entry<String, FileMeta>> it = index.entrySet().iterator();
        int purged = 0;

        while (it.hasNext()) {
            Map.Entry<String, FileMeta> entry = it.next();
            long age = now.getTime() - entry.getValue().trashedDate.getTime();
            if (age > days * 24L * 60 * 60 * 1000) {
                File file = new File(".trash", entry.getKey());
                if (file.delete()) {
                    it.remove();
                    utils.Logger.log("Purged: " + entry.getKey());
                    purged++;
                }
            }
        }

        saveIndex();
        System.out.println("Purged " + purged + " old files.");
    }

    public void listFiles() {
        if (index.isEmpty()) {
            System.out.println("Trash is empty.");
            return;
        }
        System.out.println("Files in trash:");
        for (Map.Entry<String, FileMeta> entry : index.entrySet()) {
            System.out.println(" - " + entry.getKey() + " (from: " + entry.getValue().originalPath + ")");
        }
    }

    public void showStats() {
        System.out.println("Files in trash: " + index.size());
    }

    static class FileMeta implements Serializable {
        String originalPath;
        Date trashedDate;

        FileMeta(String path, Date date) {
            this.originalPath = path;
            this.trashedDate = date;
        }
    }
}