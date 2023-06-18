package com.Bikkadit.blog.Services.ServiceImpl;

import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.Bikkadit.blog.Repositories.CategoryRepo;
import com.Bikkadit.blog.Services.CategoryService;
import com.Bikkadit.blog.config.Appconstants;
import com.Bikkadit.blog.entities.Category;
import com.Bikkadit.blog.exceptions.ResourceNotFoundException;
import com.Bikkadit.blog.payloads.CategoryDto;
import com.Bikkadit.blog.payloads.CategoryResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryServiceimpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper mapper;
/**
 * @apiNote This Api is used to Create Category
 */
	@Override
	public CategoryDto createCateory(CategoryDto categoryDto) {

		Category catogery = mapper.map(categoryDto, Category.class);
		log.info("Enterging Into Persistence to Create Category in table");
		Category catogery2 = categoryRepo.save(catogery);
		log.info("Send Request to controller");
		return this.mapper.map(catogery2, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		log.info("Enterging Into Persistence to Update category with categoryId):{}", categoryId);
		Category catogery = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

		catogery.setCatogorydesciption(categoryDto.getCatogorydesciption());
		catogery.setCatogorytitle(categoryDto.getCatogorytitle());
		Category category = categoryRepo.save(catogery);
		log.info("Successfully Update category with categoryId):{}", categoryId);
		return this.mapper.map(category , CategoryDto.class);
	}

	@Override
	public List<CategoryDto> GetAllCategory() {
		log.info("Enterging Into Persistence to get all Categories");
		List<Category> findAll = categoryRepo.findAll();
		List<CategoryDto> list = findAll.stream().map((c) -> mapper.map(c, CategoryDto.class))
				.collect(Collectors.toList());
		log.info("Successfully  get all Categories");
		return list;
	}

	@Override
	public void DeleteCatogery(Integer categoryId) {
		log.info("Enterging Into Persistence to Delete a Single User with categoryId :{}", categoryId);
		// Category catogery = categoryRepo.findById(categoryId).orElseThrow(()->new
		// ResourceNotFoundException("Category", "CategoryId",categoryId));
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new RuntimeException(Appconstants.NOT_FOUND + categoryId));
		categoryRepo.delete(category);
		log.info("Successfully  Delete Category from Dao with categoryId :{}", categoryId);
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {

		log.info("Enterging Into Persistence to get a Single User with categoryId :{}", categoryId);
		Category catogery = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CaategoryId", categoryId));
		log.info("Successfulyy Get Category from Dao with categoryId :{}", categoryId);

		return this.mapper.map(catogery, CategoryDto.class);

	}

	@Override
	public CategoryResponse getAllCategoryResponse(Integer pageNumber, Integer pageSize) {
		log.info("Enterging Into Persistence to get all Categories");
		PageRequest of = PageRequest.of(pageNumber, pageSize);
		Page<Category> findAll = categoryRepo.findAll(of);
		List<Category> list2 = findAll.getContent();
		List<CategoryDto> list = list2.stream().map((c) -> mapper.map(c, CategoryDto.class))
				.collect(Collectors.toList());
		log.info("Successfully  get all Categories");
		CategoryResponse categoryResponse = new CategoryResponse();
		categoryResponse.setContent(list);
		categoryResponse.setPageNumber(findAll.getNumber());
		categoryResponse.setPageSize(findAll.getSize());
		categoryResponse.setTotalElements(findAll.getTotalElements());
		categoryResponse.setTotalPages(findAll.getTotalPages());
		categoryResponse.setLastpage(findAll.isLast());

		return categoryResponse;
	}

}
