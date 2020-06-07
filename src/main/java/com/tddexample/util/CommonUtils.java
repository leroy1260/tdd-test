package com.tddexample.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class CommonUtils {

    public static List<Integer> inclusiveRange(int from, int limit) {

        return IntStream.rangeClosed(from, limit)
                .boxed()
                .collect(toList());
    }

    public static List<Integer> inclusiveRangeWithStep500(int from, int to) {
        int step = 500;
        return IntStream.range(0, ((to - from) / step) + 1) // +1 depends on inclusve or exclusive
                .mapToObj(i -> (from + i * step))
                .collect(Collectors.toList());
    }

    // Generic function to split a list into two sublists in Java
    public static<T> List[] split(List<T> list)
    {
        // get size of the list
        int size = list.size();

        // construct new list from the returned view by list.subList() method
        List<T> first = new ArrayList<>(list.subList(0, (size + 1)/2));
        List<T> second = new ArrayList<>(list.subList((size + 1)/2, size));

        // return an List array to accommodate both lists
        return new List[] {first, second};
    }

    public static <T> List<T> union(List<T> list1, List<T> list2) {
        List<T> newList = new ArrayList<>();
        newList.addAll(list1);
        newList.addAll(list2);
        return Collections.unmodifiableList(newList);
    }
}
