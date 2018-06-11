package ua.nure.queue_managment_android.model;

public class CompanyEntity extends AbstractEntity {

    private String name;

    private String description;

    private CategoryEntity rootCategory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryEntity getRootCategory() {
        return rootCategory;
    }

    public void setRootCategory(CategoryEntity rootCategory) {
        this.rootCategory = rootCategory;
    }
}
