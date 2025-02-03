package task15.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import task15.cache.CacheSource;

@Component
public class CacheProxy {

  private final CacheSource cacheSource;

  @Autowired
  public CacheProxy(CacheSource cacheSource) {
    this.cacheSource = cacheSource;
  }

  public Object get(String key) {
    return cacheSource.get(key).orElse(null);
  }

  public void put(String key, Object value) {
    cacheSource.put(key, value);
  }
}