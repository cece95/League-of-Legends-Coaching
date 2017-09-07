package com.example.cesare.leagueoflegendscoaching.Classes.Singletons;

/**
 * Created by cesare on 05/08/2017.
 */

public class LoggedUser {
        private static LoggedUser mInstance = null;

        private String ign;
        private String password;
        private boolean isCoach;

        public static String token;

        private LoggedUser(String ign, String password, boolean isCoach){
            this.ign = ign;
            this.password = password;
            this.isCoach = isCoach;
        }

        public static LoggedUser getIstance(String ign, String password, boolean isCoach){
            if(mInstance == null)
            {
                if (ign == null){
                    return  null;
                }
                mInstance = new LoggedUser(ign, password, isCoach);
            }
            return mInstance;
        }

        public static void logout(){
            mInstance = null;
        }

    public String getIgn() {
        return ign;
    }

    public void setIgn(String ign) {
        this.ign = ign;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isCoach() {
        return isCoach;
    }

    public void setCoach(boolean coach) {
        isCoach = coach;
    }
}
