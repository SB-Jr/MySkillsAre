package com.futurefirst.sbjr.myskillsare;

/**
 * Created by sbjr on 12/25/15.
 */
public class DatabaseContract {


    public static final class MemberTable{
        public static String TABLENAME="member";
        public static String EMAILID="email_id";
        public static String FIRSTNAME="first_name";
        public static String LASTNAME="last_name";
        public static String LOCATION="location";
        public static String PROFILEPIC="profile_pic";
    }

    public static final class SkillTable{
        public static String TABLENAME="skills";
        public static String EMAILID="email_id";
        public static String SKILLNAME="skill_name";
        public static String CATEGORY="category";
        public static String DESCRIPTION="description";
        public static String STARTYEAR="start_year";
    }

    public static final class CategoryTable{
        public static String TABLENAME="category";
        public static String CATEGORY="category";
        public static String SKILLNAME="skill_name";
    }

    public static class TempTable{
        public static String TABLENAME="temptable";
        public static String Index="sr_no";
        public static String SKILLNAME="skill_name";
        public static String CATEGORY="category";
        public static String DESCRIPTION="description";
        public static String STARTYEAR="start_year";
    }

}
