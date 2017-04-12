/*
 * Created by Yan Jian on 2017/4/9.
 * Email: yanjian_cn@163.com
 */
package com.github.watera.algo.whitelist;

import org.junit.Test;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TestIpv4WhiteList {
    private final AtomicReference<Ipv4WhiteList> ipv4WhiteListRef = new
            AtomicReference<Ipv4WhiteList>(
            Ipv4WhiteList.build(Collections.singletonList("127.0.0.1")));

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
        Ipv4WhiteList iw = Ipv4WhiteList.build(Collections.singletonList(conf));
        assertThat(iw.matches("192.168.1.20"), is(true));
        assertThat(iw.matches("192.168.1.41"), is(false));
    }

    @Test
    public void testRef() {
        assertThat(matches("127.0.0.1"), is(true));
        Ipv4WhiteList niw = Ipv4WhiteList.build(Collections.singletonList("127.0.0.2"));
        ipv4WhiteListRef.set(niw);
        assertThat(matches("127.0.0.1"), is(false));
        assertThat(matches("127.0.0.2"), is(true));
    }

    private boolean matches(String ipv4) {
        Ipv4WhiteList iw = ipv4WhiteListRef.get();
        return iw.matches(ipv4);
    }
}
