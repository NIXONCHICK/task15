package task15.cache;

import java.util.Optional;

public interface CacheSource {

  void put(String key, Object value);
  Optional<Object> get(String key);
  void clear();
}
