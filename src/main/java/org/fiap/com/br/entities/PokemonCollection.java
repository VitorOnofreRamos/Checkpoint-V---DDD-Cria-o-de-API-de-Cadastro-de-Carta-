package org.fiap.com.br.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class PokemonCollection extends _BaseEntity{
    private String name;
    private String description;

    public PokemonCollection(){}

    public PokemonCollection(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public PokemonCollection(int id, String name, String description) {
        super(id);
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PokemonCollection.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("description='" + description + "'")
                .toString();
    }

    public Map<Boolean, ArrayList<String>> validate() {
        var errors = new ArrayList<String>();
        if (name == null || name.isBlank())
            errors.add("Nome da coleção não pode ser vazio");
        if (description == null || description.isBlank())
            errors.add("A coleção tem que ter descrição");

        return !errors.isEmpty() ?
                Map.of(false, errors) :
                Map.of(true, errors);
    }
}
