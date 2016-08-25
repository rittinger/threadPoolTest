package com.example;

import com.example.util.ThreadUtil;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class TestWorker {
  Set<Integer> nodes = new ConcurrentSkipListSet<>();
  Set<Integer> relationships = new ConcurrentSkipListSet<>();
  private Logger logger = LoggerFactory.getLogger(TestWorker.class);

  @Async("testThreadPool")
  public void doSomething(String type, List<Integer> list, CountDownLatch latch) {
    logger.info("Updating {} {}s", list.size(), type);
    list.forEach(item -> {
      if( type.equals("relationship") && !nodes.contains(item)){
        throw new IllegalStateException("Node not found, relationship creation has failed for item "+item);
      }
      //ThreadUtil.sleepAtLeast(10L);
      if( type.equals("node")) {
        nodes.add(item);
      }else{
        relationships.add(item);
      }
      latch.countDown();
      logger.info("Nodes ({}), relationships ({})",nodes.size(),relationships.size());
    });
  }
}
