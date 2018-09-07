package com.sjtu.donate.Controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.sjtu.donate.Common.AppSettings;
import com.sjtu.donate.Common.ResponseWrapper;
import com.sjtu.donate.Entity.History;
import com.sjtu.donate.Entity.Product;
import com.sjtu.donate.Entity.User;
import com.sjtu.donate.Repository.HistoryRepository;
import com.sjtu.donate.Repository.ProductRepository;
import com.sjtu.donate.Repository.UserRepository;

@RestController
public class ProductController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private HistoryRepository historyRepository;

	@RequestMapping("/buy/user/{userId}/product/{productId}")
	public JSONObject buy(@PathVariable int userId, @PathVariable int productId) {

		Optional<Product> productOpt = productRepository.findById(productId);
		Optional<User> userOpt = userRepository.findById(userId);
		JSONObject res = null;

		if (userOpt.isPresent() && productOpt.isPresent()) {
			User user = userOpt.get();
			Product product = productOpt.get();

			int price = product.getPrice();
			int newBalance = user.getBalance() - price;
			int newAmount = product.getAmount() - 1;

			if (newBalance < 0) {
				res = ResponseWrapper.getResult(false, AppSettings.ERROR_BALANCE);
				return res;
			}

			if (newAmount < 0) {
				res = ResponseWrapper.getResult(false, AppSettings.ERROR_PRODUCT_AMOUNT);
				return res;
			}

			user.setBalance(newBalance);
			product.setAmount(newAmount);

			userRepository.save(user);
			productRepository.save(product);

			History history = new History(userId, productId, price, product.getName(), product.getPhotoUrl(), product.getDescription());
			historyRepository.save(history);

			res = ResponseWrapper.getResult(true, null);
		}

		return res;
	}

	@RequestMapping("/product")
	public List<Product> product() {
		return productRepository.findByAmountGreaterThan(0);
	}

	@RequestMapping("/history/user/{userId}")
	public List<History> history(@PathVariable int userId) {
		return historyRepository.findByUserIdOrderByCreateTimeDesc(userId);
	}

	@RequestMapping("/build")
	public void build() {
		if (AppSettings.BUILD_DATABASE) {
			buildDatabase();
		}
	}

	public void buildDatabase() {
		List<Product> productList = new LinkedList<Product>();
		productList.add(new Product("Pencil", 10,
				"Since 1988, MUJI has been constantly revising the design of its PP gel pens, augmenting the smoothness of writing and providing different choices in colors. A specially designed tip prevents ink reflux or drying up, creating an effortless writing experience.",
				"MUJI", "pencil.jpg", 100));
		productList.add(new Product("Scissors", 20,
				"Different designs to suit different needs. For example Easy-cut Scissors with a curved blade that keeps the arc at 30 degrees, creating an effortless cutting experience. The blades have undergone transparent fluororesin processing, so tape doesn’t stick easily ; Compact and slim Pocket-sized Scissors or Safety Scissors for kids satisfies different demands as well.",
				"MUJI", "scissors.jpg", 80));
		productList.add(new Product("Schedule Books", 15,
				"Begin or pause as you wish. Simple design in and out allows a one-of-a-kind item, enabling everyone to create their personalized schedule book. “How to use” that can be freely determined by the user is one of MUJI’s main design principals.",
				"MUJI", "schedule_books.jpg", 100));
		productList.add(new Product("Ladies' Dress", 200,
				"Using sturdy fabric produced from compact spun yarn which is not fuzzy, it underwent washing process to create the worn-in comfort.",
				"MUJI", "lady_dress.jpg", 10));
		productList.add(new Product("Pencil", 15,
				"Soft, smudge-free, latex-free eraser secured to end for conveniently wiping away mistakes.",
				"SKKSTATIONERY", "pencil2.jpg", 200));
		productList.add(new Product("Shampoo", 35,
				"Fuels hair with a potent blend of Pro-V nutrients and antioxidants so hair is strong against damage",
				"Pantene", "shampoo.jpg", 50));
		productList.add(new Product("Shampoo", 40,
				"Provides your hair with moisture and 95% more volume when using Shampoo and Conditioner as a system or other products in the range vs. flat, limp hair",
				"Dove", "shampoo2.jpg", 40));
		productList.add(new Product("Tissue", 30,
				"One gentle, indulgently soft tissue is all it takes to show you care", "Kleenex", "tissue.jpg", 60));
		productList.add(new Product("Sticky Notes", 30,
				"Commodity real shot, What you see is what you get, Superior paper quality, bright color, ruled design, 3 inch* 3 inch Square Sticky Notes, Lined, 100 sheets/pad, 1200 Sheets in Total, Available in assorted bright fluorescent colors: Pink, Green, Orange, Yellow, White and Rose Red; these sticky notes get noticed easily",
				"Creatiburg", "notes.jpg", 100));

		productRepository.saveAll(productList);
	}

}
