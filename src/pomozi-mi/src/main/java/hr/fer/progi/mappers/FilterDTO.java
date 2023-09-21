package hr.fer.progi.mappers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterDTO {

    public enum Order {

        STANDARD,

        ATOZ,

        ZTOA
    }

    @NotNull
    private int radius;
    @NotNull
    private Boolean virtual;
    @NotNull
    private Order order;
}
