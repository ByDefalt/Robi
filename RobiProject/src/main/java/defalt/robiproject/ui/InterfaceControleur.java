package defalt.robiproject.ui;

import defalt.robiproject.algo.*;
import defalt.robiproject.socket.Client;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.stage.FileChooser;
import javafx.scene.control.Alert.AlertType;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Base64;

import java.io.ByteArrayInputStream;
import java.util.Objects;

import javax.imageio.ImageIO;

/**
 * Contrôleur pour l'interface utilisateur.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class InterfaceControleur extends Client {
    @FXML
    private TextField entreeIp;

    @FXML
    private TextField entreePort;

    @FXML
    private Label labelEtatConnexion;

    @FXML
    private TextArea areaCommand;

    @FXML
    private TextArea entreeCommand;

    @FXML
    private CheckBox checkboxPas;

    @FXML
    private ImageView Images;

    @FXML
    private TreeView treeenvironement;
    @FXML
    private TreeView treesnode;

    @FXML
    private TextArea areaSNode;

    private boolean IsConnected=false;

    private Thread myThread;

    private int possition=0;

    /**
     * Méthode exécutée lorsqu'un utilisateur clique sur le bouton de connexion.
     * Cette méthode vérifie si les champs d'entrée pour l'adresse IP et le port ne sont pas vides.
     * Si les champs ne sont pas vides, elle tente d'établir une connexion en utilisant les informations fournies.
     * Si la connexion réussit, elle lance un thread pour recevoir les messages entrants.
     * Elle met à jour l'état de la connexion et affiche "Connecté" dans le label correspondant.
     * En cas d'erreur lors de la connexion, elle affiche "erreur de connexion" dans le label.
     * Si le port spécifié n'est pas un entier valide, elle affiche "port non valide".
     * Si l'un des champs d'entrée est vide, elle affiche "entrée non valide".
     */
    @FXML
    private void actionBoutonConnexion() {
        if(entreeIp.getText()!=null && !entreeIp.getText().isEmpty() && entreePort.getText()!=null && !entreePort.getText().isEmpty()){
            try {
                super.startSocket(entreeIp.getText(),Integer.parseInt(entreePort.getText()));
                Thread receiveThread=new Thread(this::receiveMessage);
                myThread=receiveThread;
                IsConnected=true;
                receiveThread.start();
                labelEtatConnexion.setText("Connecté");
            } catch (IOException e) {
                labelEtatConnexion.setText("erreur de connexion");
            } catch (NumberFormatException e) {
                showError("port non valide");
            }
        }else{
            showError("entree non valide");
        }
    }

    /**
     * Déclenche l'action de déconnexion lorsque le bouton de déconnexion est pressé.
     * Cette méthode vérifie d'abord si une connexion est établie avant de procéder à la déconnexion.
     * Si une connexion est active, elle la ferme en appelant la méthode stopSocket() héritée de la classe parente.
     * Si une IOException survient lors de la fermeture de la connexion, une erreur est affichée.
     */
    @FXML
    private void actionBoutonDeconnexion() {
        if(IsConnected){
            try {
                IsConnected=false;
                super.stopSocket();
            } catch (IOException e) {
                showError("erreur de Deconnexion");
            }
        }
    }

    /**
     * Méthode pour gérer l'action du bouton "Envoyer" lorsqu'il est activé.
     * Si une connexion est établie, le texte entré dans le champ d'entrée est ajouté à la zone de commande,
     * puis envoyé au serveur sous forme de commande JSON via une socket.
     * Une fois la commande envoyée, le champ d'entrée est effacé.
     * En cas d'erreur lors de l'envoi, une erreur est affichée.
     */
    @FXML
    private void actionBoutonEnvoyer() {
        if(IsConnected){
            try {
                areaCommand.appendText(entreeCommand.getText() + "\n\n");
                CommandeSocket commande = new CommandeSocket("envoyer","String" ,entreeCommand.getText());
                super.sendMessage(commande.Commande2Json());
                entreeCommand.clear();
            } catch (IOException e) {
                showError("erreur d'envoie");
            }
        }
    }

    /**
     * Méthode pour gérer l'action de clic sur le bouton d'ouverture de fichier.
     * Ouvre une boîte de dialogue permettant à l'utilisateur de sélectionner un fichier à ouvrir.
     * Si un fichier est sélectionné, son contenu est lu ligne par ligne et affiché dans un composant de texte.
     *
     * @implNote Cette méthode utilise JavaFX pour afficher une boîte de dialogue de sélection de fichier.
     * @implNote Le contenu du fichier sélectionné est lu à l'aide d'un BufferedReader pour une efficacité accrue.
     * @implNote Les erreurs liées à la lecture du fichier sont gérées et affichent un message d'erreur approprié.
     *
     * @see FileChooser
     * @see BufferedReader
     * @see FileReader
     * @see IOException
     */
    @FXML
    private void actionBoutonOpen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir un fichier à ouvrir");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                StringBuilder content = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                entreeCommand.setText(content.toString());
            } catch (IOException e) {
                showError("Erreur lors de la lecture du fichier");
            }
        }
    }

    /**
     * Cette méthode est appelée lorsque le bouton d'exécution est activé. Elle vérifie d'abord si une connexion est établie.
     * Si c'est le cas, elle crée une commande socket appropriée en fonction de l'état de la case à cocher "Pas" et envoie cette commande.
     * Si la case à cocher "Pas" est sélectionnée, la commande "executer_pas" est créée, sinon la commande "executer_block" est utilisée.
     * La position est également mise à jour en fonction de l'état de la case à cocher "Pas".
     */
    @FXML
    private void actionBoutonExecution() {
        if(IsConnected){
            try {
                CommandeSocket commande=(checkboxPas.isSelected() ? new CommandeSocket("executer_pas") : new CommandeSocket("executer_block"));
                possition=(checkboxPas.isSelected() ? 1 : 0);
                super.sendMessage(commande.Commande2Json());
            } catch (IOException e) {
                showError("erreur d'envoie");
            }
        }
    }

    /**
     * Cette méthode est appelée lorsque le bouton "Précédent" est déclenché.
     * Elle envoie une commande "precedent" via un socket si l'utilisateur est connecté.
     * Si la position est supérieure à 1, elle efface tous les enfants de l'élément racine.
     */
    @FXML
    private void actionBoutonPrecedent() {
        if(IsConnected){
            try {
                CommandeSocket commande=new CommandeSocket("precedent");
                if(possition>1){rootItem.getChildren().clear();}
                super.sendMessage(commande.Commande2Json());
            } catch (IOException e) {
                showError("erreur d'envoie");
            }
        }
    }

    /**
     * Méthode invoquée lorsqu'un utilisateur clique sur le bouton "Suivant".
     * Elle envoie une commande "suivant" via une socket si l'application est connectée.
     */
    @FXML
    private void actionBoutonSuivant() {
        if(IsConnected){
            try {
                CommandeSocket commande=new CommandeSocket("suivant");
                super.sendMessage(commande.Commande2Json());
            } catch (IOException e) {
                showError("erreur d'envoie");
            }
        }
    }

    /**
     * Méthode appelée lorsqu'un utilisateur clique sur le bouton de fermeture.
     * Si une connexion est établie, cette méthode interrompt le thread de communication,
     * ferme la connexion, et quitte l'application.
     */
    @FXML
    private void actionBoutonQuit() {
        if(IsConnected) {
            try {myThread.interrupt();
                IsConnected = false;
                super.stopSocket();
            } catch (IOException e) {
                showError("Erreur lors de la fermeture des services");
            }
        }
        Platform.exit();
    }

    /**
     * Méthode pour recevoir et traiter les messages provenant d'un socket.
     * Cette méthode est exécutée dans une boucle tant que le socket n'est pas fermé.
     * Les messages reçus sont lus sous forme d'objets et traités en fonction de leur type.
     * Les différentes actions à effectuer dépendent du contenu des messages reçus.
     * Les exceptions sont capturées et gérées de manière à afficher des messages appropriés à l'utilisateur.
     *
     * @throws EOFException Si le serveur ferme la connexion.
     * @throws SocketException Si une erreur se produit sur la connexion, par exemple si le client se déconnecte ou si le serveur est arrêté.
     * @throws IOException Si une erreur d'entrée/sortie survient pour d'autres raisons.
     * @throws ClassNotFoundException Si la classe de l'objet reçu n'a pas été trouvée.
     */
    @Override
    public final void receiveMessage() {
        while (!getSocket().isClosed()) {
            try {
                Object recv = getIn().readObject();
                    if(recv instanceof String){
                        CommandeSocket commande=new CommandeSocket("");
                        CommandeSocket mycommande = commande.Json2Commande((String) recv);
                        switch (mycommande.getName()){
                            case "EnvironementJson":
                                ArrayList<EnvironnementJSONFormat> env=(ArrayList<EnvironnementJSONFormat>) mycommande.getObject();
                                TreeConstructEnvironnement(env);
                                break;
                            case "SNodeJSON":
                                SNodeJSONFormat snode=(SNodeJSONFormat) mycommande.getObject();
                                TreeConstructSNodes(snode);
                                break;
                            case "ImageBase64":
                                String ImageBase64=(String) mycommande.getObject();
                                Base64ToImage(ImageBase64);
                                break;
                            case "Position":
                                possition = (Integer) mycommande.getObject();
                                break;
                            default:
                                break;
                        }
                    }
            }catch (EOFException e) {
                // Cette exception est levée lorsque le serveur ferme la connexion
                Platform.runLater(() -> {labelEtatConnexion.setText("Deconnexion server");});
                // Traiter la fermeture de la connexion du serveur
            } catch (SocketException e) {
                // Cette exception est levée lorsqu'une erreur se produit sur la connexion (par exemple, le client se déconnecte ou kill server)
                Platform.runLater(() -> {labelEtatConnexion.setText("Deconnexion");});
                // Traiter la fermeture de la connexion du client
            } catch (IOException e) {
                // Cette exception est levée pour d'autres erreurs d'entrée/sortie
                Platform.runLater(() -> {labelEtatConnexion.setText("Une erreur d'entrée/sortie s'est produite");});
                // Traiter l'erreur d'entrée/sortie
            } catch (ClassNotFoundException e) {
                // Cette exception est levée si la classe de l'objet reçu n'a pas été trouvée
                Platform.runLater(() -> {labelEtatConnexion.setText("Une erreur d'entrée s'est produite");});
                // Traiter l'erreur de classe non trouvée
            }
        }
    }

    /**
     * Convertit une chaîne encodée en Base64 représentant une image en une Image JavaFX et l'affiche.
     *
     * @param base64 Une chaîne encodée en Base64 représentant l'image.
     * @throws IllegalArgumentException Si la chaîne Base64 fournie est vide ou nulle.
     * @throws IllegalStateException    Si une erreur survient lors du processus de conversion ou si l'image ne peut pas être affichée.
     */
    private void Base64ToImage(String base64){
        byte[] imageBytes = Base64.getDecoder().decode(base64);
        if (imageBytes.length == 0) {
            showError("Erreur lors de la reception du message");
        } else {
            try (ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes)) {
                BufferedImage bufferedImage = ImageIO.read(bis);

                if (bufferedImage == null) {
                    showError("Erreur lors de la reception du message");
                } else {
                    // Convertir BufferedImage en Image de JavaFX
                    Image fxImage = SwingFXUtils.toFXImage(bufferedImage, null);
                    // Créer un ImageView et l'ajouter à une scène
                    Platform.runLater(()->{
                        Images.setImage(fxImage);
                    });
                }
            } catch (IOException e) {
                showError("Erreur lors de la reception du message");
            }
        }
    }

    /**
	 * Affiche une boîte de dialogue d'erreur avec le message spécifié.
	 * 
	 * @param message Le message d'erreur à afficher.
	 */
    private void showError(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.setResizable(true);
            alert.showAndWait();
        });
    }
    /**
     * Construit un arbre d'environnement à partir d'une liste d'objets au format JSON représentant les environnements.
     *
     * @param environnement La liste d'objets au format JSON représentant les environnements.
     */
    public void TreeConstructEnvironnement(ArrayList<EnvironnementJSONFormat> environnement){
        if(environnement!=null) {
            TreeItem<String> rootItem = new TreeItem<>("Root");
            for (EnvironnementJSONFormat env : environnement) {
                TreeItem<String> newItem = new TreeItem<>(env.getName());
                rootItem.getChildren().add(newItem);
                TreeAddChildrenEnvironnement(env, newItem);
            }
            Platform.runLater(() -> {
                treeenvironement.setRoot(rootItem);
                treeenvironement.getRoot().setExpanded(true);
            });
        }
    }

    /**
     * Ajoute les enfants de l'environnement spécifié à l'arborescence représentée par l'élément d'arbre donné.
     *
     * @param environement L'environnement au format JSON contenant les enfants à ajouter.
     * @param Item L'élément d'arbre auquel les enfants seront ajoutés.
     */
    public final void TreeAddChildrenEnvironnement(EnvironnementJSONFormat environement, TreeItem<String> Item){
        for(EnvironnementJSONFormat env :environement.getChildren()){
            TreeItem<String> newItem = new TreeItem<>(env.getName());
            Item.getChildren().add(newItem);
            TreeAddChildrenEnvironnement(env,newItem);
        }
    }



    TreeItem<String> rootItem = new TreeItem<>("Root");

    /**
     * Construit un arbre de nœuds à partir des données fournies au format JSON pour les nœuds S.
     *
     * @param sNodeJSONFormat Les données au format JSON représentant le nœud S à utiliser pour construire l'arbre.
     */
    public void TreeConstructSNodes(SNodeJSONFormat sNodeJSONFormat){
        if(sNodeJSONFormat!=null) {
            TreeItem<String> newItem = new TreeItem<>(sNodeJSONFormat.getCommandeChildren());
            rootItem.getChildren().add(newItem);
            TreeAddChildrenSNodes(sNodeJSONFormat,newItem);
            Platform.runLater(() -> {
                //rootItem.getChildren().clear();
                treesnode.setRoot(rootItem);
                treesnode.getRoot().setExpanded(true);
            });
        }
    }
    /**
     * Ajoute récursivement les enfants du nœud S spécifié à l'élément d'arbre donné.
     *
     * @param sNodeJSONFormat Les données au format JSON représentant le nœud S dont les enfants doivent être ajoutés.
     * @param Item L'élément d'arbre auquel les enfants doivent être ajoutés.
     */
    public final void TreeAddChildrenSNodes(SNodeJSONFormat sNodeJSONFormat, TreeItem<String> Item){
        if(!sNodeJSONFormat.getChildren().isEmpty()) {
            int a = 1;
            for (SNodeJSONFormat sNodeJSONFormat2 : sNodeJSONFormat.getChildren()) {
                if (Objects.equals(sNodeJSONFormat2.getCommandname(), "null")) {
                    TreeItem<String> newItem2 = new TreeItem<>("\""+(a++) + "\" " + sNodeJSONFormat2.getCommandeChildren());
                    Item.getChildren().add(newItem2);
                    TreeAddChildrenSNodes(sNodeJSONFormat2,newItem2);
                }
            }
        }
    }

    /**
     * Arrête le thread et ferme la connexion.
     * Cette méthode arrête le thread associé à la connexion en cours et ferme la connexion réseau.
     *
     * @throws IOException si une erreur survient lors de la fermeture de la connexion
     */
    public final void stopThreadAndConnection() {
            try {
                super.stopSocket();
                myThread.interrupt();
                IsConnected = false;
            } catch (IOException e) {
                showError("Erreur lors de la fermeture de la connexion");
            }
    }
    
	/**
	 * Initialise le contrôleur.
	 */
    public final void initialize() {
        areaCommand.setEditable(false);
    }
}
