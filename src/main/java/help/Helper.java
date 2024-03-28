package help;


import diycomponents.*;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import static burp.MyExtender.stdout;

import static jrcet.Main.JrcetComponentList;

public class Helper {

    public static Color gray =new Color(203,208,209);

    /*
    组件类函数
     */
    private static int deep = 1;

    public static int getComponentPos(JComponent rootComponent, String tComponentName){

        Component[] components = rootComponent.getComponents();
        for(int i = 0; i<components.length; i++){
            if(Objects.equals(components[i].getName(), tComponentName)){
                return i;
            }
        }
        return -1;
    }

    //寻找指定名字的组件，并返回该组件的Panel
    public static JComponent getComponent(JComponent rootComponent, String tComponentName){

        if (Objects.equals(rootComponent.getName(), tComponentName)){
            return rootComponent;
        }

        for( Component i : rootComponent.getComponents()) {
//            API.logging().output().println(i.getName()+":"+i.getClass());
            if (Objects.equals(i.getName(), tComponentName)){
                return (JComponent) i;
            }
//            API.logging().output().println(i.getClass());
            switch (Arrays.asList(String.valueOf(i.getClass()).split("^([^.]*\\.)*")).get(1)) {
                case "DiyJLogTable","JTabbedPane","JSplitPane","DiyJTextField", "DiyVariablePanel", "JPanel", "JList", "JTable", "JComboBox", "JTextArea" -> {
                    JComponent cj = getComponent((JComponent) i, tComponentName);
                    if (cj != null) return cj;
                }
                case "DiyJTextAreaScrollPane", "JScrollPane" -> {
                    JComponent cs = getComponent((JComponent) ((JScrollPane) i).getViewport().getView(), tComponentName);
                    if (cs != null) return cs;
                }
            }
        }
        return null;
    }

    // 遍历传入组件下的全部组件
    public static void travelComponent(JComponent tComponent){

        if(deep==1){
            JrcetComponentList.add(String.join("", tComponent.getName()));
        }

        for( Component i : tComponent.getComponents()){

            switch (Arrays.asList(String.valueOf(i.getClass()).split("^([^.]*\\.)*")).get(1)) {
                case "DiyJTextField", "JPanel" -> {
                    JrcetComponentList.add(String.join("", Collections.nCopies(deep, "    ")) + i.getName());
                    deep += 1;
                    travelComponent((JComponent) i);
                    deep -= 1;
                }
                case "DiyJTextAreaScrollPane", "JScrollPane" -> {
                    JComponent viewPanel = (JComponent) ((JScrollPane) i).getViewport().getComponent(0);
                    JrcetComponentList.add(String.join("", Collections.nCopies(deep, "    ")) + viewPanel.getName() + "(" + i.getName() + ")");
                    deep += 1;
                    travelComponent(viewPanel);
                    deep -= 1;
                }
                case "DiyJComboBox", "JList" ->
                        JrcetComponentList.add(String.join("", Collections.nCopies(deep, "    ")) + i.getName());
            }
        }
    }



    public static String getContent(String text) {
        return Helper.isFile(text)?Helper.readFile(text):text;
    }



    //实现类似Tree命令效果的图形显示
    public static ArrayList<String> treeComponent(ArrayList<String> targetList){

        ArrayList<String> JrcetTreeList =new ArrayList<>();
        for(int i=0; i<=targetList.size()-1;i++){
            int j = i+1;
            int nowLineTabCount = CharCount(targetList.get(i), " ");

            if(i==targetList.size()-1){
                JrcetTreeList.add(Pattern.compile(" ([^ ]*)$").matcher(targetList.get(i)).replaceAll(" └─$1"));
            }

            while(j<targetList.size()) {
                int tmpTabCount = CharCount(targetList.get(j)," ");
                if(nowLineTabCount < tmpTabCount &&  j==targetList.size()-1){
                    JrcetTreeList.add(Pattern.compile(" ([^ ]*)$").matcher(targetList.get(i)).replaceAll(" └─$1"));
                    break;
                }
                if(nowLineTabCount > tmpTabCount) {
                    JrcetTreeList.add(Pattern.compile(" ([^ ]*)$").matcher(targetList.get(i)).replaceAll(" └─$1"));
                    break;
                }

                if( nowLineTabCount == tmpTabCount ) {
                    JrcetTreeList.add(Pattern.compile(" ([^ ]*)$").matcher(targetList.get(i)).replaceAll(" ├─$1"));
                    break;
                }
                j++;
            }
        }

        for (int i=0; i<JrcetTreeList.size()-1;i++){
            int j = i+1;
            int pos = JrcetTreeList.get(i).indexOf("├");
            while (j<JrcetTreeList.size()-1){

                if(j==1 || JrcetTreeList.get(j).indexOf("│") == pos || JrcetTreeList.get(j).indexOf("└") == pos){
                    break;
                }

                if(JrcetTreeList.get(j).indexOf("├") == pos){
                    j=j+1;
                    continue;
                }

                StringBuilder lineBuilder = new StringBuilder(JrcetTreeList.get(j));
                if(!Objects.equals(JrcetTreeList.get(j), " ")){
                    JrcetTreeList.set(j,lineBuilder.replace(pos,pos+1,"│").toString());
                }
                j=j+1;
            }
        }

        return JrcetTreeList;
    }

    /*
    字符操作
     */

    public static byte[] getByteByType(String text, String type){
        return switch (type){
            case "Base64" -> Helper.base64Decode2Byte(text);
            case "Hex" -> Helper.hexStringToByteArray(text);
            default -> text.getBytes(StandardCharsets.ISO_8859_1);
        };
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        try {
            for (int i = 0; i < len; i += 2) {
                data[i/2] = (byte) (
                        ( Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16)
                ) ;
            }
        } catch (Exception ignored) {

        }
        return data;
    }

    public static String listToStringByN(List<String> list){
        StringBuilder textContentBuilder = new StringBuilder();
        for(int i=0;i<list.size();i++){
            textContentBuilder.append(list.get(i));
            if(i!=list.size()-1){
                textContentBuilder.append("\n");
            }
        }

        return textContentBuilder.toString();
    }




    /*
    是否为合法域名
     */
    public static boolean isDomain(String str) {
        String regex = "^((?!-)[A-Za-z0-9-]"
                + "{1,63}(?<!-)\\.)"
                + "+[A-Za-z]{2,6}";

        Pattern p = Pattern.compile(regex);

        if (str == null) {
            return false;
        }
        Matcher m = p.matcher(str);
        return m.matches();
    }


    /*
    是否合法url
     */
    public static Boolean isURL(String s){
        try{
            URL url = new URL(s);
            if(url.getProtocol().startsWith("http") && (isDomain(url.getHost()) || isIpAddress(url.getHost()) )){
                return true;
            }
        }catch (Exception e){
            return false;
        }
        return false;
    }

    /*
    是否合法IP
     */
    public static Boolean isIpAddress(String s){

        String regex = "(((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(s);
        return m.matches();

    }
    /*
    判断是否全为数字
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
    /*
    文件类函数
     */
    public static int CharCount(String srcText, String findText) {
        int count = 0;
        Pattern p = Pattern.compile(findText);
        Matcher m = p.matcher(srcText);
        while (m.find()) {
            count++;
        }
        return count;
    }


    //读取文件并返回字符串
    public static String readFile(String filename) {

        StringBuilder content = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8));
            String line = "";
            while ((line = in.readLine()) != null) {
                content.append(line).append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public static void appendWriteFile(String filename, String fileContent){
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(filename,true));
            out.write(fileContent);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile(String filename, int maxLength){
        StringBuilder content = new StringBuilder();
        try {
            FileInputStream fis = new FileInputStream(filename);
            if(fis.available()>maxLength) return "文件太大";
            BufferedReader in = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8));
            String line = "";
            while ((line = in.readLine()) != null) {
                content.append(line).append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }


    public static String topUpper(String word){
        char[] charArray = word.toCharArray();
        charArray[0]-=32;
        return String.valueOf(charArray);
    }

    public static String readStream(InputStream in) {
        try {
            //<1>创建字节数组输出流，用来输出读取到的内容
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //<2>创建缓存大小
            byte[] buffer = new byte[1024]; // 1KB
            //每次读取到内容的长度
            int len = -1;
            //<3>开始读取输入流中的内容
            while ((len = in.read(buffer)) != -1) { //当等于-1说明没有数据可以读取了
                baos.write(buffer, 0, len);   //把读取到的内容写到输出流中
            }
            //<4> 把字节数组转换为字符串
            String content = baos.toString();
            //<5>关闭输入流和输出流
            in.close();
            baos.close();
            //<6>返回字符串结果
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static boolean isFile(String filename){
        File file = new File(filename);

        return file.isFile();
    }

    public static boolean isDict(String filename){
        File file = new File(filename);

        return file.isDirectory();
    }

    public static void setConstraints(HashMap<String,GridBagConstraints> tHasMap, JComponent pComponent, JComponent tComponent, GridBagConstraints tGBC){
        tHasMap.put(tComponent.getName(), tGBC);
        pComponent.add(tComponent, tGBC);
    }

    public static JComponent blackPanel(){
        JPanel blackPanel = new JPanel();
        blackPanel.setName("HelperBlackPanel");
        blackPanel.setPreferredSize(new Dimension(0,0));

        return blackPanel;
    }

    public static JComponent blackPanel(Color color){
        JPanel blackPanel = new JPanel();
        blackPanel.setName("HelperBlackPanel");
        blackPanel.setBackground(color);
        blackPanel.setPreferredSize(new Dimension(0,0));
        return blackPanel;
    }

    public static String list2String(List<String> list, String separator){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i<list.size()-1; i++){
            stringBuilder.append(list.get(i)).append(separator);
        }
        stringBuilder.append(list.get(list.size()-1));

        return stringBuilder.toString();
    }

    /*
        创建随机字符串（小写）
     */
    public static String createRandomString(int length){

        String str = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder stringBuffer = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int number = random.nextInt(26);
            stringBuffer.append(str.charAt(number));
        }

        return stringBuffer.toString();
    }

    /*
        睡眠
     */
    public static void sleep(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    /*
    获取当前时间戳
     */
    public static String getTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());
    }

    /*
    获得粘贴板内容
     */
    public static String getSysClipboardText() {
        String ret = "";
        Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
        // 获取剪切板中的内容
        Transferable clipTf = sysClip.getContents(null);

        if (clipTf != null) {
            // 检查内容是否是文本类型
            if (clipTf.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    ret = (String) clipTf
                            .getTransferData(DataFlavor.stringFlavor);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return ret;
    }



    public static ImageIcon byte2img(byte[] img) {
        InputStream buffin = new ByteArrayInputStream(img);
        Image image = null;
        ImageIcon icon = null;
        try {
            image = ImageIO.read(buffin);
            icon = new ImageIcon(image);
        } catch (IOException e) {

        }
        return icon;
    }

    public static String trimStart(String str) {
        if (Objects.equals(str, "") || str == null) {
            return str;
        }

        final char[] value = str.toCharArray();
        int start = 0;
        int end = str.length();
        while ((start <= end) && (value[start] <= ' ')) {
            start++;
        }
        if (start == 0) {
            return str;
        }
        if (start >= end) {
            return "";
        }
        return str.substring(start, end);
    }

    public static String base64Encode2String(byte[] byteArray){
        return Base64.getEncoder().encodeToString(byteArray);
    }

    public static byte[] base64Encode2Byte(byte[] byteArray){
        return Base64.getEncoder().encode(byteArray);
    }

    public static String base64Encode2String(String str){
        return Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] base64Encode2Byte(String str){
        return Base64.getEncoder().encode(str.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] base64Decode2Byte(String str){
        return Base64.getDecoder().decode(str);
    }

    public static String base64Decode2String(String str){
        return new String(Base64.getDecoder().decode(str),StandardCharsets.UTF_8);
    }

    public static String base64UrlEncode2String(String str){
        return new String(Base64.getEncoder().encode(str.getBytes(StandardCharsets.UTF_8)),StandardCharsets.UTF_8).replace("=","").replace("+","-").replace("/","_");
    }

    public static String base64UrlEncode2String(byte[] str){
        return new String(Base64.getEncoder().encode(str),StandardCharsets.UTF_8).replace("=","").replace("+","-").replace("/","_");
    }

    public static String base64UrlDecode2String(String str){
        return new String(Base64.getDecoder().decode(str.replace("-","+").replace("_","/")),StandardCharsets.UTF_8);
    }

    public static byte[] base64UrlDecode2Byte(String str){
        return Base64.getDecoder().decode(str.replace("-","+").replace("_","/"));
    }


    public static boolean notAndDeleteZip(File file) {

        if(file == null){
            return false;
        }

        if (file.isDirectory()) {
            return false;
        }

        boolean isArchive = false;
        InputStream input = null;
        try {
            input = new FileInputStream(file);
            byte[] buffer = new byte[4];
            int length = input.read(buffer, 0, 4);
            if (length == 4) {
                isArchive = (Arrays.equals(new byte[] { 80, 75, 3, 4 }, buffer)) || (Arrays.equals(new byte[] { 80, 75, 5, 6 }, buffer));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }
        if(isArchive){
            file.delete();
        }
        return  isArchive;
    }
    public static String matchByRegular(String str, String reg){
        String res = "";
        Pattern r = Pattern.compile(reg, Pattern.DOTALL);
        Matcher m = r.matcher(str);
        if (m.find()) {
            res = m.group(1);//0会获取多余的内容
        }
        return res;
    }

    public static String matchByRegular(String str,String reg,int n){
        String res = "";
        Pattern r = Pattern.compile(reg,Pattern.MULTILINE);
        Matcher m = r.matcher(str);
        if (m.find()) {
            res = m.group(n);//0会获取多余的内容
        }
        return res;
    }

    public static String URLEncode(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {

        }
        return result;
    }

    public static String URLDecode(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {

        }
        return result;
    }

    public static String firstWordUp(String name){
        char[] cs=name.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
    }
    public static boolean isBase64(String str) {
        String base64Pattern = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
        return Pattern.matches(base64Pattern, str);
    }
    public static boolean isHexString(String str) {
        char[] hexChar = str.toCharArray();
        for(char c : hexChar){
            if(!Character.toString(c).matches("[0-9A-Fa-f]")){
                return false;
            }
        }
        return true;
    }

    public static String aiFindBase64(String reg,String str){

        Pattern r = Pattern.compile(reg, Pattern.DOTALL);
        Matcher m = r.matcher(str);
        String res = "";
        String tmp;
        while (m.find()){
            tmp = m.group(1);
            if(tmp.length()>res.length()){
                res = tmp;
            }
        }
        return res;
    }

    public static boolean isUrlBase64(String str) {
        String base64Pattern = "^([A-Za-z0-9\\-\\_]{4})*[A-Za-z0-9\\-\\_]{0,3}$";
        return Pattern.matches(base64Pattern, str);
    }

    public static DiyJTextAreaScrollPane createDiyJTextAreaScrollPane(String name){
        return new DiyJTextAreaScrollPane(name);
    }

    public static String getEncoding(String str) {
        String encode = "UTF-8";
        try {
            if (isEncoding(str, encode)) { // 判断是不是UTF-8
                return encode;
            }
        } catch (Exception exception2) {
        }
        encode = "GB2312";
        try {
            if (isEncoding(str, encode)) { // 判断是不是GB2312
                return encode;
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (isEncoding(str, encode)) { // 判断是不是ISO-8859-1
                return encode;
            }
        } catch (Exception exception1) {
        }

        encode = "GBK";
        try {
            if (isEncoding(str, encode)) { // 判断是不是GBK
                return encode;
            }
        } catch (Exception exception3) {
        }
        return ""; // 如果都不是，说明输入的内容不属于常见的编码格式。
    }

    public static boolean isEncoding(String str, String encode) {
        try {
            if (str.equals(new String(str.getBytes(), encode))) {
                return true;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String byte2hex(byte[] bytes) {
        char[] HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        final int nBytes = bytes.length;
        char[] result = new char[2 * nBytes];         //  1 hex contains two chars
        //  hex = [0-f][0-f], e.g 0f or ff

        int j = 0;
        for (byte aByte : bytes) {                    // loop byte by byte

            // 0xF0 = FFFF 0000
            result[j++] = HEX[(0xF0 & aByte) >>> 4];    // get the top 4 bits, first half hex char

            // 0x0F = 0000 FFFF
            result[j++] = HEX[(0x0F & aByte)];          // get the bottom 4 bits, second half hex char

            // combine first and second half, we get a complete hex
        }

        return new String(result);
    }

}
