package ru.javaops.bootjava.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "vote")
@Entity
public class Vote extends BaseEntity {

    @Column(name = "date", nullable = false)
    private final LocalDate date = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Restaurant restaurant;

    public Vote(Integer id, Integer restaurantId, User user){
        this.id = id;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        this.restaurant = restaurant;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", date=" + date +
                ", user=" + user.getId() + ". " + user.getName() + ' ' + user.getEmail() +
                ", restaurant=" + restaurant.getId() + ". " + restaurant.getName() +
                '}';
    }
}
