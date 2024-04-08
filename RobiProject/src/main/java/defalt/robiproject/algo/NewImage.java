package defalt.robiproject.algo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import defalt.robiproject.graphicLayer.GImage;
import defalt.robiproject.parser.SNode;

public class NewImage implements Command {

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

