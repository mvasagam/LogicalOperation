package com.perf.evaluation.LogicalOperationPerformance;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.collections.ListUtils;
import org.roaringbitmap.RoaringBitmap;

import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashSet;

public class LogicalOperation {
  private int maxCount = 500;
  
  public static void main(String[] args) {
    
    int startRange1 = 2000;
    int startRange2 = 200000;
    int startRange3 = 10000000;
    LogicalOperation app = new LogicalOperation();
    //app.processBitSet(startRange1,startRange2,startRange3, 5, 5, 5);
    //app.processGuavaSet(startRange1,startRange2,startRange3, 5, 5, 5);
    app.processJDKList(startRange1,startRange2,startRange3, 5, 5, 5);
    //app.processRoaringBitMap(startRange1,startRange2,startRange3, 5, 5, 5);

  }

  private void processBitSet(int startRange1, int startRange2, int startRange3, int incrementA, int incrementB, int incrementC) {
    BitSet a = new BitSet();
    BitSet b = new BitSet();
    BitSet c = new BitSet();

    loopBitSet(a, startRange1, incrementA);
    loopBitSet(b, startRange1, incrementB);
    loopBitSet(c, startRange1, incrementC);

    loopBitSet(a, startRange2, incrementA);
    loopBitSet(b, startRange2, incrementB);
    loopBitSet(c, startRange2, incrementC);

    loopBitSet(a, startRange3, incrementA);
    loopBitSet(b, startRange3, incrementB);
    loopBitSet(c, startRange3, incrementC);

    System.gc();
    Runtime runtime = Runtime.getRuntime();
    long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();

    // BitSet evaluation (a and b) or c
    long st = System.currentTimeMillis();
    a.and(b);
    a.or(c);
    System.out.println("JDK BitSet totalItems:"+a.cardinality());
    performanceMetrics(startRange1, startRange2, startRange3, incrementA, incrementB, incrementC, st, usedMemoryBefore);
    //System.out.println("Result:"+ a);
  }

  private void performanceMetrics(int startRange1, int startRange2, int startRange3, int incrementA, 
      int incrementB, int incrementC, long startTime, long usedMemoryBefore) {
    String str = String.format("startRange:%s, %s, %s MaxCount:%s increments:%s, %s, %s", 
        startRange1,startRange2,startRange3, maxCount, incrementA,
        incrementB, incrementC);
    System.out.println(str);
    System.out.print("Timetaken=" + (System.currentTimeMillis() - startTime));
    Runtime runtime = Runtime.getRuntime();
    long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
    System.out.println(" Memory increased:" + (usedMemoryAfter - usedMemoryBefore)/1024 +" kB");
    
  }
  
  private void processJDKList(int startRange1, int startRange2, int startRange3, int incrementA, int incrementB, int incrementC) {
    List<Integer> a = new ArrayList<Integer>();
    List<Integer> b = new ArrayList<Integer>();
    List<Integer> c = new ArrayList<Integer>();

    loopList(a, startRange1, incrementA);
    loopList(b, startRange1, incrementB);
    loopList(c, startRange1, incrementC);
    
    loopList(a, startRange2, incrementA);
    loopList(b, startRange2, incrementB);
    loopList(c, startRange2, incrementC);

    loopList(a, startRange3, incrementA);
    loopList(b, startRange3, incrementB);
    loopList(c, startRange3, incrementC);

    System.gc();
    Runtime runtime = Runtime.getRuntime();
    long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();

    // BitSet evaluation (a and b) or c
    long st = System.currentTimeMillis();
    List<Integer> interResult = ListUtils.intersection(a, b);
    List<Integer> result = ListUtils.sum(interResult, c);
    System.out.println("JDK List, Apache Utils totalItems:"+result.size());
    performanceMetrics(startRange1, startRange2, startRange3, incrementA, incrementB, incrementC, st, usedMemoryBefore);
    //System.out.println("Result:"+ result);

  }

  private void processRoaringBitMap(int startRange1, int startRange2, int startRange3, int incrementA, int incrementB, int incrementC) {
    RoaringBitmap a = new RoaringBitmap();
    RoaringBitmap b = new RoaringBitmap();
    RoaringBitmap c = new RoaringBitmap();

    loopRoaringBitmap(a, startRange1, incrementA);
    loopRoaringBitmap(b, startRange1, incrementB);
    loopRoaringBitmap(c, startRange1, incrementC);

    loopRoaringBitmap(a, startRange2, incrementA);
    loopRoaringBitmap(b, startRange2, incrementB);
    loopRoaringBitmap(c, startRange2, incrementC);

    loopRoaringBitmap(a, startRange3, incrementA);
    loopRoaringBitmap(b, startRange3, incrementB);
    loopRoaringBitmap(c, startRange3, incrementC);

    System.gc();
    Runtime runtime = Runtime.getRuntime();
    long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();

    // BitSet evaluation (a and b) or c
    long st = System.currentTimeMillis();
    a.and(b);
    a.and(c);
    System.out.println("Roaring Bitmap totalItems:"+a.getCardinality());
    performanceMetrics(startRange1, startRange2, startRange3, incrementA, incrementB, incrementC, st, usedMemoryBefore);
    //System.out.println("Result:"+ a);
  }

  private void processGuavaSet(int startRange1, int startRange2, int startRange3, int incrementA, int incrementB, int incrementC) {
    Set<Integer> a = new TreeSet<Integer>();
    Set<Integer> b = new TreeSet<Integer>();
    Set<Integer> c = new TreeSet<Integer>();

    loopGuavaSet(a, startRange1, incrementA);
    loopGuavaSet(b, startRange1, incrementB);
    loopGuavaSet(c, startRange1, incrementC);
    
    loopGuavaSet(a, startRange2, incrementA);
    loopGuavaSet(b, startRange2, incrementB);
    loopGuavaSet(c, startRange2, incrementC);

    loopGuavaSet(a, startRange3, incrementA);
    loopGuavaSet(b, startRange3, incrementB);
    loopGuavaSet(c, startRange3, incrementC);

    System.gc();
    Runtime runtime = Runtime.getRuntime();
    long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();

    // BitSet evaluation (a and b) or c
    long st = System.currentTimeMillis();
    SetView setview = Sets.intersection(a, b);
    SetView resSet = Sets.union(setview.immutableCopy(), c);
    System.out.println("Guava totalItems:"+resSet.size());
    performanceMetrics(startRange1, startRange2, startRange3, incrementA, incrementB, incrementC, st, usedMemoryBefore);
    //System.out.println("Result:"+ resSet);
  }
  
  private void loopBitSet(BitSet bs, int startRange, int increment) {
    int limit = startRange+maxCount;
    for (int i = startRange; i < limit; i += increment) {
      bs.set(i);
    }
  }
  

  private void loopList(List list, int startRange, int increment) {
    int limit = startRange+maxCount;
    for (int i = startRange; i < limit; i += increment) {
      list.add(i);
    }
  }
  
  private void loopRoaringBitmap(RoaringBitmap bm, int startRange, int increment) {
    int limit = startRange+maxCount;
    for (int i = startRange; i < limit; i += increment) {
      bm.add(i);
    }
  }
  

  private void loopGuavaSet(Set<Integer> bm, int startRange, int increment) {
    int limit = startRange+maxCount;
    for (int i = startRange; i < limit; i += increment) {
      bm.add(i);
    }
  }

}
