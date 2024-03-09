package com.first.dumka.room_book_app.std_room.Std_Room.Modal_Class;

public class Single_Lodge_Item_Info {

    public String lodge_name,lodge_location,bed_available,lodge_img,lodge_id,bed;




    public Single_Lodge_Item_Info(String lodge_name, String lodge_location, String bed_available, String lodge_img,String lodge_id,String bed) {
        this.lodge_name = lodge_name;
        this.lodge_location = lodge_location;
        this.bed_available = bed_available;
        this.lodge_img = lodge_img;
        this.lodge_id = lodge_id;
        this.bed = bed;

    }

    public String getLodge_img() {
        return lodge_img;
    }

    public void setLodge_img(String lodge_img) {
        this.lodge_img = lodge_img;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public String getLodge_name() {
        return lodge_name;
    }

    public void setLodge_name(String lodge_name) {
        this.lodge_name = lodge_name;
    }

    public String getLodge_location() {
        return lodge_location;
    }

    public void setLodge_location(String lodge_location) {
        this.lodge_location = lodge_location;
    }

    public String getBed_available() {
        return bed_available;
    }

    public void setBed_available(String bed_available) {
        this.bed_available = bed_available;
    }

    public String getLodge_id() {
        return lodge_id;
    }

    public void setLodge_id(String lodge_id) {
        this.lodge_id = lodge_id;
    }
}
