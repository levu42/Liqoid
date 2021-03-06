/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.quadrillenschule.liquidroid.model;

import android.content.SharedPreferences;

/**
 *
 * @author andi
 */
public class Area {

    private int id = -1;
    private String name = "fail";
    private String description = "";
    private int direct_member_count = -1;
    private int member_weight = -1;
    private int autoreject_weight = -1;
    private boolean active = false;
    private Issues initiativen;
    SharedPreferences instancePrefs;

    public Area(SharedPreferences instancePrefs) {
        initiativen = new Issues(instancePrefs);
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the direct_member_count
     */
    public int getDirect_member_count() {
        return direct_member_count;
    }

    /**
     * @param direct_member_count the direct_member_count to set
     */
    public void setDirect_member_count(int direct_member_count) {
        this.direct_member_count = direct_member_count;
    }

    /**
     * @return the member_weight
     */
    public int getMember_weight() {
        return member_weight;
    }

    /**
     * @param member_weight the member_weight to set
     */
    public void setMember_weight(int member_weight) {
        this.member_weight = member_weight;
    }

    /**
     * @return the autoreject_weight
     */
    public int getAutoreject_weight() {
        return autoreject_weight;
    }

    /**
     * @param autoreject_weight the autoreject_weight to set
     */
    public void setAutoreject_weight(int autoreject_weight) {
        this.autoreject_weight = autoreject_weight;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return the initiativen
     */
    public Issues getInitiativen() {
        return initiativen;
    }

    /**
     * @param initiativen the initiativen to set
     */
    public void setInitiativen(Issues initiativen) {
        this.initiativen = initiativen;
    }
}
