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
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.List;

public class ServerRobi extends Server {

    private Thread myThread;
    private boolean connection = false;
    private Socket clientSocket;
    private String code;

    private final SParser<SNode> parser = new SParser<>();
    private List<SNode> compiled;
    private final GSpace space;
    private final Environment environment = new Environment();
    private Iterator<SNode> itor;
    private Integer position=-1;

    private final EnvironnementJSONFormat spacejson;

    private final List<EnvironnementJSONFormat> listenv=new ArrayList<>();

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
        //space.open();
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
        spacejson=new EnvironnementJSONFormat("space");
        listenv.add(spacejson);
        listenv.add(new EnvironnementJSONFormat("rect.class"));
        listenv.add(new EnvironnementJSONFormat("oval.class"));
        listenv.add(new EnvironnementJSONFormat("image.class"));
        listenv.add(new EnvironnementJSONFormat("label.class"));
    }
    @Override
    public final void receiveMessage() throws IOException {
        while(true){
            if(this.clientSocket.isClosed()){
                this.setClientSocket(this.accept());
            }
            try {
                Object recv = getIn().readObject();
                if(recv instanceof String){
                    CommandeSocket commande=new CommandeSocket("");
                    CommandeSocket mycommande = commande.Json2Commande((String) recv);
                    if (mycommande.getName().equals("envoyer")) {
                        code = (String) mycommande.getObject();
                        compiled = parser.parse(code);
                    }else{
                        switch (mycommande.getName()) {
                            case "executer_pas":
                                itor = compiled.iterator();
                                if (itor.hasNext()) {
                                    new Interpreter().compute(environment, itor.next());
                                    position = 1;
                                }
                                break;
                            case "executer_block":
                                itor = compiled.iterator();
                                while (itor.hasNext()) {
                                    new Interpreter().compute(environment, itor.next());
                                }
                                break;
                            case "precedent":
                                if (position > 1) {
                                    space.clear();
                                    space.changeWindowSize(new Dimension(800, 500));
                                    itor = compiled.iterator();
                                    position--;
                                    for (int i = 1; i <= position; i++) {
                                        if (itor.hasNext()) {
                                            new Interpreter().compute(environment, itor.next());
                                        }
                                    }
                                }
                                break;
                            case "suivant":
                                if(position!=0) {
                                    if (itor.hasNext()) {
                                        new Interpreter().compute(environment, itor.next());
                                        position++;
                                    }
                                }
                                break;

                                default:
                                    break;
                        }
                        createAndSendPosition();
                        createAndSendEnvironement();
                        createAndSendImage();
                    }}
                }catch (EOFException e){
                    try {
                        this.clientSocket.close(); // Fermeture de la socket côté serveur
                    } catch (IOException ex) {
                        ex.printStackTrace(); // Gérer les erreurs de fermeture de la socket
                    }
                }catch(ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
        }
    }
    public void createAndSendImage(){
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
            CommandeSocket commande=new CommandeSocket("ImageBase64",base64Image);
            super.sendMessage(commande.Commande2Json());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void createAndSendEnvironement() throws IOException {
        for (Map.Entry<String, Reference> m : environment.getVariables().entrySet()) {
            if(!m.getKey().equals("space") && !m.getKey().equals("rect.class") && !m.getKey().equals("oval.class") && !m.getKey().equals("image.class") && !m.getKey().equals("label.class")){
                spacejson.add(m.getKey());
            }
        }
        CommandeSocket commande=new CommandeSocket("EnvironementJson",listenv);
        super.sendMessage(commande.Commande2Json());
    }
    public void createAndSendPosition() throws IOException {
        CommandeSocket commande=new CommandeSocket("Position",position);
        super.sendMessage(commande.Commande2Json());
    }
    public static void main(String[] args) {
        /*
        if (args.length == 0) {
            System.out.println("Usage: java ServerRobi port");
            return;
        }
        */

        ServerRobi serverRobi = new ServerRobi();
        /*
        EnvironnementJSONFormat space = new EnvironnementJSONFormat("space");
        space.add("space.robi");
        space.add("space.ibor");
        space.add("space.robi.jsp1");
        space.add("space.jsp1.jsp2");
        EnvironnementJSONFormat space2 = new EnvironnementJSONFormat("space");
        space2.add("space.robi");
        space2.add("space.ibor");
        space2.add("space.robi.jsp1");
        space2.add("space.jsp1.jsp2");
        List<EnvironnementJSONFormat> list=new ArrayList<>();
        list.add(space);
        list.add(space2);
        CommandeSocket commande=new CommandeSocket("jsp",list);
        System.out.println(commande.getObject().toString());
        System.out.println(commande.Commande2Json());

         */

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
