package android.mtsmda.com.application170717.helper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dminzat on 7/18/2017.
 */

public class ListHelper {

    public static final <T>List<T> getList(T ... ts){
        List<T> returnList = new ArrayList<>();
        for(T t : ts){
            returnList.add(t);
        }
        return returnList;
    }

}