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
			System.out.println("����Ͻ÷��� ����Ű�� �����ּ���.");
			System.in.read();
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			scanner.close();
		}
	}

	private static void program(Scanner scanner) {
		System.out.println("A Dance of Fire and Ice �� ��ȯ��");
		System.out.println("ver 1.2.0");
		System.out.println("������ : Luxus io");
		System.out.println("YouTube : https://www.youtube.com/channel/UCkznd9aLn0GXIP5VjDKo_nQ");
		System.out.println("Github : https://github.com/Luxusio/ADOFAI-Map-Converter");
		System.out.println();

		System.out.println("1. �ܰ� ��ȯ");
		System.out.println("2. ���� ��ȯ");
		System.out.println("3. ���� ��ȯ");
		System.out.println("4. ���Ÿ�Ͽ� ȸ�� �ֱ�");
		System.out.println("5. ���Ÿ�Ͽ� ȸ�� ����");
		System.out.println("6. ����Ʈ ����");
		System.out.println("7. ���� ��ȯ");
		System.out.println("8. ����");
		System.out.print("�Է� : ");

		int mode = scanner.nextInt();
		scanner.nextLine();
		String pattern = null;
		MapData patternMap = null;
		boolean useCameraOptimization = false;
		boolean removeDecoration = false;
		int opacity = 0;
		
		if (mode == 3) {
			System.out.println("*.adofai ���� ���� pathData�������� �Է��Ͽ��� �մϴ�*");
			System.out.print("����(Ȥ�� .adofai����) : ");
			pattern = scanner.nextLine().trim();

			if (pattern.endsWith(".adofai")) {
				File file = new File(pattern);
				if (!file.exists()) {
					System.out.println("E> ������ �������� �ʽ��ϴ�!");
					return;
				}

				try {
					patternMap = new MapData();
					patternMap.load(pattern);
					pattern = patternMap.getPathData();
				} catch (Throwable t) {
					System.out.println("E> ���� �߻�(" + pattern + ")");
					t.printStackTrace();
					return;
				}
			}

			if (pattern.isEmpty()) {
				System.out.println("������ ������� �� �����ϴ�. ���α׷��� �����մϴ�.");
				return;
			}

			for (char c : pattern.toCharArray()) {
				TileAngle tileAngle = MapModule.getCharTileAngleBiMap().get(c);
				if (tileAngle == null) {
					System.out.println("�߸��� �����Դϴ�(" + c + "). ���α׷��� �����մϴ�.");
					return;
				}
			}
		}
//		if(mode == 2 || mode == 3 || mode == 4 || mode == 5) {
//			System.out.print("ī�޶� ����ȭ ���(y, n):");
//			useCameraOptimization = scanner.nextLine().trim().equalsIgnoreCase("y");
//		}
		if(mode == 6) {
			System.out.print("��� ����(y, n):");
			removeDecoration = scanner.nextLine().trim().equalsIgnoreCase("y");
		}
		if(mode == 7) {
			System.out.print("����(0~100):");
			opacity = scanner.nextInt();
			scanner.nextLine();
		}
		if (mode == 8) {
			System.out.println("���α׷��� �����մϴ�.");
			return;
		}
		if (mode < 0 || mode > 8) {
			System.out.println("�߸��� ����Դϴ�. ���α׷��� �����մϴ�.");
			return;
		}
		

		
		
		System.out.println();
		System.out.println("*all�� backup.adofai�� ������ ��� ���� ������ ������ ��ȯ�մϴ�*");
		System.out.print("���(.adofai����) : ");
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
				System.out.println("E> ������ �������� �ʽ��ϴ�!");
				System.out.println("��� : " + path);
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
				System.out.println("E> ���� �߻�(" + path + ")");
				t.printStackTrace();
			}
		}
		System.out.println("complete");

	}

}
