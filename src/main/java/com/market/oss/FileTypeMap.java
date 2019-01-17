package com.market.oss;

import java.util.HashMap;
import java.util.Map;


/**
 * The type File type map.
 *
 * @author Ruizhi
 * @date 2019.01.17
 */
public class FileTypeMap {

    public static final Map<String, String> map = new HashMap<String, String>(9);
    static {
        map.put("jpeg","FFD8FF");
        map.put("jpg","FFD8FF");
        map.put("png","89504E47");
        map.put("gif","47494638");
        map.put("bmp","424D");
        map.put("wav","57415645");
        map.put("avi","41564920");
        map.put("mp4","00000020667479706D70");
        map.put("mp3","49443303000000002176");
    }


}
