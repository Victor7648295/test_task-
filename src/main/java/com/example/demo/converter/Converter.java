package com.example.demo.converter;

import java.util.Collection;
import java.util.List;

public interface Converter<S,T> {

    Class<S> getSourceClass();

    Class<T> getTargetClass();

    T convert(S source);

    List<T> convertList(Collection<S> sourceList);

}