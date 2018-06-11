package ua.nure.queue_managment_android.model;

import java.io.Serializable;

public class AbstractEntity implements Serializable {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
