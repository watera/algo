/*
 * Created by Yan Jian on 2017/3/11.
 * Email: yanjian_cn@163.com
 */
package com.github.watera.algo.expression;

import java.util.NoSuchElementException;
import java.util.Stack;

public abstract class AbstractSuffixExpr {

    public String toSuffix(String input) {
        return toSuffix(input.toCharArray());
    }

    /**
     * Transferring infix to suffix.
     * <p>See http://blog.csdn.net/yilip/article/details/44775949.
     * @param input the input infix expression string.
     * @return suffix result.
     */
    private String toSuffix(char[] input) {
        char[] out = new char[input.length * 2];
        int outIndex = 0;
        Stack<Character> opStack = new Stack<Character>();
        for (char ch : input) {
            if (isNumber(ch)) {
                out[outIndex++] = ch;
            } else if (isLeft(ch)) {
                opStack.push(ch);
            } else if (isOperator(ch)) {
                int pri = priority(ch);
                while (!opStack.empty() && priority(opStack.lastElement()) >= pri) {
                    out[outIndex++] = opStack.pop();
                }
                opStack.push(ch);
                out[outIndex++] = ' ';
            } else if (isEnd(ch)) {
                char begin = getBegin(ch);
                while (!opStack.empty() && !opStack.lastElement()
                        .equals(begin)) {
                    out[outIndex++] = ' ';
                    out[outIndex++] = opStack.pop();
                }
                if (!opStack.empty()) {
                    opStack.pop();
                } else {
                    throw new NoSuchElementException("Expect " + begin + ", but absent.");
                }
            }
        }
        while (!opStack.empty()) {
            Character pop = opStack.pop();
            if (!isOperator(pop)) {
                throw new IllegalStateException("Only expect operator here.");
            }
            out[outIndex++] = ' ';
            out[outIndex++] = pop;
        }
        return String.copyValueOf(out, 0, outIndex);
    }

    protected abstract int priority(char c);

    protected abstract boolean isNumber(char c);

    protected abstract boolean isOperator(char c);

    protected abstract char getBegin(char end);

    protected abstract boolean isEnd(char ch);

    protected abstract boolean isLeft(char ch);
}
