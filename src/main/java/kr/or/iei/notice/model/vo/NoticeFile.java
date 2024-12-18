package kr.or.iei.notice.model.vo;

public class NoticeFile {
	private String fileNo;
	private String noticeNo;
	private String fileName;
	private String filePath;

	public NoticeFile() {
		super();
	}

	public NoticeFile(String fileNo, String noticeNo, String fileName, String filePath) {
		this.fileNo = fileNo;
		this.noticeNo = noticeNo;
		this.fileName = fileName;
		this.filePath = filePath;
	}

	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

	public String getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(String noticeNo) {
		this.noticeNo = noticeNo;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
