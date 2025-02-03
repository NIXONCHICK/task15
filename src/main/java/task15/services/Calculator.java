package task15.services;

import task15.annotations.Cachable;
import task15.cache.H2DB;

import java.util.List;

public interface Calculator {
  @Cachable(H2DB.class)
  List<Integer> fibonacci(int n);
}
