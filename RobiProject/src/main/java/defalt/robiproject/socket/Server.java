package defalt.robiproject.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public  class Server implements SocketInterface{
    private ServerSocket ServerSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public final ObjectInputStream getIn() {
        return in;
    }
    @Override
    public void startSocket(String serverAddress, int port) throws IOException {
        ServerSocket = new ServerSocket(port);
    }
    public Socket accept() throws IOException {
        Socket clientSocket = this.ServerSocket.accept();
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
        System.out.println(clientSocket.toString());
        return clientSocket;
    }

    @Override
    public void stopSocket() throws IOException {
        in.close();
        out.close();
        ServerSocket.close();
    }

    @Override
    public void sendMessage(Object message) throws IOException {
        out.writeObject(message);
    }

    @Override
    public void receiveMessage() throws IOException {

    }
}