package jrcet;

import java.util.HashMap;

/**
 * 128进制加解密, 一个符号可表示7个bit
 * 可以自定义符号表, 符号不能重复
 * @author lry
 *
 */
public class test {

    public static final char[] Base128SymbolTable = new char[]{
            (char)0,(char)1,(char)2,(char)3,(char)4,(char)5,(char)6,(char)7,(char)8,(char)9,(char)10,(char)11,(char)12,(char)13,(char)14,(char)15,(char)16,(char)17,(char)18,(char)19,(char)20,(char)21,(char)22,(char)23,(char)24,(char)25,(char)26,(char)27,(char)28,(char)29,(char)30,(char)31,(char)32,(char)33,(char)34,(char)35,(char)36,(char)37,(char)38,(char)39,(char)40,(char)41,(char)42,(char)43,(char)44,(char)45,(char)46,(char)47,(char)48,(char)49,(char)50,(char)51,(char)52,(char)53,(char)54,(char)55,(char)56,(char)57,(char)58,(char)59,(char)60,(char)61,(char)62,(char)63,(char)64,(char)65,(char)66,(char)67,(char)68,(char)69,(char)70,(char)71,(char)72,(char)73,(char)74,(char)75,(char)76,(char)77,(char)78,(char)79,(char)80,(char)81,(char)82,(char)83,(char)84,(char)85,(char)86,(char)87,(char)88,(char)89,(char)90,(char)91,(char)92,(char)93,(char)94,(char)95,(char)96,(char)97,(char)98,(char)99,(char)100,(char)101,(char)102,(char)103,(char)104,(char)105,(char)106,(char)107,(char)108,(char)109,(char)110,(char)111,(char)112,(char)113,(char)114,(char)115,(char)116,(char)117,(char)118,(char)119,(char)120,(char)121,(char)122,(char)123,(char)124,(char)125,(char)126,(char)127
    };
    public static final HashMap<Character, Integer> Base128IndexTable = new HashMap<Character, Integer>(){
        {
            put((char)0,0);put((char)1,1);put((char)2,2);put((char)3,3);put((char)4,4);put((char)5,5);put((char)6,6);put((char)7,7);put((char)8,8);put((char)9,9);put((char)10,10);put((char)11,11);put((char)12,12);put((char)13,13);put((char)14,14);put((char)15,15);put((char)16,16);put((char)17,17);put((char)18,18);put((char)19,19);put((char)20,20);put((char)21,21);put((char)22,22);put((char)23,23);put((char)24,24);put((char)25,25);put((char)26,26);put((char)27,27);put((char)28,28);put((char)29,29);put((char)30,30);put((char)31,31);put((char)32,32);put((char)33,33);put((char)34,34);put((char)35,35);put((char)36,36);put((char)37,37);put((char)38,38);put((char)39,39);put((char)40,40);put((char)41,41);put((char)42,42);put((char)43,43);put((char)44,44);put((char)45,45);put((char)46,46);put((char)47,47);put((char)48,48);put((char)49,49);put((char)50,50);put((char)51,51);put((char)52,52);put((char)53,53);put((char)54,54);put((char)55,55);put((char)56,56);put((char)57,57);put((char)58,58);put((char)59,59);put((char)60,60);put((char)61,61);put((char)62,62);put((char)63,63);put((char)64,64);put((char)65,65);put((char)66,66);put((char)67,67);put((char)68,68);put((char)69,69);put((char)70,70);put((char)71,71);put((char)72,72);put((char)73,73);put((char)74,74);put((char)75,75);put((char)76,76);put((char)77,77);put((char)78,78);put((char)79,79);put((char)80,80);put((char)81,81);put((char)82,82);put((char)83,83);put((char)84,84);put((char)85,85);put((char)86,86);put((char)87,87);put((char)88,88);put((char)89,89);put((char)90,90);put((char)91,91);put((char)92,92);put((char)93,93);put((char)94,94);put((char)95,95);put((char)96,96);put((char)97,97);put((char)98,98);put((char)99,99);put((char)100,100);put((char)101,101);put((char)102,102);put((char)103,103);put((char)104,104);put((char)105,105);put((char)106,106);put((char)107,107);put((char)108,108);put((char)109,109);put((char)110,110);put((char)111,111);put((char)112,112);put((char)113,113);put((char)114,114);put((char)115,115);put((char)116,116);put((char)117,117);put((char)118,118);put((char)119,119);put((char)120,120);put((char)121,121);put((char)122,122);put((char)123,123);put((char)124,124);put((char)125,125);put((char)126,126);put((char)127,127);
        }
    };
//    static {
//        int i = 0;
//        for (int j = 0; j < 128; j++) {
////            if('A' <= j && j <= 'Z'
////                    || 'a' <= j && j <= 'z'
////                    || '0' <= j && j <= '9') {
////                symbolTable[i++] = (char) j;
////            }
//            symbolTable[i++] = (char) j;
//        }
////        for (char c : "这是中国人写地[
////        备思要爱发]编解码程序&拥有独特的自定义符号表|不过布能使用重复字,汗啊(还差一十二个知*.#)。为我们加油吧，亲！".toCharArray()) {
////            symbolTable[i++] = c;
////        }
//
//        checkTable();
//
//        for (int j = 0; j < 128; j++) {
//            indexTable.put(symbolTable[j], j);
//        }
//    }

    private static void checkTable() throws Error {
//        System.out.println(symbolTable);
        if(Base128SymbolTable[127] == 0) {
            throw new Error("符号表长度不正确！");
        }
        for (char a : Base128SymbolTable) {
            int count = 0;
            for (char b : Base128SymbolTable) {
                if(a == b) {
                    count++;
                }
            }
            if(count > 2) {
                throw new Error("符号表有重复符号！");
            }
        }
    }

    public String encode(byte[] data) {
        if(data == null || data.length == 0) {
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

    public byte[] decode(String base128) {
        if(base128 == null || base128.length() == 0) {
            return new byte[] { };
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
        return result;
    }

    ///////////////////////测试方法///////////////////////////////
    public static void main(String[] args) {

        String a="";
        for(int i=0; i<128; i++){
           a+="put((char)"+i+","+i+");";
        }
        System.out.println(a);
        test base128 = new test();
//        test1(base128);

        String txt = "李茹钰的所得税的速度是滴哦osidusiou3247IZIiauydiYUI";
        String enc = base128.encode(txt.getBytes());
        System.out.println(enc);
        System.out.println("----------------");
        System.out.println(new String(base128.decode(enc)));
    }


}

