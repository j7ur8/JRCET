package jrcet.lib;

import java.util.HashMap;
import java.util.Map;

public class Services {

    private static final Map<Object,Object> serviceStream = new HashMap<>();

    public Services(){}

    public static void setService(Object k, Object v){
        serviceStream.put(k,v);
    }

    public static Object getService(Object k, Object v){
        return serviceStream.get(k);
    }

    public static Map<Object, Object> getServices(){
        return serviceStream;
    }
}
