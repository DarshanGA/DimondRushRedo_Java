package org.jarc.entity;

public class Entity {

    public int worldPosX, worldPosY, movementSpeed;

    public enum Directions{

        UP,
        DOWN,
        LEFT,
        RIGHT,
        STILL
    }
}
