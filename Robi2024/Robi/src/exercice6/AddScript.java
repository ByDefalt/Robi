package exercice6;

import exercice4.Command;
import exercice4.Environment;
import exercice4.Reference;
import exercice4.SetColor;
import exercice4.SetDim;
import exercice4.Translate;
import graphicLayer.GElement;
import graphicLayer.GSpace;
import stree.parser.SNode;

public class AddScript implements Command {
    private Environment environment;

    public AddScript(Environment environment) {
        this.environment = environment;
    }

    public Reference run(Reference receiver, SNode method) {
        String scriptName = method.get(2).contents();
        Reference selfRef = receiver;

        // Extract script parameters
        SNode scriptParams = method.get(3);
        String[] params = new String[scriptParams.size() - 1];
        for (int i = 1; i < scriptParams.size(); i++) {
            params[i - 1] = scriptParams.get(i).contents();
        }

        // Define script command
        Command scriptCommand = new Command() {
            public Reference run(Reference receiver, SNode method) {
                // Pass additional parameters to the script
                /*receiver.addParam("self", selfRef);
                for (int i = 0; i < params.length; i++) {
                    receiver.addParam(Integer.toString(i), params[i]);
                }
                // Execute the script
                new Interpreter().compute(environment, method.get(1));
                return receiver;*/
            }
        };

        // Add the script command to the receiver's reference
        receiver.addCommand(scriptName, scriptCommand);
        return receiver;
    }
}
