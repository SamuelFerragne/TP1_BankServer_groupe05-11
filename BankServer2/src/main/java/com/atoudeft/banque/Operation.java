package com.atoudeft.banque;

import java.io.Serializable;
import java.util.Date;

public abstract class Operation implements Serializable {
    private TypeOperation type;
    private Date date;

    public Operation(TypeOperation type) {
        this.type = type;
        this.date = new Date(System.currentTimeMillis()); //À vérifier
    }
    @Override
    public String toString() {
        return date + "\t" + type;
    }
}