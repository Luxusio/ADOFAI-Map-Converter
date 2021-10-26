package io.luxus.adofai;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.luxus.adofai.converter.*;
import io.luxus.lib.adofai.CustomLevel;
import io.luxus.lib.adofai.Tile;
import io.luxus.lib.adofai.parser.CustomLevelParser;
import io.luxus.lib.adofai.parser.FlowFactory;
import io.luxus.lib.adofai.util.NumberUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
        System.out.println("ver 1.2.2");
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
        System.out.println("8. 종료");
        System.out.print("입력 : ");

        int mode = scanner.nextInt();
        scanner.nextLine();
        // 3
        List<Double> angleData = null;
        CustomLevel patternLevel = null;
        // 2 3 4 5
        boolean useCameraOptimization = false;
        // 4
        double twirlRate = 0.0;
        // 6
        boolean removeDecoration = false;
        boolean removeTileMove = false;
        boolean removeCameraEvents = false;
        boolean removeFlash = false;
        // 7
        int opacity = 0;

        if (mode == 3) {
            System.out.println("*.adofai 파일 내의 pathData 혹은 angleData 형식으로 입력하여야 합니다*");
            System.out.print("패턴(혹은 .adofai 파일) : ");
            String patternStr = scanner.nextLine().trim();

            if (patternStr.endsWith(".adofai")) {
                File file = new File(patternStr);
                if (!file.exists()) {
                    System.err.println("파일이 존재하지 않습니다");
                    return;
                }

                try {
                    patternLevel = CustomLevelParser.read(file);
                    angleData = patternLevel.getTiles().stream()
                            .map(Tile::getAngle)
                            .collect(Collectors.toList());
                } catch (Throwable t) {
                    System.err.println("파일 불러오기에 실패했습니다");
                    t.printStackTrace();
                    return;
                }
            }
            else {

                angleData = FlowFactory.readPathData(patternStr);
                if (angleData == null) {
                    if (patternStr.charAt(0) != '[') {
                        patternStr = "[" + patternStr;
                    }
                    if (patternStr.charAt(patternStr.length() - 1) != ']') {
                        patternStr = patternStr + "]";
                    }
                    try {
                        angleData = FlowFactory.readAngleData(new ObjectMapper().readTree(patternStr));
                    } catch (Throwable throwable) {
                        System.err.println("패턴 읽어오기에 실패했습니다.");
                        throwable.printStackTrace();
                        return;
                    }
                }

            }

            if (angleData.isEmpty()) {
                System.err.println("패턴은 비어있을 수 없습니다. 프로그램을 종료합니다.");
                return;
            }

        }
//		if(mode == 2 || mode == 3 || mode == 4 || mode == 5) {
//			System.out.print("카메라 최적화 사용(y, n):");
//			useCameraOptimization = scanner.nextLine().trim().equalsIgnoreCase("y");
//		}
        if (mode == 4) {
            System.out.print("회전 넣을 비율(0.0~1.0):");
            twirlRate = scanner.nextDouble();
        }
        if (mode == 5) {
            System.out.print("장식 제거(y, n):");
            removeDecoration = scanner.nextLine().trim().equalsIgnoreCase("y");
            System.out.print("타일이동 제거(y, n):");
            removeTileMove = scanner.nextLine().trim().equalsIgnoreCase("y");
            System.out.print("카메라이벤트 제거(y, n):");
            removeCameraEvents = scanner.nextLine().trim().equalsIgnoreCase("y");
            System.out.print("플래시 제거(y, n):");
            removeFlash = scanner.nextLine().trim().equalsIgnoreCase("y");
        }
        if (mode == 6) {
            System.out.print("투명도(0~100):");
            opacity = scanner.nextInt();
            scanner.nextLine();
        }
        if (mode == 8) {
            System.out.println("프로그램을 종료합니다.");
            return;
        }
        if (mode < 0 || mode > 9) {
            System.out.println("잘못된 모드입니다. 프로그램을 종료합니다.");
            return;
        }

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

            try {
                CustomLevel result;
                String outPath = path.replace(".adofai", "");

                if (mode == 1) {
                    result = OuterMapConverter.convert(path);
                    outPath += " Outer.adofai";
                } else if (mode == 2) {
                    result = LinearMapConverter.convert(path, useCameraOptimization);
                    outPath += " Linear.adofai";
                } else if (mode == 3) {
                    if (patternLevel == null) {
                        result = ShapedMapConverter.convert(path, angleData, useCameraOptimization);
                    } else {
                        result = MapShapedMapConverter.convert(path, patternLevel, useCameraOptimization);
                    }
                    outPath += " Shape.adofai";
                } else if (mode == 4) {

                    result = TwirlConverter.convert(path, twirlRate, useCameraOptimization);

                    if (NumberUtil.fuzzyEquals(twirlRate, 0.0)) {
                        outPath += " No Twirl.adofai";
                    }
                    else if (NumberUtil.fuzzyEquals(twirlRate, 1.0)) {
                        outPath += " All Twirl.adofai";
                    }
                    else {
                        outPath += " Twirl rate " + twirlRate + ".adofai";
                    }

                } else if (mode == 5) {
                    result = NonEffectConverter.convert(path, removeDecoration, removeTileMove, removeCameraEvents, removeFlash);
                    outPath +=  "Non-Effect.adofai";
                } else if (mode == 6) {
                    result = TransposeMapConverter.convert(path, opacity);
                    outPath += " Transpose.adofai";
                } else if (mode == 7) {
                    result = OnlyBpmSetSpeedConverter.convert(path);
                    outPath += " OnlyBpm.adofai";
                } else {
                    System.err.println("잘못된 변환 모드.(" + mode + ")");
                    return;
                }

                CustomLevelParser.write(result, outPath);

            } catch (Throwable t) {
                System.out.println("E> 오류 발생(" + path + ")");
                t.printStackTrace();
            }
        }
        System.out.println("complete");

    }

}
