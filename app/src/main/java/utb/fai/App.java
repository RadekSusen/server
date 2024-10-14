package utb.fai;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class App {

    public static void main(String[] args) {
        int port = 12345;
        int max_threads = 10;    

        // Implement input parameter processing
        if (args.length < 2) {
            System.out.println("Usage: java App <port> <max_threads>");
            return;
        }

        try {
            port = Integer.parseInt(args[0]);
            max_threads = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Both port and max_threads must be integers.");
            return;
        }
        // Implementation of the main server loop
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            ExecutorService threadPool = Executors.newFixedThreadPool(max_threads);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                threadPool.execute(new ClientThread(clientSocket));
            }
        } catch (Exception e) {
            System.out.println("Error starting server: " + e.getMessage());
        }
    }
}
