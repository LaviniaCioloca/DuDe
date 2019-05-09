package lrg.memoria.importer.recoder;

import recoder.convenience.ASTIterator;
import recoder.convenience.ASTIteratorListener;
import recoder.java.NonTerminalProgramElement;
import recoder.java.ProgramElement;
import recoder.java.Comment;
import recoder.list.generic.ASTList;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ModelConstructor implements ASTIteratorListener {

    private static Map factories=new HashMap(25),
                       allFactories=new HashMap(90);
    private LinkedList listenersList = new LinkedList();

    static void cleanUp() {
        if (factories != null) {
            Iterator it = factories.values().iterator();
            while (it.hasNext()) {
                ((IFactory) it.next()).cleanUp();
            }
        }
    }

    public static void addFactory(String fName, IFactory f) {
        factories.put(fName, f);
    }

    public Listener getListener(String name) {
        String listenerName = "lrg.memoria.importer.recoder." + name + "Listener";

        if (allFactories.containsKey(listenerName)==false) {
            try {
                allFactories.put(listenerName,"");
                Class.forName(listenerName);
                IFactory fac = (IFactory) factories.get(listenerName);
                return fac.getListener();
            } catch (ClassNotFoundException e) {
                return null;
            }
        } else {
            IFactory fac = (IFactory) factories.get(listenerName);
            if (fac!=null) return fac.getListener();
            else return null;
        }
    }
    
    public boolean enterChildNode(ASTIterator it,
                                  NonTerminalProgramElement thisNode,
                                  ProgramElement childNode) {
        return true;
    }

    public int enterChildren(ASTIterator it, NonTerminalProgramElement thisNode) {
        return ENTER_ALL;
    }

    public void enteringNode(ASTIterator it, ProgramElement node) {
        String curClassName = node.getClass().getName();
        String name = curClassName.substring(curClassName.lastIndexOf(".") + 1);
        Listener curentListener = getListener(name);
        // System.out.println(">>"+ name + " " + node);
        if (curentListener != null) {
            listenersList.addLast(curentListener);
            curentListener.enterModelComponent(node);
        }        
        //set the number of comments for this program element
        MetricsRepository mer = DefaultMetricRepository.getMetricRepository();
        ASTList<Comment> cml = node.getComments();
        if (cml != null)
            mer.addComments(cml.size());
    }

    public void leavingNode(ASTIterator it, ProgramElement node) {
        String curClassName = node.getClass().getName();
        String name = curClassName.substring(curClassName.lastIndexOf(".") + 1);
        Listener curentListener = getListener(name);
        if (curentListener != null) {
            curentListener = (Listener) listenersList.removeLast();
            curentListener.leaveModelComponent(node);
        }
    }

    public void returnedFromChildNode(ASTIterator it,
                                      NonTerminalProgramElement thisNode,
                                      ProgramElement childNode) {

    }
}