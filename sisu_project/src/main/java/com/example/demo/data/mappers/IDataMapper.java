package com.example.demo.data.mappers;

import java.util.Map;

// Not used at the moment
// Idea of this interface is that it can be used when adding new APIs

public interface IDataMapper<T> {
    T mapIncomingData(T rawData);
}