package ui;


import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;
import jrcet.frame.tools.Dencrypt.Base.Base;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static jrcet.frame.tools.Dencrypt.Base.Base.b64decoder;


public class test {

    public static void main(String[] args) throws Exception {
        String aa="var fs = require('fs');\n" +
                "var webserver = require('webserver');\n" +
                "server = webserver.create();\n" +
                "\n" +
                "var logfile = 'jsEncrypter.log';\n" +
                "var host = '127.0.0.1';\n" +
                "var port = '1664';\n" +
                "\n" +
                "/* 1.在这引入实现加密所有js文件,注意引入顺序和网页一致 */\n" +
                "loadScript(\"script-1.js\");\n" +
                "loadScript(\"script-2.js\");\n" +
                "loadScript(\"script-n.js\");\n" +
                "/**********************************************/\n" +
                "\n" +
                "function loadScript(scriptName) {\n" +
                "    var isSuccess = phantom.injectJs(scriptName);\n" +
                "    if(isSuccess){\n" +
                "        console.log(\"[*] load \" + scriptName + \" successful\")\n" +
                "    }else{\n" +
                "        console.log(\"[!] load \" + scriptName + \" fail\")\n" +
                "        console.log(\"[*] phantomjs server exit\");\n" +
                "        phantom.exit();\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "function jsEncrypt(burp_payload){\n" +
                "\tvar new_payload;\n" +
                "\t/* 2.在这里编写调用加密函数进行加密的代码,并把结果赋值给new_payload */\n" +
                "\n" +
                "\t/*********************************************************/\n" +
                "\treturn new_payload;\n" +
                "}\n" +
                "\n" +
                "console.log(\"[*] Phantomjs server for jsEncrypter started successfully!\");\n" +
                "console.log(\"[*] address: http://\"+host+\":\"+port);\n" +
                "console.log(\"[!] ^_^\");\n" +
                "\n" +
                "var service = server.listen(host+':'+port,function(request, response){\n" +
                " \ttry{\n" +
                "\t\tif(request.method == 'POST'){\n" +
                "\t\t\tvar payload = request.post['payload'];\n" +
                "\t\t\tvar encrypt_payload = jsEncrypt(payload);\n" +
                "\t\t\tvar log = payload + ':' + encrypt_payload;\n" +
                "\t\t\tconsole.log('[+] ' + log);\n" +
                "            fs.write(logfile,log + '\\n', 'w+');\n" +
                "\t\t\tresponse.statusCode = 200;\n" +
                "            response.setEncoding('UTF-8');\n" +
                "\t\t\tresponse.write(encrypt_payload.toString());\n" +
                "\t\t\tresponse.close();\n" +
                "\t\t}else{\n" +
                "\t\t\t  response.statusCode = 200;\n" +
                "\t\t\t  response.setEncoding('UTF-8');\n" +
                "\t\t\t  response.write(\"^_^\\n\\rhello jsEncrypter!\");\n" +
                "\t\t\t  response.close();\n" +
                "\t\t}\n" +
                "\t}catch(e){\n" +
                "\t\t//console.log('[Error]'+e.message+' happen '+e.line+'line');\n" +
                "\t\tconsole.log('\\n-----------------Error Info--------------------');\n" +
                "\t\tvar fullMessage = \"Message: \"+e.toString() + ':'+ e.line;\n" +
                "\t\tfor (var p in e) {\n" +
                "\t\t\tfullMessage += \"\\n\" + p.toUpperCase() + \": \" + e[p];\n" +
                "\t\t} \n" +
                "\t\tconsole.log(fullMessage);\n" +
                "\t\tconsole.log('---------------------------------------------');\n" +
                "        response.statusCode = 200;\n" +
                "        response.setEncoding('UTF-8');\n" +
                "        response.write(fullMessage);\n" +
                "        response.close();\n" +
                "\t\tconsole.log('[*] phantomJS exit!');\n" +
                "\t\tphantom.exit();\n" +
                "    }\t\n" +
                "});";
        System.out.println(new String(Base.b64encoder.encode(aa.getBytes()), StandardCharsets.UTF_8));
    }


}

