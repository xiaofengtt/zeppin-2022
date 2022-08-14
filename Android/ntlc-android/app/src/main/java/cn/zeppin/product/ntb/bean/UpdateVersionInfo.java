package cn.zeppin.product.ntb.bean;

import java.io.Serializable;

/**
 * 描述：版本更新
 * 开发人: geng
 * 创建时间: 17/11/28
 */

public class UpdateVersionInfo implements Serializable{
    /**
     * currentVersionName :
     * flagCompel : true
     * currentVersion :
     * flagUpdate : true
     * newVersion : {"uuid":"6456a385-afe1-4b85-9b77-ace6397ef16a","version":5,"versionName":"2.1.3","versionDes":"优化了一些bug","resource":"2591f7fa-212b-4eb0-b226-dcb9c2b8958d","resourceUrl":"/upload/2591f7fa/212b/4eb0/b226/dcb9c2b8958d/nt-2.1.3-20171128.apk","device":"Android","flagCompel":false,"status":"published"}
     */

    private String currentVersionName;
    private boolean flagCompel;//是否强制更新
    private String currentVersion;//是否有最新版本
    private boolean flagUpdate;
    private NewVersionBean newVersion;

    public String getCurrentVersionName() {
        return currentVersionName;
    }

    public void setCurrentVersionName(String currentVersionName) {
        this.currentVersionName = currentVersionName;
    }

    public boolean isFlagCompel() {
        return flagCompel;
    }

    public void setFlagCompel(boolean flagCompel) {
        this.flagCompel = flagCompel;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public boolean isFlagUpdate() {
        return flagUpdate;
    }

    public void setFlagUpdate(boolean flagUpdate) {
        this.flagUpdate = flagUpdate;
    }

    public NewVersionBean getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(NewVersionBean newVersion) {
        this.newVersion = newVersion;
    }

    public static class NewVersionBean {
        /**
         * uuid : 6456a385-afe1-4b85-9b77-ace6397ef16a
         * version : 5
         * versionName : 2.1.3
         * versionDes : 优化了一些bug
         * resource : 2591f7fa-212b-4eb0-b226-dcb9c2b8958d
         * resourceUrl : /upload/2591f7fa/212b/4eb0/b226/dcb9c2b8958d/nt-2.1.3-20171128.apk
         * device : Android
         * flagCompel : false
         * status : published
         */
        private String uuid;
        private int version;
        private String versionName;//最新版本名
        private String versionDes;//版本描述
        private String resource;
        private String resourceUrl;//更新的apk地址
        private String device;
        private boolean flagCompel;
        private String status;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getVersionDes() {
            return versionDes;
        }

        public void setVersionDes(String versionDes) {
            this.versionDes = versionDes;
        }

        public String getResource() {
            return resource;
        }

        public void setResource(String resource) {
            this.resource = resource;
        }

        public String getResourceUrl() {
            return resourceUrl;
        }

        public void setResourceUrl(String resourceUrl) {
            this.resourceUrl = resourceUrl;
        }

        public String getDevice() {
            return device;
        }

        public void setDevice(String device) {
            this.device = device;
        }

        public boolean isFlagCompel() {
            return flagCompel;
        }

        public void setFlagCompel(boolean flagCompel) {
            this.flagCompel = flagCompel;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }


}
