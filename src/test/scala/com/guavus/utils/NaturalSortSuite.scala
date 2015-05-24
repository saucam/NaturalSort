package com.guavus.utils

import com.google.common.base.Stopwatch
import com.google.common.io.Files

import java.io.File
import java.nio.charset.Charset
import java.util.{Arrays, ArrayList, Collections, List => JList}

import org.scalatest.FunSuite
import org.scalatest.Matchers

import scala.collection.JavaConverters._
/**
 * Created by yash.datta on 23/05/15.
 */
class NaturalSortSuite extends FunSuite with Matchers {

  test("basic natural order test") {
    val unorderedList: List[String] = List("part-1", "part-100", "part-99", "part-22", "part-3")
    val resultList: List[String] = List("part-1", "part-3", "part-22", "part-99", "part-100")
    val sortedList = unorderedList.sortWith(NaturalComparatorObj.compareBool)

    sortedList should contain theSameElementsInOrderAs resultList
  }

  test("basic natural order test JavaList") {
    val unorderedList: JList[String] = Arrays.asList("part-1", "part-100", "part-99", "part-22", "part-3")
    val resultList: List[String] = List("part-1", "part-3", "part-22", "part-99", "part-100")
    Collections.sort(unorderedList, new NaturalComparator)

    unorderedList should contain theSameElementsInOrderAs resultList
  }

  test("natural order test with only alphabets") {
    val unorderedList: List[String] = List("abc", "x", "xyz", "tkl", "mno", "y", "x", "acb")
    val resultList: List[String] = List("abc", "acb", "mno", "tkl", "x", "x", "xyz", "y")
    val sortedList = unorderedList.sortWith(NaturalComparatorObj.compareBool)

    sortedList should contain theSameElementsInOrderAs resultList
  }

  test("natural order test with only alphabets JavaList") {
    val unorderedList: JList[String] = Arrays.asList("abc", "x", "xyz", "tkl", "mno", "y", "x", "acb")
    val resultList: List[String] = List("abc", "acb", "mno", "tkl", "x", "x", "xyz", "y")
    Collections.sort(unorderedList, new NaturalComparator)

    unorderedList should contain theSameElementsInOrderAs resultList
  }

  test("natural order test with only numerals") {
    val unorderedList: List[String] = List("123", "234", "9", "560", "12345", "123", "10", "1")
    val resultList: List[String] = List("1", "9", "10", "123", "123", "234", "560", "12345")
    val sortedList = unorderedList.sortWith(NaturalComparatorObj.compareBool)

    sortedList should contain theSameElementsInOrderAs resultList
  }

  test("natural order test with only numerals JavaList") {
    val unorderedList: JList[String] = Arrays.asList("123", "234", "9", "560", "12345", "123", "10", "1")
    val resultList: List[String] = List("1", "9", "10", "123", "123", "234", "560", "12345")
    Collections.sort(unorderedList, new NaturalComparator)

    unorderedList should contain theSameElementsInOrderAs resultList
  }

  test("complex natural order test") {
    val unorderedList: List[String] = List("this54complex", "this100complex90", "this23", "this23more", "this99complex90", "54complex")
    val resultList: List[String] = List("54complex", "this23", "this23more", "this54complex", "this99complex90", "this100complex90")
    val sortedList = unorderedList.sortWith(NaturalComparatorObj.compareBool)

    sortedList should contain theSameElementsInOrderAs resultList
  }

  test("complex natural order test JavaList") {
    val unorderedList: JList[String] = Arrays.asList("this54complex", "this100complex90", "this23", "this23more", "this99complex90", "54complex")
    val resultList: List[String] = List("54complex", "this23", "this23more", "this54complex", "this99complex90", "this100complex90")
    Collections.sort(unorderedList, new NaturalComparator)

    unorderedList should contain theSameElementsInOrderAs resultList
  }

  test("performance benchmark 1") {
    val filePath = getClass.getClassLoader.getResource("perftest/perfinput.txt").getFile
    val unorderedJList: JList[String] = Files.readLines(new File(filePath), Charset.forName("utf-8"))
    val unorderedList: List[String] = unorderedJList.asScala.toList

    var time: Stopwatch = null
    // repeated for warming up the JVM
    for(i <- 0 to 7) {
      val list2 = new ArrayList(unorderedJList)

      val timer1 = Stopwatch.createStarted()
      Collections.sort(list2, new NaturalComparator)
      time = timer1.stop
    }
    System.out.println("Time taken to natural sort 1 million entries (java implementation): " + time)

    // repeated for warming up the JVM
    for(i <- 0 to 7) {
      val timer1 = Stopwatch.createStarted()
      val sortedList = unorderedList.sortWith(NaturalComparatorObj.compareBool)
      time = timer1.stop
    }
    System.out.println("Time taken to natural sort 1 million entries (scala implementation): " + time)


    // repeated for warming up the JVM
    for(i <- 0 to 7) {
      val list2 = new ArrayList(unorderedJList)

      val timer1 = Stopwatch.createStarted()
      Collections.sort(list2)
      time = timer1.stop
    }
    System.out.println("Time taken to lexicographically sort 1 million entries: " + time)

  }

}

