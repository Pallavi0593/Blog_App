package com.Bikkadit.blog.Services;

import java.util.List;

import com.Bikkadit.blog.payloads.CategoryDto;
import com.Bikkadit.blog.payloads.CategoryResponse;

public interface CategoryService {
	
public CategoryDto createCateory(CategoryDto categorydto);

public CategoryDto updateCategory(CategoryDto categoryDto,Integer cid);

public CategoryDto getCategoryById(Integer cid);

public List<CategoryDto> GetAllCategory();

public void DeleteCatogery(Integer cid);

public CategoryResponse getAllCategoryResponse(Integer pageNumber,Integer pageSize);
}
