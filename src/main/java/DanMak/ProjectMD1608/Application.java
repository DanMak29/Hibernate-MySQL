package DanMak.ProjectMD1608;

import DanMak.ProjectMD1608.entity.Category;
import DanMak.ProjectMD1608.entity.Feature;
import DanMak.ProjectMD1608.entity.OptionValue;
import DanMak.ProjectMD1608.entity.Product;

import javax.persistence.*;
import java.util.List;
import java.util.Scanner;

public class Application {

    private static final EntityManagerFactory factory =
            Persistence.createEntityManagerFactory("main");
    private static final Scanner IN = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {

            System.out.println("-Создание[1]");
            System.out.println("-Редактирование[2]");
            System.out.println("-Удаление[3]");
            System.out.println("-Завершить программу[4]");

            System.out.println("Выберите действие: ");
            String action = IN.nextLine();

            switch (action) {
                case "1" -> create();
                case "2" -> update();
                case "3" -> delete();
                case "4" -> {
                    return;
                }
            }
        }
    }

    private static void create() {
        EntityManager manager = factory.createEntityManager();

        try {
            manager.getTransaction().begin();

            System.out.println("---Создание товара---");
            TypedQuery<Category> query = manager.createQuery(
                    "select c from Category c", Category.class);
            List<Category> categories = query.getResultList();
            for (int i = 0; i < categories.size(); i++) {
                System.out.println(i + 1 + ") " + categories.get(i).getName());
            }
            System.out.println("Введите идентификатор списка: ");
            String categoryId = IN.nextLine();

            int j = Integer.parseInt(categoryId) - 1;
            Category category = categories.get(j);

            Product product = new Product();
            boolean c = categoryId.matches("\\d+");
            while (!c) {
                System.out.println("Некорретно, введите заново: ");
                categoryId = IN.nextLine();
                c = categoryId.matches("\\d+");
            }

            product.setCategory(category);

            System.out.println("---Введите значения---");

            System.out.println("Введите название товара: ");
            String NewName = IN.nextLine();
            product.setName(NewName);
            System.out.println("Введите цену товара: ");
            String price = IN.nextLine();
            boolean b = price.matches("\\d+");
            while (!b) {
                System.out.println("Некорректно, введите заново: ");
                price = IN.nextLine();
                b = price.matches("\\d+");
            }
            product.setPrice(Long.parseLong(price));
            manager.persist(product);

            List<Feature> features = category.getFeatures();
            for (Feature feature : features) {
                OptionValue optionValue = new OptionValue();
                System.out.println(feature.getCategoryOption());
                String featureValue = IN.nextLine();
                optionValue.setFeature(feature);
                optionValue.setProduct(product);
                optionValue.setValue(featureValue);
                manager.persist(optionValue);
            }

            manager.getTransaction().commit();

        } catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();

        } finally {
            manager.close();
        }
    }


    private static void update() {
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();

            System.out.println("--- Изменение данных о товаре ---");

            TypedQuery<Product> query = manager.createQuery(
                    "select p from Product p", Product.class
            );
            List<Product> productList = query.getResultList();

            for (int i = 0; i < productList.size(); i++) {
                System.out.println(i + 1 + ")" + productList.get(i).getName() + " - " + productList.get(i).getPrice());
            }
            System.out.println("Введите номер товара: ");
            String productId = IN.nextLine();

            int j = Integer.parseInt(productId) - 1;
            Product product = productList.get(j);

            System.out.println("Введите новое название товара: ");
            String newName = IN.nextLine();
            if (!newName.equals("")) {
                product.setName(newName);
            }
            System.out.println("Введите цену: ");
            String newPrice = IN.nextLine();
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
                String featureValue = IN.nextLine();
                OptionValue newValue = new OptionValue();
                if (!featureValue.equals("")) {
                    if (values.size() == 0) {
                        newValue.setFeature(feature);
                        newValue.setProduct(product);
                        newValue.setValue(featureValue);
                        manager.persist(newValue);
                    }
                    if (values.size() > 0) {
                        values.get(0).setValue(featureValue);
                        manager.persist(values.get(0));
                    }
                }
            }

            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();

        } finally {
            manager.close();
        }
    }


    private static void delete() {
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();

            System.out.println("---Удаление товара---");

            TypedQuery<Product> query = manager.createQuery(
                    "select p from Product p", Product.class
            );
            List<Product> productList = query.getResultList();

            for (int i = 0; i < productList.size(); i++) {
                System.out.println(i + 1 + ")" + productList.get(i).getName());
            }

            System.out.println("Введите номер товара: ");
            String productId = IN.nextLine();

            int j = Integer.parseInt(productId) - 1;
            Product product = productList.get(j);

            Query queryOption = manager.createQuery("delete from OptionValue where product.id = ?1");
            queryOption.setParameter(1, product.getId());
            queryOption.executeUpdate();

            Query query1 = manager.createQuery("delete from Product where id= ?1");
            query1.setParameter(1, product.getId());
            query1.executeUpdate();

            manager.getTransaction().commit();

        } catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
    }
}
