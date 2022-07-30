package io.luxus.adofai;

import io.luxus.adofai.converter.MapConverter;
import io.luxus.adofai.converter.MapConverterDispatcher;
import io.luxus.adofai.converter.converters.*;
import io.luxus.adofai.converter.converters.effect.NonEffectMapConverter;
import io.luxus.adofai.converter.converters.effect.OnlyBpmSetMapConverter;
import io.luxus.adofai.converter.converters.effect.TransparentMapConverter;
import io.luxus.adofai.converter.i18n.I18n;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static io.luxus.adofai.converter.i18n.I18nCode.*;

public class Program {

    public static void main(String[] args) {
        I18n i18n = new I18n();

        try (Scanner scanner = new Scanner(System.in)) {
            Program.program(scanner, i18n);
        } catch (Throwable t) {
            t.printStackTrace();
        }

        try {
            System.out.println(i18n.getText(PROGRAM_PRESS_ENTER_TO_CONTINUE));
            int read = System.in.read();
        } catch (IOException ignored) { }
    }

    private static void program(Scanner scanner, I18n i18n) {
        i18n.println("ADOFAI Map Converter");
        i18n.println("Version : 1.5.2");
        i18n.println("Developer : Luxus io");
        i18n.println("YouTube : https://www.youtube.com/c/Luxusio");
        i18n.println("Github : https://github.com/Luxusio/ADOFAI-Map-Converter");
        i18n.println();

        i18n.println("0. Set language");
        i18n.println(PROGRAM_OPTION_1);
        i18n.println(PROGRAM_OPTION_2);
        i18n.println(PROGRAM_OPTION_3);
        i18n.println(PROGRAM_OPTION_4);
        i18n.println(PROGRAM_OPTION_5);
        i18n.println(PROGRAM_OPTION_6);
        i18n.println(PROGRAM_OPTION_7);
        i18n.println(PROGRAM_OPTION_8);
        i18n.println(PROGRAM_OPTION_9);
        i18n.println(PROGRAM_OPTION_10);
        i18n.println(PROGRAM_OPTION_11);
        i18n.println(PROGRAM_OPTION_12);
        i18n.println(PROGRAM_OPTION_13);
        i18n.println(PROGRAM_OPTION_14);
        i18n.print(INPUT);

        int mode = scanner.nextInt();
        scanner.nextLine();

        if (mode == 0) {
            i18n.println(PROGRAM_INPUT_LANGUAGE, i18n.getSupportedLanguage());

            String languageTag = scanner.nextLine();
            if (i18n.isSupportedLanguage(languageTag)) {
                i18n.setLanguage(languageTag);
            } else {
                i18n.printlnErr(PROGRAM_INPUT_LANGUAGE_ERROR);
            }


            program(scanner, i18n);
            return;
        }

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
                mode == 13 ? PlanetNumberMapConverter.class :
                        null;

        if (converterType == null) {
            i18n.println(PROGRAM_EXIT);
            return;
        }

        convert(converterType, scanner, i18n);

        i18n.println("complete");
    }

    private static <CT extends MapConverter<T>, T> void convert(Class<?> converterType, Scanner scanner, I18n i18n) {

        @SuppressWarnings("unchecked")
        Class<CT> type = (Class<CT>) converterType;

        MapConverterDispatcher dispatcher = new MapConverterDispatcher(new I18n());
        T parameters = dispatcher.prepareParameters(type, scanner);


        i18n.println();
        i18n.println(PROGRAM_CONVERT_MESSAGE_1);
        i18n.print(PROGRAM_CONVERT_MESSAGE_2);
        String input = scanner.nextLine();

        List<String> pathList = new ArrayList<>();
        if (input.equalsIgnoreCase("all")) {
            pathList.addAll(FileUtil.findRecursive(System.getProperty("user.dir"), ".*.adofai"));
        } else {
            pathList.add(input);
        }

        i18n.println("load");
        for (String path : pathList) {
            File file = new File(path);
            if (!file.exists()) {
                i18n.printlnErr(ERROR_FILE_NOT_EXIST, path);
                continue;
            }
            if (file.getName().equals("backup.adofai")) {
                continue;
            }

            dispatcher.convertMapAndSave(path, type, parameters);
        }
    }

}
