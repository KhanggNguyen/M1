package com.example.tpex1;

class Android_Contact {
    public String android_conctact_nom = "";
    //public String android_contact_prenom = "";
    public String android_contact_telephone = "";
    public int android_contact_id;

    public Android_Contact(String nom, String telephone){
        this.android_conctact_nom = nom;
        this.android_contact_telephone = telephone;
    }

    public Android_Contact(int id, String nom, String telephone) {
        this.android_conctact_nom = nom;
        this.android_contact_telephone = telephone;
        this.android_contact_id = id;
    }

    public Android_Contact() {

    }


    public String getName() {
        return this.android_conctact_nom;
    }


    public String getPhone_Number() {
        return this.android_contact_telephone;
    }

    public void setId(int id) {
        this.android_contact_id = id;
    }

    public void setName(String nom) {
        this.android_conctact_nom = nom;
    }

    public void setPhone(String telephone) {
        this.android_contact_telephone = telephone;
    }

    public int getId() {
        return android_contact_id;
    }
}
