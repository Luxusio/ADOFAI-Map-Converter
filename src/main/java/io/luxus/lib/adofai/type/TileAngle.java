package io.luxus.lib.adofai.type;

import lombok.RequiredArgsConstructor;

import static io.luxus.lib.adofai.util.NumberUtil.generalizeAngle;

@RequiredArgsConstructor
public class TileAngle {

    public enum Type {
        NORMAL,
        MIDSPIN,
    }

    public static TileAngle MIDSPIN = new TileAngle(Type.MIDSPIN, 0);

    public static TileAngle ZERO = createNormal(0.0);

    private final Type type;    // angle type
    private final double angle; // = static angle


    public static TileAngle createNormal(double angle) {
        return new TileAngle(Type.NORMAL, generalizeAngle(angle));
    }

    public double getAngle() {
        if (isMidspin()) {
            throw new IllegalStateException("angle is midspin");
        }
        return angle;
    }

    public boolean isMidspin() {
        return this.type == Type.MIDSPIN;
    }


}
