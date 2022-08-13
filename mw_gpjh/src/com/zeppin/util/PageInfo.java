package com.zeppin.util;

import java.util.ArrayList;
import java.util.List;

public class PageInfo {

	private int totalRow;
	private int totalPage;
	private int currentPage = 1;
	private int pageSize = 20;
	private boolean hasPrevious;
	private boolean hasNext;
	private boolean bof;
	private boolean eof;
	private int showNum = 5;
	
	private int nextPage;
	private int previousPage;
	
	public PageInfo(int totalRow, int pageSize, int page){
		this.totalRow = totalRow;
		this.pageSize = pageSize;
		this.totalPage = countTotalPage(this.pageSize, this.totalRow);
		
		setCurrentPage(page);
		
		init();
	}

	public int getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		
		if(this.currentPage > this.totalPage){
			this.currentPage = this.totalPage;
		} else if(this.currentPage < 1){
			this.currentPage = 1;
		} else{
			this.currentPage = currentPage;
		}
	}
	
	public int getBeginIndex(){
		int beginIndex = (currentPage - 1) * pageSize;
		
		return beginIndex;
	}
	
	public int countTotalPage(int pageSize, int totalRow){
		int totalPage = totalRow % pageSize == 0 ? totalRow/pageSize : totalRow/pageSize + 1;
		return totalPage;
	}
	
	public int getNextPage(){
		if(currentPage + 1 > this.totalPage) {
			return this.totalPage;
		}
		return currentPage + 1;
	}
	
	public int getPreviousPage(){
		if(currentPage - 1 <= 1) {
			return 1;
		} else {
			return currentPage -1;
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean HasPrevious() {
		return currentPage > 1;
	}


	public boolean hasNext() {
		return currentPage < this.totalPage;
	}

	
	public boolean isFirst(){
		return currentPage == 1;
	}

	public boolean isLast(){
		return currentPage >= this.totalPage;
	}
	
	public boolean isBof() {
		return bof;
	}


	public boolean isEof() {
		return eof;
	}
	
	public List pageList(){
		
		List ret = new ArrayList();
		int startPage = 1;
		
		if(this.currentPage > this.showNum/2 && this.totalPage - this.currentPage >= this.showNum/2)  startPage = this.currentPage - 2;
		if(this.totalPage > this.showNum && this.totalPage - this.currentPage < this.showNum/2) startPage = this.totalPage - this.showNum + (this.totalPage - this.currentPage) < 1 ? 1 : this.totalPage - this.showNum + (this.totalPage - this.currentPage);
		if(this.totalPage > this.showNum && this.totalPage == this.currentPage) startPage = this.totalPage - this.showNum + 1 < 1 ? 1 : this.totalPage - this.showNum + 1;
		
		for(int i = startPage; i < startPage + this.showNum; i++){
			if(i <= this.totalPage){
				ret.add(i+"");
			}
		}
		
		return ret;
	}
	private void init(){
		this.hasNext = hasNext();
		this.hasPrevious = HasPrevious();
		this.bof = isFirst();
		this.eof = isLast();
	}

}
