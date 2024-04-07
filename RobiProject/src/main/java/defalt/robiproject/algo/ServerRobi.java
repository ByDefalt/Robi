package defalt.robiproject.algo;
import defalt.robiproject.socket.Server;

import java.io.IOException;
import java.net.Socket;

public class ServerRobi extends Server {

    private Thread myThread;
    private boolean connection = false;
    private Socket clientSocket;


    public boolean isConnection() {
        return connection;
    }

    public void setConnection(boolean connection) {
        this.connection = connection;
    }

    public Thread getMyThread() {
        return myThread;
    }

    public void setMyThread(Thread myThread) {
        this.myThread = myThread;
    }
    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    @Override
    public void receiveMessage() throws IOException {
        // Implémentez la logique pour recevoir et traiter les messages ici
        while(true){
            if(!this.clientSocket.isClosed()){
                this.setClientSocket(this.accept());
            }else{

            }

        }
    }

    public static void main(String[] args) {
        /*
        if (args.length == 0) {
            System.out.println("Usage: java ServerRobi port");
            return;
        }
        */
        ServerRobi serverRobi = new ServerRobi();
        try {
            serverRobi.startSocket("localhost", Integer.parseInt("5555"));
            serverRobi.setClientSocket(serverRobi.accept());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Thread receiveThread = new Thread(() -> {
            try {
                serverRobi.receiveMessage();
            } catch (IOException e) {
                e.printStackTrace(); // Gérer les erreurs de réception
            }
        });

        serverRobi.setMyThread(receiveThread);
        serverRobi.setConnection(true);
        receiveThread.start();
    }


}
