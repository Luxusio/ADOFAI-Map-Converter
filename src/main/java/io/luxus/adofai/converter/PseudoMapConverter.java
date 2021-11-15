package io.luxus.adofai.converter;

import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.TileMeta;
import io.luxus.lib.adofai.action.type.EventType;
import io.luxus.lib.adofai.helper.AngleHelper;
import io.luxus.lib.adofai.parser.CustomLevelParser;

import java.io.IOException;
import java.util.List;

import static io.luxus.lib.adofai.Constants.ANGLE_MID_TILE;

public class PseudoMapConverter {
    public static CustomLevel convert(String path, double defaultMultiHitTravelAngle, int pseudo, boolean removeColorTrackEvents) throws IOException {

        if (pseudo < 1) {
            System.err.println("Pseudo 값이 너무 낮습니다! 1 이상으로 해주세요!");
            return null;
        }

        CustomLevel customLevel = CustomLevelParser.readPath(path);

        return MapConverterBase.convert(customLevel, false,
                applyEach -> {
            List<Tile> oneTimingTiles = applyEach.getOneTimingTiles();
            List<Tile> newTiles = MapConverterBase.copyTiles(oneTimingTiles);

            double travelAngle = TileMeta.calculateTotalTravelAngle(oneTimingTiles);

            double eachHitTravelAngle = Math.min(defaultMultiHitTravelAngle, travelAngle / 2 / pseudo);

            TileMeta lastTileMeta = oneTimingTiles.get(oneTimingTiles.size() - 1).getTileMeta();
            double currStaticAngle = lastTileMeta.getStaticAngle();

            if (oneTimingTiles.size() % 2 == 0) {
                currStaticAngle = AngleHelper.getNextStaticAngle(currStaticAngle, 0.0, lastTileMeta.isReversed());
            }

            currStaticAngle = AngleHelper.getNextStaticAngle(currStaticAngle, eachHitTravelAngle, lastTileMeta.isReversed());

            if (removeColorTrackEvents) {
            newTiles.forEach(newTile -> {
                newTile.getActions(EventType.RECOLOR_TRACK).clear();
                newTile.getActions(EventType.COLOR_TRACK).clear();
            });
            }

            for (int i = 1; i < pseudo; i++) {
                newTiles.add(new Tile(currStaticAngle));
                newTiles.add(new Tile(ANGLE_MID_TILE));
                currStaticAngle = AngleHelper.getNextStaticAngle(currStaticAngle, eachHitTravelAngle, lastTileMeta.isReversed());
                currStaticAngle = AngleHelper.getNextStaticAngle(currStaticAngle, 0, lastTileMeta.isReversed());
            }

            return newTiles;
        });
    }
}
