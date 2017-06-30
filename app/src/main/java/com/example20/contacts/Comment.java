package com.example20.contacts;

/**
 * Created by AKANKSHA on 29-06-2017.
 */
public class Comment {

//    static final String ROW_ID="id";
    static final String NAME = "name";
    static final String NUMBER = "number";
    //DB PROPERTIES
    static final String DB_NAME="b_DB";
    static final String TB_NAME="b_TB";
    static final int DB_VERSION='1';
    //CREATE TABLE
    static final String CREATE_TB="CREATE TABLE "+TB_NAME+"("+NAME +" "+"TEXT NOT NULL,"+NUMBER +" "+"TEXT NOT NULL, PRIMARY KEY ("+NUMBER+" ));";
}
