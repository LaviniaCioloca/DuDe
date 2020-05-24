package lrg.dude.duplication.processor;

/**
 * Created by IntelliJ IDEA.
 * User: Richard
 * Date: 13.05.2004
 * Time: 23:05:06
 * To change this template use File | Settings | File Templates.
 */
public interface Subject {
    void attach(Observer observer);

    void detach(Observer observer);

    void notifyObservers();
}
