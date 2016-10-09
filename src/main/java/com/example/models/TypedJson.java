package com.example.models;

import com.avaje.ebean.annotation.DbJsonB;
import com.example.util.JsonUtil;
import lombok.Data;

import javax.persistence.Embeddable;
import java.util.Map;

@Data
@Embeddable
public class TypedJson {

    @DbJsonB
    private Map<String, Object> data;

    private String type;

    public void setObject(Object object) {
        if (object == null) {
            data = null;
            type = null;
            return;
        }

        type = object.getClass().getTypeName();
        data = JsonUtil.toJsonMap(object);
    }

    public Object getObject() throws ClassNotFoundException {
        if (data == null || type == null) {
            return null;
        }

        Class c = Class.forName(type);
        return JsonUtil.fromJsonMap(data, c);
    }
}
