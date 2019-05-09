package lrg.memoria.importer.recoder;

import recoder.java.ProgramElement;
import recoder.java.statement.If;
import lrg.memoria.core.CodeStripe;

public class IfListener extends StripeContainerListener {

    static {
        ModelConstructor.addFactory("lrg.memoria.importer.recoder.IfListener", new Factory());
    }

    protected IfListener() {
    }

    private static Listener listener;

    static class Factory implements IFactory {
        public Listener getListener() {
            if (listener != null)
                return listener;
            else
                return (listener = new IfListener());
        }

        public void cleanUp() {
            listener = null;
        }
    }

    public void enterModelComponent(ProgramElement pe) {
        MetricsRepository mer = DefaultMetricRepository.getMetricRepository();
        mer.addDecision();
        mer.addStatements(1);
        mer.updateNestingLevel(1);

        // me thinks that *Condition ? Then : Else* also arrive here
        // this is a problem if it is in attribute initialisation
        // because there is no CodeStripe to use as scope there

        if (pe instanceof If==false) return;
        // but me not care about  C ? T : E  anyway because it can only
        // be part of expression which must be part of statement which,
        // again is only mutable as whole

        ModelRepository mr=DefaultModelRepository.getModelRepository(null);

        CodeStripe cs=new CodeStripe(mr.getCurrentStripe());
        cs.setAccess(CodeStripe.NONSTATIC_STRIPE);
        cs.setSignature(CodeStripe.IF);
        if (((If)pe).getElse()!=null)
            setActiveStripe(cs,mr,pe,((If)pe).getThen().getBody(),((If)pe).getElse().getBody());
        else
            setActiveStripe(cs,mr,pe,((If)pe).getThen().getBody(),((If)pe).getThen().getBody());
    }

    public void leaveModelComponent(ProgramElement pe) {
        MetricsRepository mer = DefaultMetricRepository.getMetricRepository();
        mer.updateNestingLevel(-1);

        if (pe instanceof If==false) return; // I don't care about conditional expression
        
        restoreStripe(DefaultModelRepository.getModelRepository(null));
    }
}
