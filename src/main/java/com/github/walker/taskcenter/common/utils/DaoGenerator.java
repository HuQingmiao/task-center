package com.github.walker.taskcenter.common.utils;

import com.github.walker.mybatis.daoj.core.CodeBuilder;
import com.github.walker.mybatis.daoj.core.ConfigLoader;
import com.github.walker.mybatis.daoj.core.Generator;
import com.github.walker.mybatis.daoj.utils.MappingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Mybatis的DAO层代码生成器， 生成dao接口, mapper.xml, 实体类.
 *
 * 执行main()前， 请确保mybatis-daoj.xml配置正确。
 *
 * Created by huqingmiao on 2018/1/25.
 */
public class DaoGenerator extends Generator{
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        try {
            new DaoGenerator().generator();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void generator() {
        //BasicEntity类名
        String basicEntity = ConfigLoader.getBasicEntity();

        //BasicDao类名
        String basicDao = ConfigLoader.getBasicDao();

        //生成的实体类所在的包
        String entityPackage = ConfigLoader.getEntityPackage();

        //生成的DAO类所在的包
        String daoPackage = ConfigLoader.getDaoPackage();

        //代码生成的输出目录
        String outputDircName = ConfigLoader.getOutputDirc();

        //列出要生成的表名
        String[] tables = ConfigLoader.getTables();

        File outputDirc = new File(outputDircName);
        if (!outputDirc.exists()) {
            outputDirc.mkdirs();
        }

        File poPackageDirc = new File(outputDirc, "vo");
        poPackageDirc.mkdirs();

        File daoPackageDirc = new File(outputDirc, "dao");
        daoPackageDirc.mkdirs();


        for (int i = 0; i < tables.length; i++) {
            try {
                CodeBuilder codeBuilder = new CodeBuilder(tables[i]);

                //生成实体类
                String codeStr = codeBuilder.buildEntitySource(basicEntity, entityPackage);
                this.createFile(codeStr, poPackageDirc.getCanonicalPath()
                        + File.separator + MappingUtil.getEntityName(tables[i]) + ".java");

                //生成DAO类
                codeStr = codeBuilder.buildDaoSource(basicDao, daoPackage);
                this.createFile(codeStr, daoPackageDirc.getCanonicalPath()
                        + File.separator + MappingUtil.getEntityName(tables[i]) + "Dao.java");

                //生成MAPPER
                codeStr = codeBuilder.buildMapperSource(daoPackage, entityPackage);
                this.createFile(codeStr, daoPackageDirc.getCanonicalPath()
                        + File.separator + MappingUtil.getEntityName(tables[i]) + "Mapper.xml");

//                //复制DAO/VO基类
//                InputStream is = Generator.class.getClassLoader().getResourceAsStream("BasicDao.java");
//                File basicDaoFile = new File(outputDirc, "BasicDao.java");
//                this.writeToFile(is, basicDaoFile);
//
//                is = Generator.class.getClassLoader().getResourceAsStream("BasicVo.java");
//                File basicVoFile = new File(outputDirc, "BasicVo.java");
//                this.writeToFile(is, basicVoFile);

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }


}
