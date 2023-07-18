package siwei.ahon.qualitySafetyInspection.expection;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageData<T> {
    Long current;
    Long size;
    Long totalCount;
    List<T> dataList;


    public PageData(IPage<T> page) {

        List<T> list = page.getRecords();

        setDataList((List<T>) list);
        setSize((long) list.size());
        setCurrent(page.getCurrent());
        setTotalCount(page.getTotal());

    }


}
