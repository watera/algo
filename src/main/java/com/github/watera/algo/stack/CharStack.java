/*
 * Created by Yan Jian on 2017/3/19.
 * Email: yanjian_cn@163.com
 */
package com.github.watera.algo.stack;

import java.util.RandomAccess;

public final class CharStack implements RandomAccess, Cloneable, java.io.Serializable {

    private int head;

    private char[] fData;

    public int size() {
        return head;
    }

    public boolean empty() {
        return head == 0;
    }

    public void push(final char value) {
        ensureCapacity(head + 1);
        fData[head++] = value;
    }

    public char lastElement() {
        return fData[head - 1];
    }

    public char elementAt(final int index) {
        if (index < 0 || index >= head) {
            throw new IndexOutOfBoundsException();
        }
        return fData[index];
    }

    public char pop() {
        return fData[--head];
    }

    public void clear() {
        head = 0;
    }

    @Override
    public String toString() {
        return String.copyValueOf(fData);
    }

    private void ensureCapacity(int size) {
        if (fData == null) {
            fData = new char[32];
        } else if (fData.length <= size) {
            char[] newdata = new char[fData.length * 2];
            System.arraycopy(fData, 0, newdata, 0, fData.length);
            fData = newdata;
        }
    }

}
