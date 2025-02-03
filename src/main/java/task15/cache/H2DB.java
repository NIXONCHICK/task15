package task15.cache;

import org.springframework.stereotype.Component;

import java.io.*;
import java.sql.*;
import java.util.Optional;

@Component
public class H2DB implements CacheSource {
  private static final String DB_URL = "jdbc:h2:./cacheDB";

  public H2DB() {
    try (Connection connection = DriverManager.getConnection(DB_URL);
         Statement stmt = connection.createStatement()) {
      stmt.execute(
          "CREATE TABLE IF NOT EXISTS cache (cache_key VARCHAR(255) PRIMARY KEY, cache_value BLOB)"
      );
    } catch (SQLException e) {
      throw new RuntimeException("Ошибка при создании таблицы кэша", e);
    }
  }

  @Override
  public void put(String key, Object value) {
    try (Connection connection = DriverManager.getConnection(DB_URL);
         PreparedStatement stmt = connection.prepareStatement(
             "MERGE INTO cache (cache_key, cache_value) VALUES (?, ?)"
         )) {
      stmt.setString(1, key);
      stmt.setBytes(2, serialize(value));
      stmt.executeUpdate();
    } catch (SQLException | IOException e) {
      throw new RuntimeException("Ошибка при сохранении в кэш", e);
    }
  }

  @Override
  public Optional<Object> get(String key) {
    try (Connection connection = DriverManager.getConnection(DB_URL);
         PreparedStatement stmt = connection.prepareStatement(
             "SELECT cache_value FROM cache WHERE cache_key = ?"
         )) {
      stmt.setString(1, key);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return Optional.ofNullable(deserialize(rs.getBytes("cache_value")));
      }
    } catch (SQLException | IOException | ClassNotFoundException e) {
      throw new RuntimeException("Ошибка при получении из кэша", e);
    }
    return Optional.empty();
  }

  @Override
  public void clear() {
    try (Connection connection = DriverManager.getConnection(DB_URL);
         Statement stmt = connection.createStatement()) {
      stmt.execute("DELETE FROM cache");
    } catch (SQLException e) {
      throw new RuntimeException("Ошибка при очистке кэша", e);
    }
  }

  private byte[] serialize(Object obj) throws IOException {
    try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(bos)) {
      oos.writeObject(obj);
      return bos.toByteArray();
    }
  }

  private Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
    try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
         ObjectInputStream ois = new ObjectInputStream(bis)) {
      return ois.readObject();
    }
  }
}
