package pl.zakumaj.bankapp2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Database {
    Connection conn = null;

    public List<String> listUsers() throws SQLException {
        List<String> listUsers = new ArrayList<>();
        ResultSet resultSet = conn.prepareStatement("""
                                                            
                                             SELECT  * FROM users;                                          
                """).executeQuery();
        while (resultSet.next()) {
            listUsers.add(resultSet.getString("phone_number"));
        }
        return listUsers;
    }

    public void connect() throws SQLException {


        conn =
                DriverManager.getConnection("jdbc:mysql://localhost:3306/bank-app?" +
                        "user=user&password=default");
        conn.prepareStatement("""
                CREATE TABLE IF NOT EXISTS users (
                    id           INTEGER NOT NULL,
                    area_code    VARCHAR(3) NOT NULL,
                    phone_number VARCHAR(10) NOT NULL,
                    first_name   VARCHAR(20) NOT NULL,
                    last_name    VARCHAR(20) NOT NULL,
                    birth_date   DATE NOT NULL,
                    password     VARCHAR(256) NOT NULL,
                    PRIMARY KEY(id)
                );
                    """).execute();

        conn.prepareStatement("""
                CREATE TABLE IF NOT EXISTS accounts (
                    id       INTEGER NOT NULL,
                    `name`   VARCHAR(20),
                    `user`   INTEGER NOT NULL,
                    currency VARCHAR(3) NOT NULL,
                    PRIMARY KEY(id)
                                              );
                                    """).execute();

        conn.prepareStatement("""
                               CREATE TABLE IF NOT EXISTS currencies (
                                   code VARCHAR(3) NOT NULL,
                                   `name` VARCHAR(100) NOT NULL,
                                   PRIMARY KEY(code)
                );
                                                    """).execute();
        conn.prepareStatement("""
                              CREATE TABLE IF NOT EXISTS deposits (
                                   id        INTEGER NOT NULL,
                                   amount    DECIMAL(15,2) NOT NULL,
                                   `date`     DATE NOT NULL,
                                   account_id INTEGER NOT NULL,
                                   PRIMARY KEY(id)
                );
                                                    """).execute();

        conn.prepareStatement("""
                              CREATE TABLE IF NOT EXISTS transactions (
                                   id        INTEGER NOT NULL,
                                   amount    DECIMAL(15,2) NOT NULL,
                                   `date`    DATE NOT NULL,
                                   sender    INTEGER NOT NULL,
                                   recipient INTEGER NOT NULL,
                                   PRIMARY KEY(id)
                );
                                                    """).execute();
        conn.prepareStatement("""
                 ALTER TABLE accounts
                        ADD FOREIGN KEY ( currency )
                        REFERENCES currencies ( code );
                """).execute();
        conn.prepareStatement("""
                        ALTER TABLE accounts
                        ADD FOREIGN KEY (`user`)
                        REFERENCES users ( id );
                """).execute();
        conn.prepareStatement("""
                        ALTER TABLE deposits
                        ADD FOREIGN KEY ( account_id )
                        REFERENCES accounts ( id );
                """).execute();
        conn.prepareStatement("""
                        ALTER TABLE transactions
                        ADD FOREIGN KEY ( recipient )
                        REFERENCES accounts ( id );
                """).execute();
        conn.prepareStatement("""
                ALTER TABLE transactions
                ADD  FOREIGN KEY ( sender )
                REFERENCES accounts ( id )
                                            """).execute();


    }

    public static void main(String[] args) throws SQLException {
        Database database = new Database();
        database.connect();
        database.listUsers();

    }
}
