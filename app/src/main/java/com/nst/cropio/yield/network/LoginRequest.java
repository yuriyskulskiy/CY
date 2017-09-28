package com.nst.cropio.yield.network;

public class LoginRequest {

    private UserLogin user_login;

    public LoginRequest(){}

    public LoginRequest(String email, String password) {
        UserLogin userLogin = new UserLogin();
        userLogin.setEmail(email);
        userLogin.setPassword(password);
        user_login = userLogin;
    }

    public void setUser_login(UserLogin user_login){
        this.user_login = user_login;
    }

    public UserLogin getUser_login() {
        return user_login;
    }

    public static class UserLogin {

        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
