package ua.opnu;

import java.util.ArrayList;
import java.util.List;

// ====== Задание 1. DiscountBill ======
class Employee {
    String name;
    public Employee(String name) { this.name = name; }
}

class ItemGrocery {
    String name;
    double price;
    double discount; // скидка в гривнах

    public ItemGrocery(String name, double price, double discount) {
        this.name = name;
        this.price = price;
        this.discount = discount;
    }
}

class GroceryBill {
    protected Employee clerk;
    protected List<ItemGrocery> items = new ArrayList<>();

    public GroceryBill(Employee clerk) {
        this.clerk = clerk;
    }

    public void add(ItemGrocery item) {
        items.add(item);
    }

    public double getTotal() {
        double total = 0;
        for (ItemGrocery i : items) total += i.price;
        return total;
    }
}

// DiscountBill через наследование
class DiscountBill extends GroceryBill {
    private boolean regularCustomer;

    public DiscountBill(Employee clerk, boolean regularCustomer) {
        super(clerk);
        this.regularCustomer = regularCustomer;
    }

    @Override
    public void add(ItemGrocery item) {
        if (!regularCustomer) super.add(item);
        else {
            double discountedPrice = item.price - item.discount;
            super.add(new ItemGrocery(item.name, discountedPrice, item.discount));
        }
    }

    public int getDiscountCount() {
        int count = 0;
        for (ItemGrocery i : items) if (i.discount > 0) count++;
        return count;
    }

    public double getDiscountAmount() {
        double sum = 0;
        for (ItemGrocery i : items) sum += i.discount;
        return sum;
    }

    public double getDiscountPercent() {
        double totalPrice = 0;
        double totalPaid = 0;
        for (ItemGrocery i : items) {
            totalPrice += i.price + i.discount;
            totalPaid += i.price;
        }
        if (totalPrice == 0) return 0;
        return 100 - (totalPaid * 100 / totalPrice);
    }
}

// ====== Задание 2. MinMaxAccount ======
class Startup {
    int initialBalance;
    public Startup(int initialBalance) { this.initialBalance = initialBalance; }
}

class MinMaxAccount {
    private int balance;
    private int min;
    private int max;

    public MinMaxAccount(Startup s) {
        this.balance = s.initialBalance;
        this.min = balance;
        this.max = balance;
    }

    public void debit(int amount) {
        balance -= amount;
        updateMinMax();
    }

    public void credit(int amount) {
        balance += amount;
        updateMinMax();
    }

    private void updateMinMax() {
        if (balance < min) min = balance;
        if (balance > max) max = balance;
    }

    public int getBalance() { return balance; }
    public int getMin() { return min; }
    public int getMax() { return max; }
}

// ====== Задание 3. Point3D ======
class Point {
    protected int x, y;
    public Point() { this.x = 0; this.y = 0; }
    public Point(int x, int y) { this.x = x; this.y = y; }
    public void setLocation(int x, int y) { this.x = x; this.y = y; }
    public double distanceFromOrigin() { return Math.sqrt(x*x + y*y); }
    @Override
    public String toString() { return "(" + x + "," + y + ")"; }
}

class Point3D extends Point {
    private int z;

    public Point3D() { super(); this.z = 0; }
    public Point3D(int x, int y, int z) { super(x,y); this.z = z; }
    public void setLocation(int x, int y, int z) { super.setLocation(x,y); this.z = z; }
    public void setLocation(int x, int y) { super.setLocation(x,y); this.z = 0; }
    public double distance(Point3D p) {
        int dx = x - p.x;
        int dy = y - p.y;
        int dz = z - p.z;
        return Math.sqrt(dx*dx + dy*dy + dz*dz);
    }
    @Override
    public double distanceFromOrigin() { return Math.sqrt(x*x + y*y + z*z); }
    public int getZ() { return z; }
    @Override
    public String toString() { return "(" + x + "," + y + "," + z + ")"; }
}
// ====== Задание 4. DiscountBill2 через композицию ======
class DiscountBill2 {
    private GroceryBill bill;
    private boolean regularCustomer;

    public DiscountBill2(GroceryBill bill, boolean regularCustomer) {
        this.bill = bill;
        this.regularCustomer = regularCustomer;
    }

    public void add(ItemGrocery item) {
        if (!regularCustomer) bill.add(item);
        else bill.add(new ItemGrocery(item.name, item.price - item.discount, item.discount));
    }

    public double getTotal() { return bill.getTotal(); }
    public int getDiscountCount() {
        int count = 0;
        for (ItemGrocery i : bill.items) if (i.discount > 0) count++;
        return count;
    }
    public double getDiscountAmount() {
        double sum = 0;
        for (ItemGrocery i : bill.items) sum += i.discount;
        return sum;
    }
    public double getDiscountPercent() {
        double totalPrice = 0;
        double totalPaid = 0;
        for (ItemGrocery i : bill.items) {
            totalPrice += i.price + i.discount;
            totalPaid += i.price;
        }
        if (totalPrice == 0) return 0;
        return 100 - (totalPaid * 100 / totalPrice);
    }
}

// ====== Main для проверки всех 4 заданий ======
public class Main {
    public static void main(String[] args) {

        System.out.println("=== Задание 1: DiscountBill ===");
        Employee clerk = new Employee("John");
        DiscountBill bill = new DiscountBill(clerk, true);
        bill.add(new ItemGrocery("Milk", 50, 5));
        bill.add(new ItemGrocery("Bread", 30, 3));
        System.out.println("Total: " + bill.getTotal());
        System.out.println("Discount count: " + bill.getDiscountCount());
        System.out.println("Discount amount: " + bill.getDiscountAmount());
        System.out.println("Discount percent: " + bill.getDiscountPercent());

        System.out.println("\n=== Задание 2: MinMaxAccount ===");
        Startup startup = new Startup(1000);
        MinMaxAccount account = new MinMaxAccount(startup);
        account.debit(200);
        account.credit(500);
        System.out.println("Balance: " + account.getBalance());
        System.out.println("Min: " + account.getMin());
        System.out.println("Max: " + account.getMax());

        System.out.println("\n=== Задание 3: Point3D ===");
        Point3D p1 = new Point3D(1,2,3);
        Point3D p2 = new Point3D(4,5,6);
        System.out.println("Distance: " + p1.distance(p2));
        p1.setLocation(7,8);
        System.out.println("p1 after setLocation(x,y): " + p1);

        System.out.println("\n=== Задание 4: DiscountBill2 (composition) ===");
        GroceryBill simpleBill = new GroceryBill(clerk);
        DiscountBill2 discountBill2 = new DiscountBill2(simpleBill, true);
        discountBill2.add(new ItemGrocery("Juice", 40, 4));
        discountBill2.add(new ItemGrocery("Cheese", 60, 6));
        System.out.println("Total: " + discountBill2.getTotal());
        System.out.println("Discount count: " + discountBill2.getDiscountCount());
        System.out.println("Discount amount: " + discountBill2.getDiscountAmount());
        System.out.println("Discount percent: " + discountBill2.getDiscountPercent());
    }
}