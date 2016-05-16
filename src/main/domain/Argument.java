package main.domain;

import java.io.Serializable;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 09.05.16.
 */
public class Argument implements Serializable {

    private String type;
    private String name;

    public Argument(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
