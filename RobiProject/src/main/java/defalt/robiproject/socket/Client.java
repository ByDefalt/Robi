package defalt.robiproject.socket;

import java.io.*;
import java.net.Socket;

// Implémentation de l'interface pour le client
public class Client extends Thread implements SocketInterface {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ObjectOutputStream getOut() {
        return out;
    }

    public Socket getSocket(){
        return socket;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    @Override
    public void startSocket(String serverAddress, int port) throws IOException {
        socket = new Socket(serverAddress, port);
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
    }
    @Override
    public void stopSocket() throws IOException {
        socket.close();
        in.close();
        out.close();
    }
    @Override
    public void sendMessage(Object message) throws IOException {
        out.writeObject(message);
    }
    @Override
    public void receiveMessage() throws IOException{

    }
}
