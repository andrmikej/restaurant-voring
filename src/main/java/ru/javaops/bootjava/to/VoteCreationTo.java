package ru.javaops.bootjava.to;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.javaops.bootjava.HasId;

@Data
public class VoteCreationTo extends BaseTo implements HasId {

    @NotNull
    private Integer restaurantId;
}
