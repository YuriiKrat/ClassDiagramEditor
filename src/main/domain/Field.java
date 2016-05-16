package main.domain;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 09.05.16.
 */
public class Field extends Property{

    public Field() {
        super();
    }

    @Override
    public boolean parse(String str) {
        String[] signature = str.split(":");
        if (signature.length == 2) {
            if (signature[0].matches("\\w*") && signature[1].matches("\\w*")) {
                setName(signature[0]);
                setType(signature[1]);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString() + ";";
    }
}
