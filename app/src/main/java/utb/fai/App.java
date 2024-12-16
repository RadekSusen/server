package utb.fai;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

public class App {

    public static void main(String[] args) throws IOException {

        int port = 12345;
        int max_threads = 10;
        try {
            if (args.length >= 2) {
                port = Integer.parseInt(args[0]);
                max_threads = Integer.parseInt(args[1]);
            } else {
                System.err.println("Nedostatek vstupních parametrů. Používají se výchozí hodnoty.");
            }

            if (port <= 0 || port > 65535) {
                throw new IllegalArgumentException("Port musí být číslo mezi 1 a 65535.");
            }
            if (max_threads <= 0) {
                throw new IllegalArgumentException("Maximální počet vláken musí být kladné číslo větší než 0.");
            }

        } catch (NumberFormatException e) {
            System.err.println("Chyba: Parametry musí být platná celá čísla.");
            return;
        } catch (IllegalArgumentException e) {
            System.err.println("Chyba: " + e.getMessage());
            return;
        }

        var executor = Executors.newFixedThreadPool(max_threads);

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            while (true) {
                Socket s = serverSocket.accept();
                ClientThread task = new ClientThread(s);
                executor.execute(task);
            }
        } catch (IOException e) {

            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}
