package com.envyclient.mapcha.api.manager;

import java.util.ArrayList;

public abstract class Manager<T> {

    private ArrayList<T> contents = new ArrayList<T>();

    public ArrayList<T> getContents() {
        return contents;
    }

    public void setContents(ArrayList<T> contents) {
        this.contents = contents;
    }
}