package com.cmpe277.studentmarketplace;

public class DbResult {
    private Boolean status;
    private String message;

    public DbResult(String m, Boolean s){
        this.message = m;
        this.status = s;
    }

    public String getMessage(){
        return this.message;
    }

    public Boolean getStatus(){
        return this.status;
    }
}
