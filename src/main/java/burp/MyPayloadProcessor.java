package burp;

import burp.api.montoya.core.ByteArray;
import burp.api.montoya.intruder.IntruderInsertionPoint;
import burp.api.montoya.intruder.PayloadData;
import burp.api.montoya.intruder.PayloadProcessingResult;
import burp.api.montoya.intruder.PayloadProcessor;
import jrcet.frame.Intruder.Intruder;


public class MyPayloadProcessor implements PayloadProcessor {
    @Override
    public String displayName() {
        return "JIntruder";
    }

    @Override
    public PayloadProcessingResult processPayload(PayloadData payloadData) {
        String newPayload = "";
        newPayload = Intruder.invokeDiy(payloadData.currentPayload().getBytes());
        return PayloadProcessingResult.usePayload(ByteArray.byteArray(newPayload));
    }


}
