package at.hollander.ibex.entity;

import at.hollander.ibex.View;
import at.hollander.ibex.entity.id.CityId;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@AllArgsConstructor
public class City implements Serializable {

    @Valid
    @NotNull
    @JsonIgnore
    @EmbeddedId
    private CityId cityId;

    @JsonView({View.City.Details.class})
    private boolean enabled;

    @JsonView({View.City.Details.class})
    @Column(nullable = false)
    private BigDecimal priceShipping = BigDecimal.valueOf(-1);

    public City(int postcode, String name) {
        this(postcode, name, false, BigDecimal.valueOf(-1));
    }

    public City(int postcode, String name, boolean enabled, BigDecimal priceShipping) {
        this(new CityId(postcode, name), enabled, priceShipping);
    }

    public City() {
        this(new CityId(), false, BigDecimal.valueOf(-1));
    }

    @JsonGetter("name")
    @JsonView({View.City.Details.class})
    public String getName() {
        return cityId.getName();
    }

    @JsonSetter("name")
    public void setName(String name) {
        this.cityId.setName(name);
    }

    @JsonGetter("postcode")
    @JsonView({View.City.Details.class})
    public int getPostcode() {
        return cityId.getPostcode();
    }

    @JsonSetter("postcode")
    public void setPostcode(int postcode) {
        this.cityId.setPostcode(postcode);
    }

}
