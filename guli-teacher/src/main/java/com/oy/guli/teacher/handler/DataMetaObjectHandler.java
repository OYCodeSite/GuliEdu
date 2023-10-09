package com.oy.guli.teacher.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author OY
 * @Date 2021/2/27
 */
@Component
public class DataMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        // 自动补充teacher对象属性中的数据, isDeleted: Boolean 所以我们应该放入true, false
        this.setFieldValByName("isDeleted",false,metaObject);
        this.setFieldValByName("gmtCreate",new Date(),metaObject);
        this.setFieldValByName("gmtModified",new Date(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("gmtModified", new Date(),metaObject);
    }
}
