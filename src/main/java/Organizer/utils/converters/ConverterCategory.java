package Organizer.utils.converters;

import Organizer.database.models.Category;
import Organizer.modelFx.CategoryFx;


public class ConverterCategory {

    public static CategoryFx convertToCategoryFx(Category category){
        CategoryFx categoryFx = new CategoryFx();
        categoryFx.setId(category.getId());
        categoryFx.setName(category.getName());
        return categoryFx;
    }
}
