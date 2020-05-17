package lrg.dude.duplication;

import java.util.List;

public class ChronosImportJson {
    private String file;
    private String name;
    private String category;
    private int count;
    private List<String> value;

    public ChronosImportJson(String file, String name, String category, int count, List<String> value) {
        this.file = file;
        this.name = name;
        this.category = category;
        this.count = count;
        this.value = value;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCount() {
        return count;
    }

    public void setCount(final int count) {
        this.count = count;
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(final List<String> value) {
        this.value = value;
    }
}
