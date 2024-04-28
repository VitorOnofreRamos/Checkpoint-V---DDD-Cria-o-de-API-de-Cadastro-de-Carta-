package org.fiap.com.br.repositories;

import org.fiap.com.br.infrastructure.OracleDbConfiguration;
import org.fiap.com.br.utils.Log4jLogger;
import org.fiap.com.br.utils._Logger;

import java.sql.SQLException;

public class StarterDb{
    public void initialize() {
        try (var conn = new OracleDbConfiguration().getConnection()) {


            try (var stmt = conn.prepareStatement("BEGIN EXECUTE IMMEDIATE 'DROP TABLE " + PokemonCardRepository.TB_NAME + "'; EXCEPTION WHEN OTHERS THEN NULL; END;")) {
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try (var stmt = conn.prepareStatement("BEGIN EXECUTE IMMEDIATE 'DROP TABLE " + PokemonCollectionRepository.TB_NAME + "'; EXCEPTION WHEN OTHERS THEN NULL; END;")) {
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try (var stmt = conn.prepareStatement(
                    "CREATE TABLE " + PokemonCollectionRepository.TB_NAME + " (" +
                            "COLLECTION_ID NUMBER GENERATED AS IDENTITY CONSTRAINT COLLECTION_PK PRIMARY KEY, " +
                            "NAME VARCHAR2(255) NOT NULL, " +
                            "DESCRIPTION VARCHAR2(1000) " +
                            ")")){
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try (var stmt = conn.prepareStatement(
                    "CREATE TABLE " + PokemonCardRepository.TB_NAME + " (" +
                            "CARD_ID NUMBER GENERATED AS IDENTITY CONSTRAINT CARDS_PK PRIMARY KEY, " +
                            "NAME VARCHAR2(255) NOT NULL, " +
                            "CATEGORY VARCHAR2(255) NOT NULL, " +
                            "TYPE VARCHAR2(255) NOT NULL, " +
                            "HEALTH NUMBER NOT NULL, " +
                            "DESCRIPTION VARCHAR2(1000), " +
                            "COLLECTION_ID NUMBER "+
                            ")")){
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try (var stmt = conn.prepareStatement("ALTER TABLE "+PokemonCardRepository.TB_NAME+" ADD CONSTRAINT CARD_COLLECTION_FK FOREIGN KEY(COLLECTION_ID) REFERENCES "+PokemonCollectionRepository.TB_NAME+"(COLLECTION_ID)")) {
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}