package main.domain;

import java.io.Serializable;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 09.05.16.
 */
public class Modifiers implements Serializable{

    private final int PUBLIC = 1;
    private final int PROTECTED = 2;
    private final int PRIVATE = 3;

    private int accessModifiers;

    public Modifiers(){}

    public String getAccessLevel() {
        if ((accessModifiers & PUBLIC) == PUBLIC) {
            return "public";
        }
        else if ((accessModifiers & PROTECTED) == PROTECTED) {
            return "protected";
        }
        else if ((accessModifiers & PRIVATE) == PRIVATE) {
            return "private";
        }
        else {
            return "";
        }
    }

    public void setPublic() {
        accessModifiers &= Integer.MAX_VALUE - 3;
        accessModifiers |= PUBLIC;
    }

    public void setProtected() {
        accessModifiers &= Integer.MAX_VALUE - 3;
        accessModifiers |= PROTECTED;
    }

    public void setPrivate() {
        accessModifiers &= Integer.MAX_VALUE - 3;
        accessModifiers |= PRIVATE;
    }

    @Override
    public String toString() {
        return getAccessLevel();
    }
}
