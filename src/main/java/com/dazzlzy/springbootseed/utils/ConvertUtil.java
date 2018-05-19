package com.dazzlzy.springbootseed.utils;

import com.dazzlzy.springbootseed.support.IConvertor;
import org.nutz.lang.Lang;

import java.util.*;

/**
 * 转换器工具类
 *
 * @author dazzlzy
 * @date 2018/5/9
 */
public class ConvertUtil {

    /**
     * 将来源collection转换成目标list.
     *
     * @param sources          来源
     * @param convertor        转换器
     * @param ignoreNull       是否忽略空值
     * @param duplicateRemoval 是否去除重复
     * @param <Source>         来源
     * @param <Target>         目标对象
     * @return 转换后的List
     */
    public static <Source, Target> List<Target> convert(Collection<Source> sources, IConvertor<Source, Target> convertor,
                                                        boolean ignoreNull, boolean duplicateRemoval) {
        // 返回目标值
        Collection<Target> targets;

        //去重使用HashSet，不去冲使用ArrayList
        targets = duplicateRemoval ? new HashSet<>() : new ArrayList<>();

        // 来源不为空
        if (sources != null && sources.size() > 0) {
            for (Source source : sources) {
                Target target = convertor.convert(source);
                // 不忽略空值或者目标不为空
                if (!ignoreNull || target != null) {
                    // 添加目标
                    targets.add(convertor.convert(source));
                }
            }
        }
        // 转换成list
        return Lang.collection2list(targets);
    }

    /**
     * 将来源collection转换成目标list.
     *
     * @param sources   来源
     * @param convertor 转换器
     * @param <Source>  来源
     * @param <Target>  目标对象
     * @return 转换后的List
     */
    public static <Source, Target> List<Target> convert(Collection<Source> sources, IConvertor<Source, Target> convertor) {
        return convert(sources, convertor, false, false);
    }

    /**
     * 将来源按转换器的方式转换成Map<Key, Obj>的方式.
     *
     * @param sources   来源
     * @param convertor 转换器
     * @param <Key>     返回的Map的key的类型
     * @param <Obj>     返回的Map的value的类型，即来源集合中的类型
     * @return 转换后的对象类型
     */
    public static <Key, Obj> Map<Key, Obj> map(Collection<Obj> sources, IConvertor<Obj, Key> convertor) {
        if (sources == null) {
            return new HashMap<>(16);
        }
        Map<Key, Obj> mapping = new HashMap<>(sources.size());
        // 来源不为空
        if (sources.size() > 0) {
            for (Obj obj : sources) {
                Key key = convertor.convert(obj);
                mapping.put(key, obj);
            }
        }
        return mapping;
    }

    /**
     * 将来源按转换器的方式转换成Map<Key,List<Obj>>的方式.
     *
     * @param sources   来源
     * @param convertor 转换器
     * @param <Key>     返回的Map的key的类型
     * @param <Obj>     返回的Map的value的List包含类型
     * @return 转换后的对象类型
     */
    public static <Key, Obj> Map<Key, List<Obj>> maplist(Collection<Obj> sources, IConvertor<Obj, Key> convertor) {
        Map<Key, List<Obj>> mapping = new HashMap<>(16);
        // 来源不为空
        if (sources != null && sources.size() > 0) {
            for (Obj obj : sources) {
                Key key = convertor.convert(obj);
                List<Obj> list = mapping.get(key);

                if (list == null) {
                    mapping.put(key, Lang.list(obj));
                } else {
                    list.add(obj);
                }
            }
        }
        return mapping;
    }

}
