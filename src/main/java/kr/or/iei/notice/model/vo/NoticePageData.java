package kr.or.iei.notice.model.vo;

import java.util.ArrayList;

public class NoticePageData {
    private ArrayList<Notice> noticeList;
    private String pageNavi;

    public NoticePageData() {
        super();
    }

    public NoticePageData(ArrayList<Notice> noticeList, String pageNavi) {
        super();
        this.noticeList = noticeList;
        this.pageNavi = pageNavi;
    }

    public ArrayList<Notice> getNoticeList() {
        return noticeList;
    }

    public void setNoticeList(ArrayList<Notice> noticeList) {
        this.noticeList = noticeList;
    }

    public String getPageNavi() {
        return pageNavi;
    }

    public void setPageNavi(String pageNavi) {
        this.pageNavi = pageNavi;
    }
}
