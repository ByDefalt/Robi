package defalt.robiproject.algo;
import com.google.gson.Gson;
import defalt.robiproject.parser.SNode;
import defalt.robiproject.parser.SParser;
import defalt.robiproject.socket.Server;

import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;

public class ServerRobi extends Server {

    private Thread myThread;
    private boolean connection = false;
    private Socket clientSocket;
    private String code;

    private SParser<SNode> parser = new SParser<>();
    private List<SNode> compiled;

    Environment environment = new Environment();
    public final boolean isConnected() {
        return connection;
    }

    public final void setConnection(boolean connection) {
        this.connection = connection;
    }

    public final Thread getMyThread() {
        return myThread;
    }

    public final void setMyThread(Thread myThread) {
        this.myThread = myThread;
    }
    public final Socket getClientSocket() {
        return clientSocket;
    }

    public final void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    @Override
    public final void receiveMessage() throws IOException {
        while(true){
            if(!this.clientSocket.isClosed()){
                this.setClientSocket(this.accept());
            }else{
                try {
                    Object recv = getIn().readObject();
                    if (recv instanceof String) {
                        Gson gson = new Gson();
                        CommandeSocket commande = gson.fromJson((String) recv, CommandeSocket.class);
                        switch (commande.getName()){
                            case "envoyer":
                                code= commande.getCode();
                                compiled = parser.parse(code);
                                break;
                            case "executer_pas":
                                break;
                            case "executer_block":
                                Iterator<SNode> itor = compiled.iterator();
                                while (itor.hasNext()) {
                                    new Interpreter().compute(environment, itor.next());
                                }
                                break;
                            case "precedent":
                                break;
                            case "suivant":
                                break;
                            default:
                                System.out.println("commande name undefined");
                                break;
                        }
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
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
            e.printStackTrace();
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
