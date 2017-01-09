package com.mylauncherdemo;

/**
 * Created by huangqj on 2016/11/9.
 */
public class GsonFormatTest {


    public static class PeopleBean {
        /**
         * firstName : Brett
         * lastName : McLaughlin
         * email : aaaa
         */

        private String firstName;
        private String lastName;
        private String email;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
