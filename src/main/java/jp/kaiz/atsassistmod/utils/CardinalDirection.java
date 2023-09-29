package jp.kaiz.atsassistmod.utils;

import jp.ngt.rtm.entity.train.EntityBogie;
import jp.ngt.rtm.entity.train.EntityTrainBase;

public enum CardinalDirection {
    NORTH("NOTRH", false, Axis.Z), EAST("EAST", true, Axis.X), SOUTH("SOUTH", true, Axis.Z), WEST("WEST", false, Axis.X);

    private final String name;
    private final boolean positive;
    private final Axis axis;

    CardinalDirection(String name, boolean positive, Axis axis) {
        this.name = name;
        this.positive = positive;
        this.axis = axis;
    }

    public String getName() {
        return name;
    }

    public boolean isInDirection(EntityTrainBase entity) {
        int dir = entity.getTrainDirection();
        EntityBogie front = entity.getBogie(dir == 0 ? 0 : 1);
        EntityBogie back = entity.getBogie(dir == 0 ? 1 : 0);

        switch (axis) {
            case X:
                return positive == front.posX > back.posX;
            case Z:
                return positive == front.posZ > back.posZ;
            default:
                return false;
        }
    }

    public static CardinalDirection getDirection(String name) {
        try {
            return CardinalDirection.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return CardinalDirection.NORTH;
        }
    }

    private enum Axis {
        X, Z
    }
}
