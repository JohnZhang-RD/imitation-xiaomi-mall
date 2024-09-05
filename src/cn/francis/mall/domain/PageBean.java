package cn.francis.mall.domain;

import java.util.List;

/**
 * Name: PageBean
 * Package: cn.francis.mall.domain
 * date: 2024/09/05 - 15:20
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */

public class PageBean<T> {
    private Integer pageNum; // 页数
    private Integer pageSize; // 页大小
    private Long totalSize; // 总数据条数
    private Integer pageCount; // 总页数
    private List<T> data; // 数据
    private Integer startPage;
    private Integer endPage;

    public PageBean(Integer pageNum, Integer pageSize, Long totalSize, List<T> data) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalSize = totalSize;
        this.data = data;
        this.pageCount = (int) (totalSize % pageSize == 0 ? totalSize / pageSize : totalSize / pageSize + 1);

        this.startPage = this.pageNum - 4;
        this.endPage = this.pageNum + 5;

        if (this.pageNum < 5) {
            this.startPage = 1;
            this.endPage = 10;
        }

        if (this.pageNum > this.pageCount - 5) {
            this.startPage = pageCount - 9;
            this.endPage = pageCount;
        }

        if (this.pageCount < 10) {
            this.startPage = 1;
            this.endPage = pageCount;
        }
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Integer getStartPage() {
        return startPage;
    }

    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }

    public Integer getEndPage() {
        return endPage;
    }

    public void setEndPage(Integer endPage) {
        this.endPage = endPage;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                       "pageNum=" + pageNum +
                       ", pageSize=" + pageSize +
                       ", totalSize=" + totalSize +
                       ", pageCount=" + pageCount +
                       ", data=" + data +
                       ", startPage=" + startPage +
                       ", endPage=" + endPage +
                       '}';
    }
}
