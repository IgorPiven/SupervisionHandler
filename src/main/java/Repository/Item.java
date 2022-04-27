package Repository;

public class Item {

    private String orderCustomerNumber;
    private String itemNumber;
    private int itemQuantity;
    private int itemAvl;
    private int itemPicked;
    private int itemBckOrd;
    private int orderShipped;


    public Item(String orderCustomerNumber, String itemNumber, int itemQuantity, int itemAvl, int itemPicked,
                int itemBckOrd, int orderShipped) {
        this.orderCustomerNumber = orderCustomerNumber;
        this.itemNumber = itemNumber;
        this.itemQuantity = itemQuantity;
        this.itemAvl = itemAvl;
        this.itemPicked = itemPicked;
        this.itemBckOrd = itemBckOrd;
        this.orderShipped = orderShipped;
    }


    public String getOrderCustomerNumber() {
        return orderCustomerNumber;
    }

    public void setOrderCustomerNumber(String orderCustomerNumber) {
        this.orderCustomerNumber = orderCustomerNumber;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public int getItemAvl() {
        return itemAvl;
    }

    public void setItemAvl(int itemAvl) {
        this.itemAvl = itemAvl;
    }

    public int getItemPicked() {
        return itemPicked;
    }

    public void setItemPicked(int itemPicked) {
        this.itemPicked = itemPicked;
    }

    public int getItemBckOrd() {
        return itemBckOrd;
    }

    public void setItemBckOrd(int itemBckOrd) {
        this.itemBckOrd = itemBckOrd;
    }

    public int getOrderShipped() {
        return orderShipped;
    }

    public void setOrderShipped(int orderShipped) {
        this.orderShipped = orderShipped;
    }

}
