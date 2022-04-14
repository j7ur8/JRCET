package ui;

import jrcet.frame.tools.Dencrypt.Aes.Aes;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import static jrcet.frame.tools.Dencrypt.Base.Base.b64decoder;
import static jrcet.frame.tools.Dencrypt.Base.Base.b64encoder;
import static jrcet.frame.tools.Dencrypt.Unicode.Unicode.unicodeToString;

public class test {

    public static void main(String[] args) throws Exception {
        System.out.println(new String(b64decoder.decode("5Lit5Zu9"), StandardCharsets.UTF_8));
    }
}

