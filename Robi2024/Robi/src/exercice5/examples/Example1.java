package exercice5.examples;

import java.awt.Dimension;

import exercice5.Exercice5;
import graphicLayer.GRect;
import graphicLayer.GSpace;

public class Example1 {

	/* 
	 * Ajoute un rectangle robi avec ses propriétés par défaut
	 * On doit voir un rectangle bleu en (0,0)
	 * 
	 */
	String script = "(space setDim 150 120)";
	
	
	public  void launch() {
		Exercice5 exo = new Exercice5();
		exo.oneShot(script);
	}
	
	public static void main(String[] args) {
		new Example1().launch();
	}
}
  