package com.github.walker.common.sftp;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * SFTP客户端实例
 *
 * @author HuQingmiao
 */
public class SftpClient {

    private static Logger log = LoggerFactory.getLogger(SftpClient.class);

    private final static int DEFAULT_TIMEOUT = 30 * 1000;// 1分钟

    // 上传或下载后备份目录后缀
    private final static String DIRC_BACK = "_bak";

    // 上传或下载过程中的临时文件的扩展名
    protected final static String EXTEND_NAME_TMP = ".tmp";

    // 改名重试最多次数
    private final static int TRY_RENAME_TIMES = 3;

    private String host;
    private String port;
    private String username;
    private String password;

    private String serverUploadDirc;
    private String serverDnloadDirc;
    private String localUploadDirc;
    private String localDnloadDirc;

    private JSch jsch = new JSch();
    private Session session;
    private ChannelSftp channel;

    public SftpClient(SftpNode sftpNode) {
        this.host = sftpNode.getHost();
        this.port = sftpNode.getPort();
        this.username = sftpNode.getUsername();
        this.password = sftpNode.getPassword();
        this.serverUploadDirc = sftpNode.getServerUploadDirc();
        this.serverDnloadDirc = sftpNode.getServerDnloadDirc();
        this.localUploadDirc = sftpNode.getLocalUploadDirc();
        this.localDnloadDirc = sftpNode.getLocalDnloadDirc();
    }

    public SftpClient(String host, String port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }


    /**
     * 上传文件到FTP服务器.
     *
     * @param serverDirc FTP服务器上传目录, 若为FTP根目录，则以"/"表示；
     *                   如果传入null, 则会取默认的上传目录：SftpConfigLoader.getServerUploadDirc()。
     * @param file       本地的待上传文件
     */
    public void uploadFile(String serverDirc, File file) throws IOException, SftpException {
        if (serverDirc == null) {
            serverDirc = this.serverUploadDirc;
        }
        try {
            this.connect();

            // 转到FTP服务器上传目录
            log.info(">>cd " + serverDirc);
            channel.cd(serverDirc);

            // 上传文件
            this.upload(file);

        } finally {
            this.disConnect();
        }
    }


    /**
     * 上传多个文件到FTP服务器.
     *
     * @param serverDirc FTP服务器上传目录, 若为FTP根目录，则以"/"表示；
     *                   如果传入null, 则会取默认的上传目录：SftpConfigLoader.getServerUploadDirc()。
     * @param files      本地的待上传文件
     */
    public void uploadFiles(String serverDirc, List<File> files) throws IOException, SftpException {
        if (serverDirc == null) {
            serverDirc = this.serverUploadDirc;
        }
        try {
            this.connect();

            // 转到FTP服务器上传目录
            log.info(">>cd " + serverDirc);
            channel.cd(serverDirc);

            for (File file : files) {
                this.upload(file);
            }

        } finally {
            this.disConnect();
        }
    }

    /**
     * 指定本地目录， 上传该目录下的所有文件， 上传后将本地文件移入备份目录
     *
     * @param maxFileSize
     **/
    public void uploadFiles(String localDircPath, int maxFileSize) throws IOException, SftpException {
        if (localDircPath == null) {
            localDircPath = this.localUploadDirc;
        }
        try {
            this.connect();

            // 转到FTP服务器上传目录
            log.info(">>cd " + this.serverUploadDirc);
            channel.cd(this.serverUploadDirc);

            File localDirc = new File(localDircPath);
            File[] files = localDirc.listFiles();
            if (files == null) {
                log.error("目录 {} 下没有可上传的文件!", localDirc.getCanonicalPath());
                return;
            }

            log.info(">>>本地目录的文件数: " + files.length);
            int cnt =0;
            for (int i = 0; i < files.length && i < maxFileSize; i++) {
                String filename = files[i].getName();
                try {
                    // 不对目录做处理
                    if (files[i].isDirectory()) {
                        continue;
                    }
                    if (".".equals(filename) || "..".equals(filename)) {
                        continue;
                    }
                    if (filename.endsWith(".tmp")) {
                        continue;
                    }

                    // 上传文件
                    this.upload(files[i]);

                    // 备份文件
                    File localUpBakDir = new File(localDirc + DIRC_BACK);
                    this.backup(files[i], localUpBakDir);
                    cnt ++;

                } catch (Exception e) {
                    log.error("上传文件发生异常， 文件名：" + filename);
                    log.error("", e);
                }
            }

            log.info(">>>本次上传文件数: " + cnt);

        } finally {
            this.disConnect();
        }
    }


    private void upload(File file) throws IOException, SftpException {
        InputStream is = null;
        try {
            String serverDirc = channel.pwd();

            // 上传过程中，落地到服务器的临时文件名
            String tmpFilename = file.getName() + EXTEND_NAME_TMP;
            String absTmpFilename = null;
            if (serverDirc.endsWith("/")) {
                absTmpFilename = serverDirc + tmpFilename;
            } else {
                absTmpFilename = serverDirc + "/" + tmpFilename;
            }

            log.info(">>正在上传 {} ...", file.getCanonicalPath());
            is = new FileInputStream(file);
            channel.put(is, tmpFilename);
            log.info(">>已成功上传至 {}!", absTmpFilename);


            log.info(">>把上传至服务器的临时文件改名为正式文件... ");
            String absFilename = absTmpFilename.substring(0, absTmpFilename.length() - EXTEND_NAME_TMP.length());
            channel.rename(absTmpFilename, absFilename);
            log.info(">>已成功将文件 {} 更名为 {} .", absTmpFilename, absFilename);

        } finally {
            if (is != null) {
                is.close();
            }
        }
    }


    /**
     * 从服务器下载文件
     *
     * @param serverDirc FTP服务器目录, 若为FTP根目录，则以"/"表示；
     *                   如果传入null, 则会取默认的下载目录：SftpConfigLoader.getServerDnloadDirc()。
     * @param filename   服务器上的文件名
     * @param localDirc  下载文件的本地存放目录
     * @throws IOException
     */
    public File downloadFile(String serverDirc, String filename, File localDirc)
            throws IOException, SftpException {
        if (serverDirc == null) {
            serverDirc = this.serverDnloadDirc;
        }
        if (localDirc == null) {
            localDirc = new File(this.localDnloadDirc);
        }
        try {
            this.connect();

            // 转到FTP服务器下载目录
            log.info(">>cd " + serverDirc);
            channel.cd(serverDirc);

            // 下载文件
            return dnload(filename, localDirc);

        } finally {
            this.disConnect();
        }
    }


    /**
     * 从服务器下载多个文件
     *
     * @param serverDirc FTP服务器目录, 若为FTP根目录，则以"/"表示；
     *                   如果传入null, 则会取默认的下载目录：SftpConfigLoader.getServerDnloadDirc()。
     * @param files      服务器上的文件名
     * @param localDirc  下载文件的本地存放目录, 如果传入null, 则会取默认的本地目录：SftpConfigLoader.getLocalDnloadDirc()。
     * @throws IOException
     */
    public List<File> downloadFiles(String serverDirc, List<String> files, File localDirc) throws
            IOException, SftpException {
        if (serverDirc == null) {
            serverDirc = this.serverDnloadDirc;
        }
        if (localDirc == null) {
            localDirc = new File(this.localDnloadDirc);
        }
        try {
            this.connect();

            // 转到FTP服务器下载目录
            log.info(">>cd " + serverDirc);
            channel.cd(serverDirc);

            List<File> fileList = new ArrayList<File>();
            for (String filename : files) {
                fileList.add(this.dnload(filename, localDirc));
            }
            return fileList;

        } finally {
            this.disConnect();
        }
    }

    /**
     * 指定服务器目录，下载该目录下的所有文件
     *
     * @param maxFileSize
     * @return
     * @throws IOException
     * @throws SftpException
     */
    public ArrayList<File> downloadFiles(String serverDirc, int maxFileSize) throws IOException, SftpException {
        if (serverDirc == null) {
            serverDirc = this.serverDnloadDirc;
        }
        ArrayList<File> files = new ArrayList();
        try {
            this.connect();

            // 转到FTP服务器下载目录
            log.info(">>cd " + serverDirc);
            channel.cd(serverDirc);

            // 检查文件是否存在
            Vector<ChannelSftp.LsEntry> fileEntryVec = channel.ls(serverDirc);
            if (fileEntryVec.isEmpty()) {
                return files;
            }
            log.info(">>>服务器上的文件数: " + fileEntryVec.size());

            for (int i = 0; i < fileEntryVec.size() && i < maxFileSize; i++) {
                String filename = null;
                try {
                    ChannelSftp.LsEntry entry = fileEntryVec.get(i);
                    if (entry == null) {
                        continue;
                    }

                    filename = entry.getFilename();

                    // 不对目录做处理 @todo 这个判断不够严谨, 有待改进
                    if (!filename.contains(".")) {
                        connect();
                    }
                    if (".".equals(filename) || "..".equals(filename)) {
                        continue;
                    }
                    if (filename.endsWith(".tmp")) {
                        continue;
                    }

                    // 下载文件
                    File localDirc = new File(this.localDnloadDirc);
                    File file = this.dnload(filename, localDirc);

                    files.add(file);

                    //this.doAfterDnload(filename);

                } catch (Exception e) {
                    log.error("下载文件发生异常， 文件名：" + filename);
                    log.error("", e);
                }
            }

            log.info(">>>本次下载文件数: " + files.size());

            return files;

        } finally {
            this.disConnect();
        }
    }

    private File dnload(String filename, File localDirc) throws IOException, SftpException {
        if (!localDirc.exists()) {
            localDirc.mkdirs();
        }
        File file = new File(localDirc, filename);
        File tmpFile = new File(localDirc, filename + EXTEND_NAME_TMP);

        OutputStream os = null;
        try {
            // 检查文件是否存在
            Vector<ChannelSftp.LsEntry> fileList = channel.ls(filename);
            if (fileList.isEmpty()) {
                return null;
            }

            log.info(">>正在下载 {} ...", filename);

            os = new BufferedOutputStream(new FileOutputStream(tmpFile));
            channel.get(filename, os);
            log.info(">>已成功下载至 {}!", tmpFile.getCanonicalPath());

        } finally {
            if (os != null) {
                os.close();
            }
        }

        try {
            if (file.exists()) {
                file.delete();
            }

            log.info(">>把下载至本地的临时文件改名为正式文件... ");
            // 对已下至本地的临时文件改名, 改名失败则等待4秒后重试;
            int tryTimes = 0;
            while (!tmpFile.renameTo(file) && tryTimes < TRY_RENAME_TIMES) {
                log.debug("更改文件名出错, 文件名：" + tmpFile.getCanonicalPath());
                Thread.sleep(3 * 1000);
                tryTimes++;
            }

            if (tryTimes == TRY_RENAME_TIMES) {
                log.error("多次更名失败, 文件名：" + tmpFile.getCanonicalPath());
                throw new Exception("多次更名失败, 请手工处理!");
            }

            log.info(">>已成功将文件 {} 更名为 {} .", tmpFile.getCanonicalPath(), file.getCanonicalPath());

            return file;

        } catch (Exception e) {
            log.error("", e);
        }

        return null;
    }


    private void connect() throws IOException {
        final long RECONNECT_INTERVAL = 3 * 1000;  // 重连间隔时间
        final int MAX_RECONNECT_TIMES = 2;   // 最大重连次数

        int reConnectTimes = 0;
        try {
            //创建Session和Channel
            newChannel();

            // 如果不处于正常连接状态, 则重连
            while (channel == null && reConnectTimes < MAX_RECONNECT_TIMES) {
                Thread.sleep(RECONNECT_INTERVAL);// 等待几秒后重连
                newChannel();
                reConnectTimes++;
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }

        // 多次重连失败, 则抛出异常
        if (reConnectTimes >= MAX_RECONNECT_TIMES) {
            String err = ">>多次重连失败, 请检查SFtp服务器是否正常!";
            log.error(err);
            throw new IOException(err);
        }
    }

    private void disConnect() throws IOException {
        if (channel != null) {
            if (channel.isConnected()) {
                channel.disconnect();
            }
            channel = null;
        }
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
            }
            session = null;
        }
    }


    private void newSession() throws IOException {
        try {
            this.session = jsch.getSession(username, host, Integer.parseInt(port));
            this.session.setPassword(password);

            //设置首次登陆的提示，可选值：(ask | yes | no)
            this.session.setConfig("StrictHostKeyChecking", "no");
            this.session.connect(DEFAULT_TIMEOUT);
            log.info(">>获取Sftp的Session成功! ");

        } catch (JSchException e) {
            log.error(">>获取Sftp的Session失败!", e);
            if (session != null) {
                session = null;
            }
        }
    }

    private void newChannel() throws IOException {
        try {
            if (session == null || !session.isConnected()) {
                newSession();
            }
            channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();
            log.info(">>建立Sftp的Channel成功!");

        } catch (JSchException e) {
            log.error(">>建立Sftp的Channel失败!", e);
            if (channel != null) {
                channel = null;
            }
        }
    }

    /**
     * 上传文件的后续处理
     *
     */
    private void backup(File file, File localUpBakDir)
            throws IOException {

        if (!localUpBakDir.exists()) {
            localUpBakDir.mkdir();
        }

        File localBakFile = new File(localUpBakDir.getCanonicalPath() + File.separator + file.getName());
        if (localBakFile.exists()) {
            localBakFile.delete();
        }

        // 把已上传的本地文件移到备份目录
        log.info(">>把已上传文件 {} 备份 ...", file.getCanonicalPath());
        file.renameTo(localBakFile);
        log.info(">>已成功把 {} 备份至 ...", file.getCanonicalPath(), localUpBakDir.getCanonicalPath());
    }


//    /**
//     * 下载文件的后续处理
//     *
//     * @param filename ftp服务器上的文件名
//     */
//    public void doAfterDnload(String filename)
//            throws IOException {
//
//    }


    public static void main(String[] args) {

        File file = new File("d:/a.txt");
        File file2 = new File("d:/b.txt");

        List<File> files = new ArrayList<File>();
        files.add(file);
        files.add(file2);

        try {
            SftpClient sftpClient = new SftpClient("132.224.18.80", "22", "root", "Redis098!");

            sftpClient.uploadFile("/app/testftp", new File("d:/任务排期.xlsx"));

            log.info(">>" + sftpClient.downloadFile("/app/testftp", "a.docx", new File("d:/")));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
