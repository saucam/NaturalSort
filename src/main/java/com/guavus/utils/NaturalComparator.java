package com.guavus.utils;
/*
 * The Alphanum Algorithm is an improved sorting algorithm for strings
 * containing numbers.  Instead of sorting numbers in ASCII order like
 * a standard sort, this algorithm sorts numbers in numeric order.
 *
 * The Alphanum Algorithm is discussed at http://www.DaveKoelle.com
 *
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 */

import java.util.Comparator;

/**
 * This is an updated version with enhancements made by Daniel Migowski,
 * Andre Bogus, and David Koelle
 *
 * To convert to use Templates (Java 1.5+):
 *   - Change "implements Comparator" to "implements Comparator<String>"
 *   - Change "compare(Object o1, Object o2)" to "compare(String s1, String s2)"
 *   - Remove the type checking and casting in compare().
 *
 * To use this class:
 *   Use the static "sort" method from the java.util.Collections class:
 *   Collections.sort(your list, new NaturalComparator());
 */
public class NaturalComparator implements Comparator<String> {
    private final boolean isDigit(char ch) {
        return ch >= 48 && ch <= 57;
    }

    /** Length of string is passed in for improved efficiency (only need to calculate it once) **/
    private final int getChunkOffset(String s, int slength, int marker) {
        char c = s.charAt(marker);
        marker++;
        if (isDigit(c)) {
            while (marker < slength) {
                c = s.charAt(marker);
                if (!isDigit(c))
                    break;
                marker++;
            }
        } else {
            while (marker < slength) {
                c = s.charAt(marker);
                if (isDigit(c))
                    break;
                marker++;
            }
        }
        return marker;
    }

    public int compareRegion(String s1, int off1, int len1, String s2, int off2, int len2) {
        int lim = Math.min(len1, len2);
        int i = 0;
        while (i < lim) {
            char c1 = s1.charAt(off1+i);
            char c2 = s2.charAt(off2+i);
            if (c1 != c2) {
                return c1 - c2;
            }
            i++;
        }
        return len1 - len2;
    }

    public int compare(String s1, String s2) {
        int thisMarker = 0;
        int thatMarker = 0;
        int s1Length = s1.length();
        int s2Length = s2.length();

        while (thisMarker < s1Length && thatMarker < s2Length) {
            if (s1.charAt(thisMarker) == s2.charAt(thatMarker)) {
                thisMarker++;
                thatMarker++;
                continue;
            }
            int result = 0;
            int thisChunk = getChunkOffset(s1, s1Length, thisMarker);
            int thatChunk = getChunkOffset(s2, s2Length, thatMarker);
            int thisChunkLength = thisChunk - thisMarker;
            int thatChunkLength = thatChunk - thatMarker;
            // If both chunks contain numeric characters, sort them numerically
            if (isDigit(s1.charAt(thisMarker)) && isDigit(s2.charAt(thatMarker))) {
                // Simple chunk comparison by length.
                result = thisChunkLength - thatChunkLength;
                // If equal, the first different number counts
                if (result == 0) {
                    for (int i = thisMarker, j = thatMarker; i < thisChunk; i++, j++) {
                        result = s1.charAt(i) - s2.charAt(j);
                        if (result != 0) {
                            return result;
                        }
                    }
                }
            } else {
                result = compareRegion(s1, thisMarker, thisChunkLength, s2, thatMarker, thatChunkLength);
            }

            if (result != 0)
                return result;

            thisMarker = thisChunk;
            thatMarker = thatChunk;
        }

        return s1Length - s2Length;
    }

    public boolean compareBool(String s1, String s2) {
        return (compare(s1, s2) < 0 );
    }
}
