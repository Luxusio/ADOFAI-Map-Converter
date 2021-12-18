package io.luxus.adofai.converter.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.luxus.adofai.converter.MapConverter;
import io.luxus.adofai.converter.MapConverterBase;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.LevelSetting;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.type.action.Action;
import io.luxus.lib.adofai.type.action.Twirl;
import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.helper.TileHelper;
import io.luxus.lib.adofai.parser.CustomLevelParser;
import io.luxus.lib.adofai.parser.FlowFactory;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.luxus.lib.adofai.Constants.ANGLE_MID_TILE;


public class ShapedMapConverter implements MapConverter {

	@Override
	public Object[] prepareParameters(Scanner scanner) {
		System.out.println("*.adofai 파일 내의 pathData 혹은 angleData 형식으로 입력하여야 합니다*");
		System.out.print("패턴(혹은 .adofai 파일) : ");
		String sourceStr = scanner.nextLine().trim();

		CustomLevel patternLevel;

		if (sourceStr.endsWith(".adofai")) {
			File file = new File(sourceStr);
			if (!file.exists()) {
				System.err.println("파일이 존재하지 않습니다");
				return new Object[] { sourceStr, null, null };
			}

			try {
				patternLevel = CustomLevelParser.read(file);
			} catch (Throwable t) {
				System.err.println("파일 불러오기에 실패했습니다");
				t.printStackTrace();
				return new Object[] { sourceStr, null, null };
			}


			return new Object[] { sourceStr, patternLevel, null };
		}
		else {
			List<Double> angleData = FlowFactory.readPathData(sourceStr);
			if (angleData == null) {
				if (sourceStr.charAt(0) != '[') {
					sourceStr = "[" + sourceStr;
				}
				if (sourceStr.charAt(sourceStr.length() - 1) != ']') {
					sourceStr = sourceStr + "]";
				}
				try {
					angleData = FlowFactory.readAngleData(new ObjectMapper().readTree(sourceStr));
				} catch (Throwable throwable) {
					System.err.println("패턴 읽어오기에 실패했습니다.");
					throwable.printStackTrace();
					return new Object[] { sourceStr, null, null };
				}
			}

			return new Object[] { sourceStr, null, angleData };
		}
	}

	@Override
	public boolean impossible(CustomLevel customLevel, Object... args) {
		CustomLevel shapeLevel = (CustomLevel) args[1];
		@SuppressWarnings("unchecked")
		List<Double> shapeAngles = (List<Double>) args[2];

		if (shapeLevel != null) {
			if (shapeLevel.getTiles().size() <= 1) {
				System.err.println("패턴의 타일 수가 너무 적습니다.");
				return true;
			}
		}
		else {
			if (shapeAngles == null) {
				System.err.println("shapeAngles가 null입니다.");
				return true;
			}
			else if (shapeAngles.isEmpty()) {
				System.err.println("패턴의 각도 수가 너무 적습니다.");
				return true;
			}
		}

		return false;
	}

	@Override
	public String getLevelPostfix(CustomLevel customLevel, Object... args) {
		String sourceStr = (String) args[0];

		String shapeStr;
		if (sourceStr != null) {
			shapeStr = sourceStr.endsWith(".adofai") ?
					sourceStr.substring(0, sourceStr.length() - 7) :
					sourceStr;
		}
		else {
			@SuppressWarnings("unchecked")
			List<Double> angles = (List<Double>) args[2];
			String angleStr = angles.toString();

			shapeStr = angleStr.substring(1, angleStr.length() - 1);
		}

		return shapeStr + " Shape";
	}

	@Override
	public CustomLevel convert(CustomLevel customLevel, Object... args) {
		if (impossible(customLevel, args)) {
			return null;
		}

		CustomLevel shapeLevel;
		if (args[1] == null) {

			@SuppressWarnings("unchecked")
			List<Tile> tiles = ((List<Double>) args[2]).stream()
					.map(angle -> angle == 999.0 ? ANGLE_MID_TILE : angle)
					.map(Tile::new)
					.collect(Collectors.toList());

			tiles.add(0, new Tile(0.0));
			shapeLevel = new CustomLevel(new LevelSetting(), tiles);
		}
		else {
			shapeLevel = (CustomLevel) args[1];
		}


		final List<Tile> shapeTiles = shapeLevel.getTiles();
		shapeTiles.remove(0);

		return MapConverterBase.convert(customLevel, false,
				new Function<MapConverterBase.ApplyEach, List<Tile>>() {

					private int index = 0;

					@Override
					public List<Tile> apply(MapConverterBase.ApplyEach applyEach) {

						List<Tile> nowTimingTiles = applyEach.getOneTimingTiles();
						List<Tile> nowShapeTiles = MapConverterBase.getSameTimingTiles(shapeTiles, index);

						List<Tile> newTiles = nowShapeTiles.stream()
								.map(tile -> {
									Tile newTile = new Tile(tile.getAngle());

									tile.getActions(EventType.TWIRL)
											.forEach(newTile::addAction);

									return newTile;
								}).collect(Collectors.toList());

						int newTileIdx = 0;
						for (Tile timingTile : nowTimingTiles) {
							Tile newTile = newTiles.get(newTileIdx);

							timingTile.getActions(EventType.TWIRL).clear();

							TileHelper.combineTile(newTile, timingTile);
							if (++newTileIdx >= newTiles.size()) {
								newTileIdx--;
							}
						}

						index = index + nowShapeTiles.size();
						if (index >= shapeTiles.size()) {
							index = 0;
							if (shapeTiles.get(shapeTiles.size() - 1).getTileMeta().isReversed()) {
								List<Action> twirls = newTiles.get(newTiles.size() - 1).getActions(EventType.TWIRL);
								if (twirls.isEmpty()) {
									twirls.add(new Twirl.Builder().build());
								}
								else {
									twirls.clear();
								}
							}
						}

						return newTiles;
					}
				});
	}
}
