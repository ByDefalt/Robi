package defalt.robiproject.algo;

import java.lang.reflect.InvocationTargetException;


import defalt.robiproject.graphicLayer.GElement;
import defalt.robiproject.parser.SNode;

public class NewElement implements Command {
	private Environment environment;
	
	public NewElement(Environment environment) {
		this.environment = environment;
	}
	
	public Reference run(Reference reference, SNode method) {
		try {
			@SuppressWarnings("unchecked")
			GElement e = ((Class<GElement>) reference.getReceiver()).getDeclaredConstructor().newInstance();
			Reference ref = new Reference(e);
			ref.addCommand("setColor", new SetColor());
			ref.addCommand("translate", new Translate());
			ref.addCommand("setDim", new SetDim());
			ref.addCommand("add", new AddElement(this.environment));
			return ref;
		} catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return null;
	}
}