package ru.javaops.bootjava.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;
import ru.javaops.bootjava.to.MenuItemTo;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "menu_item")
@Entity
public class MenuItem extends NamedEntity {

//    @Column(name = "item_name", nullable = false)
//    @NotBlank
//    private String itemName;

    @Column(name = "price", nullable = false)
    @NotNull
    private Integer price;

    @Column(name = "date", nullable = false)
    private final LocalDate date = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Restaurant restaurant;

    public MenuItem(String name, Integer price, Restaurant restaurant) {
        this.name = name;
        this.price = price;
        this.restaurant = restaurant;
    }

    public MenuItem(MenuItemTo menuItemTo){
        this.id = menuItemTo.getId();
        this.name = menuItemTo.getName();
        this.price = menuItemTo.getPrice();
        Restaurant restaurant = new Restaurant();
        restaurant.setId(menuItemTo.getRestaurantId());
        this.restaurant = restaurant;
    }

                    @Override
    public String toString() {
        return "MenuItem{" +
                "id=" + id +
                "itemName='" + name + '\'' +
                ", price=" + price +
                ", date=" + date +
                ", restaurant=" + restaurant.getId() + ". " + restaurant.getName() +
                '}';
    }
}
