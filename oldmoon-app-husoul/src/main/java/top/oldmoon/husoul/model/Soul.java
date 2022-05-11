package top.oldmoon.husoul.model;

import oldmoon.api.str.StringUtilOm;

public class Soul {
    private String id;
    private String soul_name;
    private String soul_type;
    private String soul_time;
    private String soul_info;
    private String soul_author;
    private String type_name;
    private String update_time;
    private String status;
    private String sort;
    private String read_num;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSoul_name() {
        return soul_name;
    }

    public void setSoul_name(String soul_name) {
        this.soul_name = soul_name;
    }

    public String getSoul_type() {
        return soul_type;
    }

    public void setSoul_type(String soul_type) {
        this.soul_type = soul_type;
    }

    public String getSoul_time() {
        return soul_time;
    }

    public void setSoul_time(String soul_time) {
        this.soul_time = soul_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getSoul_info() {
        return soul_info;
    }

    public void setSoul_info(String soul_info) {
        this.soul_info = soul_info;
    }

    public String getSoul_author() {
        return soul_author;
    }

    public void setSoul_author(String soul_author) {
        this.soul_author = soul_author;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getRead_num() {
        return read_num;
    }

    public void setRead_num(String read_num) {
        this.read_num = read_num;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (StringUtilOm.isEmpty(this.id) || obj == null || !Object.class.equals(this.getClass())) {
            return false;
        }
        return this.id.equals(((Soul) obj).getId());
    }
}
