package DanMak.ProjectMD1608.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String name;

    private Long price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OptionValue> optionValueList;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<OptionValue> getOptionValueList() {
        return optionValueList;
    }

    public void setOptionValueList(List<OptionValue> optionValueList) {
        this.optionValueList = optionValueList;
    }
}
