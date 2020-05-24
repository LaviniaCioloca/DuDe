package lrg.dude.duplication.strategies;

public class CompareToStrategy implements StringCompareStrategy {
    public boolean similar(String source, String target) {
        return source.compareTo(target) == 0;
    }
}
