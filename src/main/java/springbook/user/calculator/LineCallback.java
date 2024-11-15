package springbook.user.calculator;

public interface LineCallback<T> {
    T executeWithLine(String T, int value);
}
