import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TrashManager manager = new TrashManager();
        System.out.println("TrashGuardFS initialized. Type 'help' for commands.");

        while (true) {
            System.out.print("TrashGuardFS> ");
            String input = scanner.nextLine();
            String[] parts = input.trim().split(" ", 2);
            String command = parts[0].toLowerCase();

            switch (command) {
                case "trash":
                    if (parts.length < 2) {
                        System.out.println("Usage: trash <filename>");
                        break;
                    }
                    manager.trashFile(parts[1]);
                    break;
                case "recover":
                    if (parts.length < 2) {
                        System.out.println("Usage: recover <filename>");
                        break;
                    }
                    manager.recoverFile(parts[1]);
                    break;
                case "purge":
                    manager.purgeOldFiles(7); // Purge files older than 7 days
                    break;
                case "list":
                    manager.listFiles();
                    break;
                case "log":
                    manager.showLog();
                    break;
                case "stats":
                    manager.showStats();
                    break;
                case "help":
                    System.out.println("Commands: trash <file>, recover <file>, purge, list, log, stats, exit");
                    break;
                case "exit":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Unknown command. Type 'help' for commands.");
            }
        }
    }
}