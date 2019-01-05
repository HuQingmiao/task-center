package com.github.walker.common.sftp;

import com.github.walker.common.BasicVo;

public class SftpNode extends BasicVo {

    private String host;
    private String port;
    private String username;
    private String password;

    private String connMode;
    private String fileType;

    private String serverUploadDirc;
    private String serverDnloadDirc;

    private String localUploadDirc;
    private String localDnloadDirc;


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConnMode() {
        return connMode;
    }

    public void setConnMode(String connMode) {
        this.connMode = connMode;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getServerUploadDirc() {
        return serverUploadDirc;
    }

    public void setServerUploadDirc(String serverUploadDirc) {
        this.serverUploadDirc = serverUploadDirc;
    }

    public String getServerDnloadDirc() {
        return serverDnloadDirc;
    }

    public void setServerDnloadDirc(String serverDnloadDirc) {
        this.serverDnloadDirc = serverDnloadDirc;
    }

    public String getLocalDnloadDirc() {
        return localDnloadDirc;
    }

    public void setLocalDnloadDirc(String localDnloadDirc) {
        this.localDnloadDirc = localDnloadDirc;
    }

    public String getLocalUploadDirc() {
        return localUploadDirc;
    }

    public void setLocalUploadDirc(String localUploadDirc) {
        this.localUploadDirc = localUploadDirc;
    }
}
