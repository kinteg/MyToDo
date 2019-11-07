package com.kinteg.mytodo.store;

import android.util.Log;

import com.kinteg.mytodo.realm.BigTask;

import java.text.ParseException;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;
import lombok.Getter;

public class StoreBigTask {
    @Getter
    private final static StoreBigTask STORE_BIG_TASK = new StoreBigTask();
    private final static String FILED_NAME = "create";
    private RealmResults<BigTask> bigTasks;
    private Realm mRealm;

    private StoreBigTask() {
        mRealm = Realm.getDefaultInstance();
        mRealm.beginTransaction();
        bigTasks = mRealm.where(BigTask.class).findAll();
        mRealm.commitTransaction();
    }


    public void add(String name, Date create, Date closed, boolean completed) {
        mRealm.beginTransaction();
        BigTask bigTask = mRealm.createObject(BigTask.class);
        bigTask.setName(name);
        bigTask.setCreate(create);
        bigTask.setClosed(closed);
        bigTask.setCompleted(completed);
        bigTasks = mRealm.where(BigTask.class).findAll();
        mRealm.commitTransaction();
    }

    public RealmResults<BigTask> getAll() {
        return bigTasks;
    }

    public RealmResults<BigTask> getAll(Days days) {
        return findTasks(days);
    }

    public RealmResults<BigTask> getAll(Date date) {
        return findTasks(date);
    }

    public void remove(BigTask bigTask) {
        mRealm.beginTransaction();
        bigTask.deleteFromRealm();
        mRealm.commitTransaction();
    }

    public void setCheck(boolean b, BigTask bigTask) {
        mRealm.beginTransaction();
        bigTask.setCompleted(b);
        mRealm.commitTransaction();
    }


    public int getSize(Days days) {
        return findTasks(days).size();
    }

    public int getSize(Date date) {
        return findTasks(date).size();
    }

    private RealmResults<BigTask> findTasks(Days days)  {
        RealmResults<BigTask> bigTasks1;
        Date startTime = days.getStartTime();
        Date endTime = days.getEndTime();
        long start = days.getStart();
        long end = days.getEnd();
        mRealm.beginTransaction();
        if (end != 0) {
            bigTasks1 = mRealm.where(BigTask.class).between(FILED_NAME, startTime, endTime).findAll();
        } else {
            if (start > 0) {
                bigTasks1 = mRealm.where(BigTask.class).greaterThan(FILED_NAME, new Date(startTime.getTime() - 1)).findAll();
            } else if (start < 0) {
                bigTasks1 = mRealm.where(BigTask.class).lessThan(FILED_NAME, startTime).findAll();
            } else {
                bigTasks1 = mRealm.where(BigTask.class).equalTo(FILED_NAME, (Date) null).findAll();
            }
        }
        mRealm.commitTransaction();
        return bigTasks1;
    }

    private RealmResults<BigTask> findTasks(Date date) {
        RealmResults<BigTask> bigTasks1;
        mRealm.beginTransaction();
        bigTasks1 = mRealm.where(BigTask.class).between(FILED_NAME, new Date(date.getTime() - 10000), new Date(date.getTime() + 86400000 - 10000)).findAll();
        mRealm.commitTransaction();
        return bigTasks1;
    }


}
