package defalt.robiproject.algo;
import defalt.robiproject.socket.Server;


public class ServerRobi extends Server {

    public ServerRobi(){
        super();
    }
    
    public void interpreter(String comand){
        //fzfezfezf
        super.sendMessage(comand);
    }

    public static void main(String[] args) {
        System.out.println("df");
    }
}
