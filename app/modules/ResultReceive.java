package modules;

public interface ResultReceive<T> {
    void add(T t);

    T get();

    boolean isClosed();
}
