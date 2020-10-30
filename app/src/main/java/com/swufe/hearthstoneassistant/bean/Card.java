package com.swufe.hearthstoneassistant.bean;

import java.util.Arrays;

public class Card {
    private String id;
    private String name;
    private String text;    //卡牌说明
    private String flavor;  //趣味介绍
    private String artist;  //画家
    private String cardClass;   //类别*
    private String faction; //派系
    private String rarity;  //稀有度*
    private String type;    //卡牌类型*
    private String set;     //所属卡包
    private String durability;  //武器耐久
    private byte[] pic;
    private int dbfId;
    private int attack;     //攻击
    private int cost;       //费用*
    private int health;     //生命

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getCardClass() {
        return cardClass;
    }

    public void setCardClass(String cardClass) {
        this.cardClass = cardClass;
    }

    public String getFaction() {
        return faction;
    }

    public void setFaction(String faction) {
        this.faction = faction;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getDurability() {
        return durability;
    }

    public void setDurability(String durability) {
        this.durability = durability;
    }

    public int getDbfId() {
        return dbfId;
    }

    public void setDbfId(int dbfId) {
        this.dbfId = dbfId;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public byte[] getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 17 * result + name.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Card)) {
            return false;
        }
        Card cardObj = (Card) obj;

        if (this == cardObj) {
            return true;
        }

        if (cardObj.id.equals(this.id) && cardObj.name.equals(this.name)) {
            return true;
        } else {
            return false;
        }
    }

}
