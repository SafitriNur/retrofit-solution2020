package id.putraprima.retrofit.api.models;

public class RegisterRequest {
    public String name;
    public String email;
    public String pass;
    public String c_pass;

    public RegisterRequest(String name, String email, String pass, String c_pass) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.c_pass = c_pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getC_pass() {
        return c_pass;
    }

    public void setC_pass(String c_pass) {
        this.c_pass = c_pass;
    }
}
