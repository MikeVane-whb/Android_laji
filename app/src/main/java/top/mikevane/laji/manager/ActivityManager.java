package top.mikevane.laji.manager;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 控制活动行为，主要用来批量关闭 activity
 * @Author mikevane
 * @Date 19:00
 * @Version 1.0
 */
public class ActivityManager {

    public static List<Activity> activities = new ArrayList<>();

    /**
     * 向 List 中添加活动
     * @param activity
     */
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 从List中移除活动
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * 将List中存储的活动全部销毁掉
     */
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activities.clear();
    }

}
