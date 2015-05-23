package com.guavus.utils

import java.util.{Arrays, Collections, List => JList}

import org.scalatest.FunSuite
import org.scalatest.Matchers

/**
 * Created by yash.datta on 23/05/15.
 */
class NaturalSortSuite extends FunSuite with Matchers {

  test("basic natural order test") {
    val unorderedList: List[String] = List("part-1", "part-100", "part-99", "part-22", "part-3")
    val resultList: List[String] = List("part-1", "part-3", "part-22", "part-99", "part-100")
    val Comparator = new NaturalComparator
    val sortedList = unorderedList.sortWith(Comparator.compareBool)

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
    val Comparator = new NaturalComparator
    val sortedList = unorderedList.sortWith(Comparator.compareBool)

    sortedList should contain theSameElementsInOrderAs resultList
  }

}
