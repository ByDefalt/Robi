package defalt.robiproject.ui;

import defalt.robiproject.algo.*;
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
import java.util.Arrays;
import java.util.Base64;

import java.io.ByteArrayInputStream;
import java.util.Objects;

import javax.imageio.ImageIO;

public class InterfaceControleur extends ClientRobi{
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

    @FXML
    private void actionBoutonEnvoyer() {
        if(IsConnected){
            try {
                areaCommand.appendText(entreeCommand.getText() + "\n\n");
                CommandeSocket commande = new CommandeSocket("envoyer","String" ,entreeCommand.getText());
                System.out.println(commande.Commande2Json());
                super.sendMessage(commande.Commande2Json());
                entreeCommand.clear();
            } catch (IOException e) {
                showError("erreur d'envoie");
            }
        }
    }

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

    @FXML
    private void actionBoutonQuit() {
        if(IsConnected) {
            try {myThread.interrupt();
                IsConnected = false;
                super.stopSocket();
                Platform.exit();
            } catch (IOException e) {
                showError("Erreur lors de la fermeture des services");
            }
        }
    }
    @Override
    public final void receiveMessage() {
        while (!getSocket().isClosed()) {
            try {
                Object recv = getIn().readObject();
                    if(recv instanceof String){
                        CommandeSocket commande=new CommandeSocket("");
                        System.out.println((String) recv);
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
    private void showError(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.setResizable(true);
            alert.showAndWait();
        });
    }
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

    public final void TreeAddChildrenEnvironnement(EnvironnementJSONFormat environement, TreeItem<String> Item){
        for(EnvironnementJSONFormat env :environement.getChildren()){
            TreeItem<String> newItem = new TreeItem<>(env.getName());
            Item.getChildren().add(newItem);
            TreeAddChildrenEnvironnement(env,newItem);
        }
    }



    TreeItem<String> rootItem = new TreeItem<>("Root");

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
    public final void stopThreadAndConnection() {
            try {
                super.stopSocket();
                myThread.interrupt();
                IsConnected = false;
            } catch (IOException e) {
                showError("Erreur lors de la fermeture de la connexion");
            }
    }

    public final void initialize() {
        areaCommand.setEditable(false);
    }
}
