package com.example.managementapi.model;


import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;


@NamedQuery(name ="Product.getAllProduct",query = "select new com.example.managementapi.dto.ProductDTO(p.id,p.name,p.description,p.price,p.status,p.category.id,p.category.name) from Product p")
@NamedQuery(name = "Product.updateProductStatus" , query = "update Product p set p.status=:status where p.id=:id")
@NamedQuery(name = "Product.getProductByCategory", query = "select new com.example.managementapi.dto.ProductDTO(p.id,p.name) from Product p where p.category.id=:id and p.status='true'")
@NamedQuery(name="Product.getProductById", query = "select new com.example.managementapi.dto.ProductDTO(p.id,p.name,p.description,p.price) from Product p where p.id=:id" )

@Data
@Entity
@DynamicUpdate
@Table(name = "product")
public class    Product  implements Serializable {

    public static final Long serialVersionUID = 123456L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_fk", nullable = false)
    private Category category;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private int price;

    @Column(name = "status")
    private String status;

}
