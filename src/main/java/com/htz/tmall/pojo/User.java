package com.htz.tmall.pojo;

/**
 * 用户类
 */
public class User {
    private Integer id;
    private String name;
    private String password;

    public User(){
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 返回名称的匿名形式
     * @return
     */
    public String getAnonymousName(){
        if(null == name){
            return null;
        }
        //若name的长度为1，返回 *
        if(name.length() <= 1){
            return "*";
        }
        //name的长度为2，返回 第一个字母 + *
        if(name.length() == 2){
            return name.substring(0,1) + "*";
        }
        //name长度大于2，返回 首字母 + *...* + 尾字母
        char[] cs = name.toCharArray();
        for(int i = 1; i < cs.length - 1; i++){
            cs[i] = '*';
        }
        return new String(cs);
    }
}
