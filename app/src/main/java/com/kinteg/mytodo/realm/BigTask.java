package com.kinteg.mytodo.realm;

import java.util.Date;

import io.realm.RealmObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BigTask extends RealmObject {

    private String name;
    private Date create;
    private Date closed;
    private boolean completed;


}
