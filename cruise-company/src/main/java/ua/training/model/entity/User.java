package ua.training.model.entity;

public class User {
    private long id;
    private String login;
    private String password;

    public enum ROLE {
        USER, ADMIN, UNKNOWN
    }
    private ROLE role;
    private long balance;

    public User(long id, String login, String password, ROLE role, long balance) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;

        this.balance = balance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ROLE getRole() {
        return role;
    }

    public void setRole(ROLE role) {
        this.role = role;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class  Builder {
        private long id;
        private String login;
        private String password;
        private ROLE role;
        private long balance;

        public Builder id(long id){
            this.id = id;
            return this;
        }
        public Builder login(String login){
            this.login = login;
            return this;
        }
        public Builder password(String password){
            this.password = password;
            return this;
        }
        public Builder role(ROLE role){
            this.role = role;
            return this;
        }
        public Builder balance(long balance){
            this.balance = balance;
            return this;
        }
        public User build(){
            return  new User(id, login, password, role, balance);
        }
    }

}
