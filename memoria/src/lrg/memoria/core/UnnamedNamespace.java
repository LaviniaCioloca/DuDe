package lrg.memoria.core;

public class UnnamedNamespace extends Namespace {
    private File file;

    public UnnamedNamespace(File file) {
        super(file.getFullName());
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public String toString() {
        return "UnnamedNamespace: \n\t File - " + file.getFullName();
    }
}
