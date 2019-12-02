package com.luffy.nfclib.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lvlufei on 2019/7/29
 *
 * @name
 * @desc
 */
public class MifareClassicBean implements Serializable {
    private String cardId;//卡片ID
    private String tagType;//卡片类型
    private String sector;//扇区数
    private String block;//块数
    private String size;//存储空间
    private List<InfoBean> info;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * contentHex : 0
         * contentDecimal : 0
         * sectorPosition : 0
         * blockPosition : 0
         */

        private String contentHex;//十六进制的内容字符串
        private String contentDecimal;//十进制的内容字符串
        private String sectorPosition;//扇区位置
        private String blockPosition;//块位置

        public String getContentHex() {
            return contentHex;
        }

        public void setContentHex(String contentHex) {
            this.contentHex = contentHex;
        }

        public String getContentDecimal() {
            return contentDecimal;
        }

        public void setContentDecimal(String contentDecimal) {
            this.contentDecimal = contentDecimal;
        }

        public String getSectorPosition() {
            return sectorPosition;
        }

        public void setSectorPosition(String sectorPosition) {
            this.sectorPosition = sectorPosition;
        }

        public String getBlockPosition() {
            return blockPosition;
        }

        public void setBlockPosition(String blockPosition) {
            this.blockPosition = blockPosition;
        }
    }
}
