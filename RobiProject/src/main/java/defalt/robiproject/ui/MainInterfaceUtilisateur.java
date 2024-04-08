package defalt.robiproject.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainInterfaceUtilisateur extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("interface.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("FXML Example");
        primaryStage.setScene(new Scene(root, 900, 425));

        // Récupérer une référence au contrôleur après le chargement du fichier FXML
        InterfaceControleur controller = loader.getController();

        // Ajout d'un gestionnaire pour l'événement de fermeture de la fenêtre principale
        primaryStage.setOnCloseRequest(event -> {
            // Appeler la méthode du contrôleur pour arrêter le thread et la connexion
            controller.stopThreadAndConnection();
        });

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
