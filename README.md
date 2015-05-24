# NaturalSort

This is port of the AplhaNum algorithm as described here: 

http://www.davekoelle.com/alphanum.html 

with optimizations and some test cases to illustrate what all cases are covered.

It has both java and scala implementations.

Compilation

To compile/package :

mvn compile/package (from the root dir)


Tests

To run tests :

mvn clean test

The last benchmark test is repeated 7 times before reporting sorting time needed to sort a million alphanumeric strings.
The test also reports time needed to lexicographically sort the same entries, in order to compare degradation from
normal sort.
The benchmark test is for now using strings having a common prefix 'part-'

Other benchmarks will be added later.

