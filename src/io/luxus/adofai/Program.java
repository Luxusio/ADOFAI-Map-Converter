package io.luxus.adofai;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import io.luxus.adofai.converter.LinearMapConverter;
import io.luxus.adofai.converter.OuterMapConverter;
import io.luxus.adofai.converter.ShapedMapConverter;
import io.luxus.api.adofai.MapData;
import io.luxus.api.adofai.module.MapModule;
import io.luxus.api.adofai.type.TileAngle;

public class Program {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		try {
			Program.program(scanner);
			System.out.println("계속하시려면 엔터키를 눌러주세요.");
			System.in.read();
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			scanner.close();
		}
	}

	private static void program(Scanner scanner) {
		System.out.println("A Dance of Fire and Ice 맵 변환기");
		System.out.println("ver 1.0.0");
		System.out.println("개발자 : Luxus io");
		System.out.println("YouTube : https://www.youtube.com/channel/UCkznd9aLn0GXIP5VjDKo_nQ");
		System.out.println("Github : https://github.com/Luxusio/ADOFAI-Map-Converter");
		System.out.println();

		System.out.println("1. 외각 변환");
		System.out.println("2. 선형 변환");
		System.out.println("3. 패턴 변환");
		System.out.println("4. 외각&선형 변환");
		System.out.println("5. 종료");
		System.out.print("입력 : ");

		int mode = scanner.nextInt();
		String pattern = null;

		if (mode == 4) {
			System.out.print("패턴 : ");
			pattern = scanner.next().trim();

			if (pattern.isBlank()) {
				System.out.println("패턴은 비어있을 수 없습니다. 프로그램을 종료합니다.");
				return;
			}
			for (char c : pattern.toCharArray()) {
				TileAngle tileAngle = MapModule.getCharTileAngleBiMap().get(c);
				if (tileAngle == null || tileAngle == TileAngle.NONE) {
					System.out.println("잘못된 문자입니다(" + c + "). 프로그램을 종료합니다.");
					return;
				}
			}

		} else if (mode == 5) {
			System.out.println("프로그램을 종료합니다.");
			return;
		} else if (mode < 0 || mode > 5) {
			System.out.println("잘못된 모드입니다. 프로그램을 종료합니다.");
			return;
		}

		System.out.println();
		System.out.println("*all시 backup.adofai를 제외한 모든 하위 폴더의 파일을 변환합니다*");
		System.out.print("경로(.adofai포함) : ");
		String input = scanner.next();

		List<String> pathList = new ArrayList<>();
		if (input.equalsIgnoreCase("all")) {
			pathList.addAll(FileUtil.findRecursive(System.getProperty("user.dir"), ".*.adofai"));
		} else {
			pathList.add(input);
		}

		System.out.println("load");
		for (String path : pathList) {
			File file = new File(path);
			if (!file.exists()) {
				System.out.println("E> 파일이 존재하지 않습니다!");
				System.out.println("경로 : " + path);
				continue;
			}
			if (file.getName().equals("backup.adofai")) {
				continue;
			}

			MapData map = new MapData();
			try {
				map.load(path);
				path = path.replace(".adofai", "");
				if (mode == 1) {
					OuterMapConverter.convert(map).save(path + " Outer.adofai");
				} else if (mode == 2) {
					LinearMapConverter.convert(map).save(path + " Linear.adofai");
				} else if (mode == 3) {
					ShapedMapConverter.convert(map, pattern).save(path + " Shape.adofai");
				} else if (mode == 4) {
					OuterMapConverter.convert(map).save(path + " Outer.adofai");
					LinearMapConverter.convert(map).save(path + " Linear.adofai");
				}
			} catch (Throwable t) {
				System.out.println("E> 오류 발생(" + path + ")");
				t.printStackTrace();
			}
		}
		System.out.println("complete");

	}

}
