package io.luxus.lib.adofai.util;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ListUtil {

    /**
     * copy array as unmodifiable XY List
     *
     * @throws NullPointerException when src is null
     * @throws IllegalArgumentException when size of src is not 1 or 2
     * @param src source list
     * @param <T> type of source list
     * @return copied XY List
     */
    public static <T> List<T> toUnmodifiableXYList(List<T> src) {
        if (src.size() == 1) {
            T value = src.get(0);
            return Collections.unmodifiableList(
                    Arrays.asList(value, value));
        }
        else if (src.size() == 2) {
            return Collections.unmodifiableList(
                    Arrays.asList(src.get(0), src.get(1)));
        }
        else {
            throw new IllegalArgumentException("size of src must be 1 or 2");
        }
    }

    /**
     * creates new unmodifiable list.
     * only use to immutable type
     *
     * @param src source list of immutable object
     * @param <T> type of list
     * @return created new unmodifiable list
     */
    public static <T> List<T> createNewUnmodifiableList(List<T> src) {
        if (src != null) {
            return Collections.unmodifiableList(new ArrayList<>(src));
        }
        return null;
    }

    /**
     * creates new numodifiable list.
     * you can use with mutable type.
     * you should give copier when deal with mutable type.
     *
     * @param src source list of mutable object
     * @param copier copier of mutable object
     * @param <T> type of list
     * @return created new unmodifiable list
     */
    public static <T> List<T> createNewUnmodifiableList(List<T> src, Function<T, T> copier) {
        if (copier == null) {
            return createNewUnmodifiableList(src);
        }
        Objects.requireNonNull(src);
        return Collections.unmodifiableList(
                src.stream().map(copier).collect(Collectors.toList()));
    }

}
