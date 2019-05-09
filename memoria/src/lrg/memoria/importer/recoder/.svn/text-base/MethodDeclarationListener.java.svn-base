package lrg.memoria.importer.recoder;

import lrg.memoria.core.DataAbstraction;
import lrg.memoria.core.Location;
import lrg.memoria.core.factories.BodyFactory;
import recoder.java.ProgramElement;
import recoder.java.declaration.ConstructorDeclaration;
import recoder.java.declaration.MethodDeclaration;
import recoder.java.reference.TypeReference;

import java.util.Stack;

public class MethodDeclarationListener implements Listener {

    private Stack oldBodies;
    private Stack oldMethods;

    static {
        ModelConstructor.addFactory("lrg.memoria.importer.recoder.MethodDeclarationListener", new Factory());
    }

    protected MethodDeclarationListener() {
        oldBodies = new Stack();
        oldMethods = new Stack();
    }

    private static Listener listener;

    static class Factory implements IFactory {
        public Listener getListener() {
            if (listener != null)
                return listener;
            else
                return (listener = new MethodDeclarationListener());
        }

        public void cleanUp() {
            listener = null;
        }
    }

    public void enterModelComponent(ProgramElement pe) {
        ModelRepository mr = DefaultModelRepository.getModelRepository(null);
        MethodDeclaration md = (MethodDeclaration) pe;
        String name = md.getName();
        lrg.memoria.core.Method mmmm = mr.addMethod(md, name);
        mmmm.setStatute(lrg.memoria.core.Statute.NORMAL);
        DataAbstraction mmmContainer = mr.getCurrentClass();
        mmmm.setScope(mmmContainer);
        mmmContainer.addMethod(mmmm);
        oldMethods.push(mr.getCurrentMethod());
        mr.setCurrentMethod(mmmm);
        Location loc = new Location(mr.getCurrentFile());
        loc.setStartLine(md.getFirstElement().getStartPosition().getLine() - 1);
        loc.setStartChar(md.getFirstElement().getStartPosition().getColumn() - 1); // Why -1?
        loc.setEndLine(md.getLastElement().getEndPosition().getLine() - 1);
        loc.setEndChar(md.getLastElement().getEndPosition().getColumn() - 1);
        mmmm.setLocation(loc);
        if (md instanceof ConstructorDeclaration)
            mmmm.setConstructor();
        else {
            // This check has been obsolated by the MethodInspector class
            if (name.startsWith("get") || name.startsWith("set"))
                mmmm.setAccessor();
            TypeReference tr = md.getTypeReference();
            mmmm.setReturnType(ReferenceConverter.getTypeFromTypeReference(tr));
        }
        mmmm.setAccessMode(RecoderToMemojConverter.convertAccessMode(md));
        if (md.isStatic())
            mmmm.setStatic();
        if (md.isFinal())
            mmmm.setFinal();
        oldBodies.push(mr.getCurrentBody());
        if (md.isAbstract()) {
            mr.setCurrentBody(null);
            //setActiveStripe(null,mr,pe,null);
            mmmm.setAbstract();
        } else {
            MetricsRepository mer = DefaultMetricRepository.getMetricRepository();
            mer.resetAll();
            lrg.memoria.core.FunctionBody mb = (lrg.memoria.core.FunctionBody)BodyFactory.getInstance().produceBody(mmmm);

            MemoriaPrettyPrinter mpp = MemoriaPrettyPrinter.getMemoriaPrettyPrinter();
            mb.setSourceCode(mpp.getSource(md));
            /*
            CodeStripe cs=new CodeStripe(mb);
            cs.setAccess(lrg.memoria.core.CodeStripe.NONSTATIC_STRIPE);
            cs.setSignature("[func_code]");
            mb.setCodeStripe(cs);
            setActiveStripe(cs,mr,pe,md.getStatementAt(0));
            /**/
            mmmm.setFunctionBody(mb);
            mr.setCurrentBody(mb);
        }
    }

    public void leaveModelComponent(ProgramElement pe) {
        ModelRepository mr = DefaultModelRepository.getModelRepository(null);
        MethodDeclaration md=(MethodDeclaration)pe;
        /*
        if (md.isAbstract()) {
            restoreStripe(mr,pe,null);
        } else {
            restoreStripe(mr,pe,md.getStatementAt(md.getStatementCount()-1));
        }
        */
        mr.setCurrentBody((lrg.memoria.core.Body) oldBodies.pop());
        mr.setCurrentMethod((lrg.memoria.core.Method) oldMethods.pop());
    }
}
