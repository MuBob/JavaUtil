package work;

import javafx.beans.DefaultProperty;

import javax.xml.transform.Source;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ReadState {
    /**
     * 初始化状态
     */
    public static final int STATE_DEFAULT = 0;
    /**
     * 从总文件过滤写入线程持有文件中
     */
    public static final int STATE_DO_MYSELF = 1;
    /**
     * 将线程文件写入父线程文件中
     */
    public static final int STATE_POST_PARENT = 2;
    /**
     * 线程文件已写入父线程文件，等待接收子线程文件
     */
    public static final int STATE_EMPTY_WAIT_CHILD = 3;
    /**
     * 线程文件等待写入父线程文件，等待接收子线程文件
     */
    public static final int STATE_FULL_WAIT_CHILD = 4;
    /**
     * 线程文件等待写入父线程文件，接收子线程文件中
     */
    public static final int STATE_FULL_RECEIVE_CHILD = 5;

    public static final int STATE_FULL_RECEIVE_CHILD_END = 6;


    @IntDef({
            STATE_DEFAULT,
            STATE_DO_MYSELF,
            STATE_POST_PARENT,
            STATE_EMPTY_WAIT_CHILD,
            STATE_FULL_WAIT_CHILD,
            STATE_FULL_RECEIVE_CHILD,
            STATE_FULL_RECEIVE_CHILD_END
    })
    @interface STATE {
    }

    public static final String getStateStr(@STATE int state) {
        switch (state) {
            case STATE_DEFAULT:
                return "STATE_DEFAULT";
            case STATE_DO_MYSELF:
                return "STATE_DO_MYSELF";
            case STATE_POST_PARENT:
                return "STATE_POST_PARENT";
            case STATE_EMPTY_WAIT_CHILD:
                return "STATE_EMPTY_WAIT_CHILD";
            case STATE_FULL_WAIT_CHILD:
                return "STATE_FULL_WAIT_CHILD";
            case STATE_FULL_RECEIVE_CHILD:
                return "STATE_FULL_RECEIVE_CHILD";
            case STATE_FULL_RECEIVE_CHILD_END:
                return "STATE_FULL_RECEIVE_CHILD_END";
            default:
                return "STATE_UNKNOWN";
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface IntDef {

        long[] value() default {};

        boolean flag() default false;
    }
}
