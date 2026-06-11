package com.comiccon;

import com.comiccon.service.BusinessQueryService;
import com.comiccon.service.CrudService;
import com.comiccon.util.DataSeeder;
import com.comiccon.util.HibernateUtil;

public class Main {

    public static void main(String[] args) {
        try {
            HibernateUtil.getEntityManagerFactory();
            DataSeeder.seed();
            System.out.println("Hibernate инициализирован, схема создана и заполнена\n");

            System.out.println("========== CRUD-операции через Hibernate ==========");
            CrudService crudService = new CrudService();
            crudService.runAll();

            System.out.println("========== Примеры бизнес-запросов (JPQL) ==========");
            BusinessQueryService queryService = new BusinessQueryService();
            queryService.runAll();

        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        } finally {
            HibernateUtil.close();
            System.out.println("\nHibernate закрыт. Готово.");
        }
    }
}