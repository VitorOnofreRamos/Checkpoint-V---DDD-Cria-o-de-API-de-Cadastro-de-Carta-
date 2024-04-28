package org.fiap.com.br.repositories;

import org.fiap.com.br.entities.PokemonCard;
import org.fiap.com.br.infrastructure.OracleDbConfiguration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PokemonCardRepository implements _BaseRepository<PokemonCard>{
    OracleDbConfiguration connection = new OracleDbConfiguration();
    public static final String TB_NAME = "POKEMON_CARDS";
    public static final Map<String, String> TB_COLUMNS = Map.of(
            "CARD_ID", "CARD_ID",
            "NAME", "NAME",
            "CATEGORY", "CATEGORY",
            "TYPE", "TYPE",
            "HEALTH", "HEALTH",
            "DESCRIPTION", "DESCRIPTION",
            "COLLECTION_ID", "COLLECTION_ID"
    );
    @Override
    public void create(PokemonCard card) {
        try (var conn = connection.getConnection()) {
            // Verifica se a coleção referenciada existe
            var checkStmt = conn.prepareStatement("SELECT COUNT(*) FROM " + PokemonCollectionRepository.TB_NAME + " WHERE " + PokemonCollectionRepository.TB_COLUMNS.get("COLLECTION_ID") + " = ?");
            checkStmt.setInt(1, card.getCollectionId());
            var rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            // Se a coleção existir, insere a carta
            if (count > 0) {
                String sql = "INSERT INTO " + TB_NAME + " (" +
                        TB_COLUMNS.get("NAME") + ", " +
                        TB_COLUMNS.get("CATEGORY") + ", " +
                        TB_COLUMNS.get("TYPE") + ", " +
                        TB_COLUMNS.get("HEALTH") + ", " +
                        TB_COLUMNS.get("DESCRIPTION") + ", " +
                        TB_COLUMNS.get("COLLECTION_ID") + ") " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

                try (var stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, card.getName());
                    stmt.setString(2, card.getCategory());
                    stmt.setString(3, card.getType());
                    stmt.setInt(4, card.getHealth());
                    stmt.setString(5, card.getDescription());
                    stmt.setInt(6, card.getCollectionId());
                    var result = stmt.executeUpdate();
                }
            } else {
                // Opcional: Tratar o caso em que a coleção não existe
                System.out.println("A coleção referenciada não existe.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public PokemonCard get(int id) {
        PokemonCard card = null;
        try (var conn = connection.getConnection()){
            var stmt = conn.prepareStatement("SELECT * FROM " +TB_NAME+ " WHERE " +TB_COLUMNS.get("CARD_ID")+" = ?");
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()){
                card = new PokemonCard(
                        rs.getInt(TB_COLUMNS.get("CARD_ID")),
                        rs.getString(TB_COLUMNS.get("NAME")),
                        rs.getString(TB_COLUMNS.get("CATEGORY")),
                        rs.getString(TB_COLUMNS.get("TYPE")),
                        rs.getInt(TB_COLUMNS.get("HEALTH")),
                        rs.getString(TB_COLUMNS.get("DESCRIPTION")),
                        rs.getInt(TB_COLUMNS.get("COLLECTION_ID"))
                );
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return card;
    }

    @Override
    public List<PokemonCard> getAll() {
        List<PokemonCard> cards = new ArrayList<>();
        try (var conn = connection.getConnection()){
            var stmt = conn.prepareStatement("SELECT * FROM "+TB_NAME+" ORDER BY "+TB_COLUMNS.get("CARD_ID")+" ASC");
            var rs = stmt.executeQuery();
            while (rs.next()){
                PokemonCard card = new PokemonCard(
                        rs.getInt(TB_COLUMNS.get("CARD_ID")),
                        rs.getString(TB_COLUMNS.get("NAME")),
                        rs.getString(TB_COLUMNS.get("CATEGORY")),
                        rs.getString(TB_COLUMNS.get("TYPE")),
                        rs.getInt(TB_COLUMNS.get("HEALTH")),
                        rs.getString(TB_COLUMNS.get("DESCRIPTION")),
                        rs.getInt(TB_COLUMNS.get("COLLECTION_ID"))
                );
                cards.add(card);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    @Override
    public boolean update(int id, PokemonCard card) {
        // Verifica se a coleção referenciada existe
        try (var conn = connection.getConnection()) {
            var checkStmt = conn.prepareStatement("SELECT COUNT(*) FROM " + PokemonCollectionRepository.TB_NAME + " WHERE " + PokemonCollectionRepository.TB_COLUMNS.get("COLLECTION_ID") + " = ?");
            checkStmt.setInt(1, card.getCollectionId());
            var rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            // Se a coleção existir, atualiza a carta
            if (count > 0) {
                String sql = "UPDATE " + TB_NAME + " SET " +
                        TB_COLUMNS.get("NAME") + " = ?, " +
                        TB_COLUMNS.get("CATEGORY") + " = ?, " +
                        TB_COLUMNS.get("TYPE") + " = ?, " +
                        TB_COLUMNS.get("HEALTH") + " = ?, " +
                        TB_COLUMNS.get("DESCRIPTION") + " = ?, " +
                        TB_COLUMNS.get("COLLECTION_ID") + " = ? " +
                        "WHERE " + TB_COLUMNS.get("CARD_ID") + " = ?";

                try (var stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, card.getName());
                    stmt.setString(2, card.getCategory());
                    stmt.setString(3, card.getType());
                    stmt.setInt(4, card.getHealth());
                    stmt.setString(5, card.getDescription());
                    stmt.setInt(6, card.getCollectionId());
                    stmt.setInt(7, id);
                    var result = stmt.executeUpdate();
                    return result > 0;
                }
            } else {
                // Opcional: Tratar o caso em que a coleção não existe
                System.out.println("A coleção referenciada não existe.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean delete(int id) {
        try(var conn = connection.getConnection()){
            var stmt = conn.prepareStatement("DELETE FROM " +TB_NAME+ " WHERE " +TB_COLUMNS.get("CARD_ID")+ " = ?");
            stmt.setInt(1, id);
            var result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
