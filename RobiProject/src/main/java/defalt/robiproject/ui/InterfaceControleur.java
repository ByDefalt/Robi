
package defalt.robiproject.ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.stage.FileChooser;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
    private Button boutonConnexion;

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
                receiveThread.start();
                IsConnected=true;
                labelEtatConnexion.setText("Connecté");
            } catch (IOException e) {
                labelEtatConnexion.setText("erreur de connexion");
            } catch (NumberFormatException e) {
                showError("port non valide", Alert.AlertType.ERROR);
            }
        }else{
            showError("entree non valide", Alert.AlertType.ERROR);
        }
    }
    @FXML
    private void actionBoutonDeconnexion() {
        if(IsConnected){
            myThread.interrupt();
            IsConnected=false;
            super.stopSocket();
            Platform.exit();
        }
    }

    @FXML
    private void actionBoutonEnvoyer() {
        if(IsConnected){
            areaCommand.appendText(entreeCommand.getText() + "\n\n");
            super.sendMessage(entreeCommand.getText());
            entreeCommand.setText("");
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
                showError("Erreur lors de la lecture du fichier", Alert.AlertType.ERROR);
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
            myThread.interrupt();
            IsConnected = false;
            super.stopSocket();
        }
        Platform.exit();
    }

    @Override
    public void receiveMessage() {
        while (true) {
            try {
                Object recv = getIn().readObject();
                if (recv instanceof String) {
                    String recvString = (String) recv;
                    byte[] imageBytes = Base64.getDecoder().decode(recvString);
                    if (imageBytes.length == 0) {
                        System.out.println("La chaîne Base64 est vide.");
                    } else {
                        try (ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes)) {
                            BufferedImage bufferedImage = ImageIO.read(bis);

                            if (bufferedImage == null) {
                                System.out.println("La chaîne Base64 ne représente pas une image valide.");
                                return;
                            }

                            // Convertir BufferedImage en Image de JavaFX
                            Image fxImage = convertToJavaFXImage(bufferedImage);

                            // Créer un ImageView et l'ajouter à une scène
                            Images.setImage(fxImage);
                        } catch (IOException e) {
                            System.out.println("Une erreur s'est produite lors de la lecture de l'image : " + e.getMessage());
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
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

    private void showError(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        if (type == Alert.AlertType.ERROR) {
            alert.setTitle("Erreur");
        } else {
            alert.setTitle("Information");
        }
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setResizable(true);
        alert.showAndWait();
    }
    public void initialize() {
        areaCommand.setEditable(false);
    }
}
