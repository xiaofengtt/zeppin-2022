package com.wangjie.androidbucket.utils.collection;

import com.wangjie.androidbucket.log.Logger;
import com.wangjie.androidbucket.utils.ABTextUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 12/11/14.
 */
public class ABCollectionPicker {
    private static final String TAG = ABCollectionPicker.class.getSimpleName();

    /**
     * 指定摘选规则
     *
     * @param <T>
     */
    public static interface ABICollectionPicker<T> {
        boolean isPicked(T target);
    }

    /**
     * 从collection中摘选满足条件的对象，对应的摘选条件由ABICollectionPicker的isPicked()来制定，只取第一个
     *
     * @param expect
     * @param collection
     * @param <T>
     * @return
     */
    public static <T extends ABICollectionPicker<T>> T pickFirst(T expect, Collection<T> collection) {
        if (null == expect || ABTextUtil.isEmpty(collection)) {
            return null;
        }
        T picked = null;
        for (T t : collection) {
            if (t.isPicked(expect)) {
                picked = t;
                break;
            }
        }
        return picked;
    }

    /**
     * 从collection中摘选满足条件的对象，对应的摘选条件由ABICollectionPicker的isPicked()来制定
     *
     * @param expect
     * @param collection
     * @param <T>
     * @return
     */
    public static <T extends ABICollectionPicker<T>> List<T> pick(T expect, Collection<T> collection) {
        if (null == expect || ABTextUtil.isEmpty(collection)) {
            return null;
        }
        List<T> picked = new ArrayList<>();
        for (T t : collection) {
            if (t.isPicked(expect)) {
                picked.add(t);
            }
        }
        return picked;
    }


    /**
     * 指定摘选规则
     * *** Picker Controller *****
     */
    public static interface ABPickerController<T> {
        boolean isPicked(T expect, T iterTarget);
    }

    public static <T> T pickFirst(T expect, Collection<T> collection, ABPickerController<T> pickerController) {
        if (null == expect || ABTextUtil.isEmpty(collection)) {
            return null;
        }
        T picked = null;
        for (T t : collection) {
            if (pickerController.isPicked(expect, t)) {
                picked = t;
                break;
            }
        }
        return picked;
    }

    public static <T> List<T> pick(T expect, Collection<T> collection, ABPickerController<T> pickerController) {
        if (null == expect || ABTextUtil.isEmpty(collection)) {
            return null;
        }
        if (null == pickerController) {
            Logger.e(TAG, "PickerController can not be null!");
            return null;
        }
        List<T> picked = new ArrayList<>();
        for (T t : collection) {
            if (pickerController.isPicked(expect, t)) {
                picked.add(t);
            }
        }
        return picked;
    }


    /**
     * 可从集合中自定义规则匹配不同类型的对象
     *
     * @param <E>
     * @param <T>
     */
    public static interface ABPickerAnyController<E, T> {
        boolean isPicked(E expect, T iterTarget);
    }

    public static <E, T> T pickAnyFirst(E expect, Collection<T> collection, ABPickerAnyController<E, T> pickerController) {
        if (null == expect || ABTextUtil.isEmpty(collection)) {
            return null;
        }
        T picked = null;
        for (T t : collection) {
            if (pickerController.isPicked(expect, t)) {
                picked = t;
                break;
            }
        }
        return picked;
    }

    public static <E, T> T pickAnyFirstReverse(E expect, List<T> collection, ABPickerAnyController<E, T> pickerController) {
        if (null == expect || ABTextUtil.isEmpty(collection)) {
            return null;
        }
        T picked = null;
        for (int i = collection.size() - 1; i > 0; i--) {
            T t = collection.get(i);
            if (pickerController.isPicked(expect, t)) {
                picked = t;
                break;
            }
        }
        return picked;
    }


    public static <E, T> List<T> pick(E expect, Collection<T> collection, ABPickerAnyController<E, T> pickerController) {
        if (null == expect || ABTextUtil.isEmpty(collection)) {
            return null;
        }
        if (null == pickerController) {
            Logger.e(TAG, "PickerController can not be null!");
            return null;
        }
        List<T> picked = new ArrayList<>();
        for (T t : collection) {
            if (pickerController.isPicked(expect, t)) {
                picked.add(t);
            }
        }
        return picked;
    }

}
