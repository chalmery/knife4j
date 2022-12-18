/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.github.xiaoymin.knife4j.common.lang;

import cn.hutool.core.util.StrUtil;
import com.github.xiaoymin.knife4j.datasource.config.ConfigProfileProvider;
import com.github.xiaoymin.knife4j.datasource.config.ConfigDataProvider;
import com.github.xiaoymin.knife4j.datasource.config.disk.DiskConfigProfileProvider;
import com.github.xiaoymin.knife4j.datasource.config.disk.DiskConfigDataProvider;
import com.github.xiaoymin.knife4j.datasource.config.nacos.NacosConfigDataProvider;
import com.github.xiaoymin.knife4j.datasource.config.nacos.NacosConfigProfileProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author <a href="xiaoymin@foxmail.com">xiaoymin@foxmail.com</a>
 * 2022/12/15 21:02
 * @since:knife4j-desktop
 */
@AllArgsConstructor
@Getter
public enum ConfigMode {
    
    /**
     * 本地磁盘配置
     */
    DISK("disk", "本地文件配置", DiskConfigDataProvider.class, DiskConfigProfileProvider.class),
    /**
     * Nacos配置中心
     */
    NACOS("nacos", "NACOS配置中心", NacosConfigDataProvider.class, NacosConfigProfileProvider.class);
    
    /**
     * knife4j.source主要类型
     */
    private String value;
    
    private String label;
    
    /**
     * 实现类
     */
    private Class<? extends ConfigDataProvider> configClazz;
    
    /**
     * 元数据实现
     */
    private Class<? extends ConfigProfileProvider> configMetaClazz;
    
    /**
     * 获取当前配置类型
     * @param value
     * @return
     */
    public static ConfigMode config(String value) {
        for (ConfigMode configMode : ConfigMode.values()) {
            if (StrUtil.equalsIgnoreCase(configMode.getValue(), value)) {
                return configMode;
            }
        }
        return ConfigMode.DISK;
    }
    
}
