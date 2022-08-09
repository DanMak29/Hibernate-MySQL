package DanMak.ProjectMD1608;

import DanMak.ProjectMD1608.entity.Category;
import DanMak.ProjectMD1608.entity.Feature;
import DanMak.ProjectMD1608.entity.OptionValue;
import DanMak.ProjectMD1608.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Scanner;

public class CreateCategory {
    public static void main(String[] args) {


        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();


        //СОЗДАНИЕ ДАННЫХ В БАЗЕ

        manager.getTransaction().begin();

        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Создание категории ---");
        System.out.println("Введите название: ");
        String name = scanner.nextLine();
        Category category = new Category();
        category.setName(name);
        manager.persist(category);

        System.out.print("Введите характеристики через запятую: ");
        String featuresVal = scanner.nextLine();
        String[] features = featuresVal.split(",");
        for (String featureVal : features) {
            Feature feature = new Feature();
            feature.setCategoryOption(featureVal);
            feature.setCategory(category);
            manager.persist(feature);
            System.out.println(featureVal.trim());
        }

        manager.getTransaction().commit();


        // ОБНОВЛЕНИЕ ДАННЫХ В БАЗЕ


//        try {
//
//            manager.getTransaction().begin();
//
//            Category category = manager.find(Category.class, 3L);
//            category.setName("new name");
//            manager.persist(category);
//
//            manager.getTransaction().commit();
//
//        } catch (Exception e){
//            manager.getTransaction().rollback();
//            e.printStackTrace();
//        }

        // УДАЛЕНИЕ ДАННЫХ В БАЗЕ

//        try {
//         manager.getTransaction().begin();
//
//         Category category = manager.find(Category.class, 15l);
//         manager.remove(category);
//
//         manager.getTransaction().commit();
//        }catch (Exception e){
//            manager.getTransaction().rollback();
//        }
        //***************************************
//        String fragment = "Т";
//        TypedQuery<Category> query = manager.createQuery(
//                "select c from Category c where c.name  like ?1", Category.class);
//        query.setParameter(1, fragment + "%");
//        List<Category> categories = query.getResultList();
//        for (Category category : categories) {
//            System.out.println(category.getName());
//        }

//        try {
//            manager.getTransaction().begin();
//
//            Scanner scanner = new Scanner(System.in);
//            System.out.println("--- Изменение категории ---");
//            System.out.println("Введите идентификатор категории: ");
//            String id = scanner.nextLine();
//            Category category = manager.find(Category.class, Long.parseLong(id));
//            System.out.println("Введите новое [" + category.getName() + "]: ");
//            String newName = scanner.nextLine();
//            if (newName != "") {
//                category.setName(newName);
//            }
//            ;
//            manager.persist(category);
//            manager.getTransaction().commit();
//        } catch (Exception e) {
//            manager.getTransaction().rollback();
//            e.printStackTrace();
//        }

//        try {
//            manager.getTransaction().begin();
//
//            Scanner scanner = new Scanner(System.in);
//
//            System.out.println("---Создание товара---");
//            TypedQuery<Category> query = manager.createQuery(
//                    "select c from Category c", Category.class);
//            List<Category> categories = query.getResultList();
//            for (Category category : categories) {
//                System.out.println(category.getId() + ") " + category.getName());
//            }
//            System.out.println("Введите идентификатор списка: ");
//            String categoryId = scanner.nextLine();
//            Product product = new Product();
//            boolean c = categoryId.matches("\\d+");
//            while (!c) {
//                System.out.println("Некорретно, введите заново: ");
//                categoryId = scanner.nextLine();
//                c = categoryId.matches("\\d+");
//            }
//            Category category = manager.find(Category.class, Long.parseLong(categoryId));
//            product.setCategory(category);
//
//            System.out.println("Введите название товара: ");
//            String NewName = scanner.nextLine();
//            product.setName(NewName);
//            System.out.println("Введите цену товара: ");
//            String price = scanner.nextLine();
//            boolean b = price.matches("\\d+");
//            while (!b) {
//                System.out.println("Некорректно, введите заново: ");
//                price = scanner.nextLine();
//                b = price.matches("\\d+");
//            }
//            product.setPrice(Long.parseLong(price));
//            manager.persist(product);
//
//            System.out.println("---Введите значения---");
//            List<Feature> features = category.getFeatures();
//            for (Feature feature : features) {
//                OptionValue value = new OptionValue();
//                System.out.println(feature.getCategoryOption());
//                String featureValue = scanner.nextLine();
//                value.setFeature(feature);
//                value.setProduct(product);
//                value.setValue(featureValue);
//                manager.persist(value);
//            }
//            ;
//
//            manager.getTransaction().commit();
//        } catch (Exception e) {
//            manager.getTransaction().rollback();
//            e.printStackTrace();
//        }

        try {
            manager.getTransaction().begin();

            Scanner scanner = new Scanner(System.in);

            System.out.println("--- Изменение данных о товаре ---");

            System.out.println("Введите идентификатор товара: ");
            String productId = scanner.nextLine();
            Product product = manager.find(Product.class, Long.parseLong(productId));
            TypedQuery<Product> query = manager.createQuery(
                    "select p from Product p", Product.class
            );
            List<Product> productList = query.getResultList();
            System.out.println(product.getId() + ") " + product.getName() + " - " + product.getPrice());

            System.out.println("Введите новое название товара: ");
            String newName = scanner.nextLine();
            if (!newName.equals("")) {
                product.setName(newName);
            }
            System.out.println("Введите цену: ");
            String newPrice = scanner.nextLine();
            if (!newPrice.equals("")) {
                product.setPrice(Long.parseLong(newPrice));
            }
            System.out.println("Введите новые значения: ");
            List<Feature> features = product.getCategory().getFeatures();
            for (Feature feature : features) {
                TypedQuery<OptionValue> value = manager.createQuery(
                        "select ov from OptionValue ov where ov.product = ?1 and ov.feature = ?2", OptionValue.class
                );
                value.setParameter(1, product);
                value.setParameter(2, feature);
                List<OptionValue> values = value.getResultList();

                System.out.println(feature.getCategoryOption());
                String featureValue = scanner.nextLine();
                OptionValue newValue = new OptionValue();
                if (!featureValue.equals("")) {
                    if (values.size() == 0) {
                        newValue.setFeature(feature);
                        newValue.setProduct(product);
                        newValue.setValue(featureValue);
                        manager.persist(newValue);
                    } else if (values.size() > 0) {
                        values.get(0).setValue(featureValue);
                        manager.persist(values.get(0));
                    }
                }
            }

            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }

    }
}