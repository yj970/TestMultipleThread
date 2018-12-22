package util;

/**
 * Created by yangjie on 2018/12/22.
 */
public class T {
    /**
     * 获取当前线程名
     * @return
     */
    public static String getThreadName() {
        return  Thread.currentThread().getName()+": ";
    }
}
