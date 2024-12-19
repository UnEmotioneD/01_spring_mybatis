package kr.or.iei;

public class FoodPlace {
    private String placeTitle;
    private String placeTel;
    private String placeHour;
    private String placeAddr;
    private String placeImg;

    public FoodPlace() {
        super();
    }

    public FoodPlace(String placeTitle, String placeTel, String placeHour, String placeAddr, String placeImg) {
        this.placeTitle = placeTitle;
        this.placeTel = placeTel;
        this.placeHour = placeHour;
        this.placeAddr = placeAddr;
        this.placeImg = placeImg;
    }

    public String getPlaceTitle() {
        return placeTitle;
    }

    public void setPlaceTitle(String placeTitle) {
        this.placeTitle = placeTitle;
    }

    public String getPlaceTel() {
        return placeTel;
    }

    public void setPlaceTel(String placeTel) {
        this.placeTel = placeTel;
    }

    public String getPlaceHour() {
        return placeHour;
    }

    public void setPlaceHour(String placeHour) {
        this.placeHour = placeHour;
    }

    public String getPlaceAddr() {
        return placeAddr;
    }

    public void setPlaceAddr(String placeAddr) {
        this.placeAddr = placeAddr;
    }

    public String getPlaceImg() {
        return placeImg;
    }

    public void setPlaceImg(String placeImg) {
        this.placeImg = placeImg;
    }
}
