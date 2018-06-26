package com.dazzlzy.common.orika;

import com.dazzlzy.common.support.IMapperRegister;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.Converter;
import ma.glasnost.orika.Mapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 实例化流程说明：
 * 1.当Spring实例化它时，它将调用从ConfigurableMapper继承的构造函数，它调用一个init方法来创建MapperFactory，
 * 并允许您通过overriden方法进行配置因此，第一个方法是configure(MapperFactory)，此外，不要像我上面所做的那样
 * 创建自己的MapperFactory，因为它不会是您用来注册映射器和转换器的工具，相反，这个类将创建一个新的MapperFactory;
 * <p>
 * 2.我们的SpringConfigurableMapper类 有引入依赖注入的 ApplicationContext @Autowired，
 * 这与您实现ApplicationContextAware的过程是一样的，但我宁愿采用这种方法此外，它还意味着在创建所有bean之后将
 * 调用该方法。既然您已经有了一个MapperFactory，那么您就可以搜索自定义组件和转换器，它们是spring bean，并
 * 在MapperFactory注册它们。
 * <p>
 * 使用方法：
 * SpringConfigurableMapper是ConfigurableMapper的子类，继承了MapperFacade接口，可以直接转型成MapperFacade
 * 在方法中直接new SpringConfigurableMapper实例，或者直接使用spring注入MapperFacade，就是注入Spring中的MapperFacade单例对象，
 * 该对象在本类的configure()方法中注入到spring的上下文ApplicationContext中。
 * 并且在设置上下文时，注入了程序中所有的IMapperRegister实现类、Mapper子类和Converter子类，
 * IMapperRegister子类示例： {@link com.dazzlzy.springbootseed.orika.register.RoleDtoMapperRegister}，IMapperRegister中的register方法中向mapperFactory注入对象映射关系
 * Mapper的实现类可以使用convertAtoB和convertBtoA方法，自定义A和B之间的转换，需要实现所有字段的转换关系
 * Converter子类示例： {@link com.dazzlzy.common.orika.converter.JsonConfigConvert}。Converter的convertTo和convertFrom两个方法实现的是两个对象的转换关系
 * SpringConfigurableMapper的单元测试示例： UserDtoMapperTest。
 * 如果两个对象之间没有特别的区别，Orika可以自动复制，而不需要写任何实现类(IMapperRegister,CustomMapper,Convert等)
 *
 * @author dazzlzy
 * @date 2018/6/14 0014
 */
@Slf4j
@Component
public class SpringConfigurableMapper extends ConfigurableMapper {

    /**
     * spring加载完成以后，上下文应用对象
     */
    private ApplicationContext applicationContext;

    /**
     * 类配置映射对象，用于获取MapperFacade
     * 使用spring注入的mapperFactory就是这个对象，单例
     */
    private MapperFactory mapperFactory;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        addSpringMapperRegisters();
        addSpringMappers();
        addSpringConverter();
    }

    @Override
    protected void configure(MapperFactory factory) {
        super.configure(factory);
        this.mapperFactory = factory;
    }

    /**
     * 向spring上下文中注入自定义的IMapperRegister接口实现
     */
    private void addSpringMapperRegisters() {
        final Map<String, IMapperRegister> mapperRegisterMap = applicationContext.getBeansOfType(IMapperRegister.class);
        for (IMapperRegister iMapperRegister : mapperRegisterMap.values()) {
            addMapperRegister(iMapperRegister);
        }
    }

    /**
     * IMapperRegister实现中的register方法需要自行注入, 注入参见：{@link com.dazzlzy.springbootseed.orika.register.RoleDtoMapperRegister}
     *
     * @param mapperRegister 自定义mapper注册
     */
    private void addMapperRegister(IMapperRegister mapperRegister) {
        log.info("==》 Orika注入 IMapperRegister: {}", mapperRegister.getClass());
        mapperRegister.register(this.mapperFactory);
    }

    /**
     * 向spring上下文中注入所有Mapper实现类
     */
    private void addSpringMappers() {
        final Map<String, Mapper> mappers = applicationContext.getBeansOfType(Mapper.class);
        for (final Mapper<?, ?> mapper : mappers.values()) {
            addMapper(mapper);
        }
    }

    /**
     * 向mapperFactory中注册指定的Mapper
     *
     * @param mapper 注册的Mapper
     */
    private void addMapper(Mapper<?, ?> mapper) {
        log.info("==》 Orika注入 Mapper: {}", mapper.getClass());
        this.mapperFactory.registerMapper(mapper);
    }

    /**
     * 向spring上下文中注入所有Converter实现类，如{@link com.dazzlzy.common.orika.converter.JsonConfigConvert}
     */
    private void addSpringConverter() {
        final Map<String, Converter> converters = applicationContext.getBeansOfType(Converter.class);
        for (final Converter<?, ?> converter : converters.values()) {
            addConverter(converter);
        }
    }

    /**
     * 向MapperFactory中注册指定的Converter
     *
     * @param converter 注册的Converter
     */
    private void addConverter(Converter<?, ?> converter) {
        log.info("==》 Orika注入 Converter: {}", converter.getClass());
        this.mapperFactory.getConverterFactory().registerConverter(converter);
    }

}  