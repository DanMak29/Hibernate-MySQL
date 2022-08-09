package DanMak.ProjectMD1608;

import DanMak.ProjectMD1608.entity.Category;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();
//        Category category = manager.find(Category.class, 2L);
//        if (category != null) {
//            List<Product> products = category.getProducts();
//            int sum = 0;
//            int avg = 0;
//            for (Product product : products) {
//                sum += product.getPrice();
//            }
//            avg = sum   / products.size();
//            System.out.println(avg);
//        }
//        manager.getTransaction().begin();
//        Category category = new Category();
//        category.setName("Материнские платы");
//        manager.persist(category);
//        manager.getTransaction().commit();
    }
}
