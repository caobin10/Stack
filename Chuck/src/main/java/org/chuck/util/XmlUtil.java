package org.chuck.util;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Administrator on 15-12-2.
 */
public class XmlUtil {
    private static DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
    private static XmlMapper mapper=new XmlMapper();
    static {
        mapper.setDateFormat(dateFormat);
    }
//
//    public static <T> T pullParseToObj(Reader reader,Class<T> cls) {
//        T obj = null;
//        try {
//            obj=mapper.readValue(reader,cls);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return obj;
//    }
//    public static <T> List<T> pullParseToObjs(InputStream is,Charset charset,Class<T> cls) {
//        Reader reader=new InputStreamReader(is, charset);
//        return pullParseToObjs(reader,cls);
//    }
//
//    public static <T> List<T> pullParseToObjs(Reader reader,Class<T> cls) {
//        List<T> objs = null;
//        try {
//            objs=mapper.readValue(reader,mapper.getTypeFactory().constructCollectionType(List.class, cls));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return objs;
//    }
//    public static void pullSerialize(){
//
//    }
}
