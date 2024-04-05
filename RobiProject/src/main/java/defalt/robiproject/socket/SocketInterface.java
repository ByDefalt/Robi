package socket;


// Interface commune pour les clients et le serveur
public interface SocketInterface {
    void start(); // Méthode pour démarrer le serveur ou le client
    void stop();  // Méthode pour arrêter le serveur ou le client
    void sendMessage(String message); // Méthode pour envoyer un message
    String receiveMessage(); // Méthode pour recevoir un message
}