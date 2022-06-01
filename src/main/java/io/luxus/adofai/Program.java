package io.luxus.adofai;

import io.luxus.adofai.converter.MapConverter;
import io.luxus.adofai.converter.MapConverterDispatcher;
import io.luxus.adofai.converter.converters.*;
import io.luxus.adofai.converter.converters.effect.NonEffectMapConverter;
import io.luxus.adofai.converter.converters.effect.OnlyBpmSetMapConverter;
import io.luxus.adofai.converter.converters.effect.TransparentMapConverter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Program.program(scanner);
        } catch (Throwable t) {
            t.printStackTrace();
        }

        try {
            System.out.println("계속하시려면 엔터키를 눌러주세요.");
            int read = System.in.read();
        } catch (IOException ignored) { }
    }

    private static void program(Scanner scanner) {
        System.out.println("A Dance of Fire and Ice 맵 변환기");
        System.out.println("ver 1.4.0");
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

        Class<? extends MapConverter<?>> converterType =
                mode == 1 ?  OuterMapConverter.class :
                mode == 2 ?  LinearMapConverter.class :
                mode == 3 ?  ShapedMapConverter.class :
                mode == 4 ?  TwirlConverter.class :
                mode == 5 ?  NonEffectMapConverter.class :
                mode == 6 ?  TransparentMapConverter.class :
                mode == 7 ?  OnlyBpmSetMapConverter.class :
                mode == 8 ?  NoSpeedChangeMapConverter.class :
                mode == 9 ?  BpmMultiplyMapConverter.class :
                mode == 10 ? ChaosBpmMapConverter.class :
                mode == 11 ? AllMidspinMapConverter.class :
                mode == 12 ? PseudoMapConverter.class :
                        null;

        if (converterType == null) {
            System.out.println("프로그램을 종료합니다.");
            return;
        }

        convert(converterType, scanner);

        System.out.println("complete");
    }

    private static <CT extends MapConverter<T>, T> void convert(Class<?> converterType, Scanner scanner) {

        @SuppressWarnings("unchecked")
        Class<CT> type = (Class<CT>) converterType;

        MapConverterDispatcher dispatcher = new MapConverterDispatcher();
        T parameters = dispatcher.prepareParameters(type, scanner);

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

            dispatcher.convertMapAndSave(path, type, parameters);
        }
    }

}
