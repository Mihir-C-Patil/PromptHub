#include <jni.h>
#include <string.h>

#define OBFUSCATED_STORAGE_SIZE 600
#define REAL_VALUE_SIZE 172

static unsigned char obfuscatedStorage[OBFUSCATED_STORAGE_SIZE];

static int real_indices[REAL_VALUE_SIZE];

static void store_real_value(int storage_index, int real_value) {
    obfuscatedStorage[storage_index] = real_value;
    real_indices[real_value] = storage_index;
}

static void store_fake_value(int storage_index, int fake_value) {
    obfuscatedStorage[storage_index] = fake_value;
}



JNIEXPORT void JNICALL
Java_com_example_prompthub_utils_KeyLoader_f0(JNIEnv
*env,
jobject thiz
) {
    store_real_value(231, 89);
    store_real_value(483, 122);
    store_real_value(377, 69);
    store_fake_value(306, 95);
    store_fake_value(61, 77);
    store_fake_value(7, 28);
    store_fake_value(70, 26);
    store_real_value(576, 121);
    store_real_value(417, 89);
    store_fake_value(535, 32);
    store_fake_value(130, 23);
    store_fake_value(322, 121);
    store_fake_value(60, 91);
    store_fake_value(255, 18);
    store_fake_value(378, 80);
    store_real_value(91, 106);
    store_fake_value(498, 116);
    store_fake_value(479, 10);
    store_fake_value(148, 45);
    store_real_value(285, 81);
    store_fake_value(482, 31);
    store_fake_value(172, 62);
    store_fake_value(265, 12);
    store_fake_value(26, 121);
    store_fake_value(237, 1);
    store_fake_value(329, 22);
    store_real_value(254, 52);
    store_real_value(526, 90);
    store_fake_value(78, 47);
    store_fake_value(99, 55);
    store_fake_value(396, 95);
    store_fake_value(106, 23);
    store_real_value(473, 106);
    store_real_value(445, 100);
    store_real_value(226, 106);
    store_fake_value(431, 53);
    store_fake_value(76, 36);
    store_real_value(509, 79);
    store_real_value(188, 68);
    store_real_value(253, 69);
    store_real_value(514, 119);
    store_fake_value(570, 27);
    store_fake_value(402, 10);
    store_fake_value(262, 77);
    store_fake_value(178, 66);
    store_real_value(328, 78);
    store_real_value(34, 106);
    store_real_value(507, 65);
}

JNIEXPORT void JNICALL
Java_com_example_prompthub_utils_KeyLoader_f1(JNIEnv
*env,
jobject thiz
    ) {
    store_fake_value(366, 60);
    store_fake_value(244, 61);
    store_real_value(472, 48);
    store_real_value(21, 78);
    store_real_value(43, 109);
    store_fake_value(18, 20);
    store_fake_value(526, 11);
    store_fake_value(54, 121);
    store_fake_value(33, 102);
    store_real_value(179, 86);
    store_real_value(59, 109);
    store_real_value(398, 78);
    store_fake_value(230, 24);
    store_fake_value(291, 86);
    store_fake_value(117, 101);
    store_fake_value(436, 62);
    store_fake_value(22, 100);
    store_real_value(148, 109);
    store_real_value(79, 69);
    store_real_value(84, 50);
    store_real_value(311, 78);
    store_fake_value(492, 118);
    store_fake_value(566, 105);
    store_fake_value(218, 46);
    store_real_value(588, 71);
    store_fake_value(438, 121);
    store_fake_value(56, 14);
    store_fake_value(454, 18);
    store_fake_value(46, 13);
    store_real_value(558, 69);
    store_real_value(581, 122);
    store_fake_value(523, 36);
    store_fake_value(228, 95);
    store_fake_value(370, 2);
    store_fake_value(394, 81);
    store_fake_value(242, 73);
    store_real_value(292, 78);
    store_real_value(539, 87);
    store_real_value(230, 82);
    store_real_value(539, 105);
    store_fake_value(211, 64);
    store_fake_value(302, 103);
    store_fake_value(15, 15);
    store_fake_value(12, 90);
    store_fake_value(344, 81);
    store_fake_value(559, 117);
    store_real_value(484, 77);
    store_fake_value(158, 27);
}

JNIEXPORT void JNICALL
Java_com_example_prompthub_utils_KeyLoader_f2(JNIEnv
*env,
jobject thiz
    ) {
    store_fake_value(100, 33);
    store_real_value(526, 71);
    store_real_value(165, 81);
    store_real_value(398, 122);
    store_fake_value(395, 17);
    store_fake_value(164, 67);
    store_fake_value(90, 86);
    store_fake_value(496, 107);
    store_fake_value(39, 73);
    store_real_value(442, 90);
    store_real_value(50, 87);
    store_real_value(61, 69);
    store_real_value(447, 121);
    store_fake_value(347, 69);
    store_fake_value(503, 116);
    store_fake_value(418, 72);
    store_real_value(587, 78);
    store_real_value(277, 84);
    store_real_value(257, 74);
    store_fake_value(380, 60);
    store_fake_value(362, 58);
    store_real_value(316, 107);
    store_real_value(412, 78);
    store_real_value(137, 68);
    store_fake_value(175, 78);
    store_fake_value(423, 57);
    store_fake_value(489, 99);
    store_real_value(11, 90);
    store_real_value(229, 104);
    store_fake_value(589, 50);
    store_fake_value(510, 117);
    store_fake_value(515, 20);
    store_real_value(9, 89);
    store_real_value(27, 50);
    store_real_value(236, 90);
    store_fake_value(236, 85);
    store_fake_value(192, 18);
    store_fake_value(327, 28);
    store_real_value(574, 109);
    store_real_value(34, 78);
    store_real_value(554, 84);
    store_real_value(473, 103);
    store_fake_value(287, 114);
    store_fake_value(553, 112);
    store_fake_value(245, 98);
    store_real_value(313, 53);
    store_real_value(292, 78);
}

JNIEXPORT void JNICALL
Java_com_example_prompthub_utils_KeyLoader_f3(JNIEnv
                                              *env,
                                              jobject thiz
) {
    store_fake_value(552, 104);
    store_fake_value(444, 99);
    store_real_value(161, 84);
    store_fake_value(363, 35);
    store_fake_value(456, 50);
    store_fake_value(28, 9);
    store_fake_value(173, 35);
    store_fake_value(579, 118);
    store_real_value(103, 100);
    store_real_value(478, 108);
    store_real_value(371, 90);
    store_fake_value(303, 84);
    store_fake_value(407, 112);
    store_fake_value(109, 119);
    store_fake_value(108, 92);
    store_fake_value(573, 109);
    store_real_value(593, 71);
    store_fake_value(258, 101);
    store_fake_value(493, 38);
    store_fake_value(141, 55);
    store_fake_value(442, 39);
    store_real_value(500, 69);
    store_real_value(416, 51);
    store_fake_value(379, 72);
    store_fake_value(475, 120);
    store_real_value(70, 90);
    store_real_value(325, 84);
    store_real_value(225, 69);
    store_fake_value(149, 121);
    store_fake_value(525, 105);
    store_fake_value(388, 53);
    store_fake_value(568, 99);
    store_fake_value(463, 81);
    store_real_value(96, 120);
    store_fake_value(27, 120);
    store_real_value(542, 90);
    store_real_value(316, 87);
    store_real_value(150, 73);
    store_real_value(148, 52);
    store_fake_value(116, 109);
    store_fake_value(206, 22);
    store_fake_value(145, 56);
    store_real_value(506, 90);
    store_fake_value(324, 21);
    store_real_value(95, 68);
    store_real_value(185, 89);
    store_fake_value(350, 20);
    store_fake_value(144, 2);
}

JNIEXPORT void JNICALL
Java_com_example_prompthub_utils_KeyLoader_f4(JNIEnv
                                              *env,
                                              jobject thiz
) {
    store_real_value(324, 53);
    store_real_value(561, 89);
    store_real_value(239, 122);
    store_real_value(341, 99);
    store_fake_value(408, 98);
    store_fake_value(150, 75);
    store_real_value(422, 49);
    store_fake_value(476, 52);
    store_real_value(553, 90);
    store_real_value(172, 106);
    store_real_value(139, 69);
    store_fake_value(462, 71);
    store_fake_value(289, 104);
    store_fake_value(241, 89);
    store_fake_value(575, 99);
    store_fake_value(162, 11);
    store_real_value(372, 53);
    store_fake_value(323, 120);
    store_fake_value(398, 31);
    store_fake_value(583, 5);
    store_fake_value(577, 26);
    store_real_value(53, 79);
    store_real_value(461, 84);
    store_real_value(573, 69);
    store_fake_value(417, 118);
    store_fake_value(314, 35);
    store_fake_value(157, 4);
    store_fake_value(235, 121);
    store_fake_value(2, 24);
    store_fake_value(174, 116);
    store_real_value(118, 121);
    store_real_value(405, 90);
    store_fake_value(154, 90);
    store_fake_value(453, 40);
    store_fake_value(129, 83);
    store_real_value(144, 109);
    store_real_value(568, 82);
    store_real_value(156, 105);
    store_fake_value(358, 121);
    store_fake_value(434, 99);
    store_fake_value(20, 121);
    store_fake_value(112, 65);
    store_fake_value(248, 100);
    store_real_value(546, 77);
    store_real_value(392, 122);
    store_real_value(533, 89);
    store_real_value(406, 49);
    store_fake_value(412, 95);
}

JNIEXPORT void JNICALL
Java_com_example_prompthub_utils_KeyLoader_f5(JNIEnv
                                              *env,
                                              jobject thiz
) {
    store_fake_value(86, 59);
    store_fake_value(268, 61);
    store_fake_value(11, 114);
    store_fake_value(232, 55);
    store_fake_value(45, 30);
    store_real_value(431, 78);
    store_real_value(456, 122);
    store_real_value(148, 69);
    store_real_value(360, 52);
    store_fake_value(21, 28);
    store_fake_value(424, 53);
    store_real_value(134, 77);
    store_real_value(280, 68);
    store_real_value(195, 73);
    store_real_value(427, 119);
    store_fake_value(251, 72);
    store_fake_value(91, 2);
    store_fake_value(217, 59);
    store_real_value(577, 89);
    store_real_value(315, 84);
    store_real_value(568, 69);
    store_fake_value(256, 82);
    store_fake_value(397, 104);
    store_fake_value(243, 43);
    store_fake_value(386, 42);
    store_fake_value(83, 79);
    store_real_value(252, 120);
    store_real_value(542, 90);
    store_real_value(383, 68);
    store_fake_value(151, 93);
    store_real_value(33, 70);
    store_fake_value(137, 65);
    store_fake_value(460, 18);
    store_fake_value(50, 41);
    store_real_value(372, 104);
    store_fake_value(101, 42);
    store_real_value(357, 78);
    store_real_value(392, 106);
    store_real_value(154, 85);
    store_fake_value(111, 0);
    store_real_value(200, 52);
    store_real_value(406, 78);
    store_fake_value(30, 6);
    store_fake_value(505, 95);
    store_fake_value(516, 22);
    store_real_value(125, 106);
    store_fake_value(464, 115);
    store_real_value(82, 73);
}

JNIEXPORT void JNICALL
Java_com_example_prompthub_utils_KeyLoader_f6(JNIEnv
                                              *env,
                                              jobject thiz
) {
    store_fake_value(312, 27);
    store_fake_value(139, 65);
    store_fake_value(514, 112);
    store_fake_value(194, 89);
    store_real_value(191, 52);
    store_real_value(465, 90);
    store_real_value(136, 109);
    store_real_value(383, 81);
    store_fake_value(478, 55);
    store_fake_value(487, 68);
    store_fake_value(92, 66);
    store_fake_value(136, 105);
    store_fake_value(563, 95);
    store_fake_value(447, 94);
    store_real_value(253, 48);
    store_fake_value(422, 93);
    store_fake_value(290, 89);
    store_fake_value(238, 105);
    store_fake_value(334, 111);
    store_fake_value(520, 13);
    store_fake_value(55, 97);
    store_real_value(542, 89);
    store_real_value(2, 87);
    store_fake_value(545, 58);
    store_fake_value(208, 121);
    store_real_value(341, 73);
    store_real_value(401, 53);
    store_real_value(471, 78);
    store_fake_value(205, 98);
    store_fake_value(375, 93);
    store_fake_value(51, 35);
    store_fake_value(198, 103);
    store_fake_value(307, 104);
    store_real_value(217, 109);
    store_real_value(504, 82);
    store_real_value(276, 109);
    store_fake_value(567, 55);
    store_fake_value(481, 109);
    store_fake_value(557, 20);
    store_real_value(87, 89);
    store_real_value(446, 84);
    store_fake_value(596, 12);
    store_real_value(492, 74);
    store_real_value(358, 109);
    store_real_value(536, 90);
    store_fake_value(338, 68);
    store_fake_value(246, 78);
    store_fake_value(305, 42);
}

JNIEXPORT void JNICALL
Java_com_example_prompthub_utils_KeyLoader_f7(JNIEnv
                                              *env,
                                              jobject thiz
) {
    store_fake_value(4, 41);
    store_fake_value(469, 41);
    store_real_value(147, 87);
    store_real_value(354, 70);
    store_fake_value(490, 28);
    store_real_value(384, 108);
    store_real_value(470, 77);
    store_fake_value(531, 38);
    store_fake_value(159, 13);
    store_fake_value(506, 16);
    store_fake_value(384, 79);
    store_real_value(356, 109);
    store_fake_value(272, 38);
    store_fake_value(288, 11);
    store_fake_value(547, 25);
    store_fake_value(491, 13);
    store_fake_value(474, 63);
    store_real_value(106, 77);
    store_real_value(263, 122);
    store_fake_value(53, 67);
    store_fake_value(369, 79);
    store_fake_value(188, 103);
    store_fake_value(140, 30);
    store_fake_value(300, 56);
    store_real_value(578, 90);
    store_real_value(2, 84);
    store_fake_value(10, 50);
    store_fake_value(518, 74);
    store_real_value(325, 89);
    store_real_value(301, 53);
    store_real_value(528, 78);
    store_fake_value(296, 19);
    store_fake_value(110, 96);
    store_fake_value(41, 3);
    store_fake_value(231, 34);
    store_fake_value(153, 107);
    store_fake_value(560, 5);
    store_real_value(540, 50);
    store_fake_value(377, 10);
    store_fake_value(390, 80);
    store_real_value(259, 73);
    store_real_value(208, 51);
    store_fake_value(67, 63);
    store_fake_value(187, 73);
    store_fake_value(212, 20);
    store_fake_value(284, 95);
    store_real_value(572, 77);
    store_fake_value(341, 101);
}

JNIEXPORT void JNICALL
Java_com_example_prompthub_utils_KeyLoader_f8(JNIEnv
                                              *env,
                                              jobject thiz
) {
    store_fake_value(142, 55);
    store_fake_value(182, 1);
    store_fake_value(120, 11);
    store_real_value(358, 84);
    store_real_value(314, 107);
    store_fake_value(180, 48);
    store_fake_value(534, 20);
    store_real_value(513, 121);
    store_real_value(3, 79);
    store_fake_value(495, 74);
    store_fake_value(415, 105);
    store_fake_value(294, 10);
    store_fake_value(214, 61);
    store_fake_value(176, 29);
    store_fake_value(165, 54);
    store_real_value(468, 68);
    store_fake_value(227, 57);
    store_fake_value(574, 31);
    store_fake_value(277, 54);
    store_fake_value(477, 113);
    store_fake_value(115, 14);
    store_real_value(455, 86);
    store_real_value(300, 105);
    store_real_value(428, 78);
    store_fake_value(215, 30);
    store_fake_value(419, 14);
    store_real_value(590, 106);
    store_real_value(21, 99);
    store_real_value(508, 51);
    store_fake_value(364, 80);
    store_fake_value(276, 26);
    store_fake_value(114, 93);
    store_real_value(540, 77);
    store_real_value(277, 84);
    store_fake_value(42, 84);
    store_fake_value(37, 117);
    store_fake_value(219, 37);
    store_real_value(350, 69);
    store_real_value(502, 61);
    store_fake_value(578, 66);
    store_fake_value(382, 28);
    store_fake_value(79, 72);
    store_fake_value(367, 36);
    store_fake_value(122, 108);
    store_fake_value(374, 25);
}