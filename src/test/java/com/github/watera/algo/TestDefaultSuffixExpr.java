/*
 * Created by Yan Jian on 2017/3/11.
 * Email: yanjian_cn@163.com
 */
package com.github.watera.algo;

import com.github.watera.algo.expression.AbstractSuffixExpr;
import com.github.watera.algo.expression.DefaultSuffixExpr;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TestDefaultSuffixExpr {
    private static AbstractSuffixExpr expr = new DefaultSuffixExpr();

    @Test
    public void testSimple() {
        test("1+2", "1 2 +");
    }

    @Test
    public void testComplex() {
        test("(1 + 2) * 3", "1 2 + 3 *");
    }

    @Test
    public void testSlice() {
        test("2000 - {1 + (3 -5)}/2 ", "2000 1 3 5 - + 2 / -");
    }

    @Test(expected = IllegalStateException.class)
    public void testUnmatch() {
        test("((1 + 2) * 3", "1 2 + 3 * (");
    }

    private void test(String input, String expectOut) {
        String str = expr.toSuffix(input);
        assertThat(str, is(expectOut));
    }
}
