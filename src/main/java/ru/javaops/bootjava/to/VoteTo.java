package ru.javaops.bootjava.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.javaops.bootjava.HasId;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class VoteTo extends BaseTo implements HasId {

    private LocalDate date;
    private Integer userId;
    private Integer restaurantId;

    public VoteTo(Integer id, LocalDate date, Integer userId, Integer restaurantId){
        super(id);
        this.date = date;
        this.userId = userId;
        this.restaurantId = restaurantId;
    }
}
