package org.fiap.com.br.repositories;

import org.fiap.com.br.entities.PokemonCard;
import org.fiap.com.br.entities.PokemonCollection;
import org.fiap.com.br.infrastructure.OracleDbConfiguration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PokemonCollectionRepository implements _BaseRepository<PokemonCollection>{
    OracleDbConfiguration connection = new OracleDbConfiguration();
    public static final String TB_NAME = "POKEMON_COLLECTION";

    public static final Map<String, String> TB_COLUMNS = Map.of(
            "COLLECTION_ID", "COLLECTION_ID",
            "NAME", "NAME",
            "DESCRIPTION", "DESCRIPTION"
    );
    @Override
    public void create(PokemonCollection collection) {
        String sql = "INSERT INTO " + TB_NAME + " (" +
                TB_COLUMNS.get("NAME") + ", " +
                TB_COLUMNS.get("DESCRIPTION") + ") " +
                "VALUES (?, ?)";

        try (var conn = connection.getConnection()) {
            try (var stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, collection.getName());
                stmt.setString(2, collection.getDescription());
                var result = stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public PokemonCollection get(int id) {
        PokemonCollection collection = null;
        try (var conn = connection.getConnection()){
            var stmt = conn.prepareStatement("SELECT * FROM "+TB_NAME+" WHERE "+TB_COLUMNS.get("COLLECTION_ID")+" = ?");
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()){
                collection = new PokemonCollection(
                        rs.getInt(TB_COLUMNS.get("COLLECTION_ID")),
                        rs.getString(TB_COLUMNS.get("NAME")),
                        rs.getString(TB_COLUMNS.get("DESCRIPTION"))
                );
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return collection;
    }

    @Override
    public List<PokemonCollection> getAll() {
        List<PokemonCollection> collections = new ArrayList<>();
        try (var conn = connection.getConnection()){
            var stmt = conn.prepareStatement("SELECT * FROM "+TB_NAME+" ORDER BY "+TB_COLUMNS.get("COLLECTION_ID")+" ASC");
            var rs = stmt.executeQuery();
            while (rs.next()){
                PokemonCollection collection = new PokemonCollection(
                        rs.getInt(TB_COLUMNS.get("COLLECTION_ID")),
                        rs.getString(TB_COLUMNS.get("NAME")),
                        rs.getString(TB_COLUMNS.get("DESCRIPTION"))
                );
                collections.add(collection);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return collections;
    }

    @Override
    public boolean update(int id, PokemonCollection collection) {
        String sql = "UPDATE " + TB_NAME + " SET " +
                TB_COLUMNS.get("NAME") + " = ?, " +
                TB_COLUMNS.get("DESCRIPTION") + " = ? " +
                "WHERE " + TB_COLUMNS.get("COLLECTION_ID") + " = ?";

        try (var conn = connection.getConnection(); var stmt = conn.prepareStatement(sql)){
            stmt.setString(1, collection.getName());
            stmt.setString(2, collection.getDescription());
            stmt.setInt(3, id);
            var result = stmt.executeUpdate();
            return result > 0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        try (var conn = connection.getConnection()) {
            // Iniciar transação
            conn.setAutoCommit(false);

            // Verificar se existem cartas associadas à coleção
            var checkStmt = conn.prepareStatement("SELECT COUNT(*) FROM " + PokemonCardRepository.TB_NAME + " WHERE " + PokemonCardRepository.TB_COLUMNS.get("COLLECTION_ID") + " = ?");
            checkStmt.setInt(1, id);
            var rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            // Se existirem cartas, deletá-las
            if (count > 0) {
                var deleteCardsStmt = conn.prepareStatement("DELETE FROM " + PokemonCardRepository.TB_NAME + " WHERE " + PokemonCardRepository.TB_COLUMNS.get("COLLECTION_ID") + " = ?");
                deleteCardsStmt.setInt(1, id);
                deleteCardsStmt.executeUpdate();
            }

            // Deletar a coleção
            var deleteCollectionStmt = conn.prepareStatement("DELETE FROM " + TB_NAME + " WHERE " + TB_COLUMNS.get("COLLECTION_ID") + " = ?");
            deleteCollectionStmt.setInt(1, id);
            var result = deleteCollectionStmt.executeUpdate();

            // Confirmar transação
            conn.commit();

            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Reverter transação em caso de erro
            try (var conn = connection.getConnection()){
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }

    public List<PokemonCard> getCardsByCollectionId(int collectionId) {
        List<PokemonCard> cards = new ArrayList<>();
        String sql = "SELECT * FROM " + PokemonCardRepository.TB_NAME + " WHERE " + TB_COLUMNS.get("COLLECTION_ID") + " = ?";

        try (var conn = connection.getConnection(); var stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, collectionId);
            var rs = stmt.executeQuery();

            while (rs.next()) {
                PokemonCard card = new PokemonCard();
                card.setId(rs.getInt("CARD_ID"));
                card.setName(rs.getString("NAME"));
                card.setCategory(rs.getString("CATEGORY"));
                card.setType(rs.getString("TYPE"));
                card.setHealth(rs.getInt("HEALTH"));
                card.setDescription(rs.getString("DESCRIPTION"));
                card.setCollectionId(rs.getInt("COLLECTION_ID"));
                cards.add(card);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cards;
    }
}
