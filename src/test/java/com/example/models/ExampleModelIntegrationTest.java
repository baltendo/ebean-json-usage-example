package com.example.models;

import com.avaje.ebeaninternal.server.type.ModifyAwareList;
import com.example.Information;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.number.BigDecimalCloseTo.closeTo;

public class ExampleModelIntegrationTest {

    private static final long ID = 27L;

    @Before
    public void before() {
        ExampleModel model = ExampleModel.find.byId(ID);

        if (model != null) {
            model.delete();
        }
    }

    @Test
    public void shouldSaveAndLoadAsObject() {
        ExampleModel model = new ExampleModel();
        model.setId(ID);
        model.setMiscAsObject(createInformation());
        model.save();

        ExampleModel loaded = ExampleModel.find.byId(ID);
        assertThat(loaded, not(nullValue()));
        assertThat(loaded.getId(), equalTo(ID));

        String type = loaded.getMisc().getType();
        assertThat(type, equalTo("com.example.Information"));

        Object object = loaded.getMiscAsObject();
        assertThat(object, instanceOf(Information.class));

        Information information = (Information) object;
        assertThat(information.getStringValue(), equalTo("hello"));
        assertThat(information.getIntegerValue(), equalTo(17));
        assertThat(information.getDoubleValue(), equalTo(0.7));

        List<String> strings = information.getStringArray();
        assertThat(strings.size(), equalTo(2));
        assertThat(strings, hasItems("one", "two"));

        LocalDateTime dateTime = information.getDateObject();
        assertThat(dateTime.getYear(), equalTo(2000));
        assertThat(dateTime.getMonthValue(), equalTo(1));
        assertThat(dateTime.getDayOfMonth(), equalTo(1));
    }

    @Test
    public void shouldSaveAndLoadAsMap() {
        ExampleModel model = new ExampleModel();
        model.setId(ID);
        model.setMiscAsObject(createInformation());
        model.save();

        ExampleModel loaded = ExampleModel.find.byId(ID);
        assertThat(loaded, not(nullValue()));
        assertThat(loaded.getId(), equalTo(ID));

        Map<String, Object> map = loaded.getMisc().getData();
        assertThat(map.get("stringValue"), instanceOf(String.class));
        assertThat(map.get("stringValue"), equalTo("hello"));

        assertThat(map.get("doubleValue"), instanceOf(BigDecimal.class));
        assertThat((BigDecimal) map.get("doubleValue"), closeTo(new BigDecimal("0.7"), new BigDecimal("0.1")));

        assertThat(map.get("stringArray"), instanceOf(com.avaje.ebeaninternal.server.type.ModifyAwareList.class));
        ModifyAwareList<Object> stringArray = (ModifyAwareList<Object>) map.get("stringArray");
        assertThat(stringArray, hasItems("one", "two"));

        assertThat(map.get("dateObject"), instanceOf(com.avaje.ebeaninternal.server.type.ModifyAwareList.class));
        ModifyAwareList<Object> dateObject = (ModifyAwareList<Object>) map.get("dateObject");
        assertThat(dateObject, hasItems(2000L, 1L, 0L));
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
