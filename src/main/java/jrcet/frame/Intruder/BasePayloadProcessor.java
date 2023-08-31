package jrcet.frame.Intruder;

import burp.api.montoya.core.ByteArray;
import burp.api.montoya.intruder.PayloadData;
import burp.api.montoya.intruder.PayloadProcessingResult;
import burp.api.montoya.intruder.PayloadProcessor;

public class BasePayloadProcessor implements PayloadProcessor {
    @Override
    public String displayName() {
        return "J-Base";
    }

    @Override
    public PayloadProcessingResult processPayload(PayloadData payloadData) {
        String newPayload = "";
        return PayloadProcessingResult.usePayload(ByteArray.byteArray(newPayload));
    }
}