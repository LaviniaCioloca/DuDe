package lrg.memoria.core.factories;

import lrg.memoria.core.Body;
import lrg.memoria.core.Method;
import lrg.memoria.core.FunctionBody;

public class BodyFactory {

    private static BodyFactory instance = null;

    public static void cleanUp() {
        instance = null;
    }

    public static BodyFactory getInstance() {

        if(instance == null) {
           instance = new BodyFactory();
        }

        return instance;

    }

    protected BodyFactory(){}

    public Body produceBody(Method m) {
        return new FunctionBody(m);
    }

}