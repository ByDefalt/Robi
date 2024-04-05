
package defalt.robiproject.ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class InterfaceControleur {

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

    @FXML
    private void actionBoutonConnexion() {
        System.out.println("Bouton Connexion cliqué ! : " + entreeIp.getText() + "   " + entreePort.getText());

    }

    @FXML
    private void actionBoutonEnvoyer() {
        areaCommand.appendText(entreeCommand.getText() + "\n\n");
        entreeCommand.setText("");
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
                areaCommand.appendText(content.toString());
            } catch (IOException e) {
                System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
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
        Platform.exit();
    }
}
