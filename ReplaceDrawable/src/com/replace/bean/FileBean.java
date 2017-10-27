package com.replace.bean;

/**
 * Created by Administrator on 2017/3/30.
 */
public class FileBean {
    private String head;  //文件名
    private String id;
    private String last;  //后缀
    private String oldName;  //旧名称
    private String newName;  //要替换的新名

    public FileBean(String head, String last) {
        this.head = head;
        this.last = last;
        id= String.valueOf(Common.generateBeanId());
    }

    @Override
    public String toString() {
        return "FileBean{" +
                "head='" + head + '\'' +
                ", id='" + id + '\'' +
                ", last='" + last + '\'' +
                ", oldName='" + oldName + '\'' +
                ", newName='" + newName + '\'' +
                '}';
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }
}
