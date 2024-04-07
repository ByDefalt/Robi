package defalt.robiproject.socket;


import java.io.IOException;

// Interface commune pour les clients et le serveur
public interface SocketInterface{
    void startSocket(String serverAddress,int port) throws IOException; // Méthode pour démarrer le serveur ou le client
    void stopSocket();  // Méthode pour arrêter le serveur ou le client
    void sendMessage(Object message); // Méthode pour envoyer un message
    void receiveMessage(); // Méthode pour recevoir un message
}