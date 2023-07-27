package jrcet.frame.Setting;

import java.awt.*;
import java.awt.datatransfer.Clipboard;

public class Setting {

    public static Color gray =new Color(203,208,209);
    public static Color orange =new Color(255,102,51);


    // 可选 Tools,Exploit,Asset
    public static String JrcetShowPanel = "Scanner";


    // 可选 Intruder, Scanner, Dominate, Password, Dencrypt, RScript, HText， Captcha
    public static String ToolsShowPanel = "Password";

    // 可选 Alone, Sort, Parsepy, Case, Format, IPUnit, Regex, Len
    public static String HTextShowPanel = "Alone";

    public static String DencryptShowPanel = "Aes";

    public static boolean DEBUG = false;

    public static Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    public static String ScannerShowPanel = "Overauth";
}
