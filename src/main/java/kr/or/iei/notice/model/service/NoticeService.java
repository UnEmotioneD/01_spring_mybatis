package kr.or.iei.notice.model.service;

import kr.or.iei.notice.model.dao.NoticeDao;
import kr.or.iei.notice.model.vo.Notice;
import kr.or.iei.notice.model.vo.NoticeFile;
import kr.or.iei.notice.model.vo.NoticePageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;

@Service("noticeService")
public class NoticeService {

  @Autowired
  @Qualifier("noticeDao")
  private NoticeDao noticeDao;

  public NoticePageData selectNoticeList(int reqPage) {
    // 한 페이지에 보여줄 게시글의 겟수
    int viewNoticeCnt = 10;

    // 게시글 시작번호, 게시글 끝 번호
    /*
    요청 페이지 | 시작 번호 ~ 끝번호
    --|-------
    1 | 1 ~ 10
    2 | 11 ~ 20
    3 | 21 ~ 30
   ...
     */
    int end = reqPage * viewNoticeCnt;
    int start = end - viewNoticeCnt;

    HashMap<String, Integer> map = new HashMap<>();
    map.put("start", start);
    map.put("end", end);

    // 게시글 리스트
    ArrayList<Notice> list = (ArrayList<Notice>) noticeDao.selectNoticeList(map);
    // 전체 게시글 갯수
    int cnt = noticeDao.selectNoticeCount();
    // 전체 페이지 갯수
    int totPage = cnt / viewNoticeCnt;
    if (cnt % viewNoticeCnt > 0) {
      totPage++;
    }

    // 페이지 네비게이션 사이즈
    int pageNaviSize = 5;
    // 페이지 네비게이션 시작번호 설정 (1, 2, 3, 4, 5 == 1)
    int pageNo = ((reqPage - 1) / pageNaviSize) * pageNaviSize + 1;

    // HTML for pageNavi
    String pageNavi = "";
    // 이전 버튼
    if (pageNo != 1) {
      pageNavi += "<a href='/notice/getList.kh?reqPage=" + (pageNo - 1) + "'>이전</a>";
    }
    // 페이지 네비게이션
    for (int i = 0; i < pageNaviSize; i++) {
      if (pageNo == reqPage) {
        pageNavi += "<span>" + pageNo + "</span>";
      } else {
        pageNavi += "<a href='/notice/getList.kh?reqPage=" + pageNo + "'>" + pageNo + "</a>";
      }
      pageNo++;

      if (pageNo > totPage) {
        break;
      }
    }
    // 다음 버튼
    if (pageNo <= totPage) {
      pageNavi += "<a href='/notice/getList.kh?reqPage=" + pageNo + "'>다음</a>";
    }

    NoticePageData pageData = new NoticePageData();
    pageData.setNoticeList(list);
    pageData.setPageNavi(pageNavi);

    return pageData;
  } // selectNoticeList()

  @Transactional
  // - RuntimeException 또는 Error 만 롤백 대상이 된다
  // - Transactional 어노테이션이 붙은 메소드에서 동일 클래스 내부에 다른 메소드 호출 시 관리가 안된다
  public int insertNotice(Notice notice, ArrayList<NoticeFile> fileList) {
    /*
    tbl_notice      : insert
    tbl_notice_file : insert

    tbl_notice_file.notice_no 는 tbl_notice.notice_no 로 존재 해야 한다

    SQL 수행 순서
    1) tbl_notice      - insert
      - SQL : insert into tbl_notice (notice_no) values (seq_notice.nextval)
    2) tbl_notice_file - insert
     */

    // 등록할 게시글의 번호를 먼저 조회(notice_file 정보 등록 시 모두 필요하므로)
    String noticeNo = noticeDao.selectNoticeNo();
    // tbl_notice, tbl_notice_file 은 참조하는 관계이므로 tbl_notice 먼저
    notice.setNoticeNo(noticeNo);
    int result = noticeDao.insertNotice(notice);

    if (result > 0) {
      for (NoticeFile file : fileList) {
        file.setNoticeNo(noticeNo);
        result = noticeDao.insertNoticeFile(file);
      }
    }
    return result;
  }

  public Notice selectOneNotice(String noticeNo) {
    Notice notice = noticeDao.selectOneNotice(noticeNo);

    if (notice != null) {
      ArrayList<NoticeFile> fileList = (ArrayList<NoticeFile>) noticeDao.selectNoticeFileList(noticeNo);
      notice.setFileList(fileList);
    }
    return notice;
  }

  public ArrayList<NoticeFile> deleteNotice(String noticeNo) {
    /*
    1) 현재 게시글에 대한 파일 정보를 조회
        2) 게시글 정보 DB 에서 삭제 (tbl_notice)
      --  3) 게시그렝 대한 피일 정보 DB 에서 삭제(tbl_notice_file)
            (2) 번 수행시 on delete cascade 가 있어서
    4) 서버에 업로드된 파일 정보 삭제
     */
    ArrayList<NoticeFile> list = (ArrayList<NoticeFile>) noticeDao.selectNoticeFileList(noticeNo);

    int result = noticeDao.deleteNotice(noticeNo);

    if (result > 0) {
      return list;
    } else {
      return null;
    }
  }

  @Transactional
  public ArrayList<NoticeFile> updateNotice(Notice notice, ArrayList<NoticeFile> fileList) {
    /*
    1) 게시글 정보 수정(tbl_notice)
    2) 서버에서 기존 파일 정보를 삭제하기 위한 조회
    3) 기존 파일 정보 삭제 처리
    4) 업로드한 파일 정보 등록 (tbl_notice_file)
     */

    // 1) 게시글 정보 수정
    int result = noticeDao.updateNotice(notice);

    ArrayList<NoticeFile> deleteFileList = null;
    if (result > 0) {
      // 2) 기존 파일 정보 삭제를 위한 파일 리스트 조회
      deleteFileList = (ArrayList<NoticeFile>) noticeDao.selectNoticeFileList(notice.getNoticeNo());

      // 3) 기존 파일 정보 DB 에서 삭제 처리
      result = noticeDao.deleteNoticeFile(notice.getNoticeNo());

      if (result > 0 && fileList.size() > 0) {
        // 4) 업로드한 파일 정보 DB에 등록 처리
        for (NoticeFile insertFile : fileList) {
          noticeDao.insertNoticeFile(insertFile);
        }
      }
    }
    return deleteFileList;
  }
}
