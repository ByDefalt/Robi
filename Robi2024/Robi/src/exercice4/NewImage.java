package exercice4;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import graphicLayer.GImage;
import stree.parser.SNode;

/**
 * Classe NewImage. Cette classe implémente la commande pour créer une nouvelle
 * image graphique.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class NewImage implements Command {

	/**
	 * Méthode run. Cette méthode crée une nouvelle image graphique à partir du
	 * chemin spécifié.
	 * 
	 * @param receiver La référence de la classe de l'image graphique à créer.
	 * @param expr     Le nœud représentant la méthode à exécuter.
	 * @return La référence de la nouvelle image graphique créée.
	 */
	@Override
	public Reference run(Reference receiver, SNode expr) {
		File path = new File(expr.get(3).get(2).contents());
		BufferedImage rawImage = null;
		try {
			rawImage = ImageIO.read(path);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Reference ref = null;
		if (rawImage != null) {
			GImage image = new GImage(rawImage);
			ref = new Reference(image);
			ref.addCommand("setColor", new SetColor());
			ref.addCommand("translate", new Translate());
		} else {
			System.out.println("Erreur lors de la lecture de l'image : " + path.getAbsolutePath());
		}

		return ref;
	}
}
