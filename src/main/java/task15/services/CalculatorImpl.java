package task15.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task15.proxy.CacheProxy;

import java.util.ArrayList;
import java.util.List;

@Service
public class CalculatorImpl implements Calculator {

  private final CacheProxy cacheProxy;

  @Autowired
  public CalculatorImpl(CacheProxy cacheProxy) {
    this.cacheProxy = cacheProxy;
  }

  @Override
  public List<Integer> fibonacci(int n) {
    String cacheKey = "fibonacci_" + n;
    List<Integer> cachedResult = (List<Integer>) cacheProxy.get(cacheKey);
    if (cachedResult != null) {
      System.out.println("Значение взято из кэша.");
      return cachedResult;
    }

    System.out.println("Выполняем вычисления впервые...");
    List<Integer> sequence = new ArrayList<>();
    int a = 0, b = 1;
    for (int i = 0; i < n; i++) {
      sequence.add(a);
      int temp = a + b;
      a = b;
      b = temp;
    }

    cacheProxy.put(cacheKey, sequence);
    return sequence;
  }
}