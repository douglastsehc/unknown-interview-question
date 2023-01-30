package model;

import exception.InvalidEntityException;

public interface Canvas {
    void addEntity(Object object) throws InvalidEntityException;
    String render();
}
