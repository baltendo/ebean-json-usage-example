package com.example.util;

import com.example.Information;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;


public class JsonUtilTest {

    @Test
    public void shouldSerializeAndDeserializeMap() {
        Information information = createInformation();
        Map<String, Object> json = JsonUtil.toJsonMap(information);

        Information object = (Information) JsonUtil.fromJsonMap(json, Information.class);
        assertThat(object.getDoubleValue(), equalTo(information.getDoubleValue()));
    }

    @Test
    public void shouldSerializeLocalDateTimeToJson() {
        Information information = createInformation();
        Map<String, Object> json = JsonUtil.toJsonMap(information);

        Object dateObject = json.get("dateObject");
        assertThat(dateObject, instanceOf(java.util.List.class));
    }

    private Information createInformation() {
        Information information = new Information();
        information.setIntegerValue(17);
        information.setDoubleValue(0.7);
        information.setStringValue("hello");
        List<String> strings = new ArrayList<>();
        strings.add("one");
        strings.add("two");
        information.setStringArray(strings);
        LocalDateTime dateTime = LocalDateTime.of(2000, 1, 1, 0, 0);
        information.setDateObject(dateTime);
        return information;
    }
}