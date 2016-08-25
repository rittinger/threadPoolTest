package com.example.util;

public class ThreadUtil {

  public static void sleepFor(long milliseconds) {
    try {
      Thread.sleep(milliseconds);
    } catch (InterruptedException e) {
    }
  }

  public static void sleepAtLeast(long min, long randomDuration) {
    ThreadUtil.sleepFor(min + (long) (Math.random() * randomDuration));
  }

  public static void sleepAtLeast(long min) {
    ThreadUtil.sleepAtLeast(min, 1000L);
  }

  private ThreadUtil() {
    throw new UnsupportedOperationException("can't instantiate utility class");
  }
}
