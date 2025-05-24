#include "keyobf_header.h"
#include <jni.h>
#include <string.h>

#define OBFUSCATED_STORAGE_SIZE 600
#define REAL_VALUE_SIZE 172

unsigned char obfuscatedStorage[OBFUSCATED_STORAGE_SIZE];

int real_indices[REAL_VALUE_SIZE];

int real_index = 0;

static void store_real_value(int storage_index, int real_value) {
    obfuscatedStorage[storage_index] = real_value;
    real_indices[real_index] = storage_index;
    real_index++;
}

static void store_fake_value(int storage_index, int fake_value) {
    obfuscatedStorage[storage_index] = fake_value;
}


JNIEXPORT void JNICALL
Java_com_example_prompthub_utils_KeyLoader_f0(JNIEnv
                                              *env,
                                              jobject thiz
) {
    store_real_value(245, 89);
    store_real_value(280, 122);
    store_real_value(502, 69);
    store_fake_value(57, 114);
    store_real_value(394, 121);
    store_real_value(270, 89);
    store_fake_value(268, 89);
    store_fake_value(180, 6);
    store_real_value(262, 106);
    store_real_value(560, 81);
    store_real_value(554, 52);
    store_fake_value(468, 37);
    store_fake_value(411, 115);
    store_real_value(131, 90);
    store_real_value(107, 106);
    store_real_value(227, 100);
    store_fake_value(27, 78);
    store_fake_value(524, 8);
    store_real_value(391, 106);
    store_real_value(178, 79);
    store_real_value(340, 68);
    store_fake_value(156, 69);
    store_real_value(221, 69);
    store_real_value(173, 119);
    store_fake_value(69, 37);
    store_real_value(60, 78);
    store_real_value(41, 106);
    store_real_value(533, 65);
    store_fake_value(433, 39);
    store_fake_value(370, 92);
    store_fake_value(441, 9);
    store_real_value(459, 48);
    store_real_value(382, 78);
    store_real_value(58, 109);
    store_fake_value(113, 34);
    store_real_value(446, 86);
    store_fake_value(205, 23);
    store_fake_value(520, 98);
}

JNIEXPORT void JNICALL
Java_com_example_prompthub_utils_KeyLoader_f1(JNIEnv
                                              *env,
                                              jobject thiz
) {
    store_fake_value(400, 118);
    store_real_value(117, 109);
    store_real_value(74, 78);
    store_real_value(121, 109);
    store_fake_value(166, 111);
    store_fake_value(281, 99);
    store_real_value(240, 69);
    store_real_value(573, 50);
    store_real_value(447, 78);
    store_fake_value(476, 48);
    store_fake_value(86, 80);
    store_real_value(52, 71);
    store_real_value(495, 69);
    store_fake_value(208, 47);
    store_fake_value(22, 27);
    store_real_value(315, 122);
    store_real_value(526, 78);
    store_real_value(568, 87);
    store_fake_value(264, 19);
    store_fake_value(302, 61);
    store_fake_value(553, 59);
    store_real_value(352, 82);
    store_fake_value(252, 16);
    store_real_value(506, 105);
    store_real_value(226, 77);
    store_real_value(525, 71);
    store_fake_value(109, 26);
    store_fake_value(486, 20);
    store_real_value(81, 81);
    store_fake_value(505, 94);
    store_fake_value(515, 71);
    store_real_value(485, 122);
    store_real_value(271, 90);
    store_fake_value(424, 21);
    store_fake_value(248, 34);
    store_fake_value(207, 82);
    store_real_value(105, 87);
    store_real_value(200, 69);
}

JNIEXPORT void JNICALL
Java_com_example_prompthub_utils_KeyLoader_f2(JNIEnv
                                              *env,
                                              jobject thiz
) {
    store_fake_value(168, 110);
    store_fake_value(23, 70);
    store_real_value(536, 121);
    store_real_value(474, 78);
    store_real_value(457, 84);
    store_fake_value(153, 106);
    store_fake_value(188, 79);
    store_fake_value(532, 108);
    store_real_value(290, 74);
    store_fake_value(275, 115);
    store_real_value(378, 107);
    store_real_value(328, 78);
    store_fake_value(589, 96);
    store_fake_value(596, 40);
    store_fake_value(422, 63);
    store_real_value(327, 68);
    store_real_value(386, 90);
    store_fake_value(578, 72);
    store_fake_value(321, 100);
    store_fake_value(232, 122);
    store_real_value(316, 104);
    store_real_value(452, 89);
    store_real_value(276, 50);
    store_fake_value(100, 68);
    store_fake_value(46, 75);
    store_fake_value(195, 35);
    store_real_value(373, 90);
    store_fake_value(575, 46);
    store_fake_value(588, 73);
    store_fake_value(383, 87);
    store_real_value(128, 109);
    store_real_value(558, 78);
    store_real_value(353, 84);
    store_fake_value(163, 44);
    store_fake_value(339, 62);
    store_real_value(116, 103);
    store_fake_value(212, 35);
    store_fake_value(565, 76);
}

JNIEXPORT void JNICALL
Java_com_example_prompthub_utils_KeyLoader_f3(JNIEnv
                                              *env,
                                              jobject thiz
) {
    store_fake_value(482, 47);
    store_real_value(7, 53);
    store_real_value(204, 78);
    store_real_value(247, 84);
    store_fake_value(365, 117);
    store_fake_value(251, 55);
    store_real_value(419, 100);
    store_real_value(87, 108);
    store_real_value(479, 90);
    store_fake_value(18, 83);
    store_real_value(143, 71);
    store_real_value(36, 69);
    store_real_value(71, 51);
    store_fake_value(377, 94);
    store_real_value(244, 90);
    store_real_value(3, 84);
    store_real_value(325, 69);
    store_fake_value(497, 40);
    store_real_value(45, 120);
    store_real_value(99, 90);
    store_real_value(301, 87);
    store_fake_value(214, 11);
    store_real_value(405, 73);
    store_fake_value(106, 16);
    store_real_value(91, 52);
    store_real_value(185, 90);
    store_fake_value(466, 58);
    store_fake_value(528, 53);
    store_fake_value(434, 117);
    store_real_value(343, 68);
    store_real_value(432, 89);
    store_real_value(132, 53);
    store_fake_value(110, 108);
    store_real_value(181, 89);
    store_fake_value(6, 44);
    store_fake_value(283, 41);
    store_fake_value(398, 57);
    store_real_value(593, 122);
}

JNIEXPORT void JNICALL
Java_com_example_prompthub_utils_KeyLoader_f4(JNIEnv
                                              *env,
                                              jobject thiz
) {
    store_real_value(192, 99);
    store_fake_value(543, 35);
    store_fake_value(194, 45);
    store_fake_value(471, 17);
    store_real_value(93, 49);
    store_fake_value(84, 118);
    store_real_value(511, 90);
    store_real_value(4, 106);
    store_fake_value(436, 72);
    store_fake_value(68, 26);
    store_fake_value(14, 55);
    store_real_value(431, 69);
    store_real_value(122, 53);
    store_real_value(413, 79);
    store_fake_value(197, 52);
    store_fake_value(392, 100);
    store_fake_value(513, 57);
    store_real_value(550, 84);
    store_real_value(265, 69);
    store_real_value(576, 121);
    store_fake_value(577, 0);
    store_fake_value(493, 17);
    store_real_value(440, 90);
    store_fake_value(258, 69);
    store_real_value(92, 109);
    store_real_value(374, 82);
    store_fake_value(162, 91);
    store_fake_value(243, 29);
    store_real_value(300, 105);
    store_real_value(324, 77);
    store_fake_value(233, 87);
    store_fake_value(456, 50);
    store_real_value(59, 122);
    store_real_value(171, 89);
    store_fake_value(350, 52);
    store_real_value(263, 49);
    store_real_value(215, 78);
    store_real_value(304, 122);
}

JNIEXPORT void JNICALL
Java_com_example_prompthub_utils_KeyLoader_f5(JNIEnv
                                              *env,
                                              jobject thiz
) {
    store_fake_value(136, 42);
    store_real_value(412, 69);
    store_real_value(314, 52);
    store_fake_value(503, 105);
    store_fake_value(285, 116);
    store_real_value(5, 77);
    store_real_value(26, 68);
    store_fake_value(435, 39);
    store_real_value(126, 73);
    store_real_value(210, 119);
    store_real_value(416, 89);
    store_fake_value(175, 8);
    store_real_value(133, 84);
    store_fake_value(572, 19);
    store_real_value(455, 69);
    store_fake_value(599, 3);
    store_real_value(501, 120);
    store_fake_value(89, 43);
    store_fake_value(535, 60);
    store_real_value(187, 90);
    store_fake_value(255, 74);
    store_fake_value(150, 81);
    store_fake_value(234, 4);
    store_real_value(345, 68);
    store_real_value(101, 70);
    store_fake_value(507, 61);
    store_real_value(582, 104);
    store_real_value(213, 78);
    store_real_value(64, 106);
    store_fake_value(312, 102);
    store_fake_value(112, 49);
    store_fake_value(196, 6);
    store_real_value(384, 85);
    store_real_value(310, 52);
    store_fake_value(344, 108);
    store_fake_value(581, 105);
    store_real_value(239, 78);
    store_real_value(448, 106);
}

JNIEXPORT void JNICALL
Java_com_example_prompthub_utils_KeyLoader_f6(JNIEnv
                                              *env,
                                              jobject thiz
) {
    store_real_value(478, 73);
    store_fake_value(332, 25);
    store_real_value(547, 52);
    store_fake_value(406, 91);
    store_fake_value(308, 10);
    store_real_value(496, 90);
    store_real_value(292, 109);
    store_fake_value(189, 71);
    store_fake_value(402, 119);
    store_real_value(477, 81);
    store_fake_value(127, 46);
    store_fake_value(488, 86);
    store_real_value(487, 48);
    store_fake_value(393, 22);
    store_fake_value(521, 20);
    store_real_value(320, 89);
    store_real_value(484, 87);
    store_fake_value(351, 91);
    store_fake_value(395, 66);
    store_fake_value(341, 39);
    store_real_value(463, 73);
    store_fake_value(552, 16);
    store_real_value(566, 53);
    store_fake_value(165, 73);
    store_fake_value(409, 74);
    store_fake_value(363, 104);
    store_real_value(545, 78);
    store_real_value(157, 109);
    store_real_value(56, 82);
    store_fake_value(76, 45);
    store_fake_value(203, 87);
    store_real_value(336, 109);
    store_fake_value(8, 39);
    store_real_value(120, 89);
    store_real_value(364, 84);
    store_fake_value(564, 84);
    store_fake_value(491, 87);
    store_real_value(579, 74);
}

JNIEXPORT void JNICALL
Java_com_example_prompthub_utils_KeyLoader_f7(JNIEnv
                                              *env,
                                              jobject thiz
) {

    store_fake_value(140, 112);
    store_fake_value(13, 28);
    store_fake_value(201, 95);
    store_real_value(138, 109);
    store_fake_value(249, 73);
    store_fake_value(242, 27);
    store_fake_value(277, 20);
    store_real_value(104, 90);
    store_fake_value(75, 113);
    store_fake_value(79, 86);
    store_real_value(439, 87);
    store_real_value(580, 70);
    store_real_value(490, 108);
    store_fake_value(50, 32);
    store_fake_value(297, 75);
    store_real_value(451, 77);
    store_fake_value(333, 103);
    store_real_value(584, 109);
    store_real_value(289, 77);
    store_real_value(179, 122);
    store_fake_value(9, 23);
    store_fake_value(534, 13);
    store_fake_value(34, 88);
    store_real_value(28, 90);
    store_real_value(335, 84);
    store_fake_value(278, 16);
    store_fake_value(65, 39);
    store_real_value(223, 89);
    store_real_value(475, 53);
    store_fake_value(510, 95);
    store_real_value(499, 78);
    store_fake_value(269, 75);
    store_fake_value(62, 83);
    store_real_value(529, 50);
    store_fake_value(78, 61);
    store_fake_value(103, 95);
    store_real_value(170, 73);

}

JNIEXPORT void JNICALL
Java_com_example_prompthub_utils_KeyLoader_f8(JNIEnv
                                              *env,
                                              jobject thiz
) {
    store_real_value(246, 51);
    store_real_value(291, 77);
    store_fake_value(182, 104);
    store_fake_value(329, 62);
    store_real_value(460, 84);
    store_fake_value(231, 67);
    store_fake_value(559, 49);
    store_fake_value(161, 75);
    store_real_value(462, 107);
    store_real_value(472, 121);
    store_real_value(118, 79);
    store_fake_value(294, 41);
    store_fake_value(450, 122);
    store_real_value(282, 68);
    store_real_value(425, 86);
    store_fake_value(303, 40);
    store_fake_value(449, 37);
    store_real_value(211, 105);
    store_real_value(376, 78);
    store_fake_value(83, 59);
    store_fake_value(428, 102);
    store_fake_value(70, 26);
    store_real_value(287, 106);
    store_fake_value(551, 46);
    store_real_value(366, 99);
    store_fake_value(379, 33);
    store_fake_value(217, 86);
    store_real_value(284, 51);
    store_real_value(51, 77);
    store_real_value(288, 84);
    store_fake_value(209, 118);
    store_fake_value(230, 74);
    store_real_value(147, 69);
    store_real_value(381, 61);
    store_fake_value(444, 48);
}