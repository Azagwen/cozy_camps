package net.kings_of_devs.cozy_camping.block.state;

import com.google.common.collect.Maps;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.Direction;

import java.util.Map;

public enum StumpShape implements StringIdentifiable {
    VERTICAL("vertical", StumpShapeHelper.CENTER, Direction.Axis.Y, StumpShapeDirection.NONE),

    X_CENTERED("x_centered", StumpShapeHelper.CENTER * StumpShapeHelper.X_MULTIPLIER, Direction.Axis.X, StumpShapeDirection.NONE),
    X_CENTERED_LARGE("x_centered_large", StumpShapeHelper.CENTER_LARGE * StumpShapeHelper.X_MULTIPLIER, Direction.Axis.X, StumpShapeDirection.BOTH),
    X_POSITIVE("x_positive", StumpShapeHelper.POSITIVE * StumpShapeHelper.X_MULTIPLIER, Direction.Axis.X, StumpShapeDirection.POSITIVE),
    X_NEGATIVE("x_negative", StumpShapeHelper.NEGATIVE * StumpShapeHelper.X_MULTIPLIER, Direction.Axis.X, StumpShapeDirection.NEGATIVE),

    Z_CENTERED("z_centered", StumpShapeHelper.CENTER * StumpShapeHelper.Z_MULTIPLIER, Direction.Axis.Z, StumpShapeDirection.NONE),
    Z_CENTERED_LARGE("z_centered_large", StumpShapeHelper.CENTER_LARGE * StumpShapeHelper.Z_MULTIPLIER, Direction.Axis.Z, StumpShapeDirection.BOTH),
    Z_POSITIVE("z_positive", StumpShapeHelper.POSITIVE * StumpShapeHelper.Z_MULTIPLIER, Direction.Axis.Z, StumpShapeDirection.POSITIVE),
    Z_NEGATIVE("z_negative", StumpShapeHelper.NEGATIVE * StumpShapeHelper.Z_MULTIPLIER, Direction.Axis.Z, StumpShapeDirection.NEGATIVE);

    public enum StumpShapeDirection {
        POSITIVE,
        NEGATIVE,
        BOTH,
        NONE;

        public boolean checkAxisDirection(Direction.AxisDirection axisDirection) {
            return switch (this) {
                case POSITIVE -> axisDirection == Direction.AxisDirection.POSITIVE;
                case NEGATIVE -> axisDirection == Direction.AxisDirection.NEGATIVE;
                case BOTH -> axisDirection == Direction.AxisDirection.POSITIVE || axisDirection == Direction.AxisDirection.NEGATIVE;
                case NONE -> axisDirection != Direction.AxisDirection.POSITIVE && axisDirection != Direction.AxisDirection.NEGATIVE;
            };
        }
    }

    public static class StumpShapeHelper {
        public static final int X_MULTIPLIER = 2;
        public static final int Z_MULTIPLIER = 4;
        public static final int CENTER = 1;         //2,    4
        public static final int CENTER_LARGE = 3;   //6,    12
        public static final int POSITIVE = 5;       //10,   20
        public static final int NEGATIVE = 7;       //14,   28
    }
    public static final int X_MULTIPLIER = StumpShapeHelper.X_MULTIPLIER;
    public static final int Z_MULTIPLIER = StumpShapeHelper.Z_MULTIPLIER;
    public static final int CENTER = StumpShapeHelper.CENTER;
    public static final int CENTER_LARGE = StumpShapeHelper.CENTER_LARGE;
    public static final int POSITIVE = StumpShapeHelper.POSITIVE;
    public static final int NEGATIVE = StumpShapeHelper.NEGATIVE;

    private final String name;
    private final int side;
    private final Direction.Axis axis;
    private final StumpShapeDirection shapeDirection;
    public static final Map<Integer, StumpShape> SHAPE_MAP = populateTable();
    StumpShape(String name, int side, Direction.Axis axis, StumpShapeDirection shapeDirection) {
        this.name = name;
        this.side = side;
        this.axis = axis;
        this.shapeDirection = shapeDirection;
    }

    private static Map<Integer, StumpShape> populateTable() {
        var map = Maps.<Integer, StumpShape>newHashMap();
        for (var constant : values()) {
            map.put(constant.side, constant);
        }
        return map;
    }

    @Override
    public String asString() {
        return this.name;
    }

    public Direction.Axis getAxis() {
        return axis;
    }

    public StumpShapeDirection getShapeDirection() {
        return shapeDirection;
    }
}
