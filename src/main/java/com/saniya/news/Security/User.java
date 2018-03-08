package com.saniya.news.Security;

import com.saniya.news.Interest;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String business;

    @Column
    private String sports;

    @Column
    private String entertainment;

    @Column
    private String general;

    @Column
    private String technology;

    @Column
    private String health;

    @Column
    private String science;

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "interest_id"))
    private Set<Interest> interests;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {
        this.interests = new HashSet<>();
        this.roles = new HashSet<>();
//        this.categories = new HashSet<>();
    }

    public void addRole(Role role)
    {
        this.roles.add(role);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getSports() {
        return sports;
    }

    public void setSports(String sports) {
        this.sports = sports;
    }

    public String getEntertainment() {
        return entertainment;
    }

    public void setEntertainment(String entertainment) {
        this.entertainment = entertainment;
    }

    public String getGeneral() {
        return general;
    }

    public void setGeneral(String general) {
        this.general = general;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getScience() {
        return science;
    }

    public void setScience(String science) {
        this.science = science;
    }
///////////////////////////////////////////////
    public void addBusiness(){
        this.business = "yes";
    }

    public void addScience(){
        this.science = "yes";
    }

    public void addTechnology(){
        this.technology = "yes";
    }

    public void addGeneral(){
        this.general = "yes";
    }

    public void addEntertainment(){
        this.entertainment = "yes";
    }

    public void addHealth(){
        this.health = "yes";
    }

    public void addSports(){
        this.sports = "yes";
    }
////////////////////////////////////////////
    public void removeBusiness(){
        this.business = "no";
    }

    public void removeScience(){
        this.science = "no";
    }

    public void removeTechnology(){
        this.technology = "no";
    }

    public void removeGeneral(){
        this.general = "no";
    }

    public void removeEntertainment(){
        this.entertainment = "no";
    }

    public void removeHealth(){
        this.health = "no";
    }

    public void removeSports(){
        this.sports = "no";
    }


    public Set<Interest> getInterests() {
        return interests;
    }

    public void setInterests(Set<Interest> interests) {
        this.interests = interests;
    }

    public void addInterest(Interest interest){
        this.interests.add(interest);
    }

    public void removeInterest(Interest interest){
        this.interests.remove(interest);
    }

}
