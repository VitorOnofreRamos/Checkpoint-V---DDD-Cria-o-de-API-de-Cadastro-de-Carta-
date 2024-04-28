package org.fiap.com.br.entities;

import java.util.ArrayList;
import java.util.Map;
import java.util.StringJoiner;

public abstract class _BaseEntity{
    private int id;

    public _BaseEntity(){}

    public _BaseEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", _BaseEntity.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .toString();
    }

    public Map<Boolean, ArrayList<String>> validade(){
        var erros = new ArrayList<String>();
        if (id < 0)
            erros.add("O id não pode ser menor que zero");

        return !erros.isEmpty() ?
                Map.of(false, erros) :
                Map.of(true, erros);
    }
}
