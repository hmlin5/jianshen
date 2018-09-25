package com.gimc.user.common.utils.burningUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageBean<T> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//直接初始化得到项
	private Integer currentPage = 1;//当前页
	private Integer pageSize = 2;//每页最大记录数,默认值为2;
	private Integer totalRecord = 0;//总记录数
	
	//计算得到项
	private Integer prePage;//上一页
	private Integer nextPage;//下一页
	private Integer startIndex;//当前页在数据库中的开始索引
	private Integer totalPage = 0;//总页数
	
	private List<T> pageData = new ArrayList<T>(0);//分页数据
	
	//动态显示条
	private Integer start = 1;
	private Integer end = 10;
	
	
	//============构造函数===============================
	
	
		//1.提供构造函数,自动为计算项赋值,每页最大记录数由使用者指定
		public PageBean(Integer currentPage,Integer pageSize,Integer totalRecord){
			
			//为计算项赋值
			this.totalPage = (totalRecord+pageSize-1)/pageSize;//pageSize-1可以理解为最大半页,可以保证和原本不整除的部分凑够一页数据,这样除法得到的整数部分就可以表示所需要的总页数了
			
			this.currentPage = currentPage > totalPage ? totalPage : currentPage; //避免请求也大于总页数
			this.startIndex = (this.currentPage-1)*pageSize;
			this.totalRecord = totalRecord;
			this.pageSize = pageSize;
			
			//展示当前页的前4页和后5页,简称前4后5模式
			start = currentPage-4;
			end = currentPage+5;
			if (start<1) {
				start = 1;
				end = 10;
			}
			
			if (totalPage<10) {
				this.end = totalPage;
			}else if (end>totalPage) {
				end = totalPage;
				start = end - 9;
			}
			
			
			
		}

		//2.提供构造函数,自动为计算项赋值,每页最大记录数使用pageBean默认值
		public PageBean(Integer currentPage,Integer totalRecord){
			this.currentPage = currentPage;
			this.totalRecord = totalRecord;
			
			this.startIndex = (currentPage-1)*pageSize;
			this.totalPage = (totalRecord+pageSize-1)/pageSize;
			
				
			//展示当前页的前4页和后5页,简称前4后5模式
			start = currentPage-4;
			end = currentPage+5;
			if (start<1) {
				start = 1;
				end = 10;
			}
			
			if (totalPage<10) {
				this.end = totalPage;
			}else if (end>totalPage) {
				end = totalPage;
				start = end - 9;
			}
		}
		
		//3.空构造函数
		public PageBean() {
		}
	//============构造函数结束===============================

		
		
	//============getter,setter===============================
		public Integer getCurrentPage() {
			return currentPage;
		}


		public void setCurrentPage(Integer currentPage) {
			this.currentPage = currentPage;
		}


		public Integer getPageSize() {
			return pageSize;
		}


		public void setPageSize(Integer pageSize) {
			this.pageSize = pageSize;
		}


		public Integer getTotalRecord() {
			return totalRecord;
		}


		public void setTotalRecord(Integer totalRecord) {
			this.totalRecord = totalRecord;
		}


		public Integer getStartIndex() {
			return startIndex;
		}


		public void setStartIndex(Integer startIndex) {
			this.startIndex = startIndex;
		}


		public Integer getTotalPage() {
			return totalPage;
		}


		public void setTotalPage(Integer totalPage) {
			this.totalPage = totalPage;
		}


		public List<T> getPageData() {
			return pageData;
		}


		public void setPageData(List<T> pageData) {
			this.pageData = pageData;
		}

		//改造getPrePage方法
		public Integer getPrePage() {
			prePage = currentPage-1;
			if (prePage<1) {
				prePage = 1;
			}
			return prePage;
		}

		public void setPrePage(Integer prePage) {
			this.prePage = prePage;
		}

		//改造getNextPage方法
		public Integer getNextPage() {
			nextPage = currentPage+1;
			if (nextPage>totalPage) {
				nextPage = totalPage;
			}
			if (nextPage<1) {
				nextPage = 1;
			}
			return nextPage;
		}

		public void setNextPage(Integer nextPage) {
			this.nextPage = nextPage;
		}
		
		public Integer getStart() {
			return start;
		}

		public void setStart(Integer start) {
			this.start = start;
		}

		public Integer getEnd() {
			return end;
		}

		public void setEnd(Integer end) {
			this.end = end;
		}

		
}
