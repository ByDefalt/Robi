package exercice1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import graphicLayer.GRect;
import graphicLayer.GSpace;

/**
 * Classe principale pour l'exercice 1. Cette classe crée un espace graphique et
 * un rectangle qui tourne autour du périmètre de la fenêtre.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class Exercice1_0 {
<<<<<<< HEAD
    public GSpace space;
    public GRect robi;
    int speed = 1;
=======
  GSpace space;
  GRect robi;
  int speed = 1;
>>>>>>> 1a688b8e99892dffe5cd195b5dbd9c3d807d036c

  /**
   * Constructeur de la classe Exercice1_0. Initialise l'espace graphique et le
   * rectangle.
   */
  public Exercice1_0() {
    this.space = new GSpace("Exercice 1", new Dimension(400, 400));
    this.robi = new GRect();
    this.space.addElement(robi);
    this.space.open();
    this.robi.setColor(new Color((int) (Math.random() * 0x1000000)));
  }

  /**
   * Méthode pour faire tourner le rectangle autour de la fenêtre.
   */
  public void tourFenetre() {
    while (true) {
      int x = this.robi.getPosition().x;
      int y = this.robi.getPosition().y;

      if (x + this.robi.getWidth() < this.space.getWidth() && y == 0) {
        x += this.speed;
      } else if (x + this.robi.getWidth() == this.space.getWidth()
          && y + this.robi.getHeight() < this.space.getHeight()) {
        y += this.speed;
      } else if (y + this.robi.getHeight() == this.space.getHeight() && x > 0) {
        x -= this.speed;
      } else if (x == 0 && y > 0) {
        y -= this.speed;
      } else if (x + this.robi.getWidth() < this.space.getWidth()) {
        x += this.speed;
      }

      this.robi.setPosition(new Point(x, y));

      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Méthode principale, point d'entrée de l'application. Crée une instance de la
   * classe Exercice1_0 et lance la méthode pour faire tourner le rectangle.
   * 
   * @param args Arguments de la ligne de commande (non utilisés).
   */
  public static void main(String[] args) {
    Exercice1_0 exercice = new Exercice1_0();
    exercice.tourFenetre();
  }
}
