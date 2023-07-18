package siwei.ahon.qualitySafetyInspection.pojo;


import org.springframework.stereotype.Component;


@Component
public class PageFilterPojo {
    String sTime="2000-01-01";
    String eTime="2099-01-01";
    Integer pageNum=1;
    Integer pageSize=100;


    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }

    public String geteTime() {
        return eTime;
    }

    public void seteTime(String eTime) {
        this.eTime = eTime;
    }


    public Integer getPageNum() {
        if (pageNum==null){
            pageNum=1;
        }
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        if(pageNum<1){
            pageNum=1;
        }
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        if(pageSize<=1000) {
            return pageSize;
        }else {
            return 1000;
        }
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "PageFilterPojo{" +
                "sTime='" + sTime + '\'' +
                ", eTime='" + eTime + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
