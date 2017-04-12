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

    Ipv4WhiteList(int[] begins, int[] ends) {
        int length = begins.length;
        assert length == ends.length;
        for (int i = 0; i < length; i++) {
            assert begins[i] <= ends[i];
        }
        for (int i = 1; i < length; i++) {
            assert begins[i - 1] <= begins[i];
            assert ends[i - 1] <= ends[i];
        }
        this.begins = begins;
        this.ends = ends;
    }

    public static Ipv4WhiteList build(List<String> confs) {
        List<Range> inputRanges = mapToRanges(confs);
        int length = inputRanges.size();
        int[] begins = new int[length];
        int[] ends = new int[length];
        int index = 0;
        int maxEnd = Integer.MIN_VALUE;
        for (Range r : inputRanges) {
            begins[index] = r.begin;
            if (r.end >= maxEnd) {
                ends[index] = r.end;
            } else {
                ends[index] = maxEnd;
                maxEnd = r.end;
            }
            index++;
        }
        return new Ipv4WhiteList(begins, ends);
    }

    private static List<Range> mapToRanges(List<String> confs) {
        List<Range> inputRanges = new ArrayList<Range>();
        for (String conf : confs) {
            inputRanges.add(textToRange(conf));
        }
        Collections.sort(inputRanges);
        return inputRanges;
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

    public boolean matches(final int ipv4) {
        final int index = Arrays.binarySearch(begins, ipv4);
        // found in begins or (found in ranges)
        return index >= 0 || (index != -1 && ipv4 <= ends[-2 - index]);
    }

    public boolean matches(final String ipv4) {
        return matches(bytesToInt(IPAddressUtil.textToNumericFormatV4(ipv4)));
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
