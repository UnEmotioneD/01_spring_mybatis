package kr.or.iei.notice.model.vo;

import java.util.ArrayList;

public class Notice {
    private String noticeNo;
    private String noticeTitle;
    private String noticeContent;
    private String noticeWriter;
    private String noticeDate;

    private ArrayList<NoticeFile> fileList;

    public Notice() {
        super();
    }

    public Notice(String noticeNo, String noticeTitle, String noticeContent, String noticeWriter, String noticeDate) {
        this.noticeNo = noticeNo;
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
        this.noticeWriter = noticeWriter;
        this.noticeDate = noticeDate;
    }

    public String getNoticeNo() {
        return noticeNo;
    }

    public void setNoticeNo(String noticeNo) {
        this.noticeNo = noticeNo;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getNoticeWriter() {
        return noticeWriter;
    }

    public void setNoticeWriter(String noticeWriter) {
        this.noticeWriter = noticeWriter;
    }

    public String getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(String noticeDate) {
        this.noticeDate = noticeDate;
    }

    public ArrayList<NoticeFile> getFileList() {
        return fileList;
    }

    public void setFileList(ArrayList<NoticeFile> fileList) {
        this.fileList = fileList;
    }

    @Override
    public String toString() {
        return "Notice{" +
            "noticeNo='" + noticeNo + '\'' +
            ", noticeTitle='" + noticeTitle + '\'' +
            ", noticeContent='" + noticeContent + '\'' +
            ", noticeWriter='" + noticeWriter + '\'' +
            ", noticeDate='" + noticeDate + '\'' +
            '}';
    }
}
