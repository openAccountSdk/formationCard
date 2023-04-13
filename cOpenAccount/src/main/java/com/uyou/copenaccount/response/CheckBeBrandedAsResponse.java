package com.uyou.copenaccount.response;


import com.uyou.copenaccount.base.BaseResponse;

public class CheckBeBrandedAsResponse extends BaseResponse {

    private Data data;

    public Data getData() {
        return data;
    }

    public static class Data {
        private String clues;//提示语，为空继续开户，不为空，弹框提示
        private String basePhotoDir;//活体截图数据库存储路径
        private String baseVideoDir;//活体视频数据存储路径

        public String getClues() {
            return clues;
        }

        public void setClues(String clues) {
            this.clues = clues;
        }

        public String getBasePhotoDir() {
            return basePhotoDir;
        }

        public void setBasePhotoDir(String basePhotoDir) {
            this.basePhotoDir = basePhotoDir;
        }

        public String getBaseVideoDir() {
            return baseVideoDir;
        }

        public void setBaseVideoDir(String baseVideoDir) {
            this.baseVideoDir = baseVideoDir;
        }
    }
}
