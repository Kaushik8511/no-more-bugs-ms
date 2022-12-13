package com.codehelper.nomorebugs.payload;

import java.util.List;

public class QuestionsResponse {

	private List<QuestionDto> content;

	private int pageNo;

	private int pageSize;

	private long totalElements;

	private int totalPages;

	private boolean lastPage;

	public QuestionsResponse() {
	}

	public QuestionsResponse(List<QuestionDto> content, int pageNo, int pageSize, long totalElements, int totalPages,
			boolean lastPage) {
		this.content = content;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
		this.lastPage = lastPage;
	}

	public List<QuestionDto> getContent() {
		return content;
	}

	public void setContent(List<QuestionDto> content) {
		this.content = content;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public boolean isLastPage() {
		return lastPage;
	}

	public void setLastPage(boolean lastPage) {
		this.lastPage = lastPage;
	}

}
