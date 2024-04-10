package defalt.robiproject.socket;

import java.io.*;
import java.net.Socket;

/**
 * Cette classe implémente l'interface pour le client et gère les opérations
 * liées à la communication avec le serveur.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class Client extends Thread implements SocketInterface {
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	/**
	 * Retourne le flux de sortie de l'objet client.
	 * 
	 * @return le flux de sortie de l'objet client
	 */
	public ObjectOutputStream getOut() {
		return out;
	}

	/**
	 * Retourne le socket de l'objet client.
	 * 
	 * @return le socket de l'objet client
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * Retourne le flux d'entrée de l'objet client.
	 * 
	 * @return le flux d'entrée de l'objet client
	 */
	public ObjectInputStream getIn() {
		return in;
	}

	/**
	 * Lance la connexion avec le serveur en initialisant le socket, le flux
	 * d'entrée et le flux de sortie.
	 * 
	 * @param serverAddress l'adresse IP du serveur
	 * @param port          le port de connexion du serveur
	 * @throws IOException si une erreur d'entrée/sortie survient lors de la
	 *                     création du socket ou des flux
	 */
	@Override
	public void startSocket(String serverAddress, int port) throws IOException {
		socket = new Socket(serverAddress, port);
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
	}

	/**
	 * Arrête la connexion en fermant le socket, le flux d'entrée et le flux de
	 * sortie.
	 * 
	 * @throws IOException si une erreur d'entrée/sortie survient lors de la
	 *                     fermeture des flux ou du socket
	 */
	@Override
	public void stopSocket() throws IOException {
		socket.close();
		in.close();
		out.close();
	}

	/**
	 * Envoie un message au serveur via le flux de sortie.
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
	 * Méthode vide pour la réception de messages côté client. Cette méthode peut
	 * être redéfinie pour inclure le traitement des messages reçus.
	 * 
	 * @throws IOException si une erreur d'entrée/sortie survient lors de la
	 *                     réception de messages
	 */
	@Override
	public void receiveMessage() throws IOException {

	}
}
