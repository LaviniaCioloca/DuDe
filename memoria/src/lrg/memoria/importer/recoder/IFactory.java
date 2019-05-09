package lrg.memoria.importer.recoder;

public interface IFactory {
    Listener getListener();

    void cleanUp();
}
