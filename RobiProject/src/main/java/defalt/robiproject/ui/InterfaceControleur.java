
package defalt.robiproject.ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.stage.FileChooser;
import javafx.scene.control.Alert.AlertType;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.io.ByteArrayInputStream;

import defalt.robiproject.algo.ClientRobi;

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
                showError("port non valide", AlertType.ERROR);
            }
        }else{
            showError("entree non valide", AlertType.ERROR);
        }
    }
    @FXML
    private void actionBoutonDeconnexion() {
        if(IsConnected){
            try {
                IsConnected=false;
                super.stopSocket();
                myThread.interrupt();
                labelEtatConnexion.setText("Déconnecté");
            } catch (IOException e) {
                showError("erreur de Deconnexion", AlertType.ERROR);
            }
        }
    }

    @FXML
    private void actionBoutonEnvoyer() {
        if(IsConnected){
            try {
                areaCommand.appendText(entreeCommand.getText() + "\n\n");
                super.sendMessage(entreeCommand.getText());
                entreeCommand.setText("");
            } catch (IOException e) {
                showError("erreur d'envoie", AlertType.ERROR);
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
                showError("Erreur lors de la lecture du fichier", AlertType.ERROR);
            }
        }
    }
    @FXML
    private void actionBoutonExecution() {
        // Méthode associée à l'action du bouton Exécuter
    }

    @FXML
    private void actionBoutonPrecedent() {
        // Méthode associée à l'action du bouton Précédent
    }

    @FXML
    private void actionBoutonSuivant() {
        // Méthode associée à l'action du bouton Suivant
    }

    @FXML
    private void actionBoutonQuit() {
        if(IsConnected) {
            try {myThread.interrupt();
                IsConnected = false;
                super.stopSocket();
                Platform.exit();
            } catch (IOException e) {
                showError("Erreur lors de la fermeture des services", AlertType.ERROR);
            }
        }
    }

    @Override
    public void receiveMessage() {
        while (!getSocket().isClosed()) {
            try {
                Object recv = getIn().readObject();
                if (recv instanceof String) {
                    String recvString = (String) recv;
                    byte[] imageBytes = Base64.getDecoder().decode(recvString);
                    if (imageBytes.length == 0) {
                        showError("Erreur lors de la reception du message", AlertType.ERROR);
                    } else {
                        try (ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes)) {
                            BufferedImage bufferedImage = ImageIO.read(bis);

                            if (bufferedImage == null) {
                                showError("Erreur lors de la reception du message", AlertType.ERROR);
                            }

                            // Convertir BufferedImage en Image de JavaFX
                            Image fxImage = convertToJavaFXImage(bufferedImage);

                            // Créer un ImageView et l'ajouter à une scène
                            Images.setImage(fxImage);
                        } catch (IOException e) {
                            showError("Erreur lors de la reception du message", AlertType.ERROR);
                        }
                    }
                }
            }catch (ClassNotFoundException | IOException e) {
                if(!getSocket().isClosed()) {
                    // Gérer ClassNotFoundException
                    System.out.println("Classe non trouvée lors de la réception des données : " + e.getMessage());
                }
            }
        }
    }

    // Fonction de conversion BufferedImage en Image de JavaFX
    private Image convertToJavaFXImage(BufferedImage bufferedImage) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        WritableImage javafxImage = new WritableImage(width, height);
        PixelWriter pixelWriter = javafxImage.getPixelWriter();
        int[] pixelData = bufferedImage.getRGB(0, 0, width, height, null, 0, width);
        ByteBuffer byteBuffer = ByteBuffer.allocate(4 * width * height);
        for (int argb : pixelData) {
            byteBuffer.putInt(argb);
        }
        byteBuffer.flip();
        pixelWriter.setPixels(0, 0, width, height, PixelFormat.getByteBgraInstance(), byteBuffer, width * 4);
        return javafxImage;
    }

    private void showError(String message, AlertType type) {
        Platform.runLater(() -> {
            Alert alert = new Alert(type);
            if (type == AlertType.ERROR) {
                alert.setTitle("Erreur");
            } else {
                alert.setTitle("Information");
            }
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.setResizable(true);
            alert.showAndWait();
        });
    }

    public void initialize() {
        areaCommand.setEditable(false);
    }
}
