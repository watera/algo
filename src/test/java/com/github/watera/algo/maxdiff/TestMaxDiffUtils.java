/*
 * Created by Yan Jian on 2017/4/30.
 * Email: yanjian_cn@163.com
 */
package com.github.watera.algo.maxdiff;

import com.github.watera.algo.Range;
import org.junit.Test;

import java.util.function.Function;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TestMaxDiffUtils {

    @Test
    public void testSimple() {
        testFun(MaxDiffUtils::simple);
    }

    @Test
    public void testDp() {
        testFun(MaxDiffUtils::dp);
    }

    private static void testFun(final Function<int[], Range> func) {
        int[] testArray = new int[] { 5, -1, 2, 6, -8 };
        Range range = func.apply(testArray);
        assertThat(range.getBegin(), is(1));
        assertThat(range.getEnd(), is(3));
    }
}
