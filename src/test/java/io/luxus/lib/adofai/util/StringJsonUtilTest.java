package io.luxus.lib.adofai.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StringJsonUtilTest {

    @Test
    void fixJsonStringTest() throws JsonProcessingException {
        // given
        String wrongJsonStr = "{\n" +
                "\t\"pathData\": \"RURDRURDRURDRURDRURDRURDRURDRURDRURDRURDRURDRURDRURDRURDRURDRURDRRRRRRRULLLLLLLLURRRRRRRRULLLLLLLLURRRRRRRRULLLLLLLLURRRRRRRRULLLLLLLLURRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRURRRRRRRRRRRRRRRRDRRRRRRRRRRRRRRRRURRRRRRRRRRRRRRRRDRRRRRRRRRRRRRRRRURRRRRRRRRRRRRRRRDRRRRRRRRRRRRRRRRURRRRRRRRRRRRRDRRRRRRRRDRRRRRRRRRRRURRRRRRRRRRRRRRRRDRRRRRRRRRRRRRRRRURRRRRRRRRRRRRRRRDRRRRRRRRRRRRRRRRURRRRRRRRRRRRRRRRDRRRRRRRRRRRRRRRRURRRRRRRRRTGLGTRTGLGTRTGLGTRTGLGTRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRDRRRRULRRRRRRRRRDRRRRULRRRRRRRRRRRRRULLLLLLLLURRRRRRRRULLLLLLLLURRRRRRRRULLLLLLLLURRRRRRRRULLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLULLLLLLLLLLLLLLLLDLLLLLLLLLLLLLLLLULLLLLLLLLLLLLLLLDLLLLLLLLLLLLLLLLULLLLLLLLLLLLLLLLDLLLLLLLLLLLLLLLLULLLLLLLLLLLLLLLLDLLLLLLLLLLLLLLLLULLLLLLLLLLLLLLLLDLLLLLLLLLLLLLLLLULLLLLLLLLLLLLLLLDLLLLLLLLLLLLLLLLULLLLLLLLLLLLLLLLDLLLLLLLLLLLLLLLLULLLLLLLLLFBRBFLFBRBFLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL\", \n" +
                "\t\"settings\":\n" +
                "\t{\n" +
                "\t\t\"version\": 2, \n" +
                "\t\t\"artist\": \"antiPLUR\", \n" +
                "\t\t\"song\": \"Speed of Link\", \n" +
                "\t\t\"author\": \"azz\", \n" +
                "\t\t\"separateCountdownTime\": \"Enabled\", \n" +
                "\t\t\"previewImage\": \"\", \n" +
                "\t\t\"previewIcon\": \"\", \n" +
                "\t\t\"previewIconColor\": \"003f52\", \n" +
                "\t\t\"previewSongStart\": 0, \n" +
                "\t\t\"previewSongDuration\": 10, \n" +
                "\t\t\"seizureWarning\": \"Disabled\", \n" +
                "\t\t\"levelDesc\": \"레벨에 대해 말해보세요!\", \n" +
                "\t\t\"levelTags\": \"\", \n" +
                "\t\t\"artistPermission\": \"\", \n" +
                "\t\t\"artistLinks\": \"\", \n" +
                "\t\t\"difficulty\": 1,\n" +
                "\t\t\"songFilename\": \"Speed of link.mp3\", \n" +
                "\t\t\"bpm\": 263.077, \n" +
                "\t\t\"volume\": 100, \n" +
                "\t\t\"offset\": 1140, \n" +
                "\t\t\"pitch\": 100, \n" +
                "\t\t\"hitsound\": \"Kick\", \n" +
                "\t\t\"hitsoundVolume\": 75, \n" +
                "\t\t\"countdownTicks\": 4,\n" +
                "\t\t\"trackColorType\": \"Glow\", \n" +
                "\t\t\"trackColor\": \"ffffff\", \n" +
                "\t\t\"secondaryTrackColor\": \"ff0000\", \n" +
                "\t\t\"trackColorAnimDuration\": 1.6, \n" +
                "\t\t\"trackColorPulse\": \"Forward\", \n" +
                "\t\t\"trackPulseLength\": 10, \n" +
                "\t\t\"trackStyle\": \"Neon\", \n" +
                "\t\t\"trackAnimation\": \"Fade\", \n" +
                "\t\t\"beatsAhead\": 5, \n" +
                "\t\t\"trackDisappearAnimation\": \"Fade\", \n" +
                "\t\t\"beatsBehind\": 0,\n" +
                "\t\t\"backgroundColor\": \"000000\", \n" +
                "\t\t\"bgImage\": \"ABG.jpg\", \n" +
                "\t\t\"bgImageColor\": \"ffffff\", \n" +
                "\t\t\"parallax\": [100, 100], \n" +
                "\t\t\"bgDisplayMode\": \"FitToScreen\", \n" +
                "\t\t\"lockRot\": \"Disabled\", \n" +
                "\t\t\"loopBG\": \"Disabled\", \n" +
                "\t\t\"unscaledSize\": 100,\n" +
                "\t\t\"relativeTo\": \"Player\", \n" +
                "\t\t\"position\": [0, 0], \n" +
                "\t\t\"rotation\": 0, \n" +
                "\t\t\"zoom\": 135,\n" +
                "\t\t\"bgVideo\": \"\", \n" +
                "\t\t\"loopVideo\": \"Disabled\", \n" +
                "\t\t\"vidOffset\": 0, \n" +
                "\t\t\"floorIconOutlines\": \"Disabled\", \n" +
                "\t\t\"stickToFloors\": \"Disabled\", \n" +
                "\t\t\"planetEase\": \"Linear\", \n" +
                "\t\t\"planetEaseParts\": 1\n" +
                "\t},\n" +
                "\t\"actions\":\n" +
                "\t[\n" +
                "\t\t{ \"floor\": 2, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 4, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 6, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 8, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 10, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 12, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 14, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 16, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 18, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 20, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 22, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 24, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 26, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 28, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 30, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 32, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 34, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 36, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 38, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 40, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 42, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 44, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 46, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 48, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 50, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 52, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 54, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 56, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 58, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 60, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 62, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 64, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 65, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 526.154, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 80, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 89, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 98, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 107, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 116, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 125, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 134, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 136, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 137, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 1052.308, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 137, \"eventType\": \"SetFilter\", \"filter\": \"Arcade\", \"enabled\": \"Enabled\", \"intensity\": 100, \"disableOthers\": \"Disabled\", \"angleOffset\": 0, \"eventTag\": \"\" },\n" +
                "\t\t{ \"floor\": 137, \"eventType\": \"MoveCamera\", \"duration\": 10, \"relativeTo\": \"Player\", \"position\": [0, 0], \"rotation\": 0, \"zoom\": 200, \"angleOffset\": 0, \"ease\": \"OutQuint\", \"eventTag\": \"\" },\n" +
                "\t\t{ \"floor\": 401, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 418, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 435, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 452, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 469, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 486, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 503, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 517, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 525, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 526, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 538, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 555, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 572, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 589, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 606, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 623, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 640, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 649, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 652, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 655, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 658, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 661, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 664, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 667, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 670, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 673, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 526.154, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 673, \"eventType\": \"SetFilter\", \"filter\": \"Arcade\", \"enabled\": \"Disabled\", \"intensity\": 100, \"disableOthers\": \"Disabled\", \"angleOffset\": 0, \"eventTag\": \"\" },\n" +
                "\t\t{ \"floor\": 673, \"eventType\": \"MoveCamera\", \"duration\": 3, \"relativeTo\": \"Player\", \"position\": [0, 0], \"rotation\": 0, \"zoom\": 150, \"angleOffset\": 0, \"ease\": \"OutQuint\", \"eventTag\": \"\" },\n" +
                "\t\t{ \"floor\": 673, \"eventType\": \"Flash\", \"duration\": 30, \"plane\": \"Foreground\", \"startColor\": \"ffffff\", \"startOpacity\": 30, \"endColor\": \"ffffff\", \"endOpacity\": 0, \"angleOffset\": 0, \"eventTag\": \"\" },\n" +
                "\t\t{ \"floor\": 674, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 801, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 131.5385, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 833, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 526.154, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 848, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 857, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 866, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 875, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 884, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 893, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 895, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 904, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 526.154, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 912, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 528, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 916, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 552, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 920, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 548, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 924, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 567.998, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 936, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 1140, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 936, \"eventType\": \"MoveCamera\", \"duration\": 10, \"relativeTo\": \"Player\", \"position\": [0, 0], \"rotation\": 0, \"zoom\": 200, \"angleOffset\": 0, \"ease\": \"OutQuint\", \"eventTag\": \"\" },\n" +
                "\t\t{ \"floor\": 936, \"eventType\": \"SetFilter\", \"filter\": \"Arcade\", \"enabled\": \"Enabled\", \"intensity\": 100, \"disableOthers\": \"Disabled\", \"angleOffset\": 0, \"eventTag\": \"\" },\n" +
                "\t\t{ \"floor\": 1200, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 1217, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 1234, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 1251, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 1268, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 1285, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 1302, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 1319, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 1336, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 1353, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 1370, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 1387, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 1404, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 1421, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 1438, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 1450, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 1453, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 1456, \"eventType\": \"Twirl\" },\n" +
                "\t\t{ \"floor\": 1467, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 1132, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1483, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 1120, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1491, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 1112, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1499, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 1104, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1503, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 1100, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1511, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 1092, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1515, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 1088, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1519, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 1084, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1527, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 1076, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1531, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 1074, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1535, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 1068, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1539, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 1064, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1543, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 1062, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1547, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 1056, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1551, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 1052, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1555, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 1048, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1559, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 1045.996, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1563, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 1040, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1567, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 1036, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1571, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 1032, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1575, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 1028, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1579, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 1024, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1583, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 1020, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1587, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 1016.004, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1591, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 1010.8, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1595, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 1008, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1599, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 1004, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1603, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 998.4, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1607, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 996, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1611, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 992, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1615, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 984, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1623, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 980, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1627, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 972, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1635, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 968, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1639, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 962.4, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1643, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 960, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1647, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 952, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1651, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 950.8, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1655, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 946.8, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1659, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 942, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1663, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 938, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1667, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 932, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1675, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 926, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1679, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 920, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1683, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 916, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1691, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 908, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1695, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 906, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1699, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 901.2, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1703, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 898, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1707, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 892, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1715, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 884, \"bpmMultiplier\": 1 },\n" +
                "\t\t{ \"floor\": 1719, \"eventType\": \"SetSpeed\", \"speedType\": \"Bpm\", \"beatsPerMinute\": 880, \"bpmMultiplier\": 1 }\n" +
                "\t]\n" +
                "}\n";

        // when
        String result = StringJsonUtil.fixJsonString(wrongJsonStr);

        // then

        JsonNode nodeResult = new ObjectMapper().readTree(result);
        assertThat(nodeResult).isNotNull();
    }

}