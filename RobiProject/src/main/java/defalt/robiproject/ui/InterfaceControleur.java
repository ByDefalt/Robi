package defalt.robiproject.ui;

import com.google.gson.GsonBuilder;
import defalt.robiproject.algo.CommandeSocketTypeAdapter;
import defalt.robiproject.algo.Reponse;
import defalt.robiproject.parser.SNode;
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
import java.util.Base64;

import java.io.ByteArrayInputStream;
import java.util.List;


import com.google.gson.Gson;

import defalt.robiproject.algo.ClientRobi;
import defalt.robiproject.algo.CommandeSocket;

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
    private TextArea areaEnvironment;

    @FXML
    private TextArea areaSNode;

    private boolean IsConnected=false;

    private Thread myThread;

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
                // Créer l'instance Gson en utilisant un GsonBuilder
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(CommandeSocket.class, new CommandeSocketTypeAdapter()) // Enregistrer l'adaptateur de type
                        .create();
                // Créer l'objet CommandeSocket
                CommandeSocket commande = new CommandeSocket("envoyer", entreeCommand.getText());

                // Convertir l'objet CommandeSocket en JSON en utilisant Gson avec l'adaptateur de type personnalisé
                String json = gson.toJson(commande);

                // Envoyer le JSON
                super.sendMessage(json);
                entreeCommand.setText("");
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
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(CommandeSocket.class, new CommandeSocketTypeAdapter()) // Enregistrer l'adaptateur de type
                        .create();
                String json = gson.toJson(commande);
                areaSNode.clear();
                areaEnvironment.clear();
                super.sendMessage(json);
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
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(CommandeSocket.class, new CommandeSocketTypeAdapter()) // Enregistrer l'adaptateur de type
                        .create();
                String json = gson.toJson(commande);
                super.sendMessage(json);
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
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(CommandeSocket.class, new CommandeSocketTypeAdapter()) // Enregistrer l'adaptateur de type
                        .create();
                String json = gson.toJson(commande);
                super.sendMessage(json);
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
                if (recv != null) {
                    if (recv instanceof String) {
                        String recvString = (String) recv;
                        byte[] imageBytes = Base64.getDecoder().decode(recvString);
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
                                    Images.setImage(null);
                                    // Créer un ImageView et l'ajouter à une scène
                                    Images.setImage(fxImage);
                                }
                            } catch (IOException e) {
                                showError("Erreur lors de la reception du message");
                            }
                        }
                    }
                    if (recv instanceof Reponse) {
                        this.setEnvironmentsSNodes((Reponse) recv);
                    }
                }
            } catch (EOFException e) {
                // Cette exception est levée lorsque le serveur ferme la connexion
                Platform.runLater(() -> {labelEtatConnexion.setText("Deconnexion server");});
                // Traiter la fermeture de la connexion du serveur
            } catch (SocketException e) {
                // Cette exception est levée lorsqu'une erreur se produit sur la connexion (par exemple, le client se déconnecte ou kill server)
                Platform.runLater(() -> {labelEtatConnexion.setText("Deconnexion");});
                // Traiter la fermeture de la connexion du client
            } catch (IOException e) {
                // Cette exception est levée pour d'autres erreurs d'entrée/sortie
                System.out.println("Une erreur d'entrée/sortie s'est produite : " + e.getMessage());
                // Traiter l'erreur d'entrée/sortie
            } catch (ClassNotFoundException e) {
                // Cette exception est levée si la classe de l'objet reçu n'a pas été trouvée
                System.out.println("Classe non trouvée lors de la réception des données : " + e.getMessage());
                // Traiter l'erreur de classe non trouvée
            }
        }
    }

    private void setEnvironmentsSNodes(Reponse reponse) {
        this.areaEnvironment.clear();
        for(String text : reponse.getEnvironment()) {
            this.areaEnvironment.appendText(text + "\n");
        }

        areaSNode.appendText(reponse.getSNode());
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
        areaEnvironment.setEditable(false);
        areaSNode.setEditable(false);
    }
}
