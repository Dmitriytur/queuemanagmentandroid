package ua.nure.queue_managment_android.model;


import java.util.List;

public class CategoryEntity extends AbstractEntity {

    private CategoryEntity parent;

    private String value;

    private String nextCategoryName;

    private List<CategoryEntity> options;

    public CategoryEntity getParent() {
        return parent;
    }

    public void setParent(CategoryEntity parent) {
        this.parent = parent;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getNextCategoryName() {
        return nextCategoryName;
    }

    public void setNextCategoryName(String nextCategoryName) {
        this.nextCategoryName = nextCategoryName;
    }

    public List<CategoryEntity> getOptions() {
        return options;
    }

    public void setOptions(List<CategoryEntity> options) {
        this.options = options;
    }
}
