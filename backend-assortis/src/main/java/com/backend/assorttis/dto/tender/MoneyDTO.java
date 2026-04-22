// MoneyDTO.java
package com.backend.assorttis.dto.tender;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class MoneyDTO {
    private BigDecimal amount;
    private String currency;
    private String formatted;
}
