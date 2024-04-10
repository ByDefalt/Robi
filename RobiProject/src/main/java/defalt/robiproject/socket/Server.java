package defalt.robiproject.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Cette classe implémente l'interface pour le serveur et gère les opérations
 * liées à la communication avec les clients.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class Server implements SocketInterface {
	private ServerSocket serverSocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	/**
	 * Retourne le flux de sortie de l'objet serveur.
	 * 
	 * @return le flux de sortie de l'objet serveur
	 */
	public ObjectInputStream getIn() {
		return in;
	}

	/**
	 * Lance le serveur en initialisant le socket serveur.
	 * 
	 * @param serverAddress l'adresse IP du serveur (pas utilisée dans le contexte
	 *                      du serveur)
	 * @param port          le port d'écoute du serveur
	 * @throws IOException si une erreur d'entrée/sortie survient lors de la
	 *                     création du socket serveur
	 */
	@Override
	public void startSocket(String serverAddress, int port) throws IOException {
		serverSocket = new ServerSocket(port);
	}

	/**
	 * Attend la connexion d'un client et retourne le socket client.
	 * 
	 * @return le socket client
	 * @throws IOException si une erreur d'entrée/sortie survient lors de
	 *                     l'acceptation de la connexion client
	 */
	public Socket accept() throws IOException {
		Socket clientSocket = this.serverSocket.accept();
		out = new ObjectOutputStream(clientSocket.getOutputStream());
		in = new ObjectInputStream(clientSocket.getInputStream());
		System.out.println(clientSocket.toString());
		return clientSocket;
	}

	/**
	 * Arrête le serveur en fermant le socket serveur et les flux de communication.
	 * 
	 * @throws IOException si une erreur d'entrée/sortie survient lors de la
	 *                     fermeture des flux ou du socket serveur
	 */
	@Override
	public void stopSocket() throws IOException {
		in.close();
		out.close();
		serverSocket.close();
	}

	/**
	 * Envoie un message au client via le flux de sortie.
	 * 
	 * @param message le message à envoyer
	 * @throws IOException si une erreur d'entrée/sortie survient lors de l'envoi du
	 *                     message
	 */
	@Override
	public void sendMessage(Object message) throws IOException {
		out.writeObject(message);
	}

	/**
	 * Méthode vide pour la réception de messages côté serveur. Cette méthode peut
	 * être redéfinie pour inclure le traitement des messages reçus.
	 * 
	 * @throws IOException si une erreur d'entrée/sortie survient lors de la
	 *                     réception de messages
	 */
	@Override
	public void receiveMessage() throws IOException {

	}
}
