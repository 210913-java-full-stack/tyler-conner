package list;

public interface MyListInterface<T> {
    int size();

    void add(T t);

    void add(T t, int index);

    T get(int index);

    void remove(int index);

    void clear();

    int contains(T t);
}
