package defalt.robiproject.algo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import defalt.robiproject.graphicLayer.*;
import defalt.robiproject.parser.SNode;
import defalt.robiproject.parser.SParser;
import defalt.robiproject.socket.Server;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

public class ServerRobi extends Server {

    private Thread myThread;
    private boolean connection = false;
    private Socket clientSocket;
    private String code;

    private SParser<SNode> parser = new SParser<>();
    private List<SNode> compiled;
    private GSpace space;
    private Environment environment = new Environment();
    private Iterator<SNode> itor;
    private int position=0;

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

    public ServerRobi(){
        space = new GSpace("Exercice 5", new Dimension(800, 500));
        space.open();
        Reference spaceRef = new Reference(space);
        Reference rectClassRef = new Reference(GRect.class);
        Reference ovalClassRef = new Reference(GOval.class);
        Reference imageClassRef = new Reference(GImage.class);
        Reference stringClassRef = new Reference(GString.class);

        spaceRef.addCommand("setColor", new SetColor());
        spaceRef.addCommand("sleep", new SleepCommand());

        spaceRef.addCommand("add", new AddElement(environment));
        spaceRef.addCommand("del", new DelElement(environment));

        spaceRef.addCommand("setDim", new SetDim());
        spaceRef.addCommand("translate", new Translate());

        rectClassRef.addCommand("new", new NewElement(environment));
        ovalClassRef.addCommand("new", new NewElement(environment));
        imageClassRef.addCommand("new", new NewImage());
        stringClassRef.addCommand("new", new NewString());

        environment.addReference("space", spaceRef);
        environment.addReference("rect.class", rectClassRef);
        environment.addReference("oval.class", ovalClassRef);
        environment.addReference("image.class", imageClassRef);
        environment.addReference("label.class", stringClassRef);
    }
    @Override
    public final void receiveMessage() throws IOException {
        while(true){
            if(this.clientSocket.isClosed()){
                this.setClientSocket(this.accept());
            }else{
                try {
                    Object recv = getIn().readObject();
                    if (recv instanceof String) {
                        // Créer l'instance Gson en utilisant un GsonBuilder
                        Gson gson = new GsonBuilder()
                                .registerTypeAdapter(CommandeSocket.class, new CommandeSocketTypeAdapter()) // Enregistrer l'adaptateur de type
                                .create();

                        // Désérialiser le JSON en un objet CommandeSocket en utilisant Gson avec l'adaptateur de type personnalisé
                        CommandeSocket commande = gson.fromJson((String) recv, CommandeSocket.class);

                        switch (commande.getName()){
                            case "envoyer":
                                code= commande.getCode();
                                compiled = parser.parse(code);
                                break;
                            case "executer_pas":
                                itor = compiled.iterator();
                                if (itor.hasNext()) {
                                    new Interpreter().compute(environment, itor.next());
                                    position=1;
                                }
                                System.out.println(position);
                                break;
                            case "executer_block":
                                itor = compiled.iterator();
                                while (itor.hasNext()) {
                                    new Interpreter().compute(environment, itor.next());
                                }
                                BufferedImage image = new BufferedImage(space.getWidth(), space.getHeight(), BufferedImage.TYPE_INT_ARGB);
                                Graphics2D g2d = image.createGraphics();
                                space.paint(g2d);
                                g2d.dispose();
                                String base64Image = null;
                                try {
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    ImageIO.write(image, "png", baos);
                                    byte[] imageBytes = baos.toByteArray();
                                    base64Image = Base64.getEncoder().encodeToString(imageBytes);
                                    super.sendMessage(base64Image);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case "precedent":
                                if(position>1){
                                    space.clear();
                                    space.changeWindowSize(new Dimension(800,500));
                                    itor = compiled.iterator();
                                    position--;
                                    System.out.println(position);
                                    for(int i=1;i<=position;i++){
                                        if (itor.hasNext()) {
                                            new Interpreter().compute(environment, itor.next());
                                        }
                                    }
                                }
                                break;
                            case "suivant":
                                if (itor.hasNext()) {
                                    new Interpreter().compute(environment, itor.next());
                                    position++;
                                }
                                System.out.println(position);
                                break;
                            default:
                                System.out.println("commande name undefined");
                                break;
                        }

                    }
                } catch (Exception e) {
                    System.out.println(this.clientSocket);
                    if(!this.clientSocket.isClosed()){
                        System.out.println("l");
                    }
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


    public GSpace getSpace() {
        return space;
    }
}
