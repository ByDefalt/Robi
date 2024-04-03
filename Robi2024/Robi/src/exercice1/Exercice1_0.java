package exercice1;

import java.awt.Dimension;
import java.awt.Point;

import graphicLayer.GRect;
import graphicLayer.GSpace;

public class Exercice1_0 {
    GSpace space;
    GRect robi;
    int speed = 1;

    public Exercice1_0() {
        this.space = new GSpace("Exercice 1", new Dimension(400, 400));
        this.robi = new GRect();
        this.space.addElement(robi);
        this.space.open();
    }

    public void tourFenetre() {
    	while(true) {
    		int x = this.robi.getPosition().x;
            int y = this.robi.getPosition().y;
            
            if(x + this.robi.getWidth() < this.space.getWidth() && y == 0) {
            	x += this.speed;
            }
            else if(x + this.robi.getWidth() == this.space.getWidth() && y + this.robi.getHeight() < this.space.getHeight()) {
            	y += this.speed;
            }
            else if(y + this.robi.getHeight() == this.space.getHeight() && x > 0) {
            	x -= this.speed;
            }
            else if(x == 0 && y > 0) {
            	y -= this.speed;
            }
            else if(x + this.robi.getWidth() < this.space.getWidth()) {
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

    public static void main(String[] args) {
        Exercice1_0 exercice = new Exercice1_0();
        exercice.tourFenetre();
    }
}

