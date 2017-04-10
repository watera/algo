/*
 * Created by Yan Jian on 2017/4/9.
 * Email: yanjian_cn@163.com
 */
package com.github.watera.algo.whitelist;

import sun.net.util.IPAddressUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Ipv4WhiteList {

    private final int[] begins;
    private final int[] ends;

    // alpha
    public static Ipv4WhiteList build(List<String> confs) {
        List<Range> srcRanges = new ArrayList<Range>();
        for (String conf : confs) {
            srcRanges.add(textToRange(conf));
        }
        Collections.sort(srcRanges);
        List<Range> inputRanges = merge(srcRanges);
        int length = inputRanges.size();
        int[] begins = new int[length];
        int[] ends = new int[length];
        int index = 0;
        for (Range r : inputRanges) {
            begins[index] = r.begin;
            ends[index] = r.end;
            index++;
        }
        return new Ipv4WhiteList(begins, ends);
    }

    // todo
    private static List<Range> merge(List<Range> confs) {
        return confs;
    }

    Ipv4WhiteList(int[] begins, int[] ends) {
        this.begins = begins;
        this.ends = ends;
    }

    public boolean matches(final int ipv4) {
        final int index = Arrays.binarySearch(begins, ipv4);
        // found in begins or (found in ranges)
        return index >= 0 || (index != -1 && ipv4 <= ends[-2 - index]);
    }

    public boolean matches(final String ipv4) {
        return matches(bytesToInt(IPAddressUtil.textToNumericFormatV4(ipv4)));
    }

    private static int bytesToInt(byte[] addr) {
        int intAddr = 0;
        for (byte b : addr) {
            intAddr <<= 8;
            intAddr |= b & 0xFF;
        }
        return intAddr;
    }

    // alpha
    private static Range textToRange(final String text) {
        String[] words = text.split("\\.");
        byte[] ipaddr = new byte[4];
        for (int i = 0; i < 3; i++) {
            ipaddr[i] = textToByte(words[i]);
        }
        if ("*".equals(words[3])) {
            ipaddr[3] = 0;
            int begin = bytesToInt(ipaddr);
            ipaddr[3] = (byte) 0xFF;
            int end = bytesToInt(ipaddr);
            return new Range(begin, end);
        } else if (words[3].contains("-")) {
            String[] suffixes = words[3].split("-");
            ipaddr[3] = textToByte(suffixes[0]);
            int begin = bytesToInt(ipaddr);
            ipaddr[3] = textToByte(suffixes[1]);
            int end = bytesToInt(ipaddr);
            return new Range(begin, end);
        } else {
            ipaddr[3] = textToByte(words[3]);
            int begin = bytesToInt(ipaddr);
            int end = begin;
            return new Range(begin, end);
        }
    }

    private static byte textToByte(String text) {
        return (byte) (Integer.parseInt(text));
    }

    private static class Range implements Comparable<Range> {
        private final int begin;
        private final int end;

        @SuppressWarnings("Since15")
        Range(int begin, int end) {
            this.begin = Integer.min(begin, end);
            this.end = Integer.max(begin, end);
        }

        @SuppressWarnings("Since15")
        public int compareTo(Range o) {
            return Integer.compare(this.begin, o.begin);
        }
    }
}
