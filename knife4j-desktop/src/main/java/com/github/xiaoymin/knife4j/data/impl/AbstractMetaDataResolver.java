/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package com.github.xiaoymin.knife4j.data.impl;

import cn.hutool.core.util.NumberUtil;
import com.github.xiaoymin.knife4j.aggre.core.RouteRepository;
import com.github.xiaoymin.knife4j.aggre.spring.configuration.Knife4jAggregationProperties;
import com.github.xiaoymin.knife4j.aggre.spring.configuration.Knife4jSettingProperties;
import com.github.xiaoymin.knife4j.core.GlobalDesktopManager;
import com.github.xiaoymin.knife4j.data.resolver.MetaDataResolver;
import com.github.xiaoymin.knife4j.data.resolver.MetaDataResolverKey;
import com.github.xiaoymin.knife4j.util.PropertyUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Optional;

/**
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a>
 * 2020/12/12 15:51
 * @since:knife4j-aggregation-desktop 1.0
 */
@Slf4j
public abstract class AbstractMetaDataResolver implements MetaDataResolver {
    @Override
    public void resolve(File file, MetaDataResolverKey metaDataResolverKey) {
        String code=file.getName();
        Long fileLastChange=GlobalDesktopManager.me.getFileValue(code);
        if (fileLastChange!=null){
            //判断文件是否发生了变化
            if (NumberUtil.compare(file.lastModified(),fileLastChange)==0){
                //log.info("文件未发生变化,不用处理,file:{}",file.getAbsolutePath());
                return;
            }
        }
        log.info("file modifier:{}，file:{}",file.lastModified(),file.getAbsolutePath());
        RouteRepository routeRepository=GlobalDesktopManager.me.repository(code);
        if (routeRepository!=null){
            GlobalDesktopManager.me.repository(code).remove(code);
            //GlobalDesktopManager.me.remove(code);
            //routeRepository.remove(code);
        }
        if (metaDataResolverKey==MetaDataResolverKey.create||metaDataResolverKey==MetaDataResolverKey.modify){
            resolverModifyAndCreate(file);
        }
    }

    /**
     * properties配置文件转换为java实体类
     * @param propertiesFile
     * @return
     */
    protected Knife4jAggregationProperties loadFromProperties(File propertiesFile){
        Optional<Knife4jSettingProperties> knife4jSettingPropertiesOptional= PropertyUtil.resolveSingle(propertiesFile,Knife4jSettingProperties.class);
        if (knife4jSettingPropertiesOptional.isPresent()) {
            Knife4jSettingProperties knife4jSettingProperties=knife4jSettingPropertiesOptional.get();
            if (knife4jSettingProperties!=null){
                return knife4jSettingProperties.getKnife4j();
            }
        }
        return null;
    }

    public abstract void resolverModifyAndCreate(File file);


}
