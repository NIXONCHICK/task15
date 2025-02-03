package task15.annotations;

import task15.cache.CacheSource;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cachable {
  Class<? extends CacheSource> value();
}
