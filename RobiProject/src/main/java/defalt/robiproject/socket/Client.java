package defalt.robiproject.socket;

import java.io.*;
import java.net.Socket;

// Implémentation de l'interface pour le client
public class Client extends Thread implements SocketInterface {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ObjectInputStream getIn() {
        return in;
    }

    public void startSocket(String serverAddress, int port) throws IOException {
        socket = new Socket(serverAddress, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    public void stopSocket() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    public void sendMessage(Object message) throws IOException {
        out.writeObject(message);
    }

    public void receiveMessage() throws IOException{

    }
}
