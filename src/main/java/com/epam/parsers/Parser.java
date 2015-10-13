package com.epam.parsers;

public interface Parser<E, T> {
    E parse(T o);
}
