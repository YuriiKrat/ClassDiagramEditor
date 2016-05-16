package main.domain;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 10.05.16.
 */
public class FigureName extends Signature {

    public FigureName() {
        super();
    }

    @Override
    public boolean rewriteProperty(String str) {
        if (str.length() > 0 && str.matches("\\w*")) {
            setName(str);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return name;
    }
}
