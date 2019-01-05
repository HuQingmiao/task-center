package com.github.walker.uloan.job;

import com.github.walker.common.sftp.SftpClient;
import com.github.walker.common.sftp.SftpConfigLoader;
import com.github.walker.common.sftp.SftpNode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * 下载Job
 *
 * @author HuQingmiao
 */
public class DnloadJob {

    private static Logger log = LoggerFactory.getLogger(DnloadJob.class);

    private List<SftpNode> sftpNodes = SftpConfigLoader.getInstance().getFtpNodes();

    public DnloadJob() {
    }

    public void execute() {
        final int maxFileSize = 30;

        log.info(">>>>>> dnloadJob.execute() , begin ...");
        for (SftpNode sftpNode : sftpNodes) {
            try {
                SftpClient sftpClientApp = new SftpClient(sftpNode);
                String serverDnloadDirc = sftpNode.getServerDnloadDirc();
                String localDnloadDirc = sftpNode.getLocalDnloadDirc();
                if (StringUtils.isEmpty(serverDnloadDirc)) {
                    log.warn(">> 没有配置服务器下载目录 'serverDnloadDirc' , 不做下载处理!");
                    continue;
                }
                if (StringUtils.isEmpty(localDnloadDirc)) {
                    log.warn(">> 没有配置本地下载目录 'localDnloadDirc' , 不做下载处理!");
                    continue;
                }

                sftpClientApp.downloadFiles(serverDnloadDirc, maxFileSize);

            } catch (Exception e) {
                log.error("", e);
            }
        }
        log.info(">>>>>> dnloadJob.execute() ... end! ");
    }

}
