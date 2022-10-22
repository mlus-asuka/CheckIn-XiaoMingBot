package vip.fubuki.util;

public class Goods {
    private Integer ID;

    private String Name;

    private Integer Price;

    private Integer Amount;

    private Boolean UnderCarriaged=false;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getPrice() {
        return Price;
    }

    public void setPrice(Integer price) {
        Price = price;
    }

    public Integer getAmount() {
        return Amount;
    }

    public void setAmount(Integer amount) {
        Amount = amount;
    }

    public Boolean getBoolean() {
        return UnderCarriaged;
    }

    public void setBoolean(Boolean underCarriaged) {
        UnderCarriaged = underCarriaged;
    }
}
