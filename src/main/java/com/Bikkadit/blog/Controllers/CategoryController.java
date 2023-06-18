package com.Bikkadit.blog.Controllers;



import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Bikkadit.blog.Services.CategoryService;
import com.Bikkadit.blog.config.Appconstants;
import com.Bikkadit.blog.payloads.CategoryDto;
import com.Bikkadit.blog.payloads.CategoryResponse;

@RestController
@RequestMapping("/api")
public class CategoryController {

	Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	private CategoryService categoryService;

	/**
	 * @author PALLAVI YEOLA
	 * @apiNote This Api is used Create Category
	 * @param categoryDto
	 * @return
	 */
	@PostMapping("/Category")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		logger.info("Request Entering for Creating New Record in  Post catogery");

		CategoryDto categoryDto2 = this.categoryService.createCateory(categoryDto);

		logger.info("Record inserted Successfully");

		return new ResponseEntity<CategoryDto>(categoryDto2, HttpStatus.CREATED);

	}

	/**
	 * @author PALLAVI YEOLA
	 * @apiNote This Api is used Update Category
	 * @param categoryDto
	 * @param categoryId
	 * @return
	 */
	@PutMapping("/Category/{categoryId}")
	public ResponseEntity<CategoryDto> UpdateCategory(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable Integer categoryId) {

		logger.info("Request Entering for updating Existing Record in  Post catogery with CategoryId :{}", categoryId);
		CategoryDto updateCategory = categoryService.updateCategory(categoryDto, categoryId);
		logger.info("Record Updated  Successfully with categoryid:{}", categoryId);
		return new ResponseEntity<CategoryDto>(updateCategory, HttpStatus.CREATED);

	}

	/**
	 * @author PALLAVI YEOLA
	 * @apiNote This Api is used to get Category By Id
	 * @param categoryId
	 * @return
	 */
	@GetMapping("/Category/{categoryId}")
	public ResponseEntity<CategoryDto> getCatogeryByid(@PathVariable Integer categoryId) {
		logger.info("Request Entering to get Records from category With CategoryId:{}", categoryId);
		CategoryDto categoryById = categoryService.getCategoryById(categoryId);
		logger.info("Record Get Successfully with categoryId:{}", categoryId);
		return new ResponseEntity<CategoryDto>(categoryById, HttpStatus.OK);

	}

	/**
	 * @author PALLAVI YEOLA
	 * @apiNote This Api is used to delete Category
	 * @param categoryId
	 * @return
	 */
	@DeleteMapping("/category/{categoryId}")
	public ResponseEntity<String> DeleteCategory(@PathVariable Integer categoryId) {
		logger.info("Request Entering to delete Records from category With CategoryId:{}", categoryId);
		categoryService.DeleteCatogery(categoryId);
		logger.info("Request delete Successfully from category With CategoryId:{}", categoryId);
		return new ResponseEntity<>(Appconstants.USER_DELETE, HttpStatus.OK);
	}

	/**
	 * @author PALLAVI YEOLA
	 * @apiNote This Api is used to get All Category in Page formate 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@GetMapping("/Category")
	public ResponseEntity<CategoryResponse> GetallCategoryResponse(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
		logger.info("Request Entering to get All Records in category");
		CategoryResponse allCategoryResponse = categoryService.getAllCategoryResponse(pageNumber, pageSize);
		logger.info("All Records From category Table Get successfully");
		return new ResponseEntity<CategoryResponse>(allCategoryResponse, HttpStatus.OK);

	}
//	@GetMapping("/Category")
//	public ResponseEntity<List<CategoryDto>> GetallCategory() {
//		logger.info("Request Entering to get All Records in category");
//		List<CategoryDto> getAllCategory = categoryService.GetAllCategory();
//		logger.info("All Records From category Table Get successfully");
//		return new ResponseEntity<List<CategoryDto>>(getAllCategory, HttpStatus.OK);
//
//	}

//	@DeleteMapping("/Category/{cid}")
//	public ResponseEntity<ApiResponse> Deleteuser(@PathVariable Integer categoryId) {
//		categoryService.DeleteCatogery(categoryId);
//		return new ResponseEntity<ApiResponse>(new ApiResponse("Record Deleted Successfully", true), HttpStatus.OK);
//
//	}
}
