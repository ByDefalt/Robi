package defalt.robiproject.algo;
import defalt.robiproject.socket.Server;

import java.io.IOException;

public class ServerRobi extends Server {



    @Override
    public void receiveMessage() throws IOException {

    }

    public static void main(String[] args) {
        if(args[0].isEmpty()){
            System.out.println("java ServerRobi port");
        }
        ServerRobi serverRobi=new ServerRobi();
        try {
            serverRobi.startSocket("localhost",Integer.parseInt(args[0]));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
