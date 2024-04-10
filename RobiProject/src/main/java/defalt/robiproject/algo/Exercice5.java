package defalt.robiproject.algo;

import defalt.robiproject.algo.*;
import defalt.robiproject.graphicLayer.*;
import defalt.robiproject.parser.SNode;
import defalt.robiproject.parser.SParser;

import java.awt.Dimension;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * La classe Exercice5 contient la méthode main pour exécuter les scripts
 * prédéfinis. Elle démontre l'utilisation des commandes définies et leur
 * exécution dans un espace graphique.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class Exercice5 {
	private final Environment environment = new Environment();
	private final String script = "(space setDim 150 120)\n" + "(space add robi (rect.class new))\n"
			+ "(space.robi setColor white)\n" + "(space.robi setDim 100 100)\n" + "(space.robi translate 20 10)\n"
			+ "(space.robi add im (image.class new alien.gif))\n" + "(space.robi.im translate 20 20)";

	/**
	 * Construit un objet Exercice5 et initialise l'espace graphique et
	 * l'environnement avec des commandes.
	 */
	public Exercice5() {
		GSpace space = new GSpace("Exercice 5", new Dimension(800, 500));
		space.open();

		Reference spaceRef = new Reference(space);
		Reference rectClassRef = new Reference(GRect.class);
		Reference imageClassRef = new Reference(GImage.class);

		spaceRef.addCommand("setColor", new SetColor());
		spaceRef.addCommand("sleep", new SleepCommand());
		spaceRef.addCommand("add", new AddElement(environment));
		spaceRef.addCommand("del", new DelElement(environment));
		spaceRef.addCommand("setDim", new SetDim());
		spaceRef.addCommand("translate", new Translate());

		rectClassRef.addCommand("new", new NewElement(environment));
		imageClassRef.addCommand("new", new NewImage());

		environment.addReference("space", spaceRef);
		environment.addReference("rect.class", rectClassRef);
		environment.addReference("image.class", imageClassRef);
	}

	/**
	 * Exécute le script fourni en le parsant et en itérant sur les nœuds
	 * résultants.
	 *
	 * @param script Le script à exécuter.
	 */
	public void oneShot(String script) {
		SParser<SNode> parser = new SParser<>();
		try {
			List<SNode> compiled = parser.parse(script);
			Iterator<SNode> iterator = compiled.iterator();
			while (iterator.hasNext()) {
				new Interpreter().compute(environment, iterator.next());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Exercice5 exo = new Exercice5();
		exo.oneShot(exo.script);
	}
}
