package io.luxus.adofai;

import io.luxus.adofai.converter.ConverterType;
import io.luxus.adofai.converter.MapConverterDispatcher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Program.program(scanner);
            System.out.println("계속하시려면 엔터키를 눌러주세요.");
            int read = System.in.read();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static void program(Scanner scanner) {
        System.out.println("A Dance of Fire and Ice 맵 변환기");
        System.out.println("ver 1.3.2");
        System.out.println("개발자 : Luxus io");
        System.out.println("YouTube : https://www.youtube.com/c/Luxusio");
        System.out.println("Github : https://github.com/Luxusio/ADOFAI-Map-Converter");
        System.out.println();

        System.out.println("1. 외각 변환");
        System.out.println("2. 선형 변환");
        System.out.println("3. 패턴 변환");
        System.out.println("4. 모든타일 회전 넣고 빼기 비율");
        System.out.println("5. 이펙트 제거");
        System.out.println("6. 투명도 변환");
        System.out.println("7. Bpm 승수->Bpm 변환");
        System.out.println("8. 무변속 맵 변환");
        System.out.println("9. 맵 전체 bpm *배수 변환");
        System.out.println("10. 모든 타일 무작위 bpm 변환");
        System.out.println("11. 미드스핀 변환");
        System.out.println("12. 동타 변환");
        System.out.println("13. 종료");
        System.out.print("입력 : ");

        int mode = scanner.nextInt();
        scanner.nextLine();

        ConverterType converterType =
                mode == 1 ? ConverterType.OUTER :
                mode == 2 ? ConverterType.LINEAR :
                mode == 3 ? ConverterType.SHAPED :
                mode == 4 ? ConverterType.TWIRL_RATIO :
                mode == 5 ? ConverterType.NO_EFFECT :
                mode == 6 ? ConverterType.TRANSPARENCY :
                mode == 7 ? ConverterType.BPM_VALUE_ONLY :
                mode == 8 ? ConverterType.NO_SPEED_CHANGE :
                mode == 9 ? ConverterType.BPM_MULTIPLIER :
                mode == 10 ? ConverterType.CHAOS :
                mode == 11 ? ConverterType.MIDSPIN :
                mode == 12 ? ConverterType.PSEUDO :
                        null;

        if (converterType == null) {
            System.out.println("프로그램을 종료합니다.");
            return;
        }

        MapConverterDispatcher dispatcher = new MapConverterDispatcher();

        Object[] args = dispatcher.prepareParameters(converterType, scanner);

        System.out.println();
        System.out.println("*all 시 backup.adofai 를 제외한 모든 하위 폴더의 파일을 변환합니다*");
        System.out.print("경로(.adofai 포함) : ");
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
                System.out.println("E> 파일이 존재하지 않습니다(" + path + ")");
                continue;
            }
            if (file.getName().equals("backup.adofai")) {
                continue;
            }

            dispatcher.convertMapAndSave(path, converterType, args);

        }
        System.out.println("complete");

    }

}
