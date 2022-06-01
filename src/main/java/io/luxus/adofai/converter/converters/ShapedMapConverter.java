package io.luxus.adofai.converter.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.luxus.adofai.converter.MapConverter;
import io.luxus.adofai.converter.MapConverterBase;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.parser.CustomLevelParser;
import io.luxus.lib.adofai.parser.FlowFactory;
import io.luxus.lib.adofai.type.EventType;
import io.luxus.lib.adofai.type.TileAngle;
import io.luxus.lib.adofai.type.action.Action;
import io.luxus.lib.adofai.type.action.Twirl;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;


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
			List<TileAngle> angleData = FlowFactory.readPathData(sourceStr);
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
		String sourceStr = (String) args[0];
		CustomLevel shapeLevel = (CustomLevel) args[1];
		@SuppressWarnings("unchecked")
		List<TileAngle> shapeAngles = (List<TileAngle>) args[2];

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
		CustomLevel shapeLevel = (CustomLevel) args[1];
		@SuppressWarnings("unchecked")
		List<TileAngle> shapeAngles = (List<TileAngle>) args[2];

		String shapeStr;
		if (sourceStr != null) {
			shapeStr = sourceStr.endsWith(".adofai") ?
					sourceStr.substring(0, sourceStr.length() - 7) :
					sourceStr;
		}
		else {
			shapeStr = shapeAngles.stream()
					.map(angle -> angle.isMidspin() ? "!" : String.valueOf(angle.getAngle()))
					.collect(Collectors.joining(","));
		}

		return shapeStr + " Shape";
	}

	@Override
	public CustomLevel convert(CustomLevel customLevel, Object... args) {
		if (impossible(customLevel, args)) {
			return null;
		}
		String sourceStr = (String) args[0];
		CustomLevel shapeLevel = (CustomLevel) args[1];
		@SuppressWarnings("unchecked")
		List<TileAngle> shapeAngles = (List<TileAngle>) args[2];

		if (shapeLevel == null) {

			List<Tile.Builder> tiles = shapeAngles.stream()
					.map(angle -> new Tile.Builder().angle(angle))
					.collect(Collectors.toList());

			tiles.add(0, new Tile.Builder());
			shapeLevel = new CustomLevel.Builder().tileBuilders(tiles).build();
		}


		final List<Tile> shapeTiles = shapeLevel.getTiles();
		shapeTiles.remove(0);

		return MapConverterBase.convert(customLevel, false,
				new Function<MapConverterBase.ApplyEach, List<Tile.Builder>>() {

					private int index = 0;

					@Override
					public List<Tile.Builder> apply(MapConverterBase.ApplyEach applyEach) {

						List<Tile> nowTimingTiles = applyEach.getOneTimingTiles();
						List<Tile> nowShapeTiles = MapConverterBase.getSameTimingTiles(shapeTiles, index);

						List<Tile.Builder> newTileBuilders = nowShapeTiles.stream()
								.map(tile -> {
									Tile.Builder newTileBuilder = new Tile.Builder().angle(tile.getAngle());

									tile.getActions(EventType.TWIRL)
											.forEach(newTileBuilder::addAction);

									return newTileBuilder;
								}).collect(Collectors.toList());

						int newTileIdx = 0;
						for (Tile timingTile : nowTimingTiles) {
							Tile.Builder newTileBuilder = newTileBuilders.get(newTileIdx);

							Tile.Builder mutableTimingTile = new Tile.Builder().from(timingTile);
							mutableTimingTile.removeActions(EventType.TWIRL);

							newTileBuilder.combineTile(mutableTimingTile);

							if (++newTileIdx >= newTileBuilders.size()) {
								newTileIdx--;
							}
						}

						index = index + nowShapeTiles.size();
						if (index >= shapeTiles.size()) {
							index = 0;
							if (shapeTiles.get(shapeTiles.size() - 1).getTileMeta().isReversed()) {
								Tile.Builder newTileBuilder = newTileBuilders.get(newTileBuilders.size() - 1);

								List<Action> twirls = newTileBuilder.getActions(EventType.TWIRL);
								if (twirls.isEmpty()) {
									newTileBuilder.addAction(new Twirl.Builder().build());
								}
								else {
									newTileBuilder.removeActions(EventType.TWIRL);
								}
							}
						}

						return newTileBuilders;
					}
				});
	}
}
