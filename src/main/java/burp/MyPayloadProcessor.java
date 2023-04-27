package burp;

import burp.api.montoya.intruder.IntruderInsertionPoint;
import burp.api.montoya.intruder.Payload;
import burp.api.montoya.intruder.PayloadProcessingResult;
import burp.api.montoya.intruder.PayloadProcessor;
import jrcet.frame.Intruder.Intruder;


public class MyPayloadProcessor implements PayloadProcessor {
    @Override
    public String displayName() {
        return "JIntruder";
    }

    @Override
    public PayloadProcessingResult processPayload(Payload currentPayload, Payload originalPayload, IntruderInsertionPoint insertionPoint) {
        String newPayload = "";
        newPayload = Intruder.invokeDiy(currentPayload.value());
        return PayloadProcessingResult.usePayload(newPayload.getBytes());
    }
}
