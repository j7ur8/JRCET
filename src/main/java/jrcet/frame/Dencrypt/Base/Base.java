package jrcet.frame.Dencrypt.Base;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

public class Base {

    public final static java.util.Base64.Encoder b64encoder = java.util.Base64.getEncoder();
    public final static java.util.Base64.Decoder b64decoder = java.util.Base64.getDecoder();

    public static final char[] Base16EncTab = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };

    // decoding characters table.
    public static final byte[] Base16DecTab = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, // 16
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, // 32
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, // 48
            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, // 64

            0x00, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, // 80
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, // 96
            0x00, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, // 112
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
    };

    private static final String Base32EncChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";
    /* lookup table used to decode() characters in Base32 strings */
    private static final byte[] Base32DecLookup = { 26, 27, 28, 29, 30, 31, -1,
            -1, -1, -1, -1, -1, -1, -1, // 23456789:;<=>?
            -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, // @ABCDEFGHIJKLMNO
            15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, // PQRSTUVWXYZ[\]^_
            -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, // `abcdefghijklmno
            15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25 // pqrstuvwxyz
    };
    /* Messsages for Illegal Parameter Exceptions in decode() */
    private static final String Base32ErrorCanonicalLength = "non canonical Base32 string length";
    private static final String Base32ErrorCanonicalEnd = "non canonical bits at end of Base32 string";
    private static final String Base32ErrorInvalidChar = "invalid character in Base32 string";

    private static final char[] Base58ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();
    private static final char Base58ENCODED_ZERO = Base58ALPHABET[0];
    private static final int[] Base58INDEXES = new int[]{
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, -1, -1, -1, -1, -1, -1, -1, 9, 10, 11, 12, 13, 14, 15, 16, -1, 17, 18, 19, 20, 21, -1, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, -1, -1, -1, -1, -1, -1, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, -1, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, -1, -1, -1, -1, -1
    };

    private static final byte[] Base62Lookup = new byte[]{
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 0, 0, 0, 0, 0, 0, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 0, 0, 0, 0, 0, 0, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    };

    private static final byte[] Base62Alphabet = new byte[]{
            (byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6', (byte) '7',
            (byte) '8', (byte) '9', (byte) 'A', (byte) 'B', (byte) 'C', (byte) 'D', (byte) 'E', (byte) 'F',
            (byte) 'G', (byte) 'H', (byte) 'I', (byte) 'J', (byte) 'K', (byte) 'L', (byte) 'M', (byte) 'N',
            (byte) 'O', (byte) 'P', (byte) 'Q', (byte) 'R', (byte) 'S', (byte) 'T', (byte) 'U', (byte) 'V',
            (byte) 'W', (byte) 'X', (byte) 'Y', (byte) 'Z', (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd',
            (byte) 'e', (byte) 'f', (byte) 'g', (byte) 'h', (byte) 'i', (byte) 'j', (byte) 'k', (byte) 'l',
            (byte) 'm', (byte) 'n', (byte) 'o', (byte) 'p', (byte) 'q', (byte) 'r', (byte) 's', (byte) 't',
            (byte) 'u', (byte) 'v', (byte) 'w', (byte) 'x', (byte) 'y', (byte) 'z'
    };

    private final static int Base85ASCII_SHIFT = 33;

    private static final int[] BASE85POW = {
            1,
            85,
            85 * 85,
            85 * 85 * 85,
            85 * 85 * 85 *85
    };

    public static final byte[] Base91ENCODING_TABLE = new byte[]{
            65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 33, 35, 36, 37, 38, 40, 41, 42, 43, 44, 46, 47, 58, 59, 60, 61, 62, 63, 64, 91, 93, 94, 95, 96, 123, 124, 125, 126, 34
    };
    private static final byte[] Base91DECODING_TABLE = new byte[]{
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, 90, 63, 64, 65, 66, -1, 67, 68, 69, 70, 71, -1, 72, 73, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 74, 75, 76, 77, 78, 79, 80, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 81, -1, 82, 83, 84, 85, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 86, 87, 88, 89, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1
    };
    private static final int Base91BASE = 91;
    private static final float Base91AVERAGE_ENCODING_RATIO = 1.2297f;

    public final static byte[] Base92ENCODE_MAPPING = new byte[]{
            33, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    };

    public final static byte[] Base92DECODE_MAPPING = new byte[]{
            (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, 0, (byte) 255, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, (byte) 255, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255
    };

    public static final char[] Base128SymbolTable = new char[]{
            (char)0,(char)1,(char)2,(char)3,(char)4,(char)5,(char)6,(char)7,(char)8,(char)9,(char)10,(char)11,(char)12,(char)13,(char)14,(char)15,(char)16,(char)17,(char)18,(char)19,(char)20,(char)21,(char)22,(char)23,(char)24,(char)25,(char)26,(char)27,(char)28,(char)29,(char)30,(char)31,(char)32,(char)33,(char)34,(char)35,(char)36,(char)37,(char)38,(char)39,(char)40,(char)41,(char)42,(char)43,(char)44,(char)45,(char)46,(char)47,(char)48,(char)49,(char)50,(char)51,(char)52,(char)53,(char)54,(char)55,(char)56,(char)57,(char)58,(char)59,(char)60,(char)61,(char)62,(char)63,(char)64,(char)65,(char)66,(char)67,(char)68,(char)69,(char)70,(char)71,(char)72,(char)73,(char)74,(char)75,(char)76,(char)77,(char)78,(char)79,(char)80,(char)81,(char)82,(char)83,(char)84,(char)85,(char)86,(char)87,(char)88,(char)89,(char)90,(char)91,(char)92,(char)93,(char)94,(char)95,(char)96,(char)97,(char)98,(char)99,(char)100,(char)101,(char)102,(char)103,(char)104,(char)105,(char)106,(char)107,(char)108,(char)109,(char)110,(char)111,(char)112,(char)113,(char)114,(char)115,(char)116,(char)117,(char)118,(char)119,(char)120,(char)121,(char)122,(char)123,(char)124,(char)125,(char)126,(char)127
    };

    public static final HashMap<Character, Integer> Base128IndexTable = new HashMap<Character, Integer>(){
        {
            put((char)0,0);put((char)1,1);put((char)2,2);put((char)3,3);put((char)4,4);put((char)5,5);put((char)6,6);put((char)7,7);put((char)8,8);put((char)9,9);put((char)10,10);put((char)11,11);put((char)12,12);put((char)13,13);put((char)14,14);put((char)15,15);put((char)16,16);put((char)17,17);put((char)18,18);put((char)19,19);put((char)20,20);put((char)21,21);put((char)22,22);put((char)23,23);put((char)24,24);put((char)25,25);put((char)26,26);put((char)27,27);put((char)28,28);put((char)29,29);put((char)30,30);put((char)31,31);put((char)32,32);put((char)33,33);put((char)34,34);put((char)35,35);put((char)36,36);put((char)37,37);put((char)38,38);put((char)39,39);put((char)40,40);put((char)41,41);put((char)42,42);put((char)43,43);put((char)44,44);put((char)45,45);put((char)46,46);put((char)47,47);put((char)48,48);put((char)49,49);put((char)50,50);put((char)51,51);put((char)52,52);put((char)53,53);put((char)54,54);put((char)55,55);put((char)56,56);put((char)57,57);put((char)58,58);put((char)59,59);put((char)60,60);put((char)61,61);put((char)62,62);put((char)63,63);put((char)64,64);put((char)65,65);put((char)66,66);put((char)67,67);put((char)68,68);put((char)69,69);put((char)70,70);put((char)71,71);put((char)72,72);put((char)73,73);put((char)74,74);put((char)75,75);put((char)76,76);put((char)77,77);put((char)78,78);put((char)79,79);put((char)80,80);put((char)81,81);put((char)82,82);put((char)83,83);put((char)84,84);put((char)85,85);put((char)86,86);put((char)87,87);put((char)88,88);put((char)89,89);put((char)90,90);put((char)91,91);put((char)92,92);put((char)93,93);put((char)94,94);put((char)95,95);put((char)96,96);put((char)97,97);put((char)98,98);put((char)99,99);put((char)100,100);put((char)101,101);put((char)102,102);put((char)103,103);put((char)104,104);put((char)105,105);put((char)106,106);put((char)107,107);put((char)108,108);put((char)109,109);put((char)110,110);put((char)111,111);put((char)112,112);put((char)113,113);put((char)114,114);put((char)115,115);put((char)116,116);put((char)117,117);put((char)118,118);put((char)119,119);put((char)120,120);put((char)121,121);put((char)122,122);put((char)123,123);put((char)124,124);put((char)125,125);put((char)126,126);put((char)127,127);
        }
    };

    public static String encrypt(String text, String mode){
        String returnString = "";
        switch (mode){
            case "Base16":
                returnString = b16Encode(text);
                break;
            case "Base32":
                returnString = b32Encode(text);
                break;
            case "Base36":
                returnString = b36Encode(text);
                break;
            case "Base58":
                returnString = b58Encode(text);
                break;
            case "Base62":
                returnString = b62Encode(text);
                break;
            case "Base64":
                returnString = new String(b64encoder.encode(text.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
                break;
            case "Base85":
                returnString = b85Encode(text);
                break;
            case "Base91":
                returnString = b91Encode(text);
                break;
            case "Base92":
                returnString = b92Encode(text);
                break;
            case "Base128":
                returnString = b128Encode(text);
                break;
        }

        return returnString;
    }

    public static String decrypt(String text, String mode){

        String returnString = "";
        switch (mode){
            case "Base16":
                returnString = b16Decode(text);
                break;
            case "Base32":
                returnString = b32Decode(text);
                break;
            case "Base36":
                returnString = b36Decode(text);
                break;
            case "Base58":
                returnString = b58Decode(text);
                break;
            case "Base62":
                returnString = b62Decode(text);
                break;
            case "Base64":
                returnString = new String(b64decoder.decode(text.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
                break;
            case "Base85":
                returnString = b85Decode(text);
                break;
            case "Base91":
                returnString = b91Decode(text);
                break;
            case "Base92":
                returnString = b92Decode(text);
                break;
            case "Base128":
                returnString = b128Decode(text);
                break;
        }

        return returnString;
    }


    public static String b16Encode(String str) {
        byte[] data = str.getBytes(StandardCharsets.UTF_8);
        int offset = 0;
        int length = data.length;
        StringBuilder buff = new StringBuilder(length * 2);
        int i = offset, total = offset + length;
        while (i < total) {
            buff.append(Base16EncTab[(data[i] & 0xF0) >> 4]);
            buff.append(Base16EncTab[data[i] & 0x0F]);
            i++;
        }

        return buff.toString();
    }

    public static String b16Decode(String hex) {
        byte[] data = new byte[hex.length() / 2];
        int i = 0, total = (hex.length() / 2) * 2, idx = 0;
        while (i < total) {
            data[idx++] = (byte) ((Base16DecTab[hex.charAt(i++)] << 4) | Base16DecTab[hex.charAt(i++)]);
        }
        return new String(data,StandardCharsets.UTF_8);
    }

    static public String b32Decode(final String base32)  {
        switch (base32.length() % 8) { // test the length of last subblock
            case 1: // 5 bits in subblock: 0 useful bits but 5 discarded
            case 3: // 15 bits in subblock: 8 useful bits but 7 discarded
            case 6: // 30 bits in subblock: 24 useful bits but 6 discarded
                return Base32ErrorCanonicalLength;
        }
        byte[] bytes = new byte[base32.length() * 5 / 8];
        int offset = 0, i = 0, lookup;
        byte nextByte, digit;
        // Also the code below does test that other discarded bits
        // (1 to 4 bits at end) are effectively 0.
        while (i < base32.length()) {
            // Read the 1st char in a 8-chars subblock
            // check that chars are not outside the lookup table and valid
            lookup = base32.charAt(i++) - '2';
            if (lookup < 0 || lookup >= Base32DecLookup.length) {
                return Base32ErrorInvalidChar;
            }
            digit = Base32DecLookup[lookup];
            if (digit == -1) {
                return Base32ErrorInvalidChar;
            }
            // // STEP n = 0: leave 5 bits
            nextByte = (byte) (digit << 3);
            // Assert(i < base32.length) // tested before loop
            // Read the 2nd char in a 8-chars subblock
            // Check that chars are not outside the lookup table and valid
            lookup = base32.charAt(i++) - '2';
            if (lookup < 0 || lookup >= Base32DecLookup.length) {
                return Base32ErrorInvalidChar;
            }
            digit = Base32DecLookup[lookup];
            if (digit == -1) {
                return Base32ErrorInvalidChar;
            }
            // // STEP n = 5: insert 3 bits, leave 2 bits
            bytes[offset++] = (byte) (nextByte | (digit >> 2));
            nextByte = (byte) ((digit & 3) << 6);
            if (i >= base32.length()) {
                if (nextByte != (byte) 0) {
                    return Base32ErrorCanonicalEnd;
                }
                break; // discard the remaining 2 bits
            }
            // Read the 3rd char in a 8-chars subblock
            // Check that chars are not outside the lookup table and valid
            lookup = base32.charAt(i++) - '2';
            if (lookup < 0 || lookup >= Base32DecLookup.length) {
                return Base32ErrorInvalidChar;
            }
            digit = Base32DecLookup[lookup];
            if (digit == -1) {
                return Base32ErrorInvalidChar;
            }
            // // STEP n = 2: leave 7 bits
            nextByte |= (byte) (digit << 1);
            // Assert(i < base32.length) // tested before loop
            // Read the 4th char in a 8-chars subblock
            // Check that chars are not outside the lookup table and valid
            lookup = base32.charAt(i++) - '2';
            if (lookup < 0 || lookup >= Base32DecLookup.length) {
                return Base32ErrorInvalidChar;
            }
            digit = Base32DecLookup[lookup];
            if (digit == -1) {
                return Base32ErrorInvalidChar;
            }
            // // STEP n = 7: insert 1 bit, leave 4 bits
            bytes[offset++] = (byte) (nextByte | (digit >> 4));
            nextByte = (byte) ((digit & 15) << 4);
            if (i >= base32.length()) {
                if (nextByte != (byte) 0) {
                    return Base32ErrorCanonicalEnd;
                }
                break; // discard the remaining 4 bits
            }
            // Read the 5th char in a 8-chars subblock
            // Assert that chars are not outside the lookup table and valid
            lookup = base32.charAt(i++) - '2';
            if (lookup < 0 || lookup >= Base32DecLookup.length) {
                return Base32ErrorInvalidChar;
            }
            digit = Base32DecLookup[lookup];
            if (digit == -1) {
                return Base32ErrorInvalidChar;
            }
            // // STEP n = 4: insert 4 bits, leave 1 bit
            bytes[offset++] = (byte) (nextByte | (digit >> 1));
            nextByte = (byte) ((digit & 1) << 7);
            if (i >= base32.length()) {
                if (nextByte != (byte) 0) {
                    return Base32ErrorCanonicalEnd;
                }
                break; // discard the remaining 1 bit
            }
            // Read the 6th char in a 8-chars subblock
            // Check that chars are not outside the lookup table and valid
            lookup = base32.charAt(i++) - '2';
            if (lookup < 0 || lookup >= Base32DecLookup.length) {
                return Base32ErrorInvalidChar;
            }
            digit = Base32DecLookup[lookup];
            if (digit == -1) {
                return Base32ErrorInvalidChar;
            }
            // // STEP n = 1: leave 6 bits
            nextByte |= (byte) (digit << 2);
            // Assert(i < base32.length) // tested before loop
            // Read the 7th char in a 8-chars subblock
            // Check that chars are not outside the lookup table and valid
            lookup = base32.charAt(i++) - '2';
            if (lookup < 0 || lookup >= Base32DecLookup.length) {
                return Base32ErrorInvalidChar;
            }
            digit = Base32DecLookup[lookup];
            if (digit == -1) {
                return Base32ErrorInvalidChar;
            }
            // // STEP n = 6: insert 2 bits, leave 3 bits
            bytes[offset++] = (byte) (nextByte | (digit >> 3));
            nextByte = (byte) ((digit & 7) << 5);
            if (i >= base32.length()) {
                if (nextByte != (byte) 0) {
                    return Base32ErrorCanonicalEnd;
                }
                break; // discard the remaining 3 bits
            }
            // Read the 8th char in a 8-chars subblock
            // Check that chars are not outside the lookup table and valid
            lookup = base32.charAt(i++) - '2';
            if (lookup < 0 || lookup >= Base32DecLookup.length) {
                return Base32ErrorInvalidChar;
            }
            digit = Base32DecLookup[lookup];
            if (digit == -1) {
                return Base32ErrorInvalidChar;
            }
            // // STEP n = 3: insert 5 bits, leave 0 bit
            bytes[offset++] = (byte) (nextByte | digit);
            // // possible end of string here with no trailing bits
        }
        // On loop exit, discard trialing n bits.
        return new String(bytes,StandardCharsets.UTF_8);
    }

    static public String b32Encode(String text) {
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        StringBuilder base32 = new StringBuilder((bytes.length * 8 + 4) / 5);
        int currByte, digit, i = 0;
        while (i < bytes.length) {
            // INVARIANTS FOR EACH STEP n in [0..5[; digit in [0..31[;
            // The remaining n bits are already aligned on top positions
            // of the 5 least bits of digit, the other bits are 0.
            // //// STEP n = 0; insert new 5 bits, leave 3 bits
            currByte = bytes[i++] & 255;
            base32.append(Base32EncChars.charAt(currByte >> 3));
            digit = (currByte & 7) << 2;
            if (i >= bytes.length) { // put the last 3 bits
                base32.append(Base32EncChars.charAt(digit));
                break;
            }
            // //// STEP n = 3: insert 2 new bits, then 5 bits, leave 1 bit
            currByte = bytes[i++] & 255;
            base32.append(Base32EncChars.charAt(digit | (currByte >> 6)));
            base32.append(Base32EncChars.charAt((currByte >> 1) & 31));
            digit = (currByte & 1) << 4;
            if (i >= bytes.length) { // put the last 1 bit
                base32.append(Base32EncChars.charAt(digit));
                break;
            }
            // //// STEP n = 1: insert 4 new bits, leave 4 bit
            currByte = bytes[i++] & 255;
            base32.append(Base32EncChars.charAt(digit | (currByte >> 4)));
            digit = (currByte & 15) << 1;
            if (i >= bytes.length) { // put the last 4 bits
                base32.append(Base32EncChars.charAt(digit));
                break;
            }
            // //// STEP n = 4: insert 1 new bit, then 5 bits, leave 2 bits
            currByte = bytes[i++] & 255;
            base32.append(Base32EncChars.charAt(digit | (currByte >> 7)));
            base32.append(Base32EncChars.charAt((currByte >> 2) & 31));
            digit = (currByte & 3) << 3;
            if (i >= bytes.length) { // put the last 2 bits
                base32.append(Base32EncChars.charAt(digit));
                break;
            }
            // /// STEP n = 2: insert 3 new bits, then 5 bits, leave 0 bit
            currByte = bytes[i++] & 255;
            base32.append(Base32EncChars.charAt(digit | (currByte >> 5)));
            base32.append(Base32EncChars.charAt(currByte & 31));
            // // This point is reached for bytes.length multiple of 5
        }
        return base32.toString();
    }

    public static String b36Decode(String in) {
//        String decorateIn = "5tknwgfs"+in;
        byte[] withoutLeadingZeroes = new BigInteger(in, 36).toByteArray();
        int zeroPrefixLength = in.length();
        for (int i = 0; i < in.length(); i++) {
            if (in.charAt(i) != '0') {
                zeroPrefixLength =  i;
                break;
            }
        }
        byte[] res = new byte[zeroPrefixLength + withoutLeadingZeroes.length];
        System.arraycopy(withoutLeadingZeroes, 0, res, zeroPrefixLength, withoutLeadingZeroes.length);
        return new String(res, StandardCharsets.UTF_8);
    }

    public static String b36Encode(String text) {
        byte[] in = text.getBytes(StandardCharsets.UTF_8);
        String withoutLeadingZeroes = new BigInteger(1, in).toString(36);

        int zeroPrefixLength = in.length;
        for (int i = 0; i < in.length; i++) {
            if (in[i] != 0) {
                zeroPrefixLength = i;
                break;
            }
        }

        StringBuilder b = new StringBuilder();
        for (int i=0; i < zeroPrefixLength; i++)
            b.append("0");
        b.append(withoutLeadingZeroes);
        return b.toString();
    }

    public static String b58Encode(String text) {
        byte[] input = text.getBytes(StandardCharsets.UTF_8);
        if (input.length == 0) {
            return "";
        }
        // Count leading zeros.
        int zeros = 0;
        while (zeros < input.length && input[zeros] == 0) {
            ++zeros;
        }
        // Convert base-256 digits to base-58 digits (plus conversion to ASCII characters)
        input = Arrays.copyOf(input, input.length); // since we modify it in-place
        char[] encoded = new char[input.length * 2]; // upper bound
        int outputStart = encoded.length;
        for (int inputStart = zeros; inputStart < input.length; ) {
            encoded[--outputStart] = Base58ALPHABET[b58DivMod(input, inputStart, 256, 58)];
            if (input[inputStart] == 0) {
                ++inputStart; // optimization - skip leading zeros
            }
        }
        // Preserve exactly as many leading encoded zeros in output as there were leading zeros in input.
        while (outputStart < encoded.length && encoded[outputStart] == Base58ENCODED_ZERO) {
            ++outputStart;
        }
        while (--zeros >= 0) {
            encoded[--outputStart] = Base58ENCODED_ZERO;
        }
        // Return encoded string (including encoded leading zeros).
        return new String(encoded, outputStart, encoded.length - outputStart);
    }

    public static String b58Decode(String input) {
        if (input.length() == 0) {
            return "";
        }
        // Convert the base58-encoded ASCII chars to a base58 byte sequence (base58 digits).
        byte[] input58 = new byte[input.length()];
        for (int i = 0; i < input.length(); ++i) {
            char c = input.charAt(i);
            int digit = c < 128 ? Base58INDEXES[c] : -1;
            if (digit < 0) {
                throw new IllegalStateException("InvalidCharacter in base 58");
            }
            input58[i] = (byte) digit;
        }
        // Count leading zeros.
        int zeros = 0;
        while (zeros < input58.length && input58[zeros] == 0) {
            ++zeros;
        }
        // Convert base-58 digits to base-256 digits.
        byte[] decoded = new byte[input.length()];
        int outputStart = decoded.length;
        for (int inputStart = zeros; inputStart < input58.length; ) {
            decoded[--outputStart] = b58DivMod(input58, inputStart, 58, 256);
            if (input58[inputStart] == 0) {
                ++inputStart; // optimization - skip leading zeros
            }
        }
        // Ignore extra leading zeroes that were added during the calculation.
        while (outputStart < decoded.length && decoded[outputStart] == 0) {
            ++outputStart;
        }
        // Return decoded data (including original number of leading zeros).
        byte[] returnByte = Arrays.copyOfRange(decoded, outputStart - zeros, decoded.length);
        return new String(returnByte, StandardCharsets.UTF_8);
    }

    private static byte b58DivMod(byte[] number, int firstDigit, int base, int divisor) {
        // this is just long division which accounts for the base of the input digits
        int remainder = 0;
        for (int i = firstDigit; i < number.length; i++) {
            int digit = (int) number[i] & 0xFF;
            int temp = remainder * base + digit;
            number[i] = (byte) (temp / divisor);
            remainder = temp % divisor;
        }
        return (byte) remainder;
    }

    public static String b62Encode(String text) {
        byte[] message = text.getBytes(StandardCharsets.UTF_8);

        final byte[] indices = b62Convert(message, 256, 62);
        byte[] returned =  b62Translate(indices, Base62Alphabet);

        return new String(returned,StandardCharsets.UTF_8);
    }

    public static String b62Decode(String b62String) {

        byte[] encoded = b62String.getBytes(StandardCharsets.UTF_8);
        if (!isBase62Encoding(encoded)) {
            throw new IllegalArgumentException("Input is not encoded correctly");
        }

        byte[] prepared = b62Translate(encoded, Base62Lookup);
        byte[] returned = b62Convert(prepared, 62, 256);

        return new String(returned, StandardCharsets.UTF_8);
    }

    public static boolean isBase62Encoding(final byte[] bytes) {
        if (bytes == null) {
            return false;
        }

        for (final byte e : bytes) {
            if ('0' > e || '9' < e) {
                if ('a' > e || 'z' < e) {
                    if ('A' > e || 'Z' < e) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private static byte[] b62Translate(final byte[] indices, final byte[] dictionary) {
        final byte[] translation = new byte[indices.length];

        for (int i = 0; i < indices.length; i++) {
            translation[i] = dictionary[indices[i]];
        }

        return translation;
    }

    private static byte[] b62Convert(final byte[] message, final int sourceBase, final int targetBase) {


        final int estimatedLength = (int) Math.ceil((Math.log(sourceBase) / Math.log(targetBase)) * message.length);
        final ByteArrayOutputStream out = new ByteArrayOutputStream(estimatedLength);
        byte[] source = message;

        while (source.length > 0) {
            final ByteArrayOutputStream quotient = new ByteArrayOutputStream(source.length);
            int remainder = 0;

            for (byte b : source) {
                final int accumulator = (b & 0xFF) + remainder * sourceBase;
                final int digit = (accumulator - (accumulator % targetBase)) / targetBase;

                remainder = accumulator % targetBase;

                if (quotient.size() > 0 || digit > 0) {
                    quotient.write(digit);
                }
            }

            out.write(remainder);
            source = quotient.toByteArray();
        }

        // pad output with zeroes corresponding to the number of leading zeroes in the message
        for (int i = 0; i < message.length - 1 && message[i] == 0; i++) {
            out.write(0);
        }

        byte[] arr = out.toByteArray();
        int length = arr.length;
        byte[] reversed = new byte[length];

        for (int i = 0; i < length; i++) {
            reversed[length - i - 1] = arr[i];
        }

        return reversed;
    }



    public static String b85Encode(String text) {
        if (text == null) {
            return "You must provide a non-null input";
        }
        byte[] payload = text.getBytes(StandardCharsets.UTF_8);

        StringBuilder stringBuff = new StringBuilder(payload.length * 5/4);

        byte[] chunk = new byte[4];
        int chunkIndex = 0;
        for (byte currByte : payload) {
            chunk[chunkIndex++] = currByte;

            if (chunkIndex == 4) {
                int value = b85ByteToInt(chunk);
                if (value == 0) {
                    stringBuff.append('z');
                } else {
                    stringBuff.append(b85EncodeChunk(value));
                }
                Arrays.fill(chunk, (byte) 0);
                chunkIndex = 0;
            }
        }

        if (chunkIndex > 0) {
            int numPadded = chunk.length - chunkIndex;
            Arrays.fill(chunk, chunkIndex, chunk.length, (byte)0);
            int value = b85ByteToInt(chunk);
            char[] encodedChunk = b85EncodeChunk(value);
            for(int i = 0 ; i < encodedChunk.length - numPadded; i++) {
                stringBuff.append(encodedChunk[i]);
            }
        }

        return stringBuff.toString();
    }

    public static String b85Decode(String chars) {
        if (chars == null) {
            throw new IllegalArgumentException("You must provide a non-null input");
        }
        final int inputLength = chars.length();

        long zCount = chars.chars().filter(c -> c == 'z').count();

        BigDecimal uncompressedZLength = BigDecimal.valueOf(zCount).multiply(BigDecimal.valueOf(4));

        BigDecimal uncompressedNonZLength = BigDecimal.valueOf(inputLength - zCount)
                .multiply(BigDecimal.valueOf(4))
                .divide(BigDecimal.valueOf(5));

        BigDecimal uncompressedLength = uncompressedZLength.add(uncompressedNonZLength);

        ByteBuffer bytebuff = ByteBuffer.allocate(uncompressedLength.intValue());
        //1. Whitespace characters may occur anywhere to accommodate line length limitations. So lets strip it.
        chars = Pattern.compile("\\s+").matcher(chars).replaceAll("");
        //Since Base85 is an ascii encoder, we don't need to get the bytes as UTF-8.
        byte[] payload = chars.getBytes(StandardCharsets.US_ASCII);
        byte[] chunk = new byte[5];
        int chunkIndex = 0;
        for (byte currByte : payload) {
            if (currByte == 'z') {
                if (chunkIndex > 0) {
                    throw new IllegalArgumentException("The payload is not base 85 encoded.");
                }
                chunk[chunkIndex++] = '!';
                chunk[chunkIndex++] = '!';
                chunk[chunkIndex++] = '!';
                chunk[chunkIndex++] = '!';
                chunk[chunkIndex++] = '!';
            } else {
                chunk[chunkIndex++] = currByte;
            }

            if (chunkIndex == 5) {
                bytebuff.put(b85DecodeChunk(chunk));
                Arrays.fill(chunk, (byte) 0);
                chunkIndex = 0;
            }
        }

        if (chunkIndex > 0) {
            int numPadded = chunk.length - chunkIndex;
            Arrays.fill(chunk, chunkIndex, chunk.length, (byte)'u');
            byte[] paddedDecode = b85DecodeChunk(chunk);
            for(int i = 0 ; i < paddedDecode.length - numPadded; i++) {
                bytebuff.put(paddedDecode[i]);
            }
        }

        bytebuff.flip();
        byte[] returned = Arrays.copyOf(bytebuff.array(),bytebuff.limit());
        return new String(returned, StandardCharsets.UTF_8);
    }

    private static char[] b85EncodeChunk(int value) {
        long longValue = value & 0x00000000ffffffffL;
        char[] encodedChunk = new char[5];
        for(int i = 0 ; i < encodedChunk.length; i++) {
            encodedChunk[i] = (char) ((longValue / BASE85POW[4 - i]) + Base85ASCII_SHIFT);
            longValue = longValue % BASE85POW[4 - i];
        }
        return encodedChunk;
    }

    private static byte[] b85DecodeChunk(byte[] chunk) {
        if (chunk.length != 5) {
            throw new IllegalArgumentException("You can only decode chunks of size 5.");
        }
        int value = 0;
        value += (chunk[0] - Base85ASCII_SHIFT) * BASE85POW[4];
        value += (chunk[1] - Base85ASCII_SHIFT) * BASE85POW[3];
        value += (chunk[2] - Base85ASCII_SHIFT) * BASE85POW[2];
        value += (chunk[3] - Base85ASCII_SHIFT) * BASE85POW[1];
        value += (chunk[4] - Base85ASCII_SHIFT) * BASE85POW[0];

        return b85IntToByte(value);
    }

    private static int b85ByteToInt(byte[] value) {
        if (value == null || value.length != 4) {
            throw new IllegalArgumentException("You cannot create an int without exactly 4 bytes.");
        }
        return ByteBuffer.wrap(value).getInt();
    }

    private static byte[] b85IntToByte(int value) {
        return new byte[] {
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) (value)
        };
    }

    public static String b91Encode(String text) {

        byte[] data = text.getBytes(StandardCharsets.UTF_8);
        int estimatedSize = (int) Math.ceil(data.length * Base91AVERAGE_ENCODING_RATIO);
        ByteArrayOutputStream output = new ByteArrayOutputStream(estimatedSize);

        int ebq = 0;
        int en = 0;
        for (byte datum : data) {
            ebq |= (datum & 255) << en;
            en += 8;
            if (en > 13) {
                int ev = ebq & 8191;

                if (ev > 88) {
                    ebq >>= 13;
                    en -= 13;
                } else {
                    ev = ebq & 16383;
                    ebq >>= 14;
                    en -= 14;
                }
                output.write(Base91ENCODING_TABLE[ev % Base91BASE]);
                output.write(Base91ENCODING_TABLE[ev / Base91BASE]);
            }
        }

        if ( en > 0 ) {
            output.write(Base91ENCODING_TABLE[ebq % Base91BASE]);
            if ( en > 7 || ebq > 90 ) {
                output.write(Base91ENCODING_TABLE[ebq / Base91BASE]);
            }
        }

        byte[] returned = output.toByteArray();

        return new String(returned, StandardCharsets.UTF_8);
    }

    public static String b91Decode(String text) {

        byte[] data = text.getBytes(StandardCharsets.UTF_8);
        if (data.length == 0)
            return "输入为空";

        int dbq = 0;
        int dn = 0;
        int dv = -1;

        int estimatedSize = Math.round(data.length / Base91AVERAGE_ENCODING_RATIO);
        ByteArrayOutputStream output = new ByteArrayOutputStream(estimatedSize);

        for (byte datum : data) {
            if (Base91DECODING_TABLE[datum] == -1)
                continue;
            if (dv == -1)
                dv = Base91DECODING_TABLE[datum];
            else {
                dv += Base91DECODING_TABLE[datum] * Base91BASE;
                dbq |= dv << dn;
                dn += (dv & 8191) > 88 ? 13 : 14;
                do {
                    output.write((byte) dbq);
                    dbq >>= 8;
                    dn -= 8;
                } while (dn > 7);
                dv = -1;
            }
        }

        if ( dv != -1 ) {
            output.write((byte) (dbq | dv << dn));
        }

        byte[] returned = output.toByteArray();
        return new String(returned, StandardCharsets.UTF_8);
    }

    public static String b92Encode(String text) {
        int i, j, size, tmp;       // i for raw, j for encoded// for the malloc
        long workspace; // bits holding bin
        short wssize;   // number of good bits in workspace
        byte c;
        byte[] res;

        byte[] str = text.getBytes(StandardCharsets.UTF_8);
        int len = str.length;
        if (len == 0) {
            return "";
        }
        // precalculate how much space we need to malloc
        size = (len * 8) % 13;
        if (size == 0) {
            size = 2 * ((len * 8) / 13);
        } else if (size < 7) {
            size = 2 * ((len * 8) / 13) + 1;
        } else {
            size = 2 * ((len * 8) / 13) + 2;
        }
        // do the malloc, add space for a null byte
        res = new byte[size];
        workspace = 0;
        wssize = 0;
        j = 0;
        for (i = 0; i < len; i++) {
            workspace = workspace << 8 | str[i];
            wssize += 8;
            if (wssize >= 13) {
                tmp = (int) ((workspace >> (wssize - 13)) & 8191);
                c = Base92ENCODE_MAPPING[tmp / 91];
                if (c == 0) {
                    // do something, illegal character
                    return "illegal character";
                }
                res[j++] = c;
                c = Base92ENCODE_MAPPING[tmp % 91];
                if (c == 0) {
                    return "illegal character";
                }
                res[j++] = c;
                wssize -= 13;
            }
        }
        // encode a last byte
        if (0 < wssize && wssize < 7) {
            tmp = (int) ((workspace << (6 - wssize)) & 63);  // pad the right side
            c = Base92ENCODE_MAPPING[tmp];
            if (c == 0) {
                // do something, illegal character
                return "illegal character";
            }
            res[j] = c;
        } else if (7 <= wssize) {
            tmp = (int) ((workspace << (13 - wssize)) & 8191); // pad the right side
            c = Base92ENCODE_MAPPING[tmp / 91];
            if (c == 0) {
                return "illegal character";
            }
            res[j++] = c;
            c = Base92ENCODE_MAPPING[tmp % 91];
            if (c == 0) {
                return "illegal character";
            }
            res[j] = c;
        }
        // add the null byte
        return new String(res, StandardCharsets.UTF_8);
    }

    public static String b92Decode(String text) {
        int i, j, b1, b2, len, size;
        byte[] res = null;
        long workspace;
        short wssize;

        byte[] str = text.getBytes(StandardCharsets.UTF_8);
        size = str.length;
        // handle small cases first
        if (str == "~".getBytes(StandardCharsets.UTF_8) || size == 0) {
            return "输入为空";
        }
        // this case does not fit the specs
        if (size < 2) {
            return "输入格式错误";
        }
        // calculate size
        len = ((size / 2 * 13) + (size % 2 * 6)) / 8;
        res = new byte[len];
        // handle pairs of chars
        workspace = 0;
        wssize = 0;
        j = 0;
        for (i = 0; i + 1 < size; i += 2) {
            b1 = Base92DECODE_MAPPING[str[i]];
            b2 = Base92DECODE_MAPPING[str[i + 1]];
            workspace = (workspace << 13) | (b1 * 91 + b2);
            wssize += 13;
            while (wssize >= 8) {
                res[j++] = (byte) ((workspace >> (wssize - 8)) & 255);
                wssize -= 8;
            }
        }
        // handle single char
        if (size % 2 == 1) {
            workspace = (workspace << 6) | Base92DECODE_MAPPING[str[size - 1]];
            wssize += 6;
            while (wssize >= 8) {
                res[j++] = (byte) ((workspace >> (wssize - 8)) & 255);
                wssize -= 8;
            }
        }
        return new String(res, StandardCharsets.UTF_8);
    }

    public static String b128Encode(String text) {
        byte[] data = text.getBytes(StandardCharsets.UTF_8);
        if(data.length == 0) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        int tail = 0;
        for (int i = 0; i < data.length; i++) {
            int mov = (i % 7 + 1);
            int curr = 0xFF & data[i];
            int code = tail + (curr >> mov);
            result.append(Base128SymbolTable[code]);
            tail = (0xFF & (curr << (8 - mov))) >> 1;
            if(mov == 7) {
                result.append(Base128SymbolTable[tail]);
                tail = 0;
            }
        }
        result.append(Base128SymbolTable[tail]);
        return result.toString();
    }

    public static String b128Decode(String base128) {
        if(base128 == null || base128.length() == 0) {
            return "";
        }
        int length = (int) Math.floor(base128.length() * 0.875);
        byte[] result = new byte[length];
        int idx = 0;
        int head = Base128IndexTable.get(base128.charAt(0)) << 1;
        for (int i = 1; i < base128.length();) {
            int mod = i % 8;
            int code = Base128IndexTable.get(base128.charAt(i++));
            result[idx++] = (byte) (0xFF & (head + (code >> (7 - mod))));
            if(mod == 7) {
                head = 0xFF & (Base128IndexTable.get(base128.charAt(i++)) << 1);
            } else {
                head = 0xFF & (code << (mod + 1));
            }
        }
        return new String(result,StandardCharsets.UTF_8);
    }


}
