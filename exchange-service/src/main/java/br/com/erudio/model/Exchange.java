package br.com.erudio.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "exchange")
public class Exchange implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_currency", nullable = false, length = 3)
    private String from;

    @Column(name = "to_currency", nullable = false, length = 3)
    private String to;

    @Column(name = "conversion_factor", nullable = false, length = 3)
    private BigDecimal conversionFactor;

    @Transient //Para dizer para o banco de dados que esse atributo deve ser ignorado, pois nao existe no db
    private BigDecimal convertedValue;

    @Transient //Para dizer para o banco de dados que esse atributo deve ser ignorado, pois nao existe no db
    private String environment;
}
