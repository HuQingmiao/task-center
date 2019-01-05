package com.github.walker.uloan.job;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 下载Job
 *
 * @author HuQingmiao
 */
public class ParseJob {

    private static Logger log = LoggerFactory.getLogger(ParseJob.class);

    //private List<SftpNode> sftpNodes = SftpConfigLoader.getInstance().getFtpNodes();

    public ParseJob() {
    }

    public void execute() {
        final int maxFileSize = 30;

        log.info(">>>>>> parseJob.execute() , begin ...");

        log.info(">>>>>> parseJob.execute() ... end! ");
    }

}
