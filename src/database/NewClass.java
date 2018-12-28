/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;
import Database.DB;
/**
 *
 * @author JustinNew
 */
public class NewClass {
    public static void main(String args[]){
        DB db = new DB("sy2017_2018_1stsem","root","root");
        System.out.println(db.maxSubjectsAvailable());
    }
    
}
