package com.hfut.mihealth.network.javaData;

import java.util.Date;

public class RecordResponse {
    private int userid;
    private int foodid;
    private int recordid;
    private int amount;
    private String name;
    private int calories;
    private double protein;
    private double carbs;
    private double fat;
    private String meals;
    private String imageurl;
    private Date date;

    public RecordResponse(int userid, int foodid, int recordid, int amount, String name, int calories, double protein, double carbs, double fat, String meals, String imageurl, Date date) {
        this.userid = userid;
        this.foodid = foodid;
        this.recordid = recordid;
        this.amount = amount;
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.meals = meals;
        this.imageurl = imageurl;
        this.date = date;
    }

    // Getters and Setters
    public int getUserid() { return userid; }
    public void setUserid(int userid) { this.userid = userid; }

    public int getFoodid() { return foodid; }
    public void setFoodid(int foodid) { this.foodid = foodid; }

    public int getRecordid() { return recordid; }
    public void setRecordid(int recordid) { this.recordid = recordid; }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getCalories() { return calories; }
    public void setCalories(int calories) { this.calories = calories; }

    public double getProtein() { return protein; }
    public void setProtein(double protein) { this.protein = protein; }

    public double getCarbs() { return carbs; }
    public void setCarbs(double carbs) { this.carbs = carbs; }

    public double getFat() { return fat; }
    public void setFat(double fat) { this.fat = fat; }

    public String getMeals() { return meals; }
    public void setMeals(String meals) { this.meals = meals; }

    public String getImageurl() { return imageurl; }
    public void setImageurl(String imageurl) { this.imageurl = imageurl; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecordResponse that = (RecordResponse) o;

        if (userid != that.userid) return false;
        if (foodid != that.foodid) return false;
        if (recordid != that.recordid) return false;
        if (amount != that.amount) return false;
        if (calories != that.calories) return false;
        if (Double.compare(that.protein, protein) != 0) return false;
        if (Double.compare(that.carbs, carbs) != 0) return false;
        if (Double.compare(that.fat, fat) != 0) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (meals != null ? !meals.equals(that.meals) : that.meals != null) return false;
        if (imageurl != null ? !imageurl.equals(that.imageurl) : that.imageurl != null) return false;
        return date != null ? date.equals(that.date) : that.date == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = userid;
        result = 31 * result + foodid;
        result = 31 * result + recordid;
        result = 31 * result + amount;
        result = 31 * result + calories;
        temp = Double.doubleToLongBits(protein);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(carbs);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(fat);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (meals != null ? meals.hashCode() : 0);
        result = 31 * result + (imageurl != null ? imageurl.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RecordResponse{" +
                "userid=" + userid +
                ", foodid=" + foodid +
                ", recordid=" + recordid +
                ", amount=" + amount +
                ", name='" + name + '\'' +
                ", calories=" + calories +
                ", protein=" + protein +
                ", carbs=" + carbs +
                ", fat=" + fat +
                ", meals='" + meals + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", date=" + date +
                '}';
    }
}
