package defalt.robiproject.socket;

import java.io.IOException;

/**
 * Interface commune pour les clients et le serveur. Cette interface définit les
 * méthodes nécessaires pour démarrer, arrêter, envoyer et recevoir des
 * messages.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public interface SocketInterface {
	/**
	 * Méthode pour démarrer le serveur ou le client.
	 * 
	 * @param serverAddress l'adresse IP du serveur (non utilisée dans le contexte
	 *                      du client)
	 * @param port          le port d'écoute du serveur ou le port de connexion du
	 *                      client
	 * @throws IOException si une erreur d'entrée/sortie survient lors du démarrage
	 */
	void startSocket(String serverAddress, int port) throws IOException;

	/**
	 * Méthode pour arrêter le serveur ou le client.
	 * 
	 * @throws IOException si une erreur d'entrée/sortie survient lors de l'arrêt
	 */
	void stopSocket() throws IOException;

	/**
	 * Méthode pour envoyer un message.
	 * 
	 * @param message le message à envoyer
	 * @throws IOException si une erreur d'entrée/sortie survient lors de l'envoi du
	 *                     message
	 */
	void sendMessage(Object message) throws IOException;

	/**
	 * Méthode pour recevoir un message.
	 * 
	 * @throws IOException si une erreur d'entrée/sortie survient lors de la
	 *                     réception du message
	 */
	void receiveMessage() throws IOException;
}
