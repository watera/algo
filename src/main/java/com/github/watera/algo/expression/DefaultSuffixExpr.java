/*
 * Created by Yan Jian on 2017/3/11.
 * Email: yanjian_cn@163.com
 */
package com.github.watera.algo.expression;

public class DefaultSuffixExpr extends AbstractSuffixExpr {
    protected int priority(char c) {
        switch (c) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }

    protected boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }

    protected boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    protected char getBegin(char end) {
        if (end == ')') return '(';
        if (end == ']') return '[';
        if (end == '}') return '{';
        throw new IllegalArgumentException();
    }

    protected boolean isEnd(char c) {
        return c == ')' || c == ']' || c == '}';
    }

    protected boolean isLeft(char c) {
        return c == '(' || c == '[' || c == '{';
    }
}
