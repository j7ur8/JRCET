package ui;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;
import jrcet.frame.tools.Dencrypt.Aes.Aes;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import static jrcet.frame.tools.Dencrypt.Base.Base.b64decoder;
import static jrcet.frame.tools.Dencrypt.Base.Base.b64encoder;
import static jrcet.frame.tools.Dencrypt.Unicode.Unicode.unicodeToString;

public class test {

    public static void main(String[] args) throws Exception {
        String imgBase64 = "iVBORw0KGgoAAAANSUhEUgAAAG4AAAAoCAMAAAAohD+4AAAAP1BMVEUAAADdGW3lIXXcGGzWEmbTD2PEAFTdGW3NCV3VEWXfG2/HA1feGm7FAVXiHnLNCV3eGm7hHXHWEmbUEGTkIHRwBsdLAAAAAXRSTlMAQObYZgAAAVJJREFUeJzsl21zgyAMgLlbYREc3gb//7fuGFZ5SQJtcX6oz4diEXkSIvQqLk5Gnx3AxdugAmcHgaP+NTb1RXKELSYWG+ccOdB7P0S3t87RPu8JH1ULpbgqbboZh9JRsypWp6rsiuGELqtF3S9w3Xo3sa2XmQ99MGmNMWg/YUvYEm1sEZUsmjG7j9MhK8y9NS/qsHr268LnRwDXLRXoevXZ8lCz2sV+AAjNjc2N7sbGoS987ARYfdXwZPx3m3oCNI4uHT/PI1S6ks8W1IPW2v2LlDKG3LA9jbWJTwaItQmD9rNRVqflKzozKpu27ijT5rtfzjNjW5alc8ben/d7DQDgp0D8HSztKdA38sbq0F3Qp2N2AO4rN104VPt1eHY8qS4e4uKh2j3hE5UOQevR/8E4ndaH+Khbw3RQwOh8DyNiSms3jZiuTJJMdJp4328AAAD//zRYHkIKs2aeAAAAAElFTkSuQmCC";

        byte[] ss = b64decoder.decode(imgBase64);

        OutputStream out = new FileOutputStream("/Users/j7ur8/Desktop/aa.jpg");
        out.write(ss);
        out.flush();
        out.close();


        OrtEnvironment ortEnv = OrtEnvironment.getEnvironment();
        OrtSession ortSession = ortEnv.createSession("/Users/j7ur8/.local/lib/python3.9/site-packages/ddddocr/common.onnx", new OrtSession.SessionOptions());
        OnnxTensor t1 = OnnxTensor.createTensor(ortEnv, ss);
        OrtSession.Result result = ortSession.run(Collections.singletonMap("input1", t1));

        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i).getValue());
        }

        result.close();
        t1.close();

    }
}

