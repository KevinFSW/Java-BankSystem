package entry;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 信息打包
 * {余额, 币种, ...}
 */
public class DataEntry implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = -3112904228826403763L;
    
    ArrayList<String> data = new ArrayList<>();

    /**
     * 构造方法，打包
     * {余额, 币种, ...}
     * @param x
     */
    public DataEntry(String...x){
        for (String string : x) {
            data.add(string);
        }

        data.trimToSize();
    }

    /**
     * 转成数组
     * @return
     */
    public String[] toStringArray(){
        String[] info = new String[data.size()];
        return data.toArray(info);
    }
}