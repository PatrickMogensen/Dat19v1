package com.semesterproject.semesterproject.Controller;

import com.semesterproject.semesterproject.Model.*;
import com.semesterproject.semesterproject.Service.CustomerService;
import com.semesterproject.semesterproject.Service.OrderService;
import com.semesterproject.semesterproject.Service.PostService;
import com.semesterproject.semesterproject.Service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController implements ErrorController {

    @Autowired
    ProductService productService;
    @Autowired
    PostService postService;
    @Autowired
    CustomerService customerService;
    @Autowired
    OrderService orderService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /* General */
    private String HOME = "/index";
    private String ABOUTUS = "/about-us";
    private String CONTACTUS = "/contact-us";
    private String SERVICES = "/service";
    private String ERROR = "/error";
    private String ACCESSDENIED = "/access-denied";
    private String ADMIN = "/admin";
    private String SIGNIN = "/sign-in";
    /* Blog */
    private String ALLPOSTS = "/all-posts";
    private String ALLBLOGPOSTADMIN = "/admin-blog";
    private String POSTREDIRCT = "redirect:/admin-blog";
    private String VIEWPOST = "/view-post";
    private String EDITPOST = "/edit-post";
    private String CREATEPOST = "/create-post";
    /* Kunde */
    private String ALLCUSTOMERADMIN = "/admin-customer";
    private String CUSTOMERREDIRCT = "redirect:/admin-customer";
    private String VIEWCUSTOMER = "/view-customer";
    private String EDITCUSTOMER = "/edit-customer";
    /* Produkt */
    private String ALLPRODUCTS = "/all-products";
    private String ALLPRODUCTSADMIN = "/admin-product";
    private String PRODUCTSREDIRCT = "redirect:/admin-product";
    private String VIEWPRODUCT = "/view-product";
    private String EDITPRODUCT = "/edit-product";
    private String CREATEPRODUCT = "/create-product";
    /* Ordre */
    private String ALLORDERSADMIN = "/admin-order";
    private String VIEWORDER = "/view-order";
    private String ORDERREDIRECT = "redirect:/admin-order";
    private String ORDERITEMS = "/view-items-order";
    /* Rapport/statistik */
    private String REPORTADMIN = "/admin-report";
    /* Kurv */
    private String CART = "/cart";
    /* Søg */
    private String SEARCHADMIN = "/admin-search";

/* ---------------------------------------------------
    GETMAPPING METODER
----------------------------------------------------- */
    @GetMapping("/index")
    public String home(Model model, SecurityContextHolderAwareRequestWrapper requestWrapper) {
        log.info("Loading home page...");
        List<BlogPost> postList = postService.fetchRecentPosts();
        model.addAttribute("post", postList);

        if (requestWrapper.isUserInRole("ADMIN")) {
            log.info("Showing admin link...");
            model.addAttribute("roleAdmin", true);
        }
        return HOME;
    }

    @GetMapping("/about-us")
    public String aboutUs(Model model) {
        log.info("Loading about us page...");
        return ABOUTUS;
    }

    @GetMapping("/contact-us")
    public String contactUs(Model model) {
        log.info("Loading contact us page...");
        return CONTACTUS;
    }

    @GetMapping("/service")
    public String services(Model model) {
        log.info("Loading services page...");
        return SERVICES;
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        log.info("Loading admin page...");
        List<Order> orderList = orderService.fetchRecentOrders();
        model.addAttribute("order", orderList);
        return ADMIN;
    }

    @GetMapping("/sign-in")
    public String signin(String error, Model model) {
        log.info("Loading log in page...");
        if (error != null) {
            model.addAttribute("ErrorParam", true);
        }
        return SIGNIN;
    }

    @GetMapping("/sign-out")
    public String signout(HttpSession session) {
        log.info("Signing out...");
        session.invalidate();
        return HOME;
    }

    @GetMapping("/urtepotteskjulere")
    public String urtepotteskjulere(Model model) {
        log.info("Showing category urtepotteskjulere...");
        List<Product> productList = productService.showUrtepotteskjulere();
        model.addAttribute("products", productList);
        return ALLPRODUCTS;
    }

    @GetMapping("/planterblomster")
    public String planterblomster(Model model) {
        log.info("Showing category planterblomster...");
        List<Product> productList = productService.showPlanterblomster();
        model.addAttribute("products", productList);
        return ALLPRODUCTS;
    }

    @GetMapping("/pedestalervogne")
    public String pedestalervogne(Model model) {
        log.info("Showing category pedestalervogne...");
        List<Product> productList = productService.showPedestalervogne();
        model.addAttribute("products", productList);
        return ALLPRODUCTS;
    }

    @GetMapping("/drivhusekasser")
    public String drivhusekasser(Model model) {
        log.info("Showing category drivhusekasser...");
        List<Product> productList = productService.showDrivhusekasser();
        model.addAttribute("products", productList);
        return ALLPRODUCTS;
    }

    @GetMapping("/vandkander")
    public String vandkander(Model model) {
        log.info("Showing category vandkander...");
        List<Product> productList = productService.showVandkander();
        model.addAttribute("products", productList);
        return ALLPRODUCTS;
    }

    @GetMapping("/tilbehor")
    public String tilbehor(Model model) {
        log.info("Showing category tilbehor...");
        List<Product> productList = productService.showTilbehor();
        model.addAttribute("products", productList);
        return ALLPRODUCTS;
    }

    @GetMapping("/nyevarer")
    public String nyevarer(Model model) {
        log.info("Showing category nyevarer...");
        List<Product> productList = productService.showNyevarer();
        model.addAttribute("products", productList);
        return ALLPRODUCTS;
    }

    @GetMapping("/specialtilbud")
    public String specialtilbud(Model model) {
        log.info("Showing category specialtilbud...");
        List<Product> productList = productService.showSpecialtilbud();
        model.addAttribute("products", productList);
        return ALLPRODUCTS;
    }

    // Lavet af Patrick
    @GetMapping("/search")
    public String search(@RequestParam("keyWord") String keyWord, Model model) {
        log.info("Loading view product page...");
        List<Product> productList = productService.fetchAllProducts();

        for (Product p: productList) {
            if (p.getName().equals(keyWord)){
                model.addAttribute("products", p);
            }

        }
        return ALLPRODUCTS;
    }

    // Lavet af Patrick
    @GetMapping("/adminSearch")
    public String adminSearch(@RequestParam("keyWord") String keyWord, Model model) {
        log.info("Loading view product page...");
        List<Product> productList = productService.fetchAllProducts();
        List<Order> orderList = orderService.fetchAllOrders();
        List<Customer> customerList = customerService.fetchAllCustomers();

        List<Product> productResults =  new ArrayList<Product>();
        List<Order> orderResults =  new ArrayList<Order>();
        List<Customer> customerResults =  new ArrayList<Customer>();


        for (Product p: productList) {
            if (p.getName().equals(keyWord)){
                productResults.add(p);
                model.addAttribute("product", productResults);
            }
        }
        for (Order o : orderList) {

            if (o.getFull_name().contains(keyWord)){
                orderResults.add(o);
                model.addAttribute("order", orderResults);
            }
        }
        for (Customer c: customerList) {
            String fullName = c.getFirst_name() + " " + c.getLast_name();
            if (fullName.contains(keyWord)){
                customerResults.add(c);
                model.addAttribute("customer", customerResults);
            }
        }
        return SEARCHADMIN;
    }
/* ---------------------------------------------------
    ERROR SIDER
----------------------------------------------------- */
    @Override
    public String getErrorPath() {
        return ERROR;
    }

    @GetMapping("/error")
    public String handleError() {
        log.info("Loading error page...");
        return ERROR;
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        log.info("Access denied!");
        return ACCESSDENIED;
    }
/* ---------------------------------------------------
    PRODUCTS METODER
----------------------------------------------------- */
    // Lavet af Patrick
    @GetMapping("/admin-product")
    public String fetchAllProducts(Model model) {
        log.info("Loading all products admin page...");
        model.addAttribute("product", productService.fetchAllProducts());
        return ALLPRODUCTSADMIN;
    }

    // Lavet af Mikkel
    @GetMapping("/create-product")
    public String createProduct() {
        log.info("Loading create product page...");
        return CREATEPRODUCT;
    }

    // Lavet af Mikkel
    @PostMapping("/create-product")
    public String createProductPOST(@ModelAttribute Product product, Model model) {
        log.info("Creating product...");
        try {
            productService.createProduct(product);
            log.info("Product was created!");
            return PRODUCTSREDIRCT;
        } catch (Exception ex) {
            log.info("Product was not created!");
            return PRODUCTSREDIRCT;
        }
    }

    // Lavet af Patrick
    @GetMapping("/view-product/{product_id}")
    public String findProductById(@PathVariable("product_id") int product_id, Model model) {
        log.info("Loading view product page...");
        model.addAttribute("product", productService.findProductById(product_id));
        return VIEWPRODUCT;
    }

    // Lavet af Mikkel
    @GetMapping("/edit-product/{product_id}")
    public String editProduct(@PathVariable("product_id") int product_id, Model model) {
        log.info("Loading edit product page...");
        model.addAttribute("product", productService.findProductById(product_id));
        return EDITPRODUCT;
    }

    // Lavet af Mikkel
    @PostMapping("/edit-product")
    public String editProductPOST(@ModelAttribute Product product, Model model){
        log.info("Publishing edits...");
        productService.editProduct(product.getProduct_id(), product);
        return PRODUCTSREDIRCT;
    }

    // Lavet af Maria
    @GetMapping("/delete-product/{product_id}")
    public String deleteProduct(@PathVariable("product_id") int product_id, Model model) {
        log.info("Deleting product...");
        try {
            productService.deleteProduct(product_id);
            log.info("Product was deleted!");
            return PRODUCTSREDIRCT;
        } catch (Exception ex) {
            log.info("Product was not deleted!");
            return PRODUCTSREDIRCT;
        }
    }

    // Lavet af Patrick
    @GetMapping("/all-products")
    public String allProducts(Model model) {
        log.info("Loading all products page...");
        model.addAttribute("products", productService.fetchAllProducts());
        return ALLPRODUCTS;
    }
/* ---------------------------------------------------
    BLOG POST METHODS
----------------------------------------------------- */
    // Lavet af Maria
    @GetMapping("/admin-blog")
    public String fetchAllPosts(Model model) {
        log.info("Showing all blog posts...");
        List<BlogPost> postList = postService.fetchAllPosts();
        model.addAttribute("post", postList);
        return ALLBLOGPOSTADMIN;
    }

    // Lavet af Maria
    @GetMapping("/view-post/{post_id}")
    public String findPostByID(@PathVariable("post_id") int post_id, Model model) {
        log.info("Showing single post...");
        model.addAttribute("post", postService.findPostByID(post_id));
        return VIEWPOST;
    }

    // Lavet af Maria
    @GetMapping("/create-post")
    public String createPost(Model model) {
        log.info("Loading create post page...");
        return CREATEPOST;
    }

    // Lavet af Maria
    @PostMapping("/create-post")
    public String createPostPOST(@ModelAttribute BlogPost blogPost, Model model) {
        log.info("Creating post...");
        try {
            postService.createPost(blogPost);
            log.info("Blog post was created!");
            return POSTREDIRCT;
        } catch (Exception ex) {
            log.info("Blog post was not created!");
            return POSTREDIRCT;
        }
    }

    // Lavet af Maria
    @GetMapping("/edit-post/{post_id}")
    public String editPost(@PathVariable("post_id") int post_id, Model model) {
        log.info("Loading edit post page...");
        model.addAttribute("post", postService.findPostByID(post_id));
        return EDITPOST;
    }

    // Lavet af Maria
    @PostMapping("/edit-post")
    public String editPostPOST(@ModelAttribute BlogPost blogPost, Model model){
        log.info("Publishing edits...");
        postService.editPost(blogPost.getPost_id(), blogPost);
        return POSTREDIRCT;
    }

    // Lavet af Maria
    @GetMapping("/delete-post/{post_id}")
    public String deletePost(@PathVariable("post_id") int post_id, Model model) {
        log.info("Deleting post...");
        try {
            postService.deletePost(post_id);
            log.info("Blog post deleted!");
            return POSTREDIRCT;
        } catch (Exception ex) {
            log.info("Blog post was not deleted!");
            return POSTREDIRCT;
        }
    }

    // Lavet af Maria
    @GetMapping("/all-posts")
    public String allPosts(Model model) {
        log.info("Showing all posts...");
        List<BlogPost> postList = postService.fetchAllPosts();
        model.addAttribute("post", postList);
        return ALLPOSTS;
    }
/* ---------------------------------------------------
    CUSTOMER METODER
----------------------------------------------------- */
    // Lavet af Maria
    @GetMapping("/admin-customer")
    public String fetchAllCustomers(Model model) {
        log.info("Showing all customers...");
        List<Customer> customerList = customerService.fetchAllCustomers();
        model.addAttribute("customer", customerList);
        return ALLCUSTOMERADMIN;
    }

    // Lavet af Maria
    @GetMapping("/view-customer/{customer_id}")
    public String findCustomerByID(@PathVariable("customer_id") int customer_id, Model model) {
        log.info("Showing single customer...");
        model.addAttribute("customer", customerService.findCustomerByID(customer_id));
        return VIEWCUSTOMER;
    }

    // Lavet af Maria
    @GetMapping("/edit-customer/{customer_id}")
    public String editCustomer(@PathVariable("customer_id") int customer_id, Model model) {
        log.info("Loading edit customer page...");
        model.addAttribute("customer", customerService.findCustomerByID(customer_id));
        return EDITCUSTOMER;
    }

    // Lavet af Maria
    @PostMapping("/edit-customer")
    public String editCustomerPOST(@ModelAttribute Customer customer, Model model){
        log.info("Publishing edits...");
        customerService.editCustomer(customer.getCustomer_id(), customer);
        return CUSTOMERREDIRCT;
    }

    // Lavet af Maria
    @GetMapping("/delete-customer/{customer_id}")
    public String deleteCustomer(@PathVariable("customer_id") int customer_id, Model model) {
        log.info("Deleting customer...");
        try {
            customerService.deleteCustomer(customer_id);
            log.info("Customer was deleted!");
            return CUSTOMERREDIRCT;
        } catch (Exception ex) {
            log.info("Customer was not deleted!");
            return CUSTOMERREDIRCT;
        }
    }
/* ---------------------------------------------------
    ORDER METODER
----------------------------------------------------- */
    // Lavet af Maria
    @GetMapping("/admin-order")
    public String fetchAllOrders(Model model) {
        log.info("Showing all orders...");
        List<Order> orderList = orderService.fetchAllOrders();
        model.addAttribute("order", orderList);
        return ALLORDERSADMIN;
    }

    // Lavet af Maria
    @GetMapping("/view-order/{order_id}")
    public String findOrderById(@PathVariable("order_id") int order_id, Model model) {
        log.info("Showing single order...");
        model.addAttribute("order", orderService.findOrderById(order_id));
        return VIEWORDER;
    }

    // Lavet af Maria
    @PostMapping("/view-order")
    public String editOrder(@ModelAttribute Order order, Model model){
        log.info("Publishing edits...");
        orderService.editOrder(order.getOrder_id(), order);
        return ORDERREDIRECT;
    }

    // Lavet af Maria
    @GetMapping("/view-items-order/{order_id}")
    public String fetchOrderItems(@PathVariable("order_id") int order_id, Model model) {
        log.info("Showing all items for order...");
        List<Order> orderList = orderService.fetchOrderItems(order_id);
        model.addAttribute("order", orderList);
        return ORDERITEMS;
    }
/* ---------------------------------------------------
    REPORT METODER
----------------------------------------------------- */
    @GetMapping("/admin-report")
    public String reportAdmin(Model model) {
        log.info("Showing all reports...");
        return REPORTADMIN;
    }
/* ---------------------------------------------------
    CART METODER
----------------------------------------------------- */
    @GetMapping("/cart")
    public String cart(Model model) {
        log.info("Showing cart...");
        return CART;
    }
}