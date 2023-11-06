package com.anzhilai.xdadmin.task;

import com.anzhilai.core.base.BaseModel;
import com.anzhilai.core.database.SqlCache;
import com.anzhilai.core.framework.GlobalValues;
import com.anzhilai.core.toolkit.LogUtil;
import com.anzhilai.core.toolkit.PathUtil;
import com.anzhilai.xdadmin.web.系统管理.XTPZ系统配置;
import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@domain RYXX人员信息
@Component
public class InitData implements CommandLineRunner {
    private static Logger log = LogUtil.getLogger(InitData.class);

    @Override
    public void run(String... strings) throws Exception {
        initDB();
        log.info("ExecutingPath::" + PathUtil.getExecutingPath());
        log.info("xdevelop ok!!!" + GlobalValues.CurrentIP + ":" + GlobalValues.CurrentPort);
    }

    public static void initDB() throws Exception {
        String s = XTPZ系统配置.Get系统配置("是否初始化");
        if (!"是".equals(s)) {
            XTPZ系统配置.Save系统配置("是否初始化", "是");
            for (String c : SqlCache.hashMapClasses.keySet()) {
                try {
                    Class<?> clazz = SqlCache.hashMapClasses.get(c);
                    BaseModel model = (BaseModel) clazz.newInstance();
                    model.InitTestData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
