package com.lazy.tcc.core;

import com.lazy.tcc.core.cache.ICache;
import com.lazy.tcc.core.logger.Logger;
import com.lazy.tcc.core.logger.LoggerFactory;
import com.lazy.tcc.core.repository.ITransactionRepository;
import com.lazy.tcc.core.serializer.Serialization;
import com.lazy.tcc.common.utils.ReflectUtils;
import com.lazy.tcc.common.utils.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.lang.reflect.Field;

/**
 * <p>
 * 框架配置
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/10/16.
 */
@ConfigurationProperties("lazy.tcc.config")
public class SpiConfiguration {

    private Class<? extends Serialization> seriClassImpl;
    private Class<? extends ICache> cacheClassImpl;
    private Class<? extends ITransactionRepository> txRepository;
    private String loggerAdapter;
    private String txTableName;

    public String getTxTableName() {
        return txTableName;
    }

    public SpiConfiguration setTxTableName(String txTableName) {
        this.txTableName = txTableName;
        return this;
    }

    public Class<? extends ITransactionRepository> getTxRepository() {
        return txRepository;
    }

    public SpiConfiguration setTxRepository(Class<? extends ITransactionRepository> txRepository) {
        this.txRepository = txRepository;
        return this;
    }

    public Class<? extends Serialization> getSeriClassImpl() {
        return seriClassImpl;
    }

    public SpiConfiguration setSeriClassImpl(Class<? extends Serialization> seriClassImpl) {
        this.seriClassImpl = seriClassImpl;
        return this;
    }

    public Class<? extends ICache> getCacheClassImpl() {
        return cacheClassImpl;
    }

    public SpiConfiguration setCacheClassImpl(Class<? extends ICache> cacheClassImpl) {
        this.cacheClassImpl = cacheClassImpl;
        return this;
    }

    public String getLoggerAdapter() {
        return loggerAdapter;
    }

    public SpiConfiguration setLoggerAdapter(String loggerAdapter) {
        this.loggerAdapter = loggerAdapter;
        return this;
    }

    /**
     * Logger
     */
    private static Logger LOGGER = LoggerFactory.getLogger(SpiConfiguration.class);

    /**
     * 单例对象
     */
    private static SpiConfiguration singleton;

    /**
     * Config Pre
     */
    private static final String ROOT_PRE = "lazy.tcc.config.";


    /**
     * 获取单例对象
     *
     * @return
     */
    public static SpiConfiguration getInstance() {
        if (singleton == null) {
            synchronized (SpiConfiguration.class) {
                if (singleton == null) {
                    LOGGER.debug("========================== init PropertiesConfiguration =============================");
                    singleton = new SpiConfiguration();
                    Class clazz = singleton.getClass();
                    Class type;
                    String configName;
                    String configVal;
                    Field[] fields = clazz.getDeclaredFields();
                    try {
                        for (Field field : fields) {
                            if (field.getName().equalsIgnoreCase("ROOT_PRE")
                                    || field.getName().equalsIgnoreCase("singleton")) {
                                continue;
                            }
                            type = field.getType();
                            configName = ROOT_PRE + StringUtils.toUnderline(field.getName()).replaceAll("_", "-");
                            configVal = PropertiesReader.getInstance().getProp(configName).getVal();
                            if (configVal == null) {
                                continue;
                            }
                            if (type.equals(Class.class)) {
                                ReflectUtils.setFieldValue(field.getName(), Class.forName(configVal), singleton);
                            } else if (type.equals(Integer.class) || type.equals(int.class)) {
                                ReflectUtils.setFieldValue(field.getName(), Integer.valueOf(configVal), singleton);
                            } else if (type.equals(Long.class) || type.equals(long.class)) {
                                ReflectUtils.setFieldValue(field.getName(), Long.valueOf(configVal), singleton);
                            } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
                                ReflectUtils.setFieldValue(field.getName(), Boolean.valueOf(configVal), singleton);
                            } else {
                                ReflectUtils.setFieldValue(field.getName(), configVal, singleton);
                            }
                            LOGGER.debug(String.format("%s:%s", configName, configVal));
                        }
                    } catch (Exception ex) {
                        LOGGER.error("init PropertiesConfiguration ERROR", ex);
                        throw new RuntimeException("init PropertiesConfiguration ERROR", ex);
                    }
                }
            }
        }
        return singleton;
    }


}
