package org.samples.marshalling.dto;

import java.io.Serializable;

/**
 * Created by marc.boulanger on 25/07/15.
 */
public class SimplePOJO implements Serializable {

    private String string;
    private Integer integer;

    public SimplePOJO() {
        this("A String", 1);
    }

    public SimplePOJO(String string, Integer integer) {
        this.string = string;
        this.integer = integer;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Integer getInteger() {
        return integer;
    }

    public void setInteger(Integer integer) {
        this.integer = integer;
    }
}
