package io.luxus.adofai;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import io.luxus.adofai.converter.LinearMapConverter;
import io.luxus.adofai.converter.MapShapedMapConverter;
import io.luxus.adofai.converter.NonEffectConverter;
import io.luxus.adofai.converter.OuterMapConverter;
import io.luxus.adofai.converter.ShapedMapConverter;
import io.luxus.adofai.converter.TransposeMapConverter;
import io.luxus.adofai.converter.TwirlConverter;
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
		System.out.println("ver 1.2.0");
		System.out.println("개발자 : Luxus io");
		System.out.println("YouTube : https://www.youtube.com/channel/UCkznd9aLn0GXIP5VjDKo_nQ");
		System.out.println("Github : https://github.com/Luxusio/ADOFAI-Map-Converter");
		System.out.println();

		System.out.println("1. 외각 변환");
		System.out.println("2. 선형 변환");
		System.out.println("3. 패턴 변환");
		System.out.println("4. 모든타일에 회전 넣기");
		System.out.println("5. 모든타일에 회전 빼기");
		System.out.println("6. 이펙트 제거");
		System.out.println("7. 투명도 변환");
		System.out.println("8. 종료");
		System.out.print("입력 : ");

		int mode = scanner.nextInt();
		scanner.nextLine();
		String pattern = null;
		MapData patternMap = null;
		boolean useCameraOptimization = false;
		boolean removeDecoration = false;
		int opacity = 0;
		
		if (mode == 3) {
			System.out.println("*.adofai 파일 내의 pathData형식으로 입력하여야 합니다*");
			System.out.print("패턴(혹은 .adofai파일) : ");
			pattern = scanner.nextLine().trim();

			if (pattern.endsWith(".adofai")) {
				File file = new File(pattern);
				if (!file.exists()) {
					System.out.println("E> 파일이 존재하지 않습니다!");
					return;
				}

				try {
					patternMap = new MapData();
					patternMap.load(pattern);
					pattern = patternMap.getPathData();
				} catch (Throwable t) {
					System.out.println("E> 오류 발생(" + pattern + ")");
					t.printStackTrace();
					return;
				}
			}

			if (pattern.isEmpty()) {
				System.out.println("패턴은 비어있을 수 없습니다. 프로그램을 종료합니다.");
				return;
			}

			for (char c : pattern.toCharArray()) {
				TileAngle tileAngle = MapModule.getCharTileAngleBiMap().get(c);
				if (tileAngle == null) {
					System.out.println("잘못된 문자입니다(" + c + "). 프로그램을 종료합니다.");
					return;
				}
			}
		}
//		if(mode == 2 || mode == 3 || mode == 4 || mode == 5) {
//			System.out.print("카메라 최적화 사용(y, n):");
//			useCameraOptimization = scanner.nextLine().trim().equalsIgnoreCase("y");
//		}
		if(mode == 6) {
			System.out.print("장식 제거(y, n):");
			removeDecoration = scanner.nextLine().trim().equalsIgnoreCase("y");
		}
		if(mode == 7) {
			System.out.print("투명도(0~100):");
			opacity = scanner.nextInt();
			scanner.nextLine();
		}
		if (mode == 8) {
			System.out.println("프로그램을 종료합니다.");
			return;
		}
		if (mode < 0 || mode > 8) {
			System.out.println("잘못된 모드입니다. 프로그램을 종료합니다.");
			return;
		}
		

		
		
		System.out.println();
		System.out.println("*all시 backup.adofai를 제외한 모든 하위 폴더의 파일을 변환합니다*");
		System.out.print("경로(.adofai포함) : ");
		String input = scanner.nextLine();

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
				String outPath = path.replace(".adofai", "");
				if (mode == 1) {
					OuterMapConverter.convert(path).save(outPath + " Outer.adofai");
				} else if (mode == 2) {
					LinearMapConverter.convert(path, useCameraOptimization).save(outPath + " Linear.adofai");
				} else if (mode == 3) {
					if (patternMap == null) {
						ShapedMapConverter.convert(path, pattern, useCameraOptimization).save(outPath + " Shape.adofai");
					} else {
						MapShapedMapConverter.convert(path, patternMap, useCameraOptimization).save(outPath + " Shape.adofai");
					}
				} else if (mode == 4) {
					TwirlConverter.convert(path, true, useCameraOptimization).save(outPath + " All Twirl.adofai");
				} else if (mode == 5) {
					TwirlConverter.convert(path, false, useCameraOptimization).save(outPath + " No Twirl.adofai");
				} else if (mode == 6) {
					NonEffectConverter.convert(path, removeDecoration).save(outPath + " Non-Effect.adofai");
				} else if (mode == 7) {
					TransposeMapConverter.convert(path, opacity).save(outPath + " Transpose.adofai");
				}
			} catch (Throwable t) {
				System.out.println("E> 오류 발생(" + path + ")");
				t.printStackTrace();
			}
		}
		System.out.println("complete");

	}

}
