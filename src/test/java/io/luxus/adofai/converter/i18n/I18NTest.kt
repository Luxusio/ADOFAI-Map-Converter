package io.luxus.adofai.converter.i18n

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNot
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.beEmpty
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class I18NTest {


    @ParameterizedTest
    @ValueSource(strings = [
        "en",
        "ko",
        "zh"
    ])
    fun isSupportedLanguage_withEnglishLanguageTag_thenReturnTrue(languageTag: String) {
        // given
        // languageTag
        val manager = I18n()

        // when
        val result = manager.isSupportedLanguage(languageTag)

        // then
        result shouldBe true
    }

    @ParameterizedTest
    @ValueSource(strings = [
        "en",
        "ko",
        "zh"
    ])
    fun getText_withAllCode_thenNoExceptionThrown_andNoEmptyTextReturn(languageTag: String) {
        // given
        // languageTag
        val manager = I18n()
        manager.setLanguage(languageTag)

        for (i18nCode in I18nCode.values()) {
            // when
            val result = manager.getText(i18nCode)

            // then
            result shouldNotBe null
            result shouldNot beEmpty()
        }
    }




}