package defalt.robiproject.algo;

import defalt.robiproject.parser.SNode;

public class SleepCommand implements Command {
    @Override
    public Reference run(Reference receiver, SNode method) {
        int duration = Integer.parseInt(method.get(2).contents());

        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return receiver;
    }
}