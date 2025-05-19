import com.test.shoebox.dto.ProductDTO;
import com.test.shoebox.repository.BrandRepository;
import com.test.shoebox.repository.CategoriesRepository;
import com.test.shoebox.repository.ProductGroupRepository;
import com.test.shoebox.service.admin.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final BrandRepository brandRepository;
    private final CategoriesRepository categoriesRepository;
    private final ProductGroupRepository productGroupRepository;

    @GetMapping("/form")
    public String productForm(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("brands", brandRepository.findAll());
        model.addAttribute("categories", categoriesRepository.findAll());
        model.addAttribute("groups", productGroupRepository.findAll());
        return "admin/product/form";
    }

    @PostMapping("/form")
    public String saveProduct(@ModelAttribute ProductDTO dto) {
        productService.saveProduct(dto);
        return "redirect:/admin/product/list";
    }

    @GetMapping("/list")
    public String productList(Model model) {
        model.addAttribute("productList", productService.findAll());
        return "admin/product/list";
    }
}
