package ru.javaops.bootjava.to;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.javaops.bootjava.HasId;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class MenuItemTo extends NamedTo implements HasId {

    @NotNull
    private Integer price;

    private final LocalDate date = LocalDate.now();

    @NotNull
    private Integer restaurantId;

    public MenuItemTo(Integer id, String name, Integer price, Integer restaurantId){
        super(id, name);
        this.price = price;
        this.restaurantId = restaurantId;
    }

}
