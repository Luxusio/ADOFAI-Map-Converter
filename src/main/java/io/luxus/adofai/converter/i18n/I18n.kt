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
            Locale.CHINESE.language to simplifiedChineseCodeMap(),
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
        mapOf()

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

    private fun simplifiedChineseCodeMap(): Map<I18nCode, String> =
        mapOf()

}