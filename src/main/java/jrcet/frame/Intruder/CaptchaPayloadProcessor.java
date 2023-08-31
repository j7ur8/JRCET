package jrcet.frame.Intruder;

import burp.api.montoya.core.ByteArray;
import burp.api.montoya.intruder.PayloadData;
import burp.api.montoya.intruder.PayloadProcessingResult;
import burp.api.montoya.intruder.PayloadProcessor;
import jrcet.frame.Tools.Captcha.Captcha;

public class CaptchaPayloadProcessor implements PayloadProcessor {
    @Override
    public String displayName() {
        return "J-Captcha";
    }

    @Override
    public PayloadProcessingResult processPayload(PayloadData payloadData) {

        byte[] newPayload = Captcha.identifyCaptchaForIntruder().getBytes();
        return PayloadProcessingResult.usePayload(ByteArray.byteArray(newPayload));
    }
}
