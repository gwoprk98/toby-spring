package springbook.calculator;

public interface LineCallback<T> {
    T executeWithLine(String T, T value);
}
