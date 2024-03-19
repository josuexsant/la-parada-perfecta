package model;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
public class DBManager {
    static Properties props = new Properties();
    String hostname = null;
    String port = null;
    String database = null;
    String username = null;
    String password = null;

    public DBManager() {
        try (InputStream in = Files.newInputStream(FileSystems.getDefault().getPath(System.getProperty("user.dir") +
                File.separator + "src/main/db_props.properties.properties"))) {
            props.load(in);
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        loadProperties();
    }

    public final void loadProperties() {
        this.hostname = props.getProperty("host_name");
        port = props.getProperty("port_number");
        database = props.getProperty("db_name");
        username = props.getProperty("username");
        password = props.getProperty("password");
    }

    public Connection getConnection() throws SQLException {
        Connection conn = null;
        String jdbcUrl;
        jdbcUrl = "jdbc:mysql://" + this.hostname + ":" +
                this.port + "/" + this.database;
        conn = DriverManager.getConnection(jdbcUrl, this.username,
                this.password);
        Log.info("Conectado a la base de datos");
        return conn;
    }
}
