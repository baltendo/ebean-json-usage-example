package com.example.models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.EmbeddedColumns;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class ExampleModel extends Model {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleModel.class);

    public static final Model.Find<Long, ExampleModel> find = new Model.Find<Long, ExampleModel>() {
    };

    @Id
    private Long id;

    @Embedded
    @EmbeddedColumns(columns = "data=miscData, type=miscType")
    private TypedJson misc;

    public void setMiscAsObject(Object object) {
        if (misc == null) {
            misc = new TypedJson();
        }

        misc.setObject(object);
    }

    public Object getMiscAsObject() {
        if (misc == null) {
            return null;
        }

        try {
            return misc.getObject();
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }
}
