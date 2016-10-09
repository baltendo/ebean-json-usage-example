package com.example.models;

import com.example.Information;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNull.nullValue;

public class ExampleModelTest {

    @Test
    public void shouldGetMiscWhenNull() {
        ExampleModel model = new ExampleModel();
        assertThat(model.getMisc(), nullValue());
    }

    @Test
    public void shouldSetMiscAsObjectToNull() {
        ExampleModel model = new ExampleModel();
        model.setMiscAsObject(null);

        TypedJson misc = model.getMisc();
        assertThat(misc.getData(), nullValue());
        assertThat(misc.getType(), nullValue());

        Object object = model.getMiscAsObject();
        assertThat(object, nullValue());
    }

    @Test
    public void shouldSetMiscAsObjectWithInformation() {
        ExampleModel model = new ExampleModel();
        model.setMiscAsObject(createInformation());

        TypedJson misc = model.getMisc();
        assertThat(misc.getType(), equalTo("com.example.Information"));

        Map<String, Object> data = misc.getData();
        assertThat(data, hasEntry("stringValue", "hello"));

        Object object = model.getMiscAsObject();
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
    public void shouldGetMiscAsObjectWithUnknownClass() {
        ExampleModel model = new ExampleModel();
        model.setMiscAsObject(createInformation());
        model.getMisc().setType("unknown");
        Object object = model.getMiscAsObject();
        assertThat(object, nullValue());
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void shouldGetMiscAsObjectWithWrongClass() {
        ExampleModel model = new ExampleModel();
        model.setMiscAsObject(createInformation());
        model.getMisc().setType("java.time.LocalDateTime");
        model.getMiscAsObject();
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