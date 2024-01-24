package ru.javaops.bootjava.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.Where;
import ru.javaops.bootjava.HasId;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "restaurant")
@Entity
public class Restaurant extends NamedEntity implements HasId {

//    @Column(name = "name", nullable = false)
//    @NotBlank
//    private String name;

    @Column(name = "address")
    @NotBlank
    private String address;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurant")
    @Where(clause = "date=current_date()")
    @JsonManagedReference
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<MenuItem> menu;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @Where(clause = "date=current_date()")
    @JsonManagedReference
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Vote> votes;

    @Formula(value = "select count(*) from vote where vote.restaurant_id=id and vote.date=current_date()")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer voteCount;

    public Restaurant(String name, String address, List<MenuItem> menu) {
        this.name = name;
        this.address = address;
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                "name='" + name + '\'' +
                "address=" + address +
                ", menu=" + menu +
//                ", votes=" + votes +
                '}';
    }
}
