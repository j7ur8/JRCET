package burp;

import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.scanner.ConsolidationAction;
import burp.api.montoya.scanner.ScanCheck;
import burp.api.montoya.scanner.audit.insertionpoint.AuditInsertionPoint;
import burp.api.montoya.scanner.audit.issues.AuditIssue;
import jrcet.frame.Scanner.Fastjson.Fastjson;
import jrcet.frame.Scanner.Springboot.Springboot;

import java.util.List;

public class MyScanCheck implements ScanCheck {
    @Override
    public List<AuditIssue> activeAudit(HttpRequestResponse baseRequestResponse, AuditInsertionPoint auditInsertionPoint) {
//        Fastjson.doScan(baseRequestResponse);
        return null;
    }

    @Override
    public List<AuditIssue> passiveAudit(HttpRequestResponse baseRequestResponse) {
        Springboot.doScan(baseRequestResponse);
        Fastjson.doScan(baseRequestResponse);
        return null;
    }

    @Override
    public ConsolidationAction consolidateIssues(AuditIssue newIssue, AuditIssue existingIssue) {
        return null;
    }
}
