package utb.fai;

import java.io.*;
import java.net.*;

public class ClientThread extends Thread {

    private Socket clientSocket;

    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            var in = clientSocket.getInputStream();
            var out = clientSocket.getOutputStream();
            int pocet;
            byte buffer[] = new byte[2048];
            while ((pocet = in.read(buffer)) != -1) {
                out.write(buffer, 0, pocet);
                out.flush();

            }
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
