package jrcet.frame.HText.Parsepy;

import jrcet.help.Helper;

import java.util.*;

public class Parsepy {

    private static final String[] pyList = new String[]{"a", "ai", "an", "ang", "ao", "ba", "bai", "ban", "bang", "bao", "bei", "ben", "beng", "bi", "bian", "biao", "bie", "bin", "bing", "bo", "bu", "ca", "cai", "can", "cang", "cao", "ce", "cei", "cen", "ceng", "cha", "chai", "chan", "chang", "chao", "che", "chen", "cheng", "chi", "chong", "chou", "chu", "chua", "chuai", "chuan", "chuang", "chui", "chun", "chuo", "ci", "cong", "cou", "cu", "cuan", "cui", "cun", "cuo", "da", "dai", "dan", "dang", "dao", "de", "den", "dei", "deng", "di", "dia", "dian", "diao", "die", "ding", "diu", "dong", "dou", "du", "duan", "dui", "dun", "duo", "e", "ei", "en", "eng", "er", "fa", "fan", "fang", "fei", "fen", "feng", "fo", "fou", "fu", "ga", "gai", "gan", "gang", "gao", "ge", "gei", "gen", "geng", "gong", "gou", "gu", "gua", "guai", "guan", "guang", "gui", "gun", "guo", "ha", "hai", "han", "hang", "hao", "he", "hei", "hen", "heng", "hong", "hou", "hu", "hua", "huai", "huan", "huang", "hui", "hun", "huo", "ji", "jia", "jian", "jiang", "jiao", "jie", "jin", "jing", "jiong", "jiu", "ju", "juan", "jue", "jun", "ka", "kai", "kan", "kang", "kao", "ke", "ken", "keng", "kong", "kou", "ku", "kua", "kuai", "kuan", "kuang", "kui", "kun", "kuo", "la", "lai", "lan", "lang", "lao", "le", "lei", "leng", "li", "lia", "lian", "liang", "liao", "lie", "lin", "ling", "liu", "long", "lou", "lu", "lv", "luan", "lve", "lun", "luo", "m", "ma", "mai", "man", "mang", "mao", "me", "mei", "men", "meng", "mi", "mian", "miao", "mie", "min", "ming", "miu", "mo", "mou", "mu", "na", "nai", "nan", "nang", "nao", "ne", "nei", "nen", "neng", "ng", "ni", "nian", "niang", "niao", "nie", "nin", "ning", "niu", "nong", "nou", "nu", "nv", "nuan", "nve", "nuo", "nun", "o", "ou", "pa", "pai", "pan", "pang", "pao", "pei", "pen", "peng", "pi", "pian", "piao", "pie", "pin", "ping", "po", "pou", "pu", "qi", "qia", "qian", "qiang", "qiao", "qie", "qin", "qing", "qiong", "qiu", "qu", "quan", "que", "qun", "ran", "rang", "rao", "re", "ren", "reng", "ri", "rong", "rou", "ru", "ruan", "rui", "run", "ruo", "sa", "sai", "san", "sang", "sao", "se", "sen", "seng", "sha", "shai", "shan", "shang", "shao", "she", "shei", "shen", "sheng", "shi", "shou", "shu", "shua", "shuai", "shuan", "shuang", "shui", "shun", "shuo", "si", "song", "sou", "su", "suan", "sui", "sun", "suo", "ta", "tai", "tan", "tang", "tao", "te", "teng", "ti", "tian", "tiao", "tie", "ting", "tong", "tou", "tu", "tuan", "tui", "tun", "tuo", "wa", "wai", "wan", "wang", "wei", "wen", "weng", "wo", "wu", "xi", "xia", "xian", "xiang", "xiao", "xie", "xin", "xing", "xiong", "xiu", "xu", "xuan", "xue", "xun", "ya", "yan", "yang", "yao", "ye", "yi", "yin", "ying", "yo", "yong", "you", "yu", "yuan", "yue", "yun", "za", "zai", "zan", "zang", "zao", "ze", "zei", "zen", "zeng", "zha", "zhai", "zhan", "zhang", "zhao", "zhe", "zhei", "zhen", "zheng", "zhi", "zhong", "zhou", "zhu", "zhua", "zhuai", "zhuan", "zhuang", "zhui", "zhun", "zhuo", "zi", "zong", "zou", "zu", "zuan", "zui", "zun", "zuo"};
    private static final HashMap<String, ArrayList<String>> pyMap = new HashMap<>();

    public static String getPYInitial(String text) {

        initialPYMap();
        StringBuilder resultBuilder = new StringBuilder();
        String textContent = Helper.isFile(text) ? Helper.readFile(text) : text;
        ArrayList<String> nameArray = new ArrayList<>(Arrays.asList(textContent.split("\n")));

        for (String name : nameArray) {

            ArrayList<String> resultList = parsePinYin(name);
            for (int i = 0; i < resultList.size(); i++) {
                resultBuilder.append(resultList.get(i).charAt(0));
                if (i == resultList.size() - 1) {
                    resultBuilder.append("\n");
                }
            }
        }

        return resultBuilder.toString();
    }

    public static String abbreviationName(ArrayList<String> nameList){

        StringBuilder abbreviationNameBuilder = new StringBuilder();
        for(int i=0 ; i<nameList.size(); i++){
            abbreviationNameBuilder.append(nameList.get(i).charAt(0));
        }
        return abbreviationNameBuilder.toString();
    }

    public static ArrayList<String> parsePinYin(String name){

        int i = 0;
        String smString;
        ArrayList<String> tmpList;
        ArrayList<String> resultList = new ArrayList<>();

        String[] nameArray = name.split("");

        while(i<nameArray.length){
            smString = nameArray[i];
            if(pyMap.containsKey(smString)){
                String tmpPartPY = "";
                tmpList = pyMap.get(smString);
                if( tmpList.contains(nameArray[i])){
                    tmpPartPY = nameArray[i];
                }
                if(i+1<nameArray.length && tmpList.contains(nameArray[i]+nameArray[i+1])){
                    tmpPartPY = nameArray[i]+nameArray[i+1];
                }
                if(i+2<nameArray.length && tmpList.contains(nameArray[i]+nameArray[i+1]+nameArray[i+2])){
                    tmpPartPY = nameArray[i]+nameArray[i+1]+nameArray[i+2];
                }
                if(i+3<nameArray.length && tmpList.contains(nameArray[i]+nameArray[i+1]+nameArray[i+2]+nameArray[i+3])){
                    tmpPartPY = nameArray[i]+nameArray[i+1]+nameArray[i+2]+nameArray[i+3];
                }
                if(i+4<nameArray.length && tmpList.contains(nameArray[i]+nameArray[i+1]+nameArray[i+2]+nameArray[i+3]+nameArray[i+4])){
                    tmpPartPY = nameArray[i]+nameArray[i+1]+nameArray[i+2]+nameArray[i+3]+nameArray[i+4];
                }
                if(i+5<nameArray.length && tmpList.contains(nameArray[i]+nameArray[i+1]+nameArray[i+2]+nameArray[i+3]+nameArray[i+4]+nameArray[i+5])){
                    tmpPartPY = nameArray[i]+nameArray[i+1]+nameArray[i+2]+nameArray[i+3]+nameArray[i+4]+nameArray[i+5];
                }
                if(tmpPartPY.equals("")){
                    tmpPartPY = smString;
                }
                resultList.add(tmpPartPY);
                i+=tmpPartPY.length();
            }else{
                resultList.add(smString);
                i+=1;
            }
        }
        return  resultList;
    }

    public static void initialPYMap(){
        for(String s : pyList){
            String prefix = s.substring(0,1);
            if(pyMap.containsKey(prefix)){
                pyMap.get(prefix).add(s);
            }else{
                pyMap.put(prefix, new ArrayList<>(Collections.singletonList(s)));
            }
        }
    }

}
