package com.dazzlzy.common.orika.converter;

import com.alibaba.fastjson.JSON;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;
import org.springframework.stereotype.Component;

/**
 * Json字符串与对象的映射转换
 * 使用方法：
 * mapperFactory.getConverterFactory()
 *              .registerConverter("xxxxConvert", new JsonConfigConvert<T>());
 * mapperFactory.classMap(Source.class, Target.class)
 *              .field("sourceField", TargetField)
 *              .fieldMap("Tfield", "jsonField").converter("xxxxConvert").add()
 *              .register();
 * 首先向mapperFactory注入T对象与json字符串的映射关系
 * 然后指定实际对象的某个对象字段转换为json字符串字段映射，使用上一步注册的映射关系
 *
 * @author dazzlzy
 * @date 2018/6/11
 */
@Component
public class JsonConfigConvert<T> extends BidirectionalConverter<T, String> {

    @Override
    public String convertTo(T source, Type<String> destinationType) {
        return JSON.toJSONString(source);
    }

    @Override
    public T convertFrom(String source, Type<T> destinationType) {
        return JSON.parseObject(source, destinationType.getRawType());
    }

}
