package jrcet.frame.tools.Scanner.Fastjson;

import burp.IHttpRequestResponse;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Fastjson {
    public final static List<FastjsonLogEntry> FastjsonLogEntries= new ArrayList<>();

    public static class FastjsonLogEntry{
        final String url;
        final String status;
        final String result;
        final String method;
        final String payload;
        final IHttpRequestResponse requestResponse;

        public FastjsonLogEntry(String url, String method, String status, String payload, String result, IHttpRequestResponse requestResponse) {
            this.url = url;
            this.method = method;
            this.status = status;
            this.payload = payload;
            this.result = result;
            this.requestResponse = requestResponse;
        }

        public String url(){
            return this.url;
        }

        public String method(){
            return this.method;
        }

        public String status(){
            return this.status;
        }

        public String payload(){
            return this.payload;
        }

        public String result(){
            return this.result;
        }

        public IHttpRequestResponse requestResponse(){
            return this.requestResponse;
        }
    }

}
