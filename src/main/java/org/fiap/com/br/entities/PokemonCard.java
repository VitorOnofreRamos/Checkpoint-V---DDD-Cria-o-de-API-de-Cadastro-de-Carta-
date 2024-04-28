package org.fiap.com.br.entities;

import java.util.ArrayList;
import java.util.Map;
import java.util.StringJoiner;

public class PokemonCard extends _BaseEntity {
    private String name;
    private String category;
    private String type;
    private int health;
    private String description;
    private int collectionId;

    public PokemonCard(){}

    public PokemonCard(int id, String name, String category, String type, int health, String description, int collectionId) {
        super(id);
        this.name = name;
        this.category = category;
        this.type = type;
        this.health = health;
        this.description = description;
        this.collectionId = collectionId;
    }

    public PokemonCard(String name, String category, String type, int health, String description, int collectionId) {
        this.name = name;
        this.category = category;
        this.type = type;
        this.health = health;
        this.description = description;
        this.collectionId = collectionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PokemonCard.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("category='" + category + "'")
                .add("type='" + type + "'")
                .add("health=" + health)
                .add("description='" + description + "'")
                .add("collectionId=" + collectionId)
                .toString();
    }

    public Map<Boolean, ArrayList<String>> validate() {
        var errors = new ArrayList<String>();
        if (name == null || name.isBlank())
            errors.add("Nome da carta não pode ser vazio");
        if (category == null || category.isBlank())
            errors.add("A carta tem que ter uma categoria");
        if (type == null || type.isBlank())
            errors.add("A carta tem que ter um tipo");
        if (health % 100 != 0 && health >= 0)
            errors.add("A carta tem que ser um multiplo de 10 e não pode ser negativa");
        if (description == null || description.isBlank())
            errors.add("A carta tem que ter descrição");
        if (collectionId < 0)
            errors.add("O ID da coleção tem que ser um ID válido");

        return !errors.isEmpty() ?
                Map.of(false, errors) :
                Map.of(true, errors);
    }
}
