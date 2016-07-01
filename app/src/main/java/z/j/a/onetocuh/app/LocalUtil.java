package z.j.a.onetocuh.app;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lumia on 2016-07-01.
 */
public class LocalUtil {
    private static Map<String,Integer[]> local;

    public static Map<String, Integer[]> getLocal() {
        return local;
    }

    public static void setLocal(Map<String, Integer[]> local) {
        if(local==null){
            LocalUtil.local = local;
        }else{
            local.putAll(local);
        }

    }
    public static void setLocal(String key,Integer[] xy) {
        if(local==null){
            LocalUtil.local = new HashMap<>();
        }
        local.put(key,xy);
    }
}
