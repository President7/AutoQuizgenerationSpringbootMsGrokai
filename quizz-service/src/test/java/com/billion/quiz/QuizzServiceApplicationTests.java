package com.billion.quiz;

import com.billion.quiz.dto.CategoryDto;
import com.billion.quiz.service.CategoryFeignService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class QuizzServiceApplicationTests {

    @Autowired
    private CategoryFeignService categoryFeignService;
    @Test
    public void testFEignALlCategories() {
        System.out.println("Getting all categories");
        List<CategoryDto> all = categoryFeignService.findAll();
        all.forEach(caregoryDto -> System.out.println(caregoryDto.getTitle()));
        //Assertions.assertEquals(3,all);
    Assertions.assertNotNull(all);
    }

    @Test
    public void testFeignCategeoryById()
    {
        System.out.println("inside single Category..");
        CategoryDto byId = categoryFeignService.findById("19c9bc66-165e-47f9-808f-35a910266e3c");
        System.out.println(byId.getTitle());
        Assertions.assertNotNull(byId);
    }


/*
    @Test
    public void testFeignCreateCategory()
    {
        System.out.println("inside single Category..");
        CategoryDto byId = categoryFeignService.createCategory();
        System.out.println(byId.getTitle());
        Assertions.assertNotNull(byId);
    }
*/

}
