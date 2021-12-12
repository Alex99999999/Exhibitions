package com.my.utils.sortBookings;

import java.util.List;

public interface Sortable<E> {

  List <E> sort(List<E> list);
}
