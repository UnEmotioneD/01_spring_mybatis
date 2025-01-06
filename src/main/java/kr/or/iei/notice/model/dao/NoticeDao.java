package kr.or.iei.notice.model.dao;

import kr.or.iei.notice.model.vo.Notice;
import kr.or.iei.notice.model.vo.NoticeFile;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository("noticeDao")
public class NoticeDao {

    @Autowired
    @Qualifier("sqlSessionTemplate")
    private SqlSessionTemplate sqlSessionTemplate;

    public List<Notice> selectNoticeList(HashMap<String, Integer> map) {
        return sqlSessionTemplate.selectList("notice.selectNoticeList", map);
    }

    public int selectNoticeCount() {
        return sqlSessionTemplate.selectOne("notice.selectNoticeCount");
    }

    public int insertNotice(Notice notice) {
        return sqlSessionTemplate.insert("notice.insertNotice", notice);
    }

    public String selectNoticeNo() {
        return sqlSessionTemplate.selectOne("notice.selectNoticeNo");
    }

    public int insertNoticeFile(NoticeFile file) {
        return sqlSessionTemplate.insert("notice.insertNoticeFile", file);
    }

    public Notice selectOneNotice(String noticeNo) {
        return sqlSessionTemplate.selectOne("notice.selectOneNotice", noticeNo);
    }

    public Object selectNoticeFileList(String noticeNo) {
        return sqlSessionTemplate.selectList("notice.selectNoticeFileList", noticeNo);
    }

    public int deleteNotice(String noticeNo) {
        return sqlSessionTemplate.delete("notice.deleteNotice", noticeNo);
    }

    public int updateNotice(Notice notice) {
        return sqlSessionTemplate.update("notice.updateNotice", notice);
    }

    public int deleteNoticeFile(String noticeNo) {
        return sqlSessionTemplate.delete("notice.deleteNoticeFile", noticeNo);
    }
}
