package co.kr.category.domain.entity;

import co.kr.category.enums.CategoryStatus;
import lombok.*;
import javax.persistence.*;

@Setter
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String catName;
    private Long upperCatCode;
    private Integer catLevel;

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private CategoryStatus status;

}
