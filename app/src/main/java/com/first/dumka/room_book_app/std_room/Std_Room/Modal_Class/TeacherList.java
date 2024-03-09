package com.first.dumka.room_book_app.std_room.Std_Room.Modal_Class;

public class TeacherList {

    String tech_id,tech_name,tech_qualification,tech_specialization,tech_status,tech_img;

    public TeacherList(String tech_id, String tech_name, String tech_qualification, String tech_specialization, String tech_status, String tech_img) {
        this.tech_id = tech_id;
        this.tech_name = tech_name;
        this.tech_qualification = tech_qualification;
        this.tech_specialization = tech_specialization;
        this.tech_status = tech_status;
        this.tech_img = tech_img;
    }

    public String getTech_id() {
        return tech_id;
    }

    public void setTech_id(String tech_id) {
        this.tech_id = tech_id;
    }

    public String getTech_name() {
        return tech_name;
    }

    public void setTech_name(String tech_name) {
        this.tech_name = tech_name;
    }

    public String getTech_qualification() {
        return tech_qualification;
    }

    public void setTech_qualification(String tech_qualification) {
        this.tech_qualification = tech_qualification;
    }

    public String getTech_specialization() {
        return tech_specialization;
    }

    public void setTech_specialization(String tech_specialization) {
        this.tech_specialization = tech_specialization;
    }

    public String getTech_status() {
        return tech_status;
    }

    public void setTech_status(String tech_status) {
        this.tech_status = tech_status;
    }

    public String getTech_img() {
        return tech_img;
    }

    public void setTech_img(String tech_img) {
        this.tech_img = tech_img;
    }
}
