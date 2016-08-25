package com.example;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class TestRunner implements CommandLineRunner {
  private static final Logger logger = LoggerFactory.getLogger(TestRunner.class);
  private TestWorker worker;

  @Autowired
  public TestRunner(TestWorker worker, TaskExecutor threadPool){
    this.worker=worker;
  }

  public void run(String... args) {
    List<Integer> list = IntStream.range(0,100).boxed().collect(Collectors.toList());

    CountDownLatch latch = new CountDownLatch(list.size());
    updateNodes(list,latch);
    //try{ latch.await(); }catch(Exception e){}

    latch = new CountDownLatch(list.size());
    updateRelationships(list, latch);
    try{ latch.await(); }catch(Exception e){}
    logger.info("Ingestion is complete");
  }

  private void updateNodes(List<Integer> items, CountDownLatch latch){
    logger.info("start update nodes");
    Lists.partition(items,200).stream().forEach(sublist -> {
      worker.doSomething("node", sublist, latch);
    });
    logger.info("end update nodes");
  }

  private void updateRelationships(List<Integer> items, CountDownLatch latch){
    logger.info("start update relationships");
    Lists.partition(items,50).stream().forEach(sublist -> {
      worker.doSomething("relationship", sublist, latch);
    });
    logger.info("end update relationships");
  }

}
