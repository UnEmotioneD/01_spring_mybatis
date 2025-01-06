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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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

    @GetMapping(value = "fileDown.kh", produces = "application/octet-stream;")
    public void noticeFileDown(HttpServletRequest request, HttpServletResponse response, String fileName, String filePath) {

        // 파일 위치 절대 경로
        String savePath = request.getSession().getServletContext().getRealPath("/resources/upload/notice/");

        // 파일 다운로드를 위한 보조 스트림 선언
        BufferedOutputStream bos = null; // 읽어들인 파일을 내보내기(다운로드) 하기 위한 보조스트림 선언
        BufferedInputStream bis = null; // 서버에서 파일을 읽어들이기 위한 보조 스트림

        try {
            FileInputStream fis = new FileInputStream(savePath + filePath);

            // 보조스트림과 주 스트림 연결
            bis = new BufferedInputStream(fis);

            // 파일을 내보내기 위한 스트림 생성
            ServletOutputStream sos = response.getOutputStream();

            bos = new BufferedOutputStream(sos);

            // 다운로드 파일명 설정
            String resFileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");

            // 브라우저에게 다운로드 자시 및 다운로드 파일명 지정
            response.setHeader("Content-Disposition", "attachment;filename=" + resFileName);

            while (true) {
                int read = bis.read();
                if (read == -1) { // 일을 데이터가 없을때 -1을 반환한다
                    break;
                } else {
                    bos.write(read);
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                bos.close();
                bis.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @GetMapping("delete.kh")
    public String delete(HttpServletRequest request, String noticeNo) {

        ArrayList<NoticeFile> fileList = noticeService.deleteNotice(noticeNo);

        if (fileList != null && fileList.size() > 0) {
            String savePath = request.getSession().getServletContext().getRealPath("/resources/upload/notice/");

            for (NoticeFile file : fileList) {
                File f = new File(savePath + file.getFilePath());
                if (f.exists()) {
                    f.delete();
                }
            }
        }
        return "redirect:/notice/getList.kh?reqPage=1";
    }

    @GetMapping("updateFrm.kh")
    public String updateFrm(String noticeNo, Model model) {

        Notice notice = noticeService.selectOneNotice(noticeNo);
        model.addAttribute("notice", notice);

        return "notice/update";
    }

    @PostMapping("update.kh")
    public String update(HttpServletRequest request, MultipartFile[] files, Notice notice) {

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

                BufferedOutputStream bos = null;

                try {
                    byte[] bytes = file.getBytes();
                    FileOutputStream fos = new FileOutputStream(new File(savePath));
                    bos = new BufferedOutputStream(fos);
                    bos.write(bytes);

                    NoticeFile noticeFile = new NoticeFile();

          /*
          게시글 신규 등록
          - tbl_notice 와 tbl_notice_file 은 참조 관계이므로
          서비스에서 notice_no 를 선 조회

          게시글 수정
          - 수정이니까, 클라이언트 전달 파라미터에 게시글에 대한 번호가 포함되어있다
          전달한 notice_no 를 NoticeFile 객체에 Set
           */
                    notice.setNoticeNo(notice.getNoticeNo());
                    noticeFile.setFileName(originalFileName);
                    noticeFile.setFilePath(filePath);

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

        // 기존 파일 정보 == 삭제 대상 파일 리스트
        ArrayList<NoticeFile> deleteFileList = noticeService.updateNotice(notice, fileList);

        if (deleteFileList != null && deleteFileList.size() > 0) {
            String savePath = request.getSession().getServletContext().getRealPath("/resources/upload/notice/");

            for (NoticeFile file : deleteFileList) {
                File f = new File(savePath + file.getFilePath());
                if (f.exists()) {
                    f.delete();
                }
            }
        }
        return "notice/getList.kh?reqPage=1";
    }

}
