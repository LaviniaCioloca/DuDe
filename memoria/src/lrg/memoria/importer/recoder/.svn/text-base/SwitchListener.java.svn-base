package lrg.memoria.importer.recoder;

import recoder.java.ProgramElement;
import recoder.java.statement.Switch;
import recoder.java.statement.Branch;
import recoder.list.generic.ASTList;
import lrg.memoria.core.CodeStripe;

public class SwitchListener extends StripeContainerListener {

    static {
        ModelConstructor.addFactory("lrg.memoria.importer.recoder.SwitchListener", new Factory());
    }

    private SwitchListener() {
    }

    private static Listener listener;

    static class Factory implements IFactory {
        public Listener getListener() {
            if (listener != null)
                return listener;
            else
                return (listener = new SwitchListener());
        }

        public void cleanUp() {
            listener = null;
        }
    }

    public void enterModelComponent(ProgramElement pe) {
        MetricsRepository mer = DefaultMetricRepository.getMetricRepository();
        //   mer.addDecision();
        Switch sw = (Switch) pe;
        for (int i = 0; i < sw.getBranchCount(); i++) {
            ASTList<Branch> bl = sw.getBranchList();
            mer.addStatements(bl.get(i).getStatementCount());
        }
        mer.updateNestingLevel(1);

        ModelRepository mr=DefaultModelRepository.getModelRepository(null);
        CodeStripe cs=new CodeStripe(mr.getCurrentStripe());
        cs.setAccess(CodeStripe.NONSTATIC_STRIPE);
        cs.setSignature(CodeStripe.SWITCH);
        if (sw.getBranchCount()>0) {
            Branch last=sw.getBranchAt(sw.getBranchCount()-1);
            if (last.getStatementCount()>0) {
                setActiveStripe(cs,mr,pe,sw.getBranchAt(0),
                    last.getStatementAt(last.getStatementCount()-1));
            } else setActiveStripe(cs,mr,pe,sw.getBranchAt(0),last);
        } else {
            cs.setAccess(CodeStripe.STATIC_CONTENT);
            setActiveStripe(cs,mr,pe,null,null);
        }

    }

    public void leaveModelComponent(ProgramElement pe) {
        MetricsRepository mer = DefaultMetricRepository.getMetricRepository();
        mer.updateNestingLevel(-1);

        restoreStripe(DefaultModelRepository.getModelRepository(null));

    }
}
