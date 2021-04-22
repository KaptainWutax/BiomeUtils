package kaptainwutax.v1_13_2;

import kaptainwutax.TestFramework;
import kaptainwutax.biomeutils.source.OverworldBiomeSource;
import kaptainwutax.mcutils.version.MCVersion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static kaptainwutax.TestFramework.randomBiomeGen;


@DisplayName("Minecraft v1.13.2 Overworld")
@Tag("v1.13.2")
@TestFramework.Overworld
public class Overworld {
	private static final String version = "1.13.2";
	public int[][] base = {{171, 497, 4}, {69, 130, 2}, {412, 107, 1}, {35, 168, 2}, {233, 337, 3}, {510, 409, 1}, {92, 263, 3}, {223, 262, 2}, {201, 226, 3}, {54, 44, 1539}, {221, 439, 2}, {234, 47, 2}, {392, 313, 0}, {506, 263, 4}, {53, 351, 2}, {269, 216, 2}, {71, 494, 2}, {44, 291, 0}, {451, 285, 3}, {457, 131, 0}, {241, 52, 1}, {326, 300, 3}, {163, 334, 0}, {86, 284, 3}, {407, 218, 0}, {121, 398, 24}, {158, 273, 24}, {213, 81, 1}, {76, 431, 2}, {76, 220, 0}, {164, 252, 24}, {342, 426, 3}, {342, 307, 2}, {446, 426, 0}, {411, 428, 0}, {379, 436, 0}, {488, 116, 515}, {278, 201, 0}, {157, 355, 3}, {199, 377, 0}, {30, 37, 24}, {15, 325, 2563}, {295, 498, 0}, {332, 477, 2}, {493, 256, 1}, {203, 121, 0}, {158, 272, 0}, {93, 150, 2}, {479, 229, 4}, {287, 9, 3}, {151, 297, 2563}, {50, 491, 4}, {3, 308, 2}, {304, 461, 3}, {202, 470, 1}, {234, 397, 3}, {334, 115, 3}, {15, 47, 3}, {91, 311, 0}, {92, 420, 3}, {460, 120, 0}, {14, 232, 24}, {44, 164, 0}, {309, 315, 0}, {121, 503, 2}, {505, 430, 3}, {324, 345, 24}, {135, 320, 0}, {109, 444, 3}, {204, 202, 3}, {267, 278, 0}, {401, 125, 2}, {507, 369, 0}, {214, 120, 3}, {297, 274, 0}, {499, 504, 4}, {504, 373, 3}, {286, 364, 24}, {0, 229, 2}, {149, 429, 24}, {232, 272, 2}, {209, 207, 0}, {382, 458, 0}, {429, 395, 24}, {506, 500, 24}, {41, 188, 1}, {472, 477, 0}, {9, 95, 0}, {501, 483, 0}, {168, 336, 2}, {270, 480, 1794}, {369, 365, 2}, {253, 318, 24}, {486, 53, 3}, {151, 372, 3}, {364, 75, 2}, {232, 293, 0}, {48, 186, 1}, {134, 177, 3}, {220, 208, 4}, {329, 462, 2}, {303, 384, 2}, {145, 426, 24}, {32, 225, 3}, {468, 505, 4}, {152, 20, 2051}, {501, 344, 3}, {37, 314, 3}, {421, 73, 0}, {321, 218, 3}, {251, 335, 2}, {417, 366, 3}, {58, 447, 3}, {139, 41, 0}, {494, 160, 2}, {30, 471, 2}, {363, 172, 0}, {24, 149, 2}, {489, 84, 0}, {219, 76, 0}, {130, 469, 2}, {187, 10, 0}, {372, 258, 0}, {431, 64, 0}, {441, 496, 24}, {381, 408, 4}, {23, 329, 0}, {507, 6, 3}, {237, 511, 0}, {1, 148, 3}, {93, 8, 4}, {395, 482, 1}, {509, 426, 2}, {283, 273, 2}, {364, 424, 1}, {210, 493, 2}, {380, 316, 1}, {161, 401, 2}, {88, 264, 2}, {340, 437, 0}, {185, 416, 0}, {121, 77, 2307}, {426, 276, 24}, {49, 10, 1}, {216, 327, 24}, {212, 394, 0}, {306, 399, 3}, {493, 133, 4}, {442, 485, 24}, {48, 486, 3}, {286, 34, 1}, {313, 220, 4}, {206, 386, 0}, {289, 472, 2}, {411, 87, 2}, {308, 103, 2}, {400, 111, 3}, {65, 57, 2}, {470, 480, 0}, {40, 473, 24}, {344, 155, 2}, {443, 263, 0}, {510, 62, 4}, {466, 161, 2}, {146, 433, 3}, {465, 351, 0}, {49, 428, 2}, {140, 40, 1}, {462, 159, 1}, {100, 29, 2}, {423, 269, 0}, {113, 429, 3}, {62, 52, 1}, {316, 386, 1539}, {66, 391, 3}, {346, 371, 3}, {63, 442, 24}, {247, 467, 2}, {215, 121, 3}, {511, 387, 4}, {46, 446, 2}, {459, 230, 0}, {478, 379, 24}, {497, 356, 3074}, {388, 128, 1}, {323, 352, 0}, {247, 64, 3}, {316, 242, 2}, {130, 251, 3}, {226, 370, 4}, {476, 332, 2}, {358, 151, 2}, {262, 218, 3}, {303, 214, 1}, {139, 201, 3}, {87, 285, 2}, {502, 306, 3}, {281, 7, 4}, {332, 501, 24}, {56, 152, 24}, {475, 386, 2306}, {234, 425, 2}, {279, 21, 2}, {456, 231, 0}, {122, 3, 1}, {503, 131, 2051}, {52, 307, 3}, {198, 196, 24}, {302, 38, 0}, {284, 126, 3}, {418, 386, 2}, {39, 109, 24}, {400, 438, 0}, {456, 438, 2561}, {412, 430, 1}, {399, 143, 2}, {132, 289, 0}, {456, 449, 0}, {243, 292, 3}, {309, 338, 0}, {61, 282, 3}, {341, 292, 0}, {366, 225, 2}, {186, 27, 3074}, {233, 482, 3}, {250, 287, 3}, {326, 45, 1}, {220, 251, 0}, {213, 113, 0}, {482, 220, 3}, {261, 116, 3}, {214, 141, 0}, {146, 477, 2}, {198, 118, 0}, {397, 54, 1}, {338, 130, 3}, {368, 374, 24}, {67, 67, 1}, {466, 274, 1}, {338, 34, 2}, {240, 71, 1}, {203, 334, 3}, {254, 465, 24}, {35, 128, 0}, {70, 246, 0}, {107, 284, 0}, {20, 370, 1}, {421, 97, 2}, {469, 331, 2}, {455, 96, 0}, {2, 335, 1}, {88, 360, 1}, {468, 255, 1}, {332, 154, 0}, {499, 166, 0}, {164, 365, 1},};
	public int[][] biomes = {{171, 497, 5}, {69, 130, 1}, {412, 107, 3}, {35, 168, 27}, {233, 337, 0}, {510, 409, 32}, {92, 263, 5}, {223, 262, 0}, {201, 226, 24}, {54, 44, 29}, {221, 439, 3}, {234, 47, 2}, {392, 313, 29}, {506, 263, 5}, {53, 351, 0}, {269, 216, 4}, {71, 494, 6}, {44, 291, 4}, {451, 285, 24}, {457, 131, 24}, {241, 52, 0}, {326, 300, 32}, {163, 334, 4}, {86, 284, 4}, {407, 218, 0}, {121, 398, 4}, {158, 273, 24}, {213, 81, 35}, {76, 431, 24}, {76, 220, 0}, {164, 252, 2}, {342, 426, 0}, {342, 307, 3}, {446, 426, 0}, {411, 428, 30}, {379, 436, 5}, {488, 116, 1}, {278, 201, 0}, {157, 355, 35}, {199, 377, 24}, {30, 37, 4}, {15, 325, 12}, {295, 498, 0}, {332, 477, 30}, {493, 256, 4}, {203, 121, 6}, {158, 272, 24}, {93, 150, 4}, {479, 229, 2}, {287, 9, 0}, {151, 297, 4}, {50, 491, 29}, {3, 308, 12}, {304, 461, 5}, {202, 470, 29}, {234, 397, 4}, {334, 115, 4}, {15, 47, 12}, {91, 311, 12}, {92, 420, 1}, {460, 120, 24}, {14, 232, 27}, {44, 164, 5}, {309, 315, 0}, {121, 503, 0}, {505, 430, 1}, {324, 345, 1}, {135, 320, 0}, {109, 444, 24}, {204, 202, 29}, {267, 278, 35}, {401, 125, 27}, {507, 369, 30}, {214, 120, 4}, {297, 274, 4}, {499, 504, 4}, {504, 373, 12}, {286, 364, 1}, {0, 229, 3}, {149, 429, 24}, {232, 272, 5}, {209, 207, 2}, {382, 458, 0}, {429, 395, 24}, {506, 500, 1}, {41, 188, 21}, {472, 477, 30}, {9, 95, 2}, {501, 483, 5}, {168, 336, 1}, {270, 480, 29}, {369, 365, 0}, {253, 318, 3}, {486, 53, 3}, {151, 372, 35}, {364, 75, 0}, {232, 293, 6}, {48, 186, 21}, {134, 177, 4}, {220, 208, 0}, {329, 462, 0}, {303, 384, 0}, {145, 426, 24}, {32, 225, 0}, {468, 505, 29}, {152, 20, 35}, {501, 344, 24}, {37, 314, 30}, {421, 73, 12}, {321, 218, 27}, {251, 335, 1}, {417, 366, 2}, {58, 447, 0}, {139, 41, 12}, {494, 160, 0}, {30, 471, 24}, {363, 172, 0}, {24, 149, 1}, {489, 84, 1}, {219, 76, 0}, {130, 469, 0}, {187, 10, 0}, {372, 258, 2}, {431, 64, 12}, {441, 496, 0}, {381, 408, 4}, {23, 329, 12}, {507, 6, 24}, {237, 511, 12}, {1, 148, 5}, {93, 8, 27}, {395, 482, 0}, {509, 426, 5}, {283, 273, 2}, {364, 424, 12}, {210, 493, 1}, {380, 316, 0}, {161, 401, 3}, {88, 264, 5}, {340, 437, 4}, {185, 416, 24}, {121, 77, 3}, {426, 276, 0}, {49, 10, 1}, {216, 327, 24}, {212, 394, 1}, {306, 399, 24}, {493, 133, 24}, {442, 485, 1}, {48, 486, 1}, {286, 34, 0}, {313, 220, 0}, {206, 386, 0}, {289, 472, 3}, {411, 87, 12}, {308, 103, 35}, {400, 111, 4}, {65, 57, 12}, {470, 480, 3}, {40, 473, 2}, {344, 155, 3}, {443, 263, 35}, {510, 62, 32}, {466, 161, 2}, {146, 433, 24}, {465, 351, 35}, {49, 428, 3}, {140, 40, 12}, {462, 159, 2}, {100, 29, 29}, {423, 269, 0}, {113, 429, 0}, {62, 52, 4}, {316, 386, 5}, {66, 391, 12}, {346, 371, 4}, {63, 442, 0}, {247, 467, 2}, {215, 121, 4}, {511, 387, 12}, {46, 446, 0}, {459, 230, 0}, {478, 379, 1}, {497, 356, 35}, {388, 128, 27}, {323, 352, 1}, {247, 64, 12}, {316, 242, 27}, {130, 251, 4}, {226, 370, 27}, {476, 332, 24}, {358, 151, 27}, {262, 218, 4}, {303, 214, 1}, {139, 201, 3}, {87, 285, 4}, {502, 306, 3}, {281, 7, 3}, {332, 501, 0}, {56, 152, 27}, {475, 386, 0}, {234, 425, 29}, {279, 21, 4}, {456, 231, 35}, {122, 3, 0}, {503, 131, 24}, {52, 307, 12}, {198, 196, 0}, {302, 38, 5}, {284, 126, 21}, {418, 386, 0}, {39, 109, 29}, {400, 438, 4}, {456, 438, 35}, {412, 430, 12}, {399, 143, 1}, {132, 289, 6}, {456, 449, 3}, {243, 292, 1}, {309, 338, 0}, {61, 282, 1}, {341, 292, 4}, {366, 225, 0}, {186, 27, 35}, {233, 482, 35}, {250, 287, 6}, {326, 45, 0}, {220, 251, 0}, {213, 113, 3}, {482, 220, 35}, {261, 116, 1}, {214, 141, 5}, {146, 477, 0}, {198, 118, 5}, {397, 54, 0}, {338, 130, 3}, {368, 374, 5}, {67, 67, 12}, {466, 274, 4}, {338, 34, 12}, {240, 71, 12}, {203, 334, 35}, {254, 465, 0}, {35, 128, 1}, {70, 246, 29}, {107, 284, 0}, {20, 370, 24}, {421, 97, 0}, {469, 331, 2}, {455, 96, 24}, {2, 335, 12}, {88, 360, 35}, {468, 255, 3}, {332, 154, 0}, {499, 166, 24}, {164, 365, 35},};
	public int[][] noise = {{171, 497, 137120}, {69, 130, 157746}, {412, 107, 200323}, {35, 168, 200600}, {233, 337, 0}, {510, 409, 239183}, {92, 263, 82082}, {223, 262, 0}, {201, 226, 253415}, {54, 44, 11919}, {221, 439, 264318}, {234, 47, 162372}, {392, 313, 125721}, {506, 263, 200324}, {53, 351, 0}, {269, 216, 31186}, {71, 494, 198751}, {44, 291, 118351}, {451, 285, 33966}, {457, 131, 294502}, {241, 52, 0}, {326, 300, 175444}, {163, 334, 193695}, {86, 284, 124614}, {407, 218, 0}, {121, 398, 149543}, {158, 273, 132493}, {213, 81, 0}, {76, 431, 265814}, {76, 220, 0}, {164, 252, 157762}, {342, 426, 0}, {342, 307, 185716}, {446, 426, 0}, {411, 428, 71435}, {379, 436, 40518}, {488, 116, 165167}, {278, 201, 0}, {157, 355, 103032}, {199, 377, 96813}, {30, 37, 7466}, {15, 325, 214420}, {295, 498, 0}, {332, 477, 232739}, {493, 256, 159558}, {203, 121, 64165}, {158, 272, 14864}, {93, 150, 61474}, {479, 229, 205432}, {287, 9, 0}, {151, 297, 267812}, {50, 491, 282957}, {3, 308, 75520}, {304, 461, 250849}, {202, 470, 182408}, {234, 397, 268540}, {334, 115, 191240}, {15, 47, 121164}, {91, 311, 87231}, {92, 420, 163524}, {460, 120, 71452}, {14, 232, 25368}, {44, 164, 167430}, {309, 315, 0}, {121, 503, 0}, {505, 430, 98722}, {324, 345, 5856}, {135, 320, 0}, {109, 444, 239686}, {204, 202, 279135}, {267, 278, 203787}, {401, 125, 244256}, {507, 369, 70999}, {214, 120, 63297}, {297, 274, 279011}, {499, 504, 108315}, {504, 373, 101731}, {286, 364, 250577}, {0, 229, 251141}, {149, 429, 59480}, {232, 272, 163286}, {209, 207, 45920}, {382, 458, 0}, {429, 395, 243756}, {506, 500, 180429}, {41, 188, 288471}, {472, 477, 282358}, {9, 95, 25376}, {501, 483, 144115}, {168, 336, 75058}, {270, 480, 150015}, {369, 365, 0}, {253, 318, 235482}, {486, 53, 173820}, {151, 372, 190713}, {364, 75, 0}, {232, 293, 285380}, {48, 186, 54572}, {134, 177, 63153}, {220, 208, 0}, {329, 462, 0}, {303, 384, 0}, {145, 426, 113545}, {32, 225, 0}, {468, 505, 219003}, {152, 20, 7109}, {501, 344, 218120}, {37, 314, 152512}, {421, 73, 287519}, {321, 218, 229210}, {251, 335, 111244}, {417, 366, 140222}, {58, 447, 0}, {139, 41, 94317}, {494, 160, 0}, {30, 471, 124473}, {363, 172, 0}, {24, 149, 247118}, {489, 84, 291939}, {219, 76, 0}, {130, 469, 0}, {187, 10, 0}, {372, 258, 63934}, {431, 64, 93764}, {441, 496, 0}, {381, 408, 28915}, {23, 329, 107102}, {507, 6, 235556}, {237, 511, 137661}, {1, 148, 236055}, {93, 8, 270059}, {395, 482, 0}, {509, 426, 197787}, {283, 273, 0}, {364, 424, 211284}, {210, 493, 90815}, {380, 316, 0}, {161, 401, 117893}, {88, 264, 298933}, {340, 437, 122087}, {185, 416, 247836}, {121, 77, 290879}, {426, 276, 0}, {49, 10, 161669}, {216, 327, 100487}, {212, 394, 2965}, {306, 399, 271925}, {493, 133, 130494}, {442, 485, 181909}, {48, 486, 1262}, {286, 34, 0}, {313, 220, 0}, {206, 386, 0}, {289, 472, 4256}, {411, 87, 13724}, {308, 103, 48785}, {400, 111, 23058}, {65, 57, 72290}, {470, 480, 17215}, {40, 473, 60097}, {344, 155, 14634}, {443, 263, 173208}, {510, 62, 297916}, {466, 161, 52817}, {146, 433, 244286}, {465, 351, 0}, {49, 428, 149860}, {140, 40, 94317}, {462, 159, 42728}, {100, 29, 139896}, {423, 269, 0}, {113, 429, 0}, {62, 52, 119606}, {316, 386, 156267}, {66, 391, 29249}, {346, 371, 272412}, {63, 442, 0}, {247, 467, 70439}, {215, 121, 0}, {511, 387, 222033}, {46, 446, 0}, {459, 230, 0}, {478, 379, 269557}, {497, 356, 224120}, {388, 128, 191726}, {323, 352, 278896}, {247, 64, 229003}, {316, 242, 243083}, {130, 251, 165428}, {226, 370, 93438}, {476, 332, 156914}, {358, 151, 184310}, {262, 218, 278573}, {303, 214, 192809}, {139, 201, 60277}, {87, 285, 18905}, {502, 306, 73829}, {281, 7, 0}, {332, 501, 0}, {56, 152, 135239}, {475, 386, 0}, {234, 425, 182774}, {279, 21, 164580}, {456, 231, 289917}, {122, 3, 0}, {503, 131, 158327}, {52, 307, 245623}, {198, 196, 0}, {302, 38, 276282}, {284, 126, 129577}, {418, 386, 0}, {39, 109, 218824}, {400, 438, 13246}, {456, 438, 226773}, {412, 430, 104735}, {399, 143, 113007}, {132, 289, 80588}, {456, 449, 99419}, {243, 292, 215619}, {309, 338, 0}, {61, 282, 23254}, {341, 292, 262631}, {366, 225, 0}, {186, 27, 294457}, {233, 482, 165525}, {250, 287, 134899}, {326, 45, 0}, {220, 251, 0}, {213, 113, 184228}, {482, 220, 176910}, {261, 116, 9101}, {214, 141, 40650}, {146, 477, 0}, {198, 118, 0}, {397, 54, 0}, {338, 130, 110526}, {368, 374, 137283}, {67, 67, 230490}, {466, 274, 191339}, {338, 34, 100472}, {240, 71, 156511}, {203, 334, 34907}, {254, 465, 0}, {35, 128, 22653}, {70, 246, 212390}, {107, 284, 0}, {20, 370, 40137}, {421, 97, 0}, {469, 331, 270100}, {455, 96, 124192}, {2, 335, 81732}, {88, 360, 94689}, {468, 255, 50733}, {332, 154, 0}, {499, 166, 244040}, {164, 365, 230456},};
	public int[][] variants = {{171, 497, 12}, {69, 130, 12}, {412, 107, 29}, {35, 168, 12}, {233, 337, 32}, {510, 409, 4}, {92, 263, 12}, {223, 262, 33}, {201, 226, 32}, {54, 44, 26}, {221, 439, 33}, {234, 47, 0}, {392, 313, 32}, {506, 263, 6}, {53, 351, 13}, {269, 216, 33}, {71, 494, 12}, {44, 291, 13}, {451, 285, 33}, {457, 131, 27}, {241, 52, 0}, {326, 300, 161}, {163, 334, 5}, {86, 284, 12}, {407, 218, 32}, {121, 398, 12}, {158, 273, 32}, {213, 81, 24}, {76, 431, 30}, {76, 220, 12}, {164, 252, 32}, {342, 426, 32}, {342, 307, 32}, {446, 426, 1}, {411, 428, 5}, {379, 436, 32}, {488, 116, 3}, {278, 201, 33}, {157, 355, 19}, {199, 377, 32}, {30, 37, 0}, {15, 325, 13}, {295, 498, 1}, {332, 477, 1}, {493, 256, 6}, {203, 121, 0}, {158, 272, 32}, {93, 150, 12}, {479, 229, 6}, {287, 9, 18}, {151, 297, 5}, {50, 491, 31}, {3, 308, 13}, {304, 461, 33}, {202, 470, 30}, {234, 397, 32}, {334, 115, 29}, {15, 47, 0}, {91, 311, 12}, {92, 420, 13}, {460, 120, 27}, {14, 232, 12}, {44, 164, 12}, {309, 315, 33}, {121, 503, 12}, {505, 430, 5}, {324, 345, 33}, {135, 320, 13}, {109, 444, 30}, {204, 202, 32}, {267, 278, 32}, {401, 125, 29}, {507, 369, 1}, {214, 120, 24}, {297, 274, 161}, {499, 504, 5}, {504, 373, 1}, {286, 364, 32}, {0, 229, 13}, {149, 429, 12}, {232, 272, 32}, {209, 207, 32}, {382, 458, 32}, {429, 395, 32}, {506, 500, 1}, {41, 188, 13}, {472, 477, 19}, {9, 95, 12}, {501, 483, 5}, {168, 336, 5}, {270, 480, 30}, {369, 365, 32}, {253, 318, 32}, {486, 53, 4}, {151, 372, 12}, {364, 75, 4}, {232, 293, 32}, {48, 186, 12}, {134, 177, 33}, {220, 208, 33}, {329, 462, 1}, {303, 384, 32}, {145, 426, 12}, {32, 225, 12}, {468, 505, 5}, {152, 20, 24}, {501, 344, 4}, {37, 314, 12}, {421, 73, 4}, {321, 218, 161}, {251, 335, 32}, {417, 366, 32}, {58, 447, 30}, {139, 41, 24}, {494, 160, 27}, {30, 471, 30}, {363, 172, 6}, {24, 149, 13}, {489, 84, 3}, {219, 76, 24}, {130, 469, 12}, {187, 10, 24}, {372, 258, 32}, {431, 64, 18}, {441, 496, 5}, {381, 408, 32}, {23, 329, 13}, {507, 6, 24}, {237, 511, 30}, {1, 148, 12}, {93, 8, 0}, {395, 482, 1}, {509, 426, 5}, {283, 273, 32}, {364, 424, 32}, {210, 493, 30}, {380, 316, 32}, {161, 401, 12}, {88, 264, 12}, {340, 437, 32}, {185, 416, 32}, {121, 77, 0}, {426, 276, 32}, {49, 10, 0}, {216, 327, 33}, {212, 394, 33}, {306, 399, 32}, {493, 133, 3}, {442, 485, 5}, {48, 486, 31}, {286, 34, 28}, {313, 220, 161}, {206, 386, 32}, {289, 472, 33}, {411, 87, 18}, {308, 103, 32}, {400, 111, 29}, {65, 57, 0}, {470, 480, 19}, {40, 473, 31}, {344, 155, 5}, {443, 263, 32}, {510, 62, 3}, {466, 161, 27}, {146, 433, 13}, {465, 351, 1}, {49, 428, 30}, {140, 40, 24}, {462, 159, 27}, {100, 29, 0}, {423, 269, 33}, {113, 429, 13}, {62, 52, 0}, {316, 386, 32}, {66, 391, 31}, {346, 371, 32}, {63, 442, 30}, {247, 467, 30}, {215, 121, 24}, {511, 387, 1}, {46, 446, 30}, {459, 230, 5}, {478, 379, 1}, {497, 356, 1}, {388, 128, 29}, {323, 352, 32}, {247, 64, 24}, {316, 242, 161}, {130, 251, 5}, {226, 370, 32}, {476, 332, 1}, {358, 151, 1}, {262, 218, 32}, {303, 214, 33}, {139, 201, 33}, {87, 285, 12}, {502, 306, 5}, {281, 7, 4}, {332, 501, 1}, {56, 152, 12}, {475, 386, 1}, {234, 425, 32}, {279, 21, 18}, {456, 231, 5}, {122, 3, 0}, {503, 131, 3}, {52, 307, 12}, {198, 196, 32}, {302, 38, 28}, {284, 126, 32}, {418, 386, 32}, {39, 109, 12}, {400, 438, 5}, {456, 438, 1}, {412, 430, 5}, {399, 143, 29}, {132, 289, 12}, {456, 449, 1}, {243, 292, 32}, {309, 338, 33}, {61, 282, 12}, {341, 292, 161}, {366, 225, 32}, {186, 27, 24}, {233, 482, 30}, {250, 287, 33}, {326, 45, 28}, {220, 251, 33}, {213, 113, 0}, {482, 220, 6}, {261, 116, 32}, {214, 141, 16}, {146, 477, 13}, {198, 118, 24}, {397, 54, 4}, {338, 130, 5}, {368, 374, 32}, {67, 67, 24}, {466, 274, 33}, {338, 34, 28}, {240, 71, 24}, {203, 334, 32}, {254, 465, 30}, {35, 128, 12}, {70, 246, 12}, {107, 284, 12}, {20, 370, 12}, {421, 97, 4}, {469, 331, 19}, {455, 96, 3}, {2, 335, 12}, {88, 360, 12}, {468, 255, 5}, {332, 154, 33}, {499, 166, 27}, {164, 365, 19},};
	public int[][] river = {{171, 497, -1}, {69, 130, -1}, {412, 107, -1}, {35, 168, -1}, {233, 337, -1}, {510, 409, -1}, {92, 263, -1}, {223, 262, -1}, {201, 226, 7}, {54, 44, -1}, {221, 439, -1}, {234, 47, -1}, {392, 313, -1}, {506, 263, -1}, {53, 351, -1}, {269, 216, -1}, {71, 494, -1}, {44, 291, 7}, {451, 285, -1}, {457, 131, -1}, {241, 52, -1}, {326, 300, -1}, {163, 334, -1}, {86, 284, -1}, {407, 218, -1}, {121, 398, 7}, {158, 273, -1}, {213, 81, -1}, {76, 431, -1}, {76, 220, -1}, {164, 252, -1}, {342, 426, -1}, {342, 307, -1}, {446, 426, -1}, {411, 428, -1}, {379, 436, -1}, {488, 116, -1}, {278, 201, -1}, {157, 355, -1}, {199, 377, -1}, {30, 37, -1}, {15, 325, -1}, {295, 498, -1}, {332, 477, -1}, {493, 256, -1}, {203, 121, -1}, {158, 272, -1}, {93, 150, -1}, {479, 229, -1}, {287, 9, -1}, {151, 297, -1}, {50, 491, -1}, {3, 308, -1}, {304, 461, -1}, {202, 470, -1}, {234, 397, -1}, {334, 115, -1}, {15, 47, 7}, {91, 311, -1}, {92, 420, -1}, {460, 120, -1}, {14, 232, -1}, {44, 164, -1}, {309, 315, -1}, {121, 503, -1}, {505, 430, -1}, {324, 345, -1}, {135, 320, -1}, {109, 444, -1}, {204, 202, -1}, {267, 278, -1}, {401, 125, -1}, {507, 369, -1}, {214, 120, -1}, {297, 274, -1}, {499, 504, -1}, {504, 373, -1}, {286, 364, -1}, {0, 229, -1}, {149, 429, -1}, {232, 272, 7}, {209, 207, 7}, {382, 458, -1}, {429, 395, -1}, {506, 500, -1}, {41, 188, -1}, {472, 477, -1}, {9, 95, 7}, {501, 483, -1}, {168, 336, -1}, {270, 480, -1}, {369, 365, -1}, {253, 318, -1}, {486, 53, -1}, {151, 372, -1}, {364, 75, -1}, {232, 293, -1}, {48, 186, -1}, {134, 177, -1}, {220, 208, -1}, {329, 462, -1}, {303, 384, -1}, {145, 426, -1}, {32, 225, -1}, {468, 505, -1}, {152, 20, -1}, {501, 344, -1}, {37, 314, -1}, {421, 73, -1}, {321, 218, -1}, {251, 335, -1}, {417, 366, -1}, {58, 447, -1}, {139, 41, -1}, {494, 160, -1}, {30, 471, -1}, {363, 172, -1}, {24, 149, -1}, {489, 84, -1}, {219, 76, -1}, {130, 469, -1}, {187, 10, -1}, {372, 258, -1}, {431, 64, -1}, {441, 496, -1}, {381, 408, -1}, {23, 329, -1}, {507, 6, -1}, {237, 511, -1}, {1, 148, -1}, {93, 8, -1}, {395, 482, -1}, {509, 426, -1}, {283, 273, -1}, {364, 424, -1}, {210, 493, -1}, {380, 316, -1}, {161, 401, -1}, {88, 264, -1}, {340, 437, -1}, {185, 416, -1}, {121, 77, -1}, {426, 276, -1}, {49, 10, -1}, {216, 327, -1}, {212, 394, -1}, {306, 399, -1}, {493, 133, -1}, {442, 485, -1}, {48, 486, -1}, {286, 34, -1}, {313, 220, -1}, {206, 386, -1}, {289, 472, -1}, {411, 87, -1}, {308, 103, -1}, {400, 111, -1}, {65, 57, -1}, {470, 480, -1}, {40, 473, -1}, {344, 155, -1}, {443, 263, -1}, {510, 62, -1}, {466, 161, -1}, {146, 433, -1}, {465, 351, 7}, {49, 428, -1}, {140, 40, -1}, {462, 159, -1}, {100, 29, -1}, {423, 269, -1}, {113, 429, -1}, {62, 52, -1}, {316, 386, -1}, {66, 391, -1}, {346, 371, -1}, {63, 442, -1}, {247, 467, -1}, {215, 121, -1}, {511, 387, -1}, {46, 446, -1}, {459, 230, -1}, {478, 379, -1}, {497, 356, -1}, {388, 128, -1}, {323, 352, -1}, {247, 64, -1}, {316, 242, -1}, {130, 251, -1}, {226, 370, -1}, {476, 332, 7}, {358, 151, -1}, {262, 218, -1}, {303, 214, -1}, {139, 201, -1}, {87, 285, -1}, {502, 306, -1}, {281, 7, -1}, {332, 501, -1}, {56, 152, -1}, {475, 386, -1}, {234, 425, -1}, {279, 21, -1}, {456, 231, -1}, {122, 3, -1}, {503, 131, -1}, {52, 307, -1}, {198, 196, -1}, {302, 38, -1}, {284, 126, -1}, {418, 386, -1}, {39, 109, -1}, {400, 438, -1}, {456, 438, -1}, {412, 430, -1}, {399, 143, -1}, {132, 289, -1}, {456, 449, -1}, {243, 292, -1}, {309, 338, -1}, {61, 282, -1}, {341, 292, -1}, {366, 225, -1}, {186, 27, -1}, {233, 482, -1}, {250, 287, -1}, {326, 45, -1}, {220, 251, -1}, {213, 113, -1}, {482, 220, -1}, {261, 116, -1}, {214, 141, -1}, {146, 477, -1}, {198, 118, -1}, {397, 54, -1}, {338, 130, -1}, {368, 374, -1}, {67, 67, -1}, {466, 274, 7}, {338, 34, -1}, {240, 71, -1}, {203, 334, -1}, {254, 465, -1}, {35, 128, -1}, {70, 246, -1}, {107, 284, -1}, {20, 370, -1}, {421, 97, -1}, {469, 331, -1}, {455, 96, -1}, {2, 335, -1}, {88, 360, -1}, {468, 255, -1}, {332, 154, -1}, {499, 166, -1}, {164, 365, -1},};
	public int[][] full = {{171, 497, 12}, {69, 130, 12}, {412, 107, 29}, {35, 168, 12}, {233, 337, 32}, {510, 409, 4}, {92, 263, 12}, {223, 262, 33}, {201, 226, 7}, {54, 44, 26}, {221, 439, 33}, {234, 47, 46}, {392, 313, 32}, {506, 263, 6}, {53, 351, 13}, {269, 216, 33}, {71, 494, 12}, {44, 291, 7}, {451, 285, 33}, {457, 131, 27}, {241, 52, 46}, {326, 300, 161}, {163, 334, 5}, {86, 284, 12}, {407, 218, 32}, {121, 398, 11}, {158, 273, 32}, {213, 81, 49}, {76, 431, 30}, {76, 220, 12}, {164, 252, 32}, {342, 426, 32}, {342, 307, 32}, {446, 426, 1}, {411, 428, 5}, {379, 436, 32}, {488, 116, 3}, {278, 201, 33}, {157, 355, 19}, {199, 377, 32}, {30, 37, 46}, {15, 325, 13}, {295, 498, 1}, {332, 477, 1}, {493, 256, 6}, {203, 121, 46}, {158, 272, 32}, {93, 150, 12}, {479, 229, 6}, {287, 9, 18}, {151, 297, 5}, {50, 491, 31}, {3, 308, 13}, {304, 461, 33}, {202, 470, 30}, {234, 397, 32}, {334, 115, 29}, {15, 47, 0}, {91, 311, 12}, {92, 420, 13}, {460, 120, 27}, {14, 232, 12}, {44, 164, 12}, {309, 315, 33}, {121, 503, 12}, {505, 430, 5}, {324, 345, 33}, {135, 320, 13}, {109, 444, 30}, {204, 202, 32}, {267, 278, 32}, {401, 125, 29}, {507, 369, 1}, {214, 120, 49}, {297, 274, 161}, {499, 504, 5}, {504, 373, 1}, {286, 364, 32}, {0, 229, 13}, {149, 429, 12}, {232, 272, 7}, {209, 207, 7}, {382, 458, 32}, {429, 395, 32}, {506, 500, 1}, {41, 188, 13}, {472, 477, 19}, {9, 95, 11}, {501, 483, 5}, {168, 336, 5}, {270, 480, 30}, {369, 365, 32}, {253, 318, 32}, {486, 53, 4}, {151, 372, 12}, {364, 75, 4}, {232, 293, 32}, {48, 186, 12}, {134, 177, 33}, {220, 208, 33}, {329, 462, 1}, {303, 384, 32}, {145, 426, 12}, {32, 225, 12}, {468, 505, 5}, {152, 20, 49}, {501, 344, 4}, {37, 314, 12}, {421, 73, 4}, {321, 218, 161}, {251, 335, 32}, {417, 366, 32}, {58, 447, 30}, {139, 41, 49}, {494, 160, 27}, {30, 471, 30}, {363, 172, 6}, {24, 149, 13}, {489, 84, 3}, {219, 76, 49}, {130, 469, 12}, {187, 10, 49}, {372, 258, 32}, {431, 64, 18}, {441, 496, 5}, {381, 408, 32}, {23, 329, 13}, {507, 6, 24}, {237, 511, 30}, {1, 148, 12}, {93, 8, 46}, {395, 482, 1}, {509, 426, 5}, {283, 273, 32}, {364, 424, 32}, {210, 493, 30}, {380, 316, 32}, {161, 401, 12}, {88, 264, 12}, {340, 437, 32}, {185, 416, 32}, {121, 77, 0}, {426, 276, 32}, {49, 10, 46}, {216, 327, 33}, {212, 394, 33}, {306, 399, 32}, {493, 133, 3}, {442, 485, 5}, {48, 486, 31}, {286, 34, 28}, {313, 220, 161}, {206, 386, 32}, {289, 472, 33}, {411, 87, 18}, {308, 103, 32}, {400, 111, 29}, {65, 57, 0}, {470, 480, 19}, {40, 473, 31}, {344, 155, 5}, {443, 263, 32}, {510, 62, 3}, {466, 161, 27}, {146, 433, 13}, {465, 351, 7}, {49, 428, 30}, {140, 40, 49}, {462, 159, 27}, {100, 29, 46}, {423, 269, 33}, {113, 429, 13}, {62, 52, 0}, {316, 386, 32}, {66, 391, 31}, {346, 371, 32}, {63, 442, 30}, {247, 467, 30}, {215, 121, 49}, {511, 387, 1}, {46, 446, 30}, {459, 230, 5}, {478, 379, 1}, {497, 356, 1}, {388, 128, 29}, {323, 352, 32}, {247, 64, 49}, {316, 242, 161}, {130, 251, 5}, {226, 370, 32}, {476, 332, 7}, {358, 151, 1}, {262, 218, 32}, {303, 214, 33}, {139, 201, 33}, {87, 285, 12}, {502, 306, 5}, {281, 7, 4}, {332, 501, 1}, {56, 152, 12}, {475, 386, 1}, {234, 425, 32}, {279, 21, 18}, {456, 231, 5}, {122, 3, 46}, {503, 131, 3}, {52, 307, 12}, {198, 196, 32}, {302, 38, 28}, {284, 126, 32}, {418, 386, 32}, {39, 109, 12}, {400, 438, 5}, {456, 438, 1}, {412, 430, 5}, {399, 143, 29}, {132, 289, 12}, {456, 449, 1}, {243, 292, 32}, {309, 338, 33}, {61, 282, 12}, {341, 292, 161}, {366, 225, 32}, {186, 27, 49}, {233, 482, 30}, {250, 287, 33}, {326, 45, 28}, {220, 251, 33}, {213, 113, 46}, {482, 220, 6}, {261, 116, 32}, {214, 141, 16}, {146, 477, 13}, {198, 118, 49}, {397, 54, 4}, {338, 130, 5}, {368, 374, 32}, {67, 67, 24}, {466, 274, 7}, {338, 34, 28}, {240, 71, 49}, {203, 334, 32}, {254, 465, 30}, {35, 128, 12}, {70, 246, 12}, {107, 284, 12}, {20, 370, 12}, {421, 97, 4}, {469, 331, 19}, {455, 96, 3}, {2, 335, 12}, {88, 360, 12}, {468, 255, 5}, {332, 154, 33}, {499, 166, 27}, {164, 365, 19},};
	public int[][] voronoi = {{171, 497, 12}, {69, 130, 46}, {412, 107, 46}, {35, 168, 26}, {233, 337, 24}, {510, 409, 16}, {92, 263, 0}, {223, 262, 0}, {201, 226, 24}, {54, 44, 49}, {221, 439, 0}, {234, 47, 26}, {392, 313, 24}, {506, 263, 49}, {53, 351, 11}, {269, 216, 0}, {71, 494, 13}, {44, 291, 0}, {451, 285, 46}, {457, 131, 49}, {241, 52, 26}, {326, 300, 0}, {163, 334, 0}, {86, 284, 0}, {407, 218, 46}, {121, 398, 12}, {158, 273, 24}, {213, 81, 26}, {76, 431, 13}, {76, 220, 0}, {164, 252, 24}, {342, 426, 0}, {342, 307, 24}, {446, 426, 5}, {411, 428, 0}, {379, 436, 26}, {488, 116, 49}, {278, 201, 0}, {157, 355, 0}, {199, 377, 0}, {30, 37, 46}, {15, 325, 11}, {295, 498, 12}, {332, 477, 12}, {493, 256, 49}, {203, 121, 46}, {158, 272, 24}, {93, 150, 46}, {479, 229, 46}, {287, 9, 12}, {151, 297, 12}, {50, 491, 13}, {3, 308, 13}, {304, 461, 12}, {202, 470, 12}, {234, 397, 24}, {334, 115, 46}, {15, 47, 0}, {91, 311, 26}, {92, 420, 12}, {460, 120, 49}, {14, 232, 7}, {44, 164, 0}, {309, 315, 24}, {121, 503, 12}, {505, 430, 5}, {324, 345, 24}, {135, 320, 12}, {109, 444, 11}, {204, 202, 24}, {267, 278, 24}, {401, 125, 46}, {507, 369, 0}, {214, 120, 46}, {297, 274, 24}, {499, 504, 5}, {504, 373, 0}, {286, 364, 24}, {0, 229, 13}, {149, 429, 12}, {232, 272, 0}, {209, 207, 24}, {382, 458, 26}, {429, 395, 0}, {506, 500, 5}, {41, 188, 11}, {472, 477, 5}, {9, 95, 0}, {501, 483, 5}, {168, 336, 0}, {270, 480, 12}, {369, 365, 0}, {253, 318, 24}, {486, 53, 49}, {151, 372, 26}, {364, 75, 46}, {232, 293, 0}, {48, 186, 7}, {134, 177, 46}, {220, 208, 24}, {329, 462, 12}, {303, 384, 0}, {145, 426, 12}, {32, 225, 0}, {468, 505, 12}, {152, 20, 46}, {501, 344, 0}, {37, 314, 26}, {421, 73, 49}, {321, 218, 0}, {251, 335, 24}, {417, 366, 0}, {58, 447, 13}, {139, 41, 46}, {494, 160, 46}, {30, 471, 12}, {363, 172, 0}, {24, 149, 26}, {489, 84, 49}, {219, 76, 0}, {130, 469, 12}, {187, 10, 46}, {372, 258, 0}, {431, 64, 49}, {441, 496, 0}, {381, 408, 26}, {23, 329, 11}, {507, 6, 46}, {237, 511, 12}, {1, 148, 0}, {93, 8, 46}, {395, 482, 7}, {509, 426, 5}, {283, 273, 24}, {364, 424, 26}, {210, 493, 12}, {380, 316, 24}, {161, 401, 26}, {88, 264, 0}, {340, 437, 7}, {185, 416, 26}, {121, 77, 46}, {426, 276, 0}, {49, 10, 46}, {216, 327, 0}, {212, 394, 0}, {306, 399, 0}, {493, 133, 49}, {442, 485, 0}, {48, 486, 13}, {286, 34, 12}, {313, 220, 0}, {206, 386, 0}, {289, 472, 12}, {411, 87, 46}, {308, 103, 12}, {400, 111, 46}, {65, 57, 46}, {470, 480, 16}, {40, 473, 13}, {344, 155, 0}, {443, 263, 46}, {510, 62, 49}, {466, 161, 46}, {146, 433, 12}, {465, 351, 0}, {49, 428, 13}, {140, 40, 46}, {462, 159, 46}, {100, 29, 46}, {423, 269, 0}, {113, 429, 12}, {62, 52, 46}, {316, 386, 0}, {66, 391, 11}, {346, 371, 0}, {63, 442, 13}, {247, 467, 12}, {215, 121, 46}, {511, 387, 16}, {46, 446, 13}, {459, 230, 49}, {478, 379, 16}, {497, 356, 24}, {388, 128, 46}, {323, 352, 0}, {247, 64, 13}, {316, 242, 24}, {130, 251, 0}, {226, 370, 24}, {476, 332, 0}, {358, 151, 46}, {262, 218, 0}, {303, 214, 0}, {139, 201, 46}, {87, 285, 0}, {502, 306, 46}, {281, 7, 12}, {332, 501, 26}, {56, 152, 0}, {475, 386, 0}, {234, 425, 0}, {279, 21, 12}, {456, 231, 49}, {122, 3, 46}, {503, 131, 49}, {52, 307, 0}, {198, 196, 24}, {302, 38, 12}, {284, 126, 12}, {418, 386, 0}, {39, 109, 0}, {400, 438, 0}, {456, 438, 5}, {412, 430, 0}, {399, 143, 46}, {132, 289, 7}, {456, 449, 5}, {243, 292, 0}, {309, 338, 24}, {61, 282, 26}, {341, 292, 0}, {366, 225, 0}, {186, 27, 46}, {233, 482, 12}, {250, 287, 24}, {326, 45, 12}, {220, 251, 0}, {213, 113, 46}, {482, 220, 46}, {261, 116, 12}, {214, 141, 0}, {146, 477, 12}, {198, 118, 46}, {397, 54, 46}, {338, 130, 26}, {368, 374, 0}, {67, 67, 46}, {466, 274, 49}, {338, 34, 26}, {240, 71, 13}, {203, 334, 0}, {254, 465, 13}, {35, 128, 0}, {70, 246, 26}, {107, 284, 0}, {20, 370, 12}, {421, 97, 46}, {469, 331, 0}, {455, 96, 49}, {2, 335, 13}, {88, 360, 12}, {468, 255, 49}, {332, 154, 0}, {499, 166, 46}, {164, 365, 0},};
	private OverworldBiomeSource overworldBiomeSource;
	private int size;

	@BeforeEach
	public void setup() {
		this.overworldBiomeSource = new OverworldBiomeSource(MCVersion.v1_13, 106816942252449L);
		this.size = 16;
	}

	@Test
	@DisplayName("Test Biomes against data for " + version)
	public void testBiomes() {
		randomBiomeGen(size, overworldBiomeSource.biomes, biomes);
	}

	@Test
	@DisplayName("Test Noise against data for " + version)
	public void testNoise() {
		randomBiomeGen(size, overworldBiomeSource.noise, noise);
	}

	@Test
	@DisplayName("Test full mixing river with biomes against data for " + version)
	public void testFull() {
		randomBiomeGen(size, overworldBiomeSource.full, full);
	}

	@Test
	@DisplayName("Test voronoi against data for " + version)
	public void testVoronoi() {
		randomBiomeGen(size, overworldBiomeSource.voronoi, voronoi);
	}

	@Test
	@DisplayName("Test river against data for " + version)
	public void testRiver() {
		randomBiomeGen(size, overworldBiomeSource.river, river);
	}

	@Test
	@DisplayName("Test Variants against data for " + version)
	public void testVariants() {
		randomBiomeGen(size, overworldBiomeSource.variants, variants);
	}

	@Test
	@DisplayName("Test First stack against data for " + version)
	public void testBase() {
		randomBiomeGen(size, overworldBiomeSource.base, base);
	}
}


/*
#### OverworldBiomeProvider
public OverworldBiomeProvider(long seed) {
   OverworldGenSettings overworldgensettings = new OverworldGenSettings();
   GenLayer[] agenlayer = LayerUtil.buildOverworldProcedure(seed, WorldType.DEFAULT, overworldgensettings);
   this.genBiomes = agenlayer[0];
   this.biomeFactoryLayer = agenlayer[1];
}

### GenLayer
public int[] generateBiomes_2(int startX, int startZ, int xSize, int zSize, @Nullable Biome defaultBiome) {
   AreaDimension areadimension = new AreaDimension(startX, startZ, xSize, zSize);
   LazyArea lazyarea = this.lazyAreaFactory.make(areadimension);
   int[] abiome = new int[xSize * zSize];
   for(int i = 0; i < zSize; ++i) {
      for(int j = 0; j < xSize; ++j) {
         abiome[j + i * xSize] =lazyarea.getValue(j, i);
      }
   }
   return abiome;
}

### Main
Bootstrap.register();
long seed=541515181818L;
OverworldBiomeProvider overworldBiomeProvider=new OverworldBiomeProvider(seed);
int size=16;
Random r= new Random(4227552225777L);
System.out.print("{");
for (int i = 0; i < size; i++) {
    for (int j = 0; j < size; j++) {
        int x = r.nextInt(512);
        int z = r.nextInt(512);
        System.out.printf("{%d, %d, %d},",x,z,overworldBiomeProvider.genBiomes.generateBiomes_2(x,z,1,1, Biomes.DEFAULT)[0]);
    }
}
System.out.print("};");
System.out.println();
 */