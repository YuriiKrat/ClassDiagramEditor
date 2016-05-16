package main.domain;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 09.05.16.
 */
public abstract class Property extends Signature {

    public Modifiers modifiers;

    protected String type;

    public Property() {
        super();
        modifiers = new Modifiers();
        type = "";
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean rewriteProperty(String str) {
        if (str.length() > 0) {
            switch (str.charAt(0)) {
                case '#':
                    modifiers.setProtected();
                    str = str.replace("#", "");
                    break;
                case '+':
                    modifiers.setPublic();
                    str = str.replace("+", "");
                    break;
                case '-':
                    modifiers.setPrivate();
                    str = str.replace("-", "");
                    break;
                case '~':
                    str = str.replace("~", "");
                    break;
                default:
                    return false;
            }
            return parse(str);
        }
        return false;
    }

    public abstract boolean parse(String str);

    @Override
    public String toString() {
        return modifiers.toString() + " " + type + " " + name;
    }

}
