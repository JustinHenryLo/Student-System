/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.*;

public class Database {
       private Connection con;
    private Statement st;
    private ResultSet rs;
    
    public Database() {
 try {
     Class.forName("com.mysql.jdbc.Driver");
     con = DriverManager.getConnection("jdbc:mysql://192.168.1.9:3306/Sy2016_17_2ndSem", "root", "root");
     st = con.createStatement();
    createDB();
     
 } catch (Exception ex) {
     System.out.println("Error: " + ex);
     
 }
    }    
    /*
    public void getData(String n) {
 try {
     String query = "select * from Student where id='" + n + "'";
     rs = st.executeQuery(query);
     System.out.println("Records from Database");
     while (rs.next()) {
  String id = rs.getString("id");
  String name = rs.getString("name");
  String address = rs.getString("address");
  String course = rs.getString("course");
  String gender = rs.getString("gender");
  String yrlvl = rs.getString("yrlevl");
  System.out.println("Your ID "+id);
  System.out.println("Your Name "+name);
  System.out.println("Your Address "+address);
  System.out.println("Your Course "+course);
  System.out.println("Your Gender "+gender);
  System.out.println("Your Year lvl "+yrlvl);
  
     }
 } catch(Exception ex) {
     System.out.println(ex);
 }
    }
    
    public void insertData(String id,String name, String address, String course ,String gender, String yrlvl) {
 try {
     String query = "insert into Student values('"+id+"','"+ name + "','" + address + "','"+course+"','"+gender+"','"+yrlvl+"')";
     st.executeUpdate(query);
     System.out.println("Records inserted");
 } catch(Exception ex) {
     System.out.println(ex);
 }
    }    
    
     public void delete(String n) {
 try {
     String query = "delete from Student where id='" + n + "'";
     st.executeUpdate(query);
     System.out.println("Records deleted");
     
     }
  catch(Exception ex) {
     System.out.println(ex);
 }
    }
     
     public void edit(String id,String name, String address, String course,String gender, String yrlvl ) {
 try {
     st.executeUpdate("update Student set name = '" + name+"',address ='"+address+"',course ='"+course+"',gender ='"+gender+"',yrlevl ='"+yrlvl+"' where id = '"+id+"';");
   System.out.println("Records edited");
     }
  catch(Exception ex) {
     System.out.println(ex);
 }
 
 }
*/
    
     public int getMonths(){
        try{
        rs= st.executeQuery("select month(now());");
        rs.next();
       return Integer.parseInt(rs.getString("month(now())"));
        }
        catch(Exception e){return 0;}
     
  }
     
     public int getYears(){ 
        try{
        rs= st.executeQuery("select year(now());");
        rs.next();
       return Integer.parseInt(rs.getString("year(now())"));
        }
        catch(Exception e){return 0;}
     
  }
     
     public void makeTables(){
         try{
         st.execute("create table student("
                 + "StudentID       int              not null auto_increment    , "
                 + "StudentName     text,"
                 + "StudentAddress  text,"
                 + "StudentCourse   text,"
                 + "Gender          bool,"
                 + "YearLevel       int,"
                 + "primary key(StudentID)"
                 + ")");
        // System.out.println("1");
         st.execute("create table subjects("
                  + "SubjectID          int     not null  auto_increment,"
                  + "SubjectCode        text,"    
                  + "SubjectDescription text,"
                  + "Schedule           text,"
                  + "Units              int,"
                  + "Primary key(SubjectID)"
                  + ")");
       //  System.out.println("2");
         st.execute("create table enrolled("
                 + "EnrollmentID       int           not null auto_increment,"
                 + "SubjectID          int           not null,"
                 + "StudentID          int           not null,"
                 + "primary key(EnrollmentID),"
                 + "foreign key(studentID) references Student(StudentID),"
                 + "foreign key(subjectID) references Subjects(SubjectID),"
                 + "Unique(SubjectID,StudentID)"
                 + ")");
         //  System.out.println("3");
          st.execute("create table grades("
                  + "GradesID           int            not null ,"
                  + "Prelim             int,"    
                  + "Midterm            int,"
                  + "Prefinal           int,"
                  + "Primary key(GradesID),"
                  + "Foreign key(GradesID) references Enrolled(EnrollmentID)"
                  + ")");
         //  System.out.println("4");
          
           
            st.execute("create table teachers("
                  + "TeacherID          int   not null  auto_increment,"
                  + "TeacherName        text,"    
                  + "TeacherDepartment text,"
                  + "primary key(TeacherID)"
                  + ")");
       //    System.out.println("5");
           
         
         
         }catch(Exception e){System.out.println(e);}
     }
     
     public void createDB(){
    try{
     if((int)getMonths()==6){
        int nextYear= getYears()+(int)1;
         st.execute("create database if not exists Sy"+getYears()+"_"+nextYear+"_1stSem");
         st.execute("use Sy"+getYears()+"_"+nextYear+"_1stSem");
        makeTables();
     }
     else if((int)getMonths()==10){
        int nextYear= getYears()+(int)1;
         st.execute("create database if not exists Sy"+getYears()+"_"+nextYear+"_2ndSem");
         st.execute("use Sy"+getYears()+"_"+nextYear+"_2ndSem"); 
         makeTables();
     }
   
    }
    catch(Exception e){}
} 
     
     public void addStudent(String StudentName, String StudentAddress, String StudentCourse, boolean Gender, int YearLevel){
         try{
         if(getMonths()>=1&&getMonths()<10){
          st.execute("use sy2017_2018_1stSem");
         }
         else{
          st.execute("use sy"+getYears()+"_"+getYears()+1+"_2ndSem");
         }
        
         st.execute("insert into student (StudentName,StudentAddress,StudentCourse,Gender,YearLevel)values("
                  +"'"+ StudentName+"',"
                  +"'"+ StudentAddress+"',"
                  +"'"+ StudentCourse+"',"
                  + Gender+","
                 + YearLevel+")");
        System.out.println("records added");
        
         }catch(Exception e){System.out.println("records not added");
              System.out.println(e);

         }    
     } 
    
     public void addEnrolled(String studID,String subjID){
         try{
         if(getMonths()>=1&&getMonths()<10){
                   st.execute("use sy2017_2018_1stSem");

         }
         else{
          st.execute("use sy"+getYears()+"_"+getYears()+1+"_2ndSem");
         }
         
         st.execute("use sy2017_2018_1stSem");
         st.execute("insert into enrolled values"
                 + studID+","
                 + subjID); 
     }
        catch(Exception e){System.out.println("records enrolled");}    
}
     
     public void addSubject(String SubjectCode,String SubjectDescription ,String Schedule,String Units){
         
         try{
           st.execute("insert into subjects (SubjectCode,SubjectDescription,Schedule,Units) values ('"+SubjectCode+"','"+SubjectDescription+"','"+Schedule+"','"+Units+"')");
         }
         catch(Exception e){
             System.out.println(e);
         }
         }
     
     public void addEnrollment(String SubjectID,String StudentID){
         try{
           st.execute("insert into enrolled (SubjectID,StudentID) values ('"+SubjectID+"','"+StudentID+"')");
         }
         catch(Exception e){
             System.out.println(e);
         }
     }
     
     public int maxSubjects(){
         try{
          rs = st.executeQuery("select max(subjectid)as max from subjects");
          rs.next();
        return  Integer.parseInt(rs.getString("max"));
     }
         catch(Exception e){}
         return 0;
     }
     
      public int maxEnrolled(){
         try{
          rs = st.executeQuery("select max(EnrollmentID)as max from enrolled");
          rs.next();
        return  Integer.parseInt(rs.getString("max"));
     }
         catch(Exception e){}
         return 0;
     }
     
      public int maxStudents(){
            try{
          rs = st.executeQuery("select max(StudentID)as max from student");
          rs.next();
        return  Integer.parseInt(rs.getString("max"));
     }
         catch(Exception e){}
         return 0;
      }
      
     public String[] table1(int b){
         String[] aaa = new String[5];
         try{
         st.execute("use sy2017_2018_1stsem");
       
        // System.out.println(a);
       //  for(int b=0; b<a; b++){
              rs= st.executeQuery("select  subjects.subjectid,SubjectCode,SubjectDescription,units ,count(student.studentid) from "+
                          "(subjects left outer join enrolled on subjects.subjectid = enrolled.subjectid)"+
                          "left outer join student on enrolled.studentID = student.StudentID where subjects.subjectid= " +b);
         
          rs.next();
           
          aaa[0] = rs.getString("subjectid");
          System.out.println(aaa[0]);
          aaa[1] = rs.getString("SubjectCode");
          System.out.println(aaa[1]);
          aaa[2] = rs.getString("SubjectDescription");
          System.out.println(aaa[2]);
          aaa[3] = rs.getString("units");
          System.out.println(aaa[3]);
          aaa[4] = rs.getString("count(student.studentid)");
          System.out.println(aaa[4]);
          
         
         }

         catch(Exception e){System.out.println(e);}
         return aaa;
     }
     
     public String[] table2(int a){
         String [] aaa= new String [3];
  try{
          st.execute("use sy2017_2018_1stsem");
          rs= st.executeQuery("select * from enrolled where EnrollmentID="+ a);
         rs.next();
         aaa[0]= rs.getString("EnrollmentID");
         System.out.println(aaa[0]);
         aaa[1]= rs.getString("SubjectID");
         System.out.println(aaa[1]);
         aaa[2]= rs.getString("StudentID");
         System.out.println(aaa[2]);
         System.out.println("here");
return aaa;


}catch(Exception e){
    System.out.println(e);
}
  return aaa;
}
     
     public int numOfStudents(int subjectid){
        
         try{
                st.execute("use sy2017_2018_1stsem");
      // System.out.println("hello");
      
              rs= st.executeQuery("select  count(student.studentid) as stud from subjects left "
                      + "outer join enrolled on subjects.subjectid = enrolled.subjectid "
                      + "left outer join student on enrolled.studentID = student.StudentID "
                      + "where subjects.subjectid= "+subjectid);
        
        //   System.out.println("hello");
            rs.next();
         return Integer.parseInt(rs.getString("stud"));
           
     }catch(Exception e){}
         return 0;
}
}


    
     
//select  count(student.studentid) from subjects left outer join enrolled on subjects.subjectid = enrolled.subjectid left outer join student on enrolled.studentID = student.StudentID where subjects.subjectid= 1