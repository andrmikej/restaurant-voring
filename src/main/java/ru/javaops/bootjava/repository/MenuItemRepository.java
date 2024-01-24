package ru.javaops.bootjava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.bootjava.model.MenuItem;
import ru.javaops.bootjava.to.MenuItemTo;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface MenuItemRepository extends BaseRepository<MenuItem>{

    @Query("SELECT i FROM MenuItem i WHERE (:restaurantId is null or i.restaurant.id=:restaurantId) and ((:date is null and i.date=current_date()) or i.date=:date)")
    List<MenuItem> findAllByDateAndRestaurantId(@Param("restaurantId") Integer restaurantId, @Param("date") LocalDate date);


}
