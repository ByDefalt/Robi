package defalt.robiproject.tools;

import java.awt.Color;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 * Une classe utilitaire pour fournir des méthodes de lecture au clavier, de
 * lecture de script, de sommeil, de récupération de couleur par nom, et de
 * tokenization de chaînes.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class Tools {
	
	/**
	 * Met en pause l'exécution du programme pour le nombre de millisecondes
	 * spécifié.
	 * 
	 * @param millis le nombre de millisecondes à dormir
	 */
	public static void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Lit une entrée à partir du clavier et la retourne sous forme de chaîne de
	 * caractères.
	 * 
	 * @return la chaîne de caractères lue à partir du clavier
	 */
	public static String readKeyboard() {
		String s = null;
		try {
			BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
			s = entree.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * Lit un script à partir de l'entrée standard et le retourne sous forme de
	 * chaîne de caractères.
	 * 
	 * @return le contenu du script lu sous forme de chaîne de caractères
	 * @throws UncheckedIOException si une erreur d'entrée/sortie non vérifiée
	 *                              survient lors de la lecture
	 * @throws IOException          si une erreur d'entrée/sortie survient lors de
	 *                              la lecture
	 */
	public static String readScript() throws UncheckedIOException, IOException {
		try (InputStreamReader in = new InputStreamReader(System.in); BufferedReader buffer = new BufferedReader(in)) {
			String contents = buffer.lines().collect(Collectors.joining());
			return contents;
		}
	}

	/**
	 * Obtient une couleur par son nom.
	 * 
	 * @param name le nom de la couleur
	 * @return la couleur correspondante
	 * https://stackoverflow.com/questions/35692823/getting-a-color-by-name-in-java
	 */
	public static Color getColorByName(String name) {
		try {
			return (Color) Color.class.getField(name.toUpperCase()).get(null);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Tokenize une chaîne de caractères en utilisant des espaces comme délimiteurs.
	 * 
	 * @param s la chaîne de caractères à tokenizer
	 * @return un tableau de chaînes de caractères tokenisées
	 */
	public static String[] tokenize(String s) {
		StringTokenizer nizer = new StringTokenizer(s);
		ArrayList<String> result = new ArrayList<String>();
		while (nizer.hasMoreElements()) {
			result.add((String) nizer.nextElement());
		}
		String[] arr = new String[result.size()];
		return result.toArray(arr);
	}
	
	/**
	 * Lit une commande et ses arguments à partir du clavier et les retourne sous
	 * forme de tableau de chaînes de caractères.
	 * 
	 * @return un tableau de chaînes de caractères contenant la commande et ses
	 *         arguments
	 */
	public static String[] readCmdAndArgs() {
		String input = readKeyboard();
		String[] cmdAndargs = tokenize(input);
		return cmdAndargs;
	}
}
