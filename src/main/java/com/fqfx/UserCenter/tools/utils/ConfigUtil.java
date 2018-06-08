package com.fqfx.UserCenter.tools.utils;

import com.fqfx.UserCenter.domain.SystemConfig;
import com.fqfx.UserCenter.repository.SystemConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 配置工具类
 *
 * @author noobug.com
 */
@Component
@DependsOn({"liquibase"})
public class ConfigUtil {

    @Autowired
    private SystemConfigRepository systemConfigRepository;

    private ConcurrentHashMap<String, String> mapConfig;

    public ConfigUtil() {
        this.mapConfig = new ConcurrentHashMap<>();
    }

    /**
     * 初始化读取数据库配置表
     */
    @PostConstruct
    public void init() {
        List<SystemConfig> configs = systemConfigRepository.getAllByDeleted(Boolean.FALSE);
        for (SystemConfig config : configs) {
            this.add(config.getKey(), config.getData());
        }
    }

    /**
     * 获取配置值
     *
     * @param k 配置名
     * @return 值
     */
    public Optional<String> get(String k) {
        return Optional.ofNullable(mapConfig.get(k));
    }

    /**
     * 获取配置值，带默认值
     *
     * @param k            配置名
     * @param defaultValue 默认值
     * @return 值
     */
    public String get(String k, String defaultValue) {
        assert defaultValue != null;
        return mapConfig.getOrDefault(k, defaultValue);
    }

    /**
     * 获取配置值 整型
     *
     * @param k 配置名
     * @return 值
     */
    public Optional<Integer> getInt(String k) {
        Optional<String> v = get(k);
        Integer n = v.map(Integer::parseInt).orElse(null);
        return Optional.ofNullable(n);
    }

    /**
     * 获取配置值，带默认值，整型
     *
     * @param k            配置名
     * @param defaultValue 默认值
     * @return 值
     */
    public Integer getInt(String k, Integer defaultValue) {
        assert defaultValue != null;
        return getInt(k).orElse(defaultValue);
    }

    /**
     * 获取配置值 浮点型
     *
     * @param k 配置名
     * @return 值
     */
    public Optional<Float> getFloat(String k) {
        Optional<String> v = get(k);
        Float n = v.map(Float::parseFloat).orElse(null);
        return Optional.ofNullable(n);
    }

    /**
     * 获取配置值，带默认值，浮点型
     *
     * @param k            配置名
     * @param defaultValue 默认值
     * @return 值
     */
    public Float getFloat(String k, Float defaultValue) {
        assert defaultValue != null;

        return getFloat(k).orElse(defaultValue);
    }

    /**
     * 获取配置值 长整型
     *
     * @param k 配置名
     * @return 值
     */
    public Optional<Long> getLong(String k) {
        Optional<String> v = get(k);
        Long n = v.map(Long::parseLong).orElse(null);
        return Optional.ofNullable(n);
    }

    /**
     * 获取配置值，带默认值，长整型
     *
     * @param k            配置名
     * @param defaultValue 默认值
     * @return 值
     */
    public Long getLong(String k, Long defaultValue) {
        assert defaultValue != null;

        return getLong(k).orElse(defaultValue);
    }

    /**
     * 添加新配置 （已存在则不添加
     *
     * @param k key
     * @param v value
     * @return 是否添加
     */
    public boolean add(String k, String v) {
        if (mapConfig.containsKey(k)) {
            return false;
        }

        mapConfig.put(k, v);
        return true;
    }

    /**
     * 批量增加新配置项，已存在的项跳过
     *
     * @param map 配置项maps
     * @return 跳过的数量
     */
    public int add(Map<String, String> map) {
        int n = 0;
        for (String k : map.keySet()) {
            if (!add(k, map.get(k))) {
                n++;
            }
        }

        return n;
    }

    /**
     * 更新配置项（不存在则返回null
     *
     * @param k key
     * @param v value
     * @return 旧的值
     */
    public String update(String k, String v) {
        if (!mapConfig.containsKey(k)) {
            return null;
        }

        return mapConfig.put(k, v);
    }

    /**
     * 批量更新存在的配置项，不存在的配置项跳过不处理
     *
     * @param map 配置项map
     * @return 跳过的数量
     */
    public int update(Map<String, String> map) {
        int n = 0;
        for (String k : map.keySet()) {
            if (update(k, map.get(k)) == null) {
                n++;
            }
        }

        return n;
    }

    /**
     * 添加配置项，存在则更新
     *
     * @param k key
     * @param v value
     * @return 旧的值
     */
    public String put(String k, String v) {
        return mapConfig.put(k, v);
    }

    /**
     * 批量添加配置项，存在则更新
     *
     * @param map 配置项map
     * @return 新增的条数
     */
    public int put(Map<String, String> map) {
        int n = 0;
        for (String k : map.keySet()) {
            if (put(k, map.get(k)) == null) {
                n++;
            }
        }

        return n;
    }

    public int clear() {
        int n = mapConfig.size();
        mapConfig.clear();
        return n;
    }
}
