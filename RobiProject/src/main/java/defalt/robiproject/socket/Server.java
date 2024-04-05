package defalt.robiproject.socket;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

// Implémentation de l'interface pour le serveur
public class Server implements socket.SocketInterface {
    protected ServerSocket serverSocket;
    protected Socket clientSocket;
    protected PrintWriter out;
    protected BufferedReader in;

    public void start() {
        try {
            serverSocket = new ServerSocket(12345);
            System.out.println("Serveur démarré. En attente de connexion...");
            clientSocket = serverSocket.accept();
            System.out.println("Client connecté : " + clientSocket);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public String receiveMessage() {
        try {
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
