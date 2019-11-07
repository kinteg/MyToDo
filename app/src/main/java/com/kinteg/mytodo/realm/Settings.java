package com.kinteg.mytodo.realm;

import io.realm.RealmObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Settings extends RealmObject {

    private int theme;
    private String locale;

}
