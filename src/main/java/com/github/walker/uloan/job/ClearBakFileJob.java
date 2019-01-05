package com.github.walker.uloan.job;

import com.github.walker.common.utils.DateTimeUtil;
import com.github.walker.common.sftp.SftpConfigLoader;
import com.github.walker.common.sftp.SftpNode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * 定时删除n天前的备份报文.
 *
 * @author HuQingmiao
 */
public class ClearBakFileJob {
    private static Logger log = LoggerFactory.getLogger(ClearBakFileJob.class);

    // 上传或下载后备份目录后缀
    private final static String DIRC_BACK = "_bak";

    private List<SftpNode> sftpNodes = SftpConfigLoader.getInstance().getFtpNodes();

    public ClearBakFileJob() {
    }

    public void execute() {
        // 当前时间的-5天
        Date delFlagDate = DateTimeUtil.getTimeByRalativeDay(new Date(), -5, false);

        log.info(">>>>>> clearBakFileJob.execute() , begin ...");
        for (SftpNode sftpNode : sftpNodes) {
            try {
                String localUploadDirc = sftpNode.getLocalUploadDirc();
                if (StringUtils.isEmpty(localUploadDirc)) {
                    continue;
                }

                // 上传后的备份目录
                File bakDirc = new File(localUploadDirc + DIRC_BACK);
                if (!bakDirc.exists()) {
                    continue;
                }

                File[] files = bakDirc.listFiles();
                log.info(">>备份目录 {} 中有 {} 个文件. ", bakDirc.getCanonicalPath(), files.length);

                for (int i = 0; i < files.length; i++) {
                    try {
                        // 获取文件最后修改时间，即创建时间
                        Long time = files[i].lastModified();

                        if (new Date(time).before(delFlagDate)) {
                            files[i].delete();
                        }

                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                }

            } catch (Exception e) {
                log.error("", e);
            }
        }
        log.info(">>>>>> clearBakFileJob.execute() ... end! ");
    }

}
