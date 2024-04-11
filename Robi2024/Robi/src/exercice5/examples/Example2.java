package exercice5.examples;

import exercice6.Exercice6;

public class Example2 {
	
	/* 
	 * Ajoute un script newSquare  à Rect 
	 * newSquare est un constructeur qui prend en argument une largeur et retourne un carre
	 * On ne voit que la fenetre par défaut, vide
	 * 
	 * Le constructeur de fonctionne pas car je n'ai pas pu faire en sorte d'ajouter un script à une reference de classe
	 * De plus, l'opérateur return (le ^) n'est pas du tout mis en oeuvre
	 * 
	 */
	String script = "" 
			+ " (script ( "
			+ "		( Rect addScript newSquare ( (self w) ((^ (self new) setDim w w) ) ) ) "
			+ "		( space add robi ( Rect newSquare 55 ) )"
			+ " ) )";
	
	
	public  void launch() {
		Exercice6 exo = new Exercice6();
		exo.oneShot(script);
	}
	
	public static void main(String[] args) {
		new Example2().launch();
	}
}
  