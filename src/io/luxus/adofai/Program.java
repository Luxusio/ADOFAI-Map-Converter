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
		System.out.println("ver 1.0.0");
		System.out.println("������ : Luxus io");
		System.out.println("YouTube : https://www.youtube.com/channel/UCkznd9aLn0GXIP5VjDKo_nQ");
		System.out.println("Github : https://github.com/Luxusio/ADOFAI-Map-Converter");
		System.out.println();

		System.out.println("1. �ܰ� ��ȯ");
		System.out.println("2. ���� ��ȯ");
		System.out.println("3. ���� ��ȯ");
		System.out.println("4. �ܰ�&���� ��ȯ");
		System.out.println("5. ����");
		System.out.print("�Է� : ");

		int mode = scanner.nextInt();
		String pattern = null;

		if (mode == 4) {
			System.out.print("���� : ");
			pattern = scanner.next().trim();

			if (pattern.isBlank()) {
				System.out.println("������ ������� �� �����ϴ�. ���α׷��� �����մϴ�.");
				return;
			}
			for (char c : pattern.toCharArray()) {
				TileAngle tileAngle = MapModule.getCharTileAngleBiMap().get(c);
				if (tileAngle == null || tileAngle == TileAngle.NONE) {
					System.out.println("�߸��� �����Դϴ�(" + c + "). ���α׷��� �����մϴ�.");
					return;
				}
			}

		} else if (mode == 5) {
			System.out.println("���α׷��� �����մϴ�.");
			return;
		} else if (mode < 0 || mode > 5) {
			System.out.println("�߸��� ����Դϴ�. ���α׷��� �����մϴ�.");
			return;
		}

		System.out.println();
		System.out.println("*all�� backup.adofai�� ������ ��� ���� ������ ������ ��ȯ�մϴ�*");
		System.out.print("���(.adofai����) : ");
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
				System.out.println("E> ���� �߻�(" + path + ")");
				t.printStackTrace();
			}
		}
		System.out.println("complete");

	}

}
