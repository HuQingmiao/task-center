package com.github.walker.common.sftp;


import com.github.walker.common.utils.XmlUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 载入FTP 结点配置
 * <p/>
 * Created by HuQingmiao on 2018-3-22.
 */
public class SftpConfigLoader implements InitializingBean {

    private static Logger log = LoggerFactory.getLogger(SftpConfigLoader.class);

    private static final String CONFIG_FILENAME = "sftp-nodes.xml";

    private static SftpConfigLoader sftpConfigLoader = null;

    private List<SftpNode> ftpNodes = new ArrayList(4);

    public static synchronized SftpConfigLoader getInstance() {
        if (sftpConfigLoader == null) {
            sftpConfigLoader = new SftpConfigLoader();
        }
        return sftpConfigLoader;
    }


    private SftpConfigLoader() {
        this.load();
    }


    public List<SftpNode> getFtpNodes() {
        return this.ftpNodes;
    }


    //从data-handler装载handlers类
    private void load() {
        log.info("Load {} begin... ", CONFIG_FILENAME);
        this.ftpNodes.clear();

        Document doc = null;
        try {
            File file = new File(CONFIG_FILENAME);
            if (!file.exists()) {
                URL url = SftpConfigLoader.class.getClassLoader().getResource(CONFIG_FILENAME);
                file = new File(url.getPath());
            }
            doc = XmlUtil.read(file);

            Element root = doc.getRootElement();
            for (Iterator<Element> nodeIt = root.elementIterator(); nodeIt.hasNext(); ) {
                Element nodeEl = nodeIt.next();

                SftpNode node = new SftpNode();
                for (Iterator<Element> it = nodeEl.elementIterator(); it.hasNext(); ) {
                    Element e = it.next();
                    String name = e.attribute("name").getValue();
                    String value = e.attribute("value").getValue();

                    log.debug(">>set " + name + " to " + value);
                    node.set(name, value);
                }
                this.ftpNodes.add(node);
            }

            log.info("Load {} successful! ", CONFIG_FILENAME);

        } catch (Exception e) {
            log.error("Load {} failed! ", CONFIG_FILENAME, e);
        } finally {
            if (doc != null) {
                doc.clearContent();
            }
        }
    }


    public static void main(String[] args) {

        SftpConfigLoader sftpConfig = new SftpConfigLoader();
        sftpConfig.load();

        for (SftpNode sftpNode : sftpConfig.ftpNodes) {
            System.out.println(">>> " + sftpNode.toString());
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
