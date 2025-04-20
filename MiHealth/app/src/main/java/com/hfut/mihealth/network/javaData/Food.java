package com.hfut.mihealth.network.javaData;

public class Food {
    private int foodid;
    private String name;
    private int calories;
    private double protein;
    private double carbs;
    private double fat;
    private String foodtype;
    private String othernutritionalinfo;
    private String imageurl;

    public Food(int foodid, String name, int calories, double protein, double carbs, double fat, String foodtype, String othernutritionalinfo, String imageurl) {
        this.foodid = foodid;
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.foodtype = foodtype;
        this.othernutritionalinfo = othernutritionalinfo;
        this.imageurl = imageurl;
    }

    // Getters and Setters
    public int getFoodid() {
        return foodid;
    }

    public void setFoodid(int foodid) {
        this.foodid = foodid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public String getFoodtype() {
        return foodtype;
    }

    public void setFoodtype(String foodtype) {
        this.foodtype = foodtype;
    }

    public String getOthernutritionalinfo() {
        return othernutritionalinfo;
    }

    public void setOthernutritionalinfo(String othernutritionalinfo) {
        this.othernutritionalinfo = othernutritionalinfo;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Food food = (Food) o;

        if (foodid != food.foodid) return false;
        if (calories != food.calories) return false;
        if (Double.compare(food.protein, protein) != 0) return false;
        if (Double.compare(food.carbs, carbs) != 0) return false;
        if (Double.compare(food.fat, fat) != 0) return false;
        if (name != null ? !name.equals(food.name) : food.name != null) return false;
        if (foodtype != null ? !foodtype.equals(food.foodtype) : food.foodtype != null)
            return false;
        if (othernutritionalinfo != null ? !othernutritionalinfo.equals(food.othernutritionalinfo) : food.othernutritionalinfo != null)
            return false;
        return imageurl != null ? imageurl.equals(food.imageurl) : food.imageurl == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = foodid;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + calories;
        temp = Double.doubleToLongBits(protein);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(carbs);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(fat);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (foodtype != null ? foodtype.hashCode() : 0);
        result = 31 * result + (othernutritionalinfo != null ? othernutritionalinfo.hashCode() : 0);
        result = 31 * result + (imageurl != null ? imageurl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Food{" +
                "foodid=" + foodid +
                ", name='" + name + '\'' +
                ", calories=" + calories +
                ", protein=" + protein +
                ", carbs=" + carbs +
                ", fat=" + fat +
                ", foodtype='" + foodtype + '\'' +
                ", othernutritionalinfo='" + othernutritionalinfo + '\'' +
                ", imageurl='" + imageurl + '\'' +
                '}';
    }
}
