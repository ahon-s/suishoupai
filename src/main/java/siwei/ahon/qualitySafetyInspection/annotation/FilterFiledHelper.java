package siwei.ahon.qualitySafetyInspection.annotation;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.util.ObjectUtils.isEmpty;
import static siwei.ahon.qualitySafetyInspection.util.VariableNameConversion.humpToLowerLine;


@Component
public class FilterFiledHelper {

    public  Map<String, Object> getFilterField(Object oj)  {
        HashMap<String, Object> filterMap = new HashMap<>();
        HashMap<String, Object> eqMap = new HashMap<>();
        HashMap<String, Object> likeMap = new HashMap<>();
        HashMap<String, Object> leftMap = new HashMap<>();
        HashMap<String, Object> rightMap = new HashMap<>();

        Field[] declaredFields = oj.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            String name=declaredField.getName();
            FilterFiled annotation = declaredField.getAnnotation(FilterFiled.class);
            if (annotation==null){
                continue;
            }
            FilterTypeEnum type = annotation.type();
            Object value = null;
            try {
                value = declaredField.get(oj);
//                System.out.println("value::"+value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if(annotation!=null&&value!=null){
                if(type.equals(FilterTypeEnum.EQ)){
                    eqMap.put(name,value);
                }else if(type.equals(FilterTypeEnum.LIKE)){
                    likeMap.put(name,value);
                }
                else if(type.equals(FilterTypeEnum.LEFT)){
                    leftMap.put(name,value);
                }
                else if(type.equals(FilterTypeEnum.RIGHT)){
                    rightMap.put(name,value);
                }
            }

        }
        filterMap.put("eq",eqMap);
        filterMap.put("like",likeMap);
        filterMap.put("left",leftMap);
        filterMap.put("right",rightMap);

        return filterMap;
    }

    public QueryWrapper getQueryWrapper(QueryWrapper qw,Object oj) {

        Map<String, Object> filterField = this.getFilterField(oj);
        Map<String, Object> eqMap = (Map<String, Object>) filterField.get("eq");
        if(!isEmpty(eqMap)) {
            for (Map.Entry<String, Object> stringObjectEntry : eqMap.entrySet()) {
                if(!isEmpty(stringObjectEntry.getValue())) {
                    qw.eq(humpToLowerLine(stringObjectEntry.getKey()), stringObjectEntry.getValue());
                }
            }
        }
        Map<String, Object> likeMap = (Map<String, Object>) filterField.get("like");
        if(!isEmpty(likeMap)) {

            for (Map.Entry<String, Object> stringObjectEntry : likeMap.entrySet()) {
                if(!isEmpty(stringObjectEntry.getValue())) {
                    qw.like(humpToLowerLine(stringObjectEntry.getKey()), stringObjectEntry.getValue());
                }
            }
        }
        Map<String, Object> leftMap = (Map<String, Object>) filterField.get("left");
        if(!isEmpty(leftMap)) {
            for (Map.Entry<String, Object> stringObjectEntry : leftMap.entrySet()) {
                if(!isEmpty(stringObjectEntry.getValue())) {
                    qw.likeLeft(humpToLowerLine(stringObjectEntry.getKey()), stringObjectEntry.getValue());
                }
            }
        }
        Map<String, Object> rightMap = (Map<String, Object>) filterField.get("right");
        if(!isEmpty(rightMap)) {
            for (Map.Entry<String, Object> stringObjectEntry : rightMap.entrySet()) {
                if(!isEmpty(stringObjectEntry.getValue())) {
                    qw.likeRight(humpToLowerLine(stringObjectEntry.getKey()), stringObjectEntry.getValue());
                }
            }
        }

        return qw;
    }


}
