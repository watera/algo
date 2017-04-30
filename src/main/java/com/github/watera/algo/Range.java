/*
 * Created by Yan Jian on 2017/4/29.
 * Email: yanjian_cn@163.com
 */
package com.github.watera.algo;

import com.google.common.base.MoreObjects;

public final class Range implements Comparable<Range> {
    private final int begin;
    private final int end;

    public Range(int begin, int end) {
        this.begin = Integer.min(begin, end);
        this.end = Integer.max(begin, end);
    }

    public int getBegin() {
        return begin;
    }

    public int getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("begin", begin)
                .add("end", end)
                .toString();
    }

    public int compareTo(Range o) {
        return Integer.compare(this.begin, o.begin);
    }
}
