package main.domain;

import java.io.Serializable;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 10.05.16.
 */
public abstract class Signature implements Serializable {

    protected String name;

    public Signature() {
        name = "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract boolean rewriteProperty(String str);
}
