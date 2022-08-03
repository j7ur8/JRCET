package burp;

public class CustomIMessageEditorController implements IMessageEditorController{

    public static IHttpRequestResponse currentIHttpRequestResponse;
    @Override
    public IHttpService getHttpService() {
        return currentIHttpRequestResponse.getHttpService();
    }

    @Override
    public byte[] getRequest() {
        return currentIHttpRequestResponse.getRequest();
    }

    @Override
    public byte[] getResponse() {
        return currentIHttpRequestResponse.getResponse();
    }
}
