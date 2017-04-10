/*
 * Created by Yan Jian on 2017/4/9.
 * Email: yanjian_cn@163.com
 */
package com.github.watera.algo.whitelist;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TestIpv4WhiteList {
    @Test
    public void testMatch() {
        int[] begins = new int[] { 1, 5 };
        int[] ends = new int[] { 3, 7 };
        Ipv4WhiteList iw = new Ipv4WhiteList(begins, ends);
        assertThat(iw.matches(1), is(true));
    }

    @Test
    public void testBuild() {
        String conf = "192.168.1.20-40";
        Ipv4WhiteList iw = Ipv4WhiteList.build(Arrays.asList(conf));
        assertThat(iw.matches("192.168.1.20"), is(true));
        assertThat(iw.matches("192.168.1.41"), is(false));
    }
}
