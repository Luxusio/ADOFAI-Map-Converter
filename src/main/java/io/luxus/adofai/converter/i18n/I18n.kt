package io.luxus.adofai.converter.i18n

import io.luxus.adofai.converter.i18n.I18nCode.*
import java.util.Locale

class I18n {

    private val allCodeMap: Map<String, Map<I18nCode, String>>
    private var codeMap: Map<I18nCode, String>

    init {
        allCodeMap = mapOf(
            Locale.ENGLISH.language to englishCodeMap(),
            Locale.KOREAN.language to koreanCodeMap(),
            Locale.CHINESE.language to chineseCodeMap(),
        )

        codeMap = allCodeMap[Locale.getDefault().language] ?: allCodeMap[Locale.ENGLISH.language]!!
    }

    fun getSupportedLanguage(): String =
        allCodeMap.keys.joinToString(separator = ", ")


    fun isSupportedLanguage(languageTag: String): Boolean {
        val locale = Locale.forLanguageTag(languageTag)
        if (locale.language.isEmpty()) {
            return false
        }

        return allCodeMap[locale.language] != null
    }

    fun println(i18NCode: I18nCode, vararg params: String) {
        println(getText(i18NCode, *params))
    }

    fun println() {
        println("")
    }

    fun println(text: String) {
        kotlin.io.println(text)
    }

    fun print(i18NCode: I18nCode, vararg params: String) {
        print(getText(i18NCode, *params))
    }

    fun printlnErr(i18NCode: I18nCode, vararg params: String) {
        System.err.println(getText(i18NCode, *params))
    }

    fun getText(i18NCode: I18nCode, vararg params: String): String {
        var text = codeMap[i18NCode]!!
        for (i in params.indices) {
            text = text.replaceFirst("{${i}}", params[i])
        }
        return text
    }

    fun setLanguage(languageTag: String) {
        codeMap = allCodeMap[Locale.forLanguageTag(languageTag).language]!!
    }

    private fun englishCodeMap(): Map<I18nCode, String> =
        mapOf(
            PROGRAM_PRESS_ENTER_TO_CONTINUE to "Press Enter to continue.",
            PROGRAM_OPTION_1 to "1. outer angle convert",
            PROGRAM_OPTION_2 to "2. linear convert",
            PROGRAM_OPTION_3 to "3. pattern convert",
            PROGRAM_OPTION_4 to "4. All tile twirl add/remove rate convert",
            PROGRAM_OPTION_5 to "5. Effect remove",
            PROGRAM_OPTION_6 to "6. Transparency convert",
            PROGRAM_OPTION_7 to "7. Bpm multiplier->Bpm convert",
            PROGRAM_OPTION_8 to "8. No speed change convert",
            PROGRAM_OPTION_9 to "9. All tile bpm multiplier convert",
            PROGRAM_OPTION_10 to "10. All tile random bpm convert",
            PROGRAM_OPTION_11 to "11. Midspin convert",
            PROGRAM_OPTION_12 to "12. Pseudo convert",
            PROGRAM_OPTION_13 to "13. Planet amount convert",
            PROGRAM_OPTION_14 to "14. Exit",
            INPUT to "Input : ",
            PROGRAM_EXIT to "Terminates the program.",
            PROGRAM_CONVERT_MESSAGE_1 to "*\"all\" will convert files in all subfolders except backup.adofai*",
            PROGRAM_CONVERT_MESSAGE_2 to "path(includes .adofai) : ",
            ERROR_FILE_NOT_EXIST to "File does not exists ({1})",
            ERROR_CANNOT_LOAD_FILE to "Failed to load file.",
            ERROR_CANNOT_LOAD_PATTERN to "Failed to read pattern.",

            PROGRAM_INPUT_LANGUAGE to "Input language({0}) : ",
            PROGRAM_INPUT_LANGUAGE_ERROR to "Unsupported language.",

            TWIRL_CONVERTER_ROTATION_RATE to "Twirl add/remove rate(0.0~1.0):",
            TWIRL_CONVERTER_ROTATION_RATE_ERROR to "Twirl ratio must be greater than or equal to 0 and less than or equal to 1.0.",

            SHAPED_MAP_CONVERTER_INPUT_FILE_OR_PATTERN_1 to "*It must be entered in the format of pathData or angleData in the .adofai file*",
            SHAPED_MAP_CONVERTER_INPUT_FILE_OR_PATTERN_2 to "pattern (or .adofai file path) : ",
            SHAPED_MAP_CONVERTER_ERROR_PATTERN_TILE_TOO_SHORT to "There are too few tiles in the pattern.",
            SHAPED_MAP_CONVERTER_ERROR_SHAPE_ANGLES_NULL to "shapeAngles is null.",
            SHAPED_MAP_CONVERTER_ERROR_PATTERN_ANGLE_TOO_SHORT to "The number of angles in the pattern is too small.",

            PSEUDO_MAP_CONVERTER_PSEUDO_AMOUNT to "Number of pseudo : ",
            PSEUDO_MAP_CONVERTER_PSEUDO_MAX_ANGLE to "Pseudo max angle : ",
            PSEUDO_MAP_CONVERTER_REMOVE_COLOR_TRACK_QUESTION to "Are you sure you want to remove colorTrack? (y/n) (If there are too many ColorTracks, the game may stop.) : ",
            PSEUDO_MAP_CONVERTER_PSEUDO_AMOUNT_ERROR to "Pseudo value too low! Please set it to 1 or higher!",
            PSEUDO_MAP_CONVERTER_PSEUDO_MAX_ANGLE_ERROR to "Pseudo max angle must be greater than 0 degrees.",

            PLANET_NUMBER_MAP_CONVERTER_PLANET_NUMBER to "Number of planets : ",
            PLANET_NUMBER_MAP_CONVERTER_KEEP_ORIGINAL_SHAPE to "Whether to keep the original map shape (true, false) : ",
            PLANET_NUMBER_MAP_CONVERTER_PLANET_NUMBER_ERROR to "The number of planets is too low! Please use a value of 2 or higher!",

            NO_SPEED_CHANGE_MAP_CONVERTER_DEST_BPM to "Destination bpm:",
            NO_SPEED_CHANGE_MAP_CONVERTER_DEST_BPM_ERROR to "The target BPM must be greater than zero.",

            CHAOS_BPM_MAP_CONVERTER_VIBRATE_RATE to "Percent of angles to oscillate (0 to 1):",

            BPM_MULTIPLY_MAP_CONVERTER_MULTIPLY_VALUE to "Multiplier value:",
            BPM_MULTIPLY_MAP_CONVERTER_MULTIPLY_VALUE_ERROR to "Multiplier value is too high. Please make it less than {0} times",

            ALL_MIDSPIN_MAP_CONVERTER_MIDSPIN_AMOUNT to "Midspin amount:",
            ALL_MIDSPIN_MAP_CONVERTER_MIDSPIN_AMOUNT_ERROR to "Midspin amount is too small! Please enter a value greater than 0!",

            TRANSPARENT_MAP_CONVERTER_TRANSPARENCY_VALUE to "Transparency(0~100):",
            TRANSPARENT_MAP_CONVERTER_TRANSPARENCY_VALUE_ERROR to "Transparency must be between 0 and 100.",

            NON_EFFECT_MAP_CONVERTER_LIST to "effect list : {0}",
            NON_EFFECT_MAP_CONVERTER_LIST_INPUT_MESSAGE to "Enter the effects you want to remove, separated by commas (,) : ",
            NON_EFFECT_MAP_CONVERTER_ERROR_UNKNOWN_EVENT_TYPE to "Unknown event type!({0})",
            NON_EFFECT_MAP_CONVERTER_LIST_ERROR_EMPTY to "There's no effect to remove.",
        )

    private fun koreanCodeMap(): Map<I18nCode, String> =
        mapOf(
            PROGRAM_PRESS_ENTER_TO_CONTINUE to "계속하시려면 엔터키를 눌러주세요.",
            PROGRAM_OPTION_1 to "1. 외각 변환",
            PROGRAM_OPTION_2 to "2. 선형 변환",
            PROGRAM_OPTION_3 to "3. 패턴 변환",
            PROGRAM_OPTION_4 to "4. 모든타일 회전 넣고 빼기 비율",
            PROGRAM_OPTION_5 to "5. 이펙트 제거",
            PROGRAM_OPTION_6 to "6. 투명도 변환",
            PROGRAM_OPTION_7 to "7. Bpm 승수->Bpm 변환",
            PROGRAM_OPTION_8 to "8. 무변속 맵 변환",
            PROGRAM_OPTION_9 to "9. 맵 전체 bpm *배수 변환",
            PROGRAM_OPTION_10 to "10. 모든 타일 무작위 bpm 변환",
            PROGRAM_OPTION_11 to "11. 미드스핀 변환",
            PROGRAM_OPTION_12 to "12. 동타 변환",
            PROGRAM_OPTION_13 to "13. 행성 수 변환",
            PROGRAM_OPTION_14 to "14. 종료",
            INPUT to "입력 : ",
            PROGRAM_EXIT to "프로그램을 종료합니다.",
            PROGRAM_CONVERT_MESSAGE_1 to "*all 시 backup.adofai 를 제외한 모든 하위 폴더의 파일을 변환합니다*",
            PROGRAM_CONVERT_MESSAGE_2 to "경로(.adofai 포함) : ",
            ERROR_FILE_NOT_EXIST to "파일이 존재하지 않습니다({1})",
            ERROR_CANNOT_LOAD_FILE to "파일 불러오기에 실패했습니다",
            ERROR_CANNOT_LOAD_PATTERN to "패턴 읽어오기에 실패했습니다.",

            PROGRAM_INPUT_LANGUAGE to "언어 입력({0}) : ",
            PROGRAM_INPUT_LANGUAGE_ERROR to "지원되지 않는 언어입니다.",

            TWIRL_CONVERTER_ROTATION_RATE to "회전 넣을 비율(0.0~1.0):",
            TWIRL_CONVERTER_ROTATION_RATE_ERROR to "회전 비율은 0 이상 1.0 이하여야 합니다.",

            SHAPED_MAP_CONVERTER_INPUT_FILE_OR_PATTERN_1 to "*.adofai 파일 내의 pathData 혹은 angleData 형식으로 입력하여야 합니다*",
            SHAPED_MAP_CONVERTER_INPUT_FILE_OR_PATTERN_2 to "패턴(혹은 .adofai 파일) : ",
            SHAPED_MAP_CONVERTER_ERROR_PATTERN_TILE_TOO_SHORT to "패턴의 타일 수가 너무 적습니다.",
            SHAPED_MAP_CONVERTER_ERROR_SHAPE_ANGLES_NULL to "shapeAngles가 null입니다.",
            SHAPED_MAP_CONVERTER_ERROR_PATTERN_ANGLE_TOO_SHORT to "패턴의 각도 수가 너무 적습니다.",

            PSEUDO_MAP_CONVERTER_PSEUDO_AMOUNT to "동타 수 : ",
            PSEUDO_MAP_CONVERTER_PSEUDO_MAX_ANGLE to "동타 최대 각도 : ",
            PSEUDO_MAP_CONVERTER_REMOVE_COLOR_TRACK_QUESTION to "colorTrack을 제거하시겠습니까? (y/n) (ColorTrack이 많을 시 게임이 멈출 수 있습니다.) : ",
            PSEUDO_MAP_CONVERTER_PSEUDO_AMOUNT_ERROR to "Pseudo 값이 너무 낮습니다! 1 이상으로 해주세요!",
            PSEUDO_MAP_CONVERTER_PSEUDO_MAX_ANGLE_ERROR to "동타 최대 각도는 0도보다 커야합니다.",

            PLANET_NUMBER_MAP_CONVERTER_PLANET_NUMBER to "행성 수 : ",
            PLANET_NUMBER_MAP_CONVERTER_KEEP_ORIGINAL_SHAPE to "원본 맵 형태 유지 여부(true, false) : ",
            PLANET_NUMBER_MAP_CONVERTER_PLANET_NUMBER_ERROR to "행성 수 값이 너무 낮습니다! 2와 같거나 큰 값으로 해주세요!",

            NO_SPEED_CHANGE_MAP_CONVERTER_DEST_BPM to "목표 BPM:",
            NO_SPEED_CHANGE_MAP_CONVERTER_DEST_BPM_ERROR to "목표 BPM은 0보다 커야합니다.",

            CHAOS_BPM_MAP_CONVERTER_VIBRATE_RATE to "진동할 각도 비율(0~1):",

            BPM_MULTIPLY_MAP_CONVERTER_MULTIPLY_VALUE to "배수:",
            BPM_MULTIPLY_MAP_CONVERTER_MULTIPLY_VALUE_ERROR to "배수가 너무 높습니다. {0}배 이하로 해주세요",

            ALL_MIDSPIN_MAP_CONVERTER_MIDSPIN_AMOUNT to "미드스핀 개수:",
            ALL_MIDSPIN_MAP_CONVERTER_MIDSPIN_AMOUNT_ERROR to "midspinAmount 가 너무 작습니다! 0 이상의 값을 입력해주세요!",

            TRANSPARENT_MAP_CONVERTER_TRANSPARENCY_VALUE to "투명도(0~100):",
            TRANSPARENT_MAP_CONVERTER_TRANSPARENCY_VALUE_ERROR to "투명도는 0이상 100이하여야합니다.",

            NON_EFFECT_MAP_CONVERTER_LIST to "목록 : {0}",
            NON_EFFECT_MAP_CONVERTER_LIST_INPUT_MESSAGE to "제거하고 싶은 effect를 콤마(,)를 통해 구분해서 입력해주세요 : ",
            NON_EFFECT_MAP_CONVERTER_ERROR_UNKNOWN_EVENT_TYPE to "알 수 없는 event type 입니다!({0})",
            NON_EFFECT_MAP_CONVERTER_LIST_ERROR_EMPTY to "제거할 effect가 없습니다.",
            )

    private fun chineseCodeMap(): Map<I18nCode, String> =
        mapOf(
            PROGRAM_PRESS_ENTER_TO_CONTINUE to "按Enter键继续",
            PROGRAM_OPTION_1 to "1. 外圈转换",
            PROGRAM_OPTION_2 to "2. 直线转换",
            PROGRAM_OPTION_3 to "3. 模式转换",
            PROGRAM_OPTION_4 to "4. 所有轨道添加旋转/删除率转换",
            PROGRAM_OPTION_5 to "5. 去掉特效",
            PROGRAM_OPTION_6 to "6. 透明度转换",
            PROGRAM_OPTION_7 to "7. Bpm乘数- > Bpm转换",
            PROGRAM_OPTION_8 to "8. 无变速转换",
            PROGRAM_OPTION_9 to "9. 全部轨道变成bpm乘数",
            PROGRAM_OPTION_10 to "10. 所有轨道变成随机bpm",
            PROGRAM_OPTION_11 to "11. 中旋转换",
            PROGRAM_OPTION_12 to "12. 多押转换",
            PROGRAM_OPTION_13 to "13. 行星数量转换",
            PROGRAM_OPTION_14 to "14. 退出",
            INPUT to "Input : ",
            PROGRAM_EXIT to "终止程序",
            PROGRAM_CONVERT_MESSAGE_1 to "*\"all\" 将转换除 backup.adofai 之外的所有子文件夹中的文件",
            PROGRAM_CONVERT_MESSAGE_2 to "路径(包括.adofai):",
            ERROR_FILE_NOT_EXIST to "文件不存在 ({1})",
            ERROR_CANNOT_LOAD_FILE to "加载文件失败",
            ERROR_CANNOT_LOAD_PATTERN to "读取模式失败",

            PROGRAM_INPUT_LANGUAGE to "输入语言({0}) : ",
            PROGRAM_INPUT_LANGUAGE_ERROR to "不支持的语言",

            TWIRL_CONVERTER_ROTATION_RATE to "旋转添加/删除率（0.0~1.0）：",
            TWIRL_CONVERTER_ROTATION_RATE_ERROR to "旋转必须大于或等于 0 且小于或等于 1.0。",

            SHAPED_MAP_CONVERTER_INPUT_FILE_OR_PATTERN_1 to "*必须在.adofai文件中以路径数据或角度数据的格式输入*",
            SHAPED_MAP_CONVERTER_INPUT_FILE_OR_PATTERN_2 to "模式（或 .adofai 文件路径）：",
            SHAPED_MAP_CONVERTER_ERROR_PATTERN_TILE_TOO_SHORT to "图案中的轨道太少。",
            SHAPED_MAP_CONVERTER_ERROR_SHAPE_ANGLES_NULL to "形状角度为空",
            SHAPED_MAP_CONVERTER_ERROR_PATTERN_ANGLE_TOO_SHORT to "图案中的角度数太少。",

            PSEUDO_MAP_CONVERTER_PSEUDO_AMOUNT to "伪押数量：",
            PSEUDO_MAP_CONVERTER_PSEUDO_MAX_ANGLE to "伪押最大度数：",
            PSEUDO_MAP_CONVERTER_REMOVE_COLOR_TRACK_QUESTION to "您确定要删除颜色轨道吗？  (y/n) （如果颜色轨道过多，游戏可能会停止。）： ",
            PSEUDO_MAP_CONVERTER_PSEUDO_AMOUNT_ERROR to "伪押值太低！ 请设置为1或更高！",
            PSEUDO_MAP_CONVERTER_PSEUDO_MAX_ANGLE_ERROR to "伪押值太低！ 请设置为1或更高！",

            PLANET_NUMBER_MAP_CONVERTER_PLANET_NUMBER to "行星数量 ",
            PLANET_NUMBER_MAP_CONVERTER_KEEP_ORIGINAL_SHAPE to "是否保持原图形状（确定，否）（确定输入true，否输入false）：",
            PLANET_NUMBER_MAP_CONVERTER_PLANET_NUMBER_ERROR to "行星数量太少了！ 请使用 2 或更高的值！",

            NO_SPEED_CHANGE_MAP_CONVERTER_DEST_BPM to "目标BPM：",
            NO_SPEED_CHANGE_MAP_CONVERTER_DEST_BPM_ERROR to "目标 BPM 必须大于零。",

            CHAOS_BPM_MAP_CONVERTER_VIBRATE_RATE to "摆动角度的百分比（0 到 1）：",

            BPM_MULTIPLY_MAP_CONVERTER_MULTIPLY_VALUE to "乘数值：",
            BPM_MULTIPLY_MAP_CONVERTER_MULTIPLY_VALUE_ERROR to "乘数值太高。 请使其少于 {0} 次",

            ALL_MIDSPIN_MAP_CONVERTER_MIDSPIN_AMOUNT to "中旋量：",
            ALL_MIDSPIN_MAP_CONVERTER_MIDSPIN_AMOUNT_ERROR to "中旋量太小！ 请输入一个大于 0 的值！",

            TRANSPARENT_MAP_CONVERTER_TRANSPARENCY_VALUE to "透明度（0~100）：",
            TRANSPARENT_MAP_CONVERTER_TRANSPARENCY_VALUE_ERROR to "透明度必须在0和100之间",

            NON_EFFECT_MAP_CONVERTER_LIST to "效果列表 : {0}",
            NON_EFFECT_MAP_CONVERTER_LIST_INPUT_MESSAGE to "输入要删除的效果，用逗号 (,) 分隔： ",
            NON_EFFECT_MAP_CONVERTER_ERROR_UNKNOWN_EVENT_TYPE to "未知事件类型！({0})",
            NON_EFFECT_MAP_CONVERTER_LIST_ERROR_EMPTY to "没有这种效果去删除",

        )

}
