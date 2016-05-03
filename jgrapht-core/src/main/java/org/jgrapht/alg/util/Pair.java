/* ==========================================
 * JGraphT : a free Java graph-theory library
 * ==========================================
 *
 * Project Info:  http://jgrapht.sourceforge.net/
 * Project Creator:  Barak Naveh (http://sourceforge.net/users/barak_naveh)
 *
 * (C) Copyright 2003-2008, by Barak Naveh and Contributors.
 *
 * This program and the accompanying materials are dual-licensed under
 * either
 *
 * (a) the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation, or (at your option) any
 * later version.
 *
 * or (per the licensee's choosing)
 *
 * (b) the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation.
 */
/* -----------------
 * Pair.java
 * -----------------
 * (C) Copyright 2015-2015, by Alexey Kudinkin and Contributors.
 *
 * Original Author:  Alexey Kudinkin
 * Contributor(s):
 *
 * $Id$
 *
 * Changes
 * -------
 */

package org.jgrapht.alg.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Generic pair.
 * <br>
 * Although the instances of this class are immutable, it is impossible
 * to ensure that the references passed to the constructor will not be
 * modified by the caller.
 *
 */
public class Pair<A, B> {

    public A first;
    public B second;

    public Pair(A a, B b) {
        this.first  = a;
        this.second = b;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!(other instanceof Pair)){
            return false;
        }
        Pair<?, ?> other_ = (Pair<?, ?>) other;
        return Objects.equals(this.first, other_.first) &&
                Objects.equals(this.second, other_.second);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Objects.hashCode(first);
        result = prime * result + Objects.hashCode(second);
        return result;
    }

    /**
     * Creates new pair of elements pulling of the necessity to provide corresponding
     * types of the elements supplied
     *
     * @param a first element
     * @param b second element
     * @return new pair
     */
    public static <A, B> Pair<A, B> of(A a, B b) {
        return new Pair<A, B>(a, b);
    }

    /**
     * Pair combinations.
     *
     * @param originalSet the original set
     * @return the list
     */
    public static <T> List<Pair<T, T>> pairCombinations(Set<T> originalSet) {
        List<T> list = new ArrayList<T>(originalSet);
        int n = list.size();
        List<Pair<T, T>> pairsList = new ArrayList<Pair<T, T>>();
        T iv;
        T jv;
        for (int i = 0; i < n; i++) {
            iv = list.get(i);
            for (int j = 0; j < n; j++) {
                if (i == j) continue;
                jv = list.get(j);
                pairsList.add(of(iv,jv));
            }
        }
        return pairsList;
    }
}
