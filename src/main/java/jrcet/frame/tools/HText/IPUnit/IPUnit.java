package jrcet.frame.tools.HText.IPUnit;

import jrcet.help.Helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class IPUnit {

    public static String IPUnitMode = "打印IP段内的全部IP";

    public static String handle(String input){
        input = Helper.getContent(input);
        switch (IPUnitMode) {
            case "打印IP段内的全部IP":
                return Helper.list2String(parseIpMaskRange(input),"\n");
            case "判断IP是否属于IP段":
                return isInRange(input);
        }

        return "系统错误";
    }


    public static String isInRange(String input) {
        String[] ipAndcidr = input.split(" ");
        if(ipAndcidr.length>2) return "输入不符合规范";
        String ip = ipAndcidr[0];
        String cidr = ipAndcidr[1];
        String[] ips = ip.split("\\.");
        int ipAddr = (Integer.parseInt(ips[0]) << 24)
                | (Integer.parseInt(ips[1]) << 16)
                | (Integer.parseInt(ips[2]) << 8) | Integer.parseInt(ips[3]);
        String[] ipSplit = cidr.split("/");
        int type = Integer.parseInt(ipSplit[1]);
        int mask = 0xFFFFFFFF << (32 - type);
        String cidrIp = ipSplit[0];
        String[] cidrIps = cidrIp.split("\\.");
        int cidrIpAddr = (Integer.parseInt(cidrIps[0]) << 24)
                | (Integer.parseInt(cidrIps[1]) << 16)
                | (Integer.parseInt(cidrIps[2]) << 8)
                | Integer.parseInt(cidrIps[3]);

        if((ipAddr & mask) == (cidrIpAddr & mask)){
            return ip+" 属于 "+cidr;
        }else{
            return ip+" 不属于 "+cidr;
        }
    }

    public static boolean isIP(String str) {
        String regex = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(str).matches();
    }

    public static List<String> parseIpMaskRange(String ipSegema){
        String[] ipAndmask = ipSegema.split("/");
        String ip=ipAndmask[0];
        if(!isIP(ip) || ipAndmask.length!=2) return Collections.singletonList("输入错误");
        String mask=ipAndmask[1];
        List<String> list=new ArrayList<>();
        if ("32".equals(mask)) {
            list.add(ip);
        }else{
            String startIp=getBeginIpStr(ip, mask);
            String endIp=getEndIpStr(ip, mask);
            if (!"31".equals(mask)) {
                String subStart=startIp.split("\\.")[0]+"."+startIp.split("\\.")[1]+"."+startIp.split("\\.")[2]+".";
                String subEnd=endIp.split("\\.")[0]+"."+endIp.split("\\.")[1]+"."+endIp.split("\\.")[2]+".";
                startIp=subStart+(Integer.parseInt(startIp.split("\\.")[3])+1);
                endIp=subEnd+(Integer.parseInt(endIp.split("\\.")[3])-1);
            }
            list=parseIpRange(startIp, endIp);
        }
        return list;
    }

    public static int getIpCount(String mask) {
        return BigDecimal.valueOf(Math.pow(2, 32 - Integer.parseInt(mask))).setScale(0, RoundingMode.DOWN).intValue();//IP总数，去小数点
    }

    public static List<String> parseIpRange(String ipfrom, String ipto) {
        List<String> ips = new ArrayList<String>();
        String[] ipfromd = ipfrom.split("\\.");
        String[] iptod = ipto.split("\\.");
        int[] int_ipf = new int[4];
        int[] int_ipt = new int[4];
        for (int i = 0; i < 4; i++) {
            int_ipf[i] = Integer.parseInt(ipfromd[i]);
            int_ipt[i] = Integer.parseInt(iptod[i]);
        }
        for (int A = int_ipf[0]; A <= int_ipt[0]; A++) {
            for (int B = (A == int_ipf[0] ? int_ipf[1] : 0); B <= (A == int_ipt[0] ? int_ipt[1]
                    : 255); B++) {
                for (int C = (B == int_ipf[1] ? int_ipf[2] : 0); C <= (B == int_ipt[1] ? int_ipt[2]
                        : 255); C++) {
                    for (int D = (C == int_ipf[2] ? int_ipf[3] : 0); D <= (C == int_ipt[2] ? int_ipt[3]
                            : 255); D++) {
                        ips.add(A + "." + B + "." + C + "." + D);
                    }
                }
            }
        }
        return ips;
    }


    public static String getIpFromLong(Long ip)
    {
        String s1 = String.valueOf((ip & 4278190080L) / 16777216L);
        String s2 = String.valueOf((ip & 16711680L) / 65536L);
        String s3 = String.valueOf((ip & 65280L) / 256L);
        String s4 = String.valueOf(ip & 255L);
        return s1 + "." + s2 + "." + s3 + "." + s4;
    }


    public static Long getIpFromString(String ip)
    {
        long ipLong = 0L;
        String ipTemp = ip;
        ipLong = Long.parseLong(ipTemp.substring(0, ipTemp.indexOf('.')));
        ipTemp = ipTemp.substring(ipTemp.indexOf('.') + 1);
        ipLong = ipLong * 256
                + Long.parseLong(ipTemp.substring(0, ipTemp.indexOf('.')));
        ipTemp = ipTemp.substring(ipTemp.indexOf(".") + 1);
        ipLong = ipLong * 256
                + Long.parseLong(ipTemp.substring(0, ipTemp.indexOf('.')));
        ipTemp = ipTemp.substring(ipTemp.indexOf('.') + 1);
        ipLong = ipLong * 256 + Long.parseLong(ipTemp);
        return ipLong;
    }

    public static String getMaskByMaskBit(String maskBit)
    {
        return "".equals(maskBit) ? "error, maskBit is null !" : getMaskMap(maskBit);
    }


    public static String getBeginIpStr(String ip, String maskBit)
    {
        return getIpFromLong(getBeginIpLong(ip, maskBit));
    }

    public static Long getBeginIpLong(String ip, String maskBit)
    {
        return getIpFromString(ip) & getIpFromString(getMaskByMaskBit(maskBit));
    }

    public static String getEndIpStr(String ip, String maskBit)
    {
        return getIpFromLong(getEndIpLong(ip, maskBit));
    }

    public static Long getEndIpLong(String ip, String maskBit)
    {
        return getBeginIpLong(ip, maskBit)
                + ~getIpFromString(getMaskByMaskBit(maskBit));
    }


    public static int getNetMask(String netmarks)
    {
        StringBuilder sbf;
        String str;
        int inetmask = 0;
        int count = 0;
        String[] ipList = netmarks.split("\\.");
        for (String s : ipList) {
            sbf = toBin(Integer.parseInt(s));
            str = sbf.reverse().toString();
            count = 0;
            for (int i = 0; i < str.length(); i++) {
                i = str.indexOf('1', i);
                if (i == -1) {
                    break;
                }
                count++;
            }
            inetmask += count;
        }
        return inetmask;
    }

    public static int getPoolMax(int maskBit)
    {
        if (maskBit <= 0 || maskBit >= 32)
        {
            return 0;
        }
        return (int) Math.pow(2, 32 - maskBit) - 2;
    }

    private static StringBuilder toBin(int x)
    {
        StringBuilder result = new StringBuilder();
        result.append(x % 2);
        x /= 2;
        while (x > 0)
        {
            result.append(x % 2);
            x /= 2;
        }
        return result;
    }

    public static String getMaskMap(String maskBit) {
        if ("1".equals(maskBit)) return "128.0.0.0";
        if ("2".equals(maskBit)) return "192.0.0.0";
        if ("3".equals(maskBit)) return "224.0.0.0";
        if ("4".equals(maskBit)) return "240.0.0.0";
        if ("5".equals(maskBit)) return "248.0.0.0";
        if ("6".equals(maskBit)) return "252.0.0.0";
        if ("7".equals(maskBit)) return "254.0.0.0";
        if ("8".equals(maskBit)) return "255.0.0.0";
        if ("9".equals(maskBit)) return "255.128.0.0";
        if ("10".equals(maskBit)) return "255.192.0.0";
        if ("11".equals(maskBit)) return "255.224.0.0";
        if ("12".equals(maskBit)) return "255.240.0.0";
        if ("13".equals(maskBit)) return "255.248.0.0";
        if ("14".equals(maskBit)) return "255.252.0.0";
        if ("15".equals(maskBit)) return "255.254.0.0";
        if ("16".equals(maskBit)) return "255.255.0.0";
        if ("17".equals(maskBit)) return "255.255.128.0";
        if ("18".equals(maskBit)) return "255.255.192.0";
        if ("19".equals(maskBit)) return "255.255.224.0";
        if ("20".equals(maskBit)) return "255.255.240.0";
        if ("21".equals(maskBit)) return "255.255.248.0";
        if ("22".equals(maskBit)) return "255.255.252.0";
        if ("23".equals(maskBit)) return "255.255.254.0";
        if ("24".equals(maskBit)) return "255.255.255.0";
        if ("25".equals(maskBit)) return "255.255.255.128";
        if ("26".equals(maskBit)) return "255.255.255.192";
        if ("27".equals(maskBit)) return "255.255.255.224";
        if ("28".equals(maskBit)) return "255.255.255.240";
        if ("29".equals(maskBit)) return "255.255.255.248";
        if ("30".equals(maskBit)) return "255.255.255.252";
        if ("31".equals(maskBit)) return "255.255.255.254";
        if ("32".equals(maskBit)) return "255.255.255.255";
        return "-1";
    }

    public static double ipToDouble(String ip) {
        String[] arr = ip.split("\\.");
        double d1 = Double.parseDouble(arr[0]);
        double d2 = Double.parseDouble(arr[1]);
        double d3 = Double.parseDouble(arr[2]);
        double d4 = Double.parseDouble(arr[3]);
        return d1 * Math.pow(256, 3) + d2 * Math.pow(256, 2) + d3 * 256 + d4;
    }

}
