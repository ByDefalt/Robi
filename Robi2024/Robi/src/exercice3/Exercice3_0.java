package exercice3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import graphicLayer.GRect;
import graphicLayer.GSpace;
import stree.parser.SNode;
import stree.parser.SParser;

public class Exercice3_0 {
	public GSpace space = new GSpace("Exercice 3", new Dimension(200, 100));
	public GRect robi = new GRect();
	String script = "" +
	"   (space setColor black) " +
	"   (robi setColor yellow)" +
	"   (space sleep 1000)" +
	"   (space setColor white)\n" + 
	"   (space sleep 1000)" +
	"	(robi setColor red) \n" + 
	"   (space sleep 1000)" +
	"	(robi translate 100 0)\n" + 
	"	(space sleep 1000)\n" + 
	"	(robi translate 0 50)\n" + 
	"	(space sleep 1000)\n" + 
	"	(robi translate -100 0)\n" + 
	"	(space sleep 1000)\n" + 
	"	(robi translate 0 -40)";

    public Exercice3_0() {
        this.space.addElement(this.robi);
        this.space.open();
        this.runScript();
    }

    private void runScript() {
        SParser<SNode> parser = new SParser<>();
        List<SNode> rootNodes = null;
        try {
            rootNodes = parser.parse(script);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Iterator<SNode> itor = rootNodes.iterator();
        while (itor.hasNext()) {
            this.run(itor.next());
        }
    }

    private void run(SNode expr) {
        Command cmd = getCommandFromExpr(expr);
        if (cmd == null)
            throw new Error("unable to get command for: " + expr);
        cmd.run();
    }

    Command getCommandFromExpr(SNode expr) {
        String target = expr.children().get(0).contents();
        String command = expr.children().get(1).contents();
        String[] params = new String[expr.children().size() - 2];

        for (int i = 0; i < params.length; i++) {
            params[i] = expr.children().get(i + 2).contents();
        }

        switch (target) {
            case "space":
                return getSpaceCommand(command, params);
            case "robi":
                return getRobiCommand(command, params);
            default:
                return null;
        }
    }

    private Command getSpaceCommand(String command, String[] params) {
        switch (command) {
            case "setColor":
                return new SpaceChangeColor(getColorFromString(params[0]));
            case "sleep":
                return new SleepCommand(Integer.parseInt(params[0]));
            default:
                return null;
        }
    }

    private Command getRobiCommand(String command, String[] params) {
        switch (command) {
            case "setColor":
                return new RobiChangeColor(this.robi, getColorFromString(params[0]));
            case "translate":
                int x = Integer.parseInt(params[0]);
                int y = Integer.parseInt(params[1]);
                return new RobiTranslate(this.robi, x, y);
            default:
                return null;
        }
    }

    private Color getColorFromString(String colorStr) {
        switch (colorStr) {
            case "black":
                return Color.BLACK;
            case "yellow":
                return Color.YELLOW;
            case "white":
                return Color.WHITE;
            case "red":
                return Color.RED;
            default:
                return null;
        }
    }

    public static void main(String[] args) {
        new Exercice3_0();
    }

    public interface Command {
        void run();
    }

    class SpaceChangeColor implements Command {
        Color newColor;

        public SpaceChangeColor(Color newColor) {
            this.newColor = newColor;
        }

        @Override
        public void run() {
            space.setColor(this.newColor);
        }
    }

    class RobiChangeColor implements Command {
        GRect robi;
        Color newColor;

        public RobiChangeColor(GRect robi, Color newColor) {
            this.robi = robi;
            this.newColor = newColor;
        }

        @Override
        public void run() {
        	this.robi.setColor(this.newColor);
        }
    }

    class RobiTranslate implements Command {
        GRect robi;
        int dx, dy;

        public RobiTranslate(GRect robi, int dx, int dy) {
            this.robi = robi;
            this.dx = dx;
            this.dy = dy;
        }

        @Override
        public void run() {
        	this.robi.translate(new Point(dx, dy));
        }
    }

    class SleepCommand implements Command {
        int duration;

        public SleepCommand(int duration) {
            this.duration = duration;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(this.duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}