package DanMak.ProjectMD1608.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "features")

public class Feature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_option")
    private String categoryOption;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "feature")
    private List<OptionValue> optionValueList;

    public Long getId() {
        return id;
    }

    public String getCategoryOption() {
        return categoryOption;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCategoryOption(String categoryOption) {
        this.categoryOption = categoryOption;
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

