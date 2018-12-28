/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.*;

public class DB {
       private Connection con;
    private Statement st;
    private ResultSet rs;
    String server = "192.168.1.7:3306";// windows 7 IP
    String serverII="192.168.1.2";// windows 10 ip
    
    
   public DB(String A,String B, String C) {
       
 try {  
     Class.forName("com.mysql.jdbc.Driver");
     con = DriverManager.getConnection("jdbc:mysql://"+server+"/"+ A + "", B,C);
     st = con.createStatement();
    createDB();
 } catch (Exception ex) {
     System.out.println("Error: " + ex);
 }
    }    
   
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
         st.execute("create table  if not exists student("
                 + "StudentID       int              not null auto_increment    , "
                 + "StudentName     text,"
                 + "StudentAddress  text,"
                 + "StudentCourse   text,"
                 + "Gender          bool,"
                 + "YearLevel       int,"
                 + "Password       text,"
                 + "primary key(StudentID)"
                 + ")");
       
         st.execute("create table if not exists subjects("
                  + "SubjectID          int     not null  auto_increment,"
                  + "SubjectCode        text,"    
                  + "SubjectDescription text,"
                  + "Schedule           text,"
                  + "Units              int,"
                  + "Primary key(SubjectID)"
                  + ")");
      
         st.execute("create table if not exists enrolled("
                 + "EnrollmentID       int           not null auto_increment,"
                 + "SubjectID          int           not null,"
                 + "StudentID          int           not null,"
                 + "primary key(EnrollmentID),"
                 + "foreign key(studentID) references Student(StudentID),"
                 + "foreign key(subjectID) references Subjects(SubjectID),"
                 + "Unique(SubjectID,StudentID)"
                 + ")");
       
          st.execute("create table if not exists grades("
                  + "GradesID           int            not null ,"
                  + "Prelim             int,"    
                  + "Midterm            int,"
                  + "Prefinal           int,"
                  + "Primary key(GradesID),"
                  + "Foreign key(GradesID) references Enrolled(EnrollmentID)"
                  + ")");
           
            st.execute("create table if not exists teachers("
                  + "TeacherID          int   not null  auto_increment,"
                  + "TeacherName        text,"    
                  + "TeacherDepartment text,"
                  + "Password text,"
                  + "primary key(TeacherID)"
                  + ")");
         
            st.execute("CREATE TABLE if not exists `assignment` (" +
"  `AssignmentID` int(11) NOT NULL AUTO_INCREMENT," +
"  `ATeachersID` int(11) NOT NULL," +
"  `ASubjectID` int(11) NOT NULL," +
"  PRIMARY KEY (`AssignmentID`)," +
"  KEY `ATeacherID_idx` (`ATeachersID`)," +
"  KEY `ASubjectID_idx` (`ASubjectID`)," +
"  CONSTRAINT `ASubjectID` FOREIGN KEY (`ASubjectID`) REFERENCES `subjects` (`SubjectID`) ON DELETE NO ACTION ON UPDATE NO ACTION," +
"  CONSTRAINT `ATeacherID` FOREIGN KEY (`ATeachersID`) REFERENCES `teachers` (`TeacherID`) ON DELETE NO ACTION ON UPDATE NO ACTION" +
") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
         }catch(Exception e){System.out.println(e);}
     }
     
 public void createDB(){    
    try{              
     if((int)getMonths()==3){
        
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
     
 public void addStudent(String StudentName, String StudentAddress, String StudentCourse, boolean Gender, int YearLevel, String password){
         try{
           
         st.execute("insert into student (StudentName,StudentAddress,StudentCourse,Gender,YearLevel,Password)values("
                  +"'"+ StudentName+"',"
                  +"'"+ StudentAddress+"',"
                  +"'"+ StudentCourse+"',"
                  + Gender+","
                 + YearLevel+","
                 +"'"+password+"')");
        loginStudent(StudentName,password,"sy2017_2018_1stSem");
        }catch(Exception e){
              System.out.println(e);
         }    
     } 
    
 public void addEnrolled(String studID,String subjID){
         try{
         st.execute("insert into enrolled values"
                 + studID+","
                 + subjID); 
     }
        catch(Exception e){System.out.println(e);}    
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
            
              rs= st.executeQuery("select  subjects.subjectid,SubjectCode,SubjectDescription,units ,count(student.studentid) from "+
                          "(subjects left outer join enrolled on subjects.subjectid = enrolled.subjectid)"+
                          "left outer join student on enrolled.studentID = student.StudentID where subjects.subjectid= " +b);
          rs.next(); 
          aaa[0] = rs.getString("subjectid");
          //System.out.println(aaa[0]);
          aaa[1] = rs.getString("SubjectCode");
         // System.out.println(aaa[1]);
          aaa[2] = rs.getString("SubjectDescription");
          //System.out.println(aaa[2]);
          aaa[3] = rs.getString("units");
         // System.out.println(aaa[3]);
          aaa[4] = rs.getString("count(student.studentid)");
         // System.out.println(aaa[4]);
          
         
         }

         catch(Exception e){System.out.println(e);}
         return aaa;
     }//Subject

public String[][] getEnrolledInArray2DSpecific(int id) {
        int index = 0;
        String[][] q = new String[countEnrolled(maxStudents())][2];
        try {
            
             rs= st.executeQuery("select  subjects.subjectid, subjects.SubjectCode ,subjects.SubjectDescription from (STUDENT inner join enrolled on STUDENT.STUDENTID = ENROLLED.STUDENTID) inner join SUBJECTS on enrolled.SUBJECTID = SUBJECTS.SUBJECTID where enrolled.STUDENTID=" +
                                   +id);
            while (rs.next()) {
               String a = rs.getString("subjectid");
               String b = rs.getString("SubjectCode");
               String[] w = {a,b};
               q[index]= w;
               index++;
            }
        } catch(Exception ex) {
            System.out.println(ex);
            //System.out.println("Exception in method getEnrolledInArray2D()");
        }
        return q;
    }//Enrolled 

 public String[] table3(int a){
         String[]array= new String [2];
         try{
         rs= st.executeQuery("select subjects.SubjectID, subjects.SubjectCode from student left outer join "
                 + "enrolled on student.StudentID= enrolled.StudentID left outer join subjects on subjects.subjectid= enrolled.subjectid"
                 + "where studentid=" +a);
         rs.next();
        array[0]= rs.getString("SubjectID");
        array[1]=rs.getString("SubjectCode");
        return array;
     }catch(Exception e){}
         return array;
     }//Enrolled
     
public int numOfStudents(int subjectid){
        
         try{
              rs= st.executeQuery("select  count(student.studentid) as stud from subjects left "
                      + "outer join enrolled on subjects.subjectid = enrolled.subjectid "
                      + "left outer join student on enrolled.studentID = student.StudentID "
                      + "where subjects.subjectid= "+subjectid);
        
    
            rs.next();
         return Integer.parseInt(rs.getString("stud"));
           
     }catch(Exception e){}
         return 0;
}//Student Count
       
private void loginStudent(String StudentName, String password, String db){
         
         try{
            
           st.execute("GRANT SELECT ON *.* TO "+StudentName+"@"+serverII+" IDENTIFIED BY '"+password+"'");
         }
         catch(Exception e){
             System.out.println(e);
         }
     }
private void loginTeacher(String StudentName, String password, String db){
         
         try{
            
           st.execute("GRANT SELECT ON *.* TO $"+StudentName+"@"+serverII+" IDENTIFIED BY '"+password+"'");
         }
         catch(Exception e){
             System.out.println(e);
         }
     }

public int table3counter(int a){
    try{
         rs= st.executeQuery("select count(subjects.SubjectID) from student left outer join "
                 + "enrolled on student.StudentID= enrolled.StudentID left outer join subjects on subjects.subjectid= enrolled.subjectid"
                 + "where studentid=" +a);
         rs.next();
       
        return Integer.parseInt(rs.getString("count(subjects.SubjectID)"));
     }catch(Exception e){}
         return 0;

}

public int countEnrolled(int a){
      
  try{
          rs= st.executeQuery("select  count(subjects.subjectid) as count from STUDENT inner join enrolled on STUDENT.STUDENTID = ENROLLED.STUDENTID inner join SUBJECTS on enrolled.SUBJECTID = SUBJECTS.SUBJECTID where enrolled.STUDENTID=" +
                                 +a);
        //select  subjects.subjectid, subjects.SubjectCode ,subjects.SubjectDescription from STUDENT inner join enrolled on STUDENT.STUDENTID = ENROLLED.STUDENTID inner join SUBJECT on enrolled.SUBJECTID = SUBJECT.SUBJECTID where STUDENT.STUDENTID=1
          rs.next();
          String b= rs.getString("count");
         return  Integer.parseInt(b);
          
         


  }catch (Exception e){}
  return 0;
}

public void getGrades(String database, String ID , String password){
      Connection con;
     Statement st;
     ResultSet rs;
     try{
        con = DriverManager.getConnection("jdbc:mysql://"+server+"/"+database+"", ID, password);
        
     }catch(Exception e){
         
     }
}

public void insertTabData() {
    try {
        //for loop for getting grades and subjects enrolled by student    
    }
    catch(Exception ee) {
    
    }
}

public String[][] fillTableGrad(String StudentName) {
    String[][] t = new String[6][];
   try{
    rs = st.executeQuery("select  * from \n" +
"grades inner join enrolled on enrolled.EnrollmentID =  grades.gradesID\n" +
"inner join subjects on subjects.SubjectID = enrolled.subjectID\n" +
"inner join student on student.studentID = enrolled.studentID\n" +
"where student.StudentName ='"+StudentName+"'");
  
   int index=0;
 
   while (rs.next()) {
      
               //System.out.println("Nge mali ka");
              String Code = rs.getString("SubjectCode");
                 //System.out.println(Code);
              String Description = rs.getString("SubjectDescription");
                //System.out.println(Description);
              String Prelim = rs.getString("Prelim");
                 //System.out.println(Prelim);
              String Midterm = rs.getString("Midterm");
              String Prefi = rs.getString("Prefinal");
              int Fin = ((Integer.parseInt(Prelim)+Integer.parseInt(Midterm)+Integer.parseInt(Prefi))/3);
            //  System.out.println(Integer.toString(Fin));
              String Final = Fin+""; 
              
              t[index]= new String [6];
              t[index][0] = Code;
              t[index][1] = Description; 
              t[index][2] = Prelim; 
              t[index][3] = Midterm; 
              t[index][4] = Prefi; 
              t[index][5] = Fin+"";
              index++;
              
            }
   }
    catch(Exception e){
        System.out.println(e.toString());
    }
   /*
   for(String[] temp : t){
       for(String a: temp){
           System.out.print(a+"  ");
       }
       System.out.print("\n");
   }
  */

   
    return t;

}//Grades

public String[] StudentInfo(String StudentName){
      String[] t = new String[4];
   try{
    rs = st.executeQuery("select  * from \n" +
"grades inner join enrolled on enrolled.EnrollmentID =  grades.gradesID\n" +
"inner join subjects on subjects.SubjectID = enrolled.subjectID\n" +
"inner join student on student.studentID = enrolled.studentID\n" +
"where student.studentName ='"+StudentName+"'");
  
   //Subject Code", "Subject Description", "Prelim", "Midterm", "Prefinal", "Final
   rs.next();
   t[0]=rs.getString("StudentName");
   t[1]= rs.getString("StudentCourse");
   t[2]=rs.getString("YearLevel");
   
}
   catch(Exception e){}
   return t;
}//Stud Info

public String[][] refreshSubject(){
    
    String[][] Subject =new String[maxSubjects()][5];
    try{
         rs=st.executeQuery("Select * from subjects left outer join assignment on ASubjectid = subjectID where ATeachersID is null");
       
         
       int a=0;
         while(rs.next()){
            
            Subject[a] = new String[5];
            Subject[a][0] =rs.getString("SubjectID");
            Subject[a][1] =rs.getString("SubjectCode");
            Subject[a][2] =rs.getString("SubjectDescription");
            Subject[a][3] =rs.getString("Schedule");
            Subject[a][4] =rs.getString("Units");
            a++;
        }
      /*
         for(String[] A : Subject){
             for(String B : A){
                 System.out.print(B+" ");
             }
       
         }
 */
         return Subject;
        }
catch(Exception e){
    System.out.println(e);
}
     return Subject;
}

public int maxSubjectsAvailable(){
     try{
         rs=st.executeQuery("Select count(subjectID) from subjects");
         rs.next();
         return Integer.parseInt(rs.getString("count(SubjectID)"));
        }
catch(Exception e){
    System.out.println(e);
}
     return 0;
}


public void AddTeacher(String name, String Department,String password){
    try{
    st.execute("INSERT INTO teachers(`TeacherName`, `TeacherDepartment`,`Password`) VALUES ('"+name+"', '"+Department+"','"+password+"')");
    loginTeacher( name ,  password, "sy2017_2018_1stsem");
    }
    
    catch(Exception e){
        
    }
}
public void AddAssignment(String TeacherID,String SubjectID){
    try{
    st.execute("INSERT INTO `assignment` (`ATeachersID`, `ASubjectID`) VALUES ('"+TeacherID+"','"+SubjectID+"');");
    }
    
    catch(Exception e){
        System.out.println(e);
    }
}

public int LastTeacher(){
    try{
     rs=st.executeQuery("Select max(TeacherID) from teachers");
         rs.next();
         return Integer.parseInt(rs.getString("max(TeacherID)"));
    }catch(Exception e){
        System.out.println(e);
    }
    return 0;
}


public String[][] refreshAssign(){
    
    String[][] Subject =new String[maxSubjects()][3];
    try{
         rs=st.executeQuery("Select * from Assignment inner join Subjects on SubjectID = ASubjectID where ATeachersID = "+LastTeacher());
       
         
       int a=0;
         while(rs.next()){
            
            Subject[a] = new String[5];
            Subject[a][0] =rs.getString("SubjectID");
            Subject[a][1] =rs.getString("SubjectCode");
            Subject[a][2] =rs.getString("SubjectDescription");
            
           
            a++;
        }
      /*
         for(String[] A : Subject){
             for(String B : A){
                 System.out.print(B+" ");
             }
       
         }
 */
         return Subject;
        }
    catch(Exception e){
        System.out.println(e);
    }
    return Subject;
}

public String[][] teacherLogin(String TeacherName){
    String[][] students = new String [100][5];
    try{
    rs= st.executeQuery("Select distinct * from " +
    "(student as S inner join enrolled as E  on S.StudentID = E.StudentID) " +
    "inner join subjects  on subjects.subjectid = E.subjectid " +
    "inner join assignment on assignment.AsubjectID= subjects.subjectID " +
    "inner join teachers on teachers.TeacherID = assignment.AteachersID " +
    "where teacherName = '"+TeacherName+"'");
    System.out.println("AAAA");
    int a=0;
    while(rs.next()){
        System.out.println(rs.getString("StudentID"));
        students[a] = new String[5];
        students[a][0]= rs.getString("StudentID");
        students[a][1]=rs.getString("StudentName");
        students[a][2]=rs.getString("StudentCourse");
        students[a][3]=rs.getString("YearLevel");
        students[a][4]=rs.getString("SubjectCode");
        //System.out.println(rs.getString("SubjectName"));
           a++;
    }
    System.out.println("BBBB");
    for(String[]A : students){
        for(String B: A){
            System.out.print(B+" ");
        }
         System.out.println();
    }
}
catch(Exception e){
    System.out.println(e);
}
    return students;
}


public int findTeacherID(String name){
     try{
     rs=st.executeQuery("Select * from teachers where TeacherName ='"+name+"'");
         rs.next();
         return Integer.parseInt(rs.getString("TeacherID"));
    }catch(Exception e){
        System.out.println(e);
    }
    return 0;
}
}




     
//select  count(student.studentid) from subjects left outer join enrolled on subjects.subjectid = enrolled.subjectid left outer join student on enrolled.studentID = student.StudentID where subjects.subjectid= 1



  
  
     
//select  count(student.studentid) from subjects left outer join enrolled on subjects.subjectid = enrolled.subjectid left outer join student on enrolled.studentID = student.StudentID where subjects.subjectid= 1