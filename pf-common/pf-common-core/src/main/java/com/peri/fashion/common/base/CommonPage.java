
package com.peri.fashion.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 公共分页实体
 *
 * @author Elliot
 */
@ApiModel("公共分页实体")
public class CommonPage<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = -5764853545343945831L;

    /**
     * 当前分页的数据集
     */
    @ApiModelProperty(value = "当前分页的数据集", name = "list")
    private List<T> list = null;

    /**
     * 总记录数
     */
    @ApiModelProperty(value = "总记录数", name = "totalCount")
    private int totalCount = 0;

    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数", name = "totalPage")
    private int totalPage = 0;

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页", name = "pageCurrent")
    private int pageCurrent = 1;

    /**
     * 每页记录数
     */
    @ApiModelProperty(value = "每页记录数", name = "pageSize")
    private int pageSize = 20;

    /**
     * 默认构造函数
     */
    public CommonPage() {
    }

    /**
     * 构造函数
     *
     * @param totalCount  总记录数
     * @param totalPage   总页数
     * @param pageCurrent 当前页
     * @param pageSize    每页记录数
     * @param list        当前分页的数据集
     */
    public CommonPage(int totalCount, int totalPage, int pageCurrent, int pageSize, List<T> list) {
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.pageCurrent = pageCurrent;
        this.pageSize = pageSize;
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageCurrent() {
        return pageCurrent;
    }

    public void setPageCurrent(int pageCurrent) {
        this.pageCurrent = pageCurrent;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "Page [list=" + list + ", totalCount=" + totalCount + ", totalPage=" + totalPage + ", pageCurrent=" + pageCurrent + ", pageSize=" + pageSize + "]";
    }

}
