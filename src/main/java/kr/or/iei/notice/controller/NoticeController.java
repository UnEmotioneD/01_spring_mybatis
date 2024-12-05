package kr.or.iei.notice.controller;

import kr.or.iei.notice.model.service.NoticeService;
import kr.or.iei.notice.model.vo.Notice;
import kr.or.iei.notice.model.vo.NoticeFile;
import kr.or.iei.notice.model.vo.NoticePageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

@Controller
@RequestMapping("/notice/")
public class NoticeController {

  @Autowired
  @Qualifier("noticeService")
  private NoticeService noticeService;

  @GetMapping("getList.kh")
  public String getList(int reqPage, Model model) {
    NoticePageData pd = noticeService.selectNoticeList(reqPage);

    // 응답 페이지 (list.jsp) 에서 게시글 목록을 보여주기 위해, 응답 데이터를 등록할 수 있는 Model 객체 매개변수에 추가
    model.addAttribute("list", pd.getNoticeList());
    model.addAttribute("pageNavi", pd.getPageNavi());

    return "notice/list";
  }

  @GetMapping("writeFrm.kh")
  public String writeFrm() {
    return "notice/write";
  }

  @PostMapping("write.kh")
  public String write(HttpServletRequest request, MultipartFile[] files, Notice notice) {
    // 서비스에 파일 정보를 전달하기 위한 ArrayList
    ArrayList<NoticeFile> fileList = new ArrayList<>();

    for (int i = 0; i < files.length; i++) {
      MultipartFile file = files[i];

      if (!file.isEmpty()) {
        String savePath = request.getSession().getServletContext().getRealPath("/resources/upload/notice/");
        String originalFileName = file.getOriginalFilename(); // 업로드한 파일명
        String fileName = originalFileName.substring(0, originalFileName.lastIndexOf(".")); // 확장자를 제외한 파일명
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

        String toDay = new SimpleDateFormat("yyyyMMdd").format(new Date());
        int ranNum = new Random().nextInt(10000) + 1;
        String filePath = fileName + "_" + toDay + "_" + ranNum + extension;

        savePath += filePath;

        // 파일 업로드를 위한 보조스트림
        BufferedOutputStream bos = null;

        try {
          byte[] bytes = file.getBytes();
          FileOutputStream fos = new FileOutputStream(new File(savePath));
          bos = new BufferedOutputStream(fos);
          bos.write(bytes);

          NoticeFile noticeFile = new NoticeFile();
          // tbl_notice_file (file_no, notice_no, file_name, file_path)
          // 해당 컬럼에 대한 작업 ^^^^^^^^^, ^^^^^^^^^
          noticeFile.setFileName(originalFileName); // 원본 파일명
          noticeFile.setFilePath(filePath); // 업로드 파일명

          // 리스트에 추가
          fileList.add(noticeFile);

        } catch (IOException e) {
          e.printStackTrace();
        } finally {
          try {
            if (bos != null) {
              bos.close();
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }
    int result = noticeService.insertNotice(notice, fileList);

    if (result > 0) {
      return "redirect:/notice/getList.kh?reqPage=1";
    } else {
      return "notice/writeFail";
    }

  } // write()

  @GetMapping("detailView.kh")
  public String detailView(String noticeNo, Model model) {
    Notice notice = noticeService.selectOneNotice(noticeNo);

    model.addAttribute("notice", notice);

    return "notice/detailView";
  }

}
