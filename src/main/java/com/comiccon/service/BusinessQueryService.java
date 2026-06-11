package com.comiccon.service;

import com.comiccon.util.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class BusinessQueryService {

    public void top5MembersByMarks() {
        printHeader("Топ-5 участников по сумме баллов (сложность + артистизм)");
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            List<Object[]> results = em.createQuery("""
                SELECT
                    mb.lastName,
                    mb.firstName,
                    mb.hero,
                    SUM(c.difficultMark + c.artisticMark)
                FROM Mark m
                JOIN m.performance p
                JOIN p.members mb
                JOIN m.criterion c
                GROUP BY mb.id, mb.lastName, mb.firstName, mb.hero
                ORDER BY SUM(c.difficultMark + c.artisticMark) DESC
                """, Object[].class).setMaxResults(5).getResultList();

            System.out.printf("     %-15s %-10s %-20s %-12s%n", "Фамилия", "Имя", "Герой", "Сумма баллов");
            System.out.println("     " + "─".repeat(62));
            for (Object[] row : results) {
                System.out.printf("     %-15s %-10s %-20s %-12.2f%n",
                        row[0], row[1], row[2], (BigDecimal) row[3]);
            }
        }
        printDivider();
    }

    public void festByDate(LocalDate date) {
        printHeader("Мероприятия на " + date);

        OffsetDateTime from = date.atStartOfDay().atOffset(ZoneOffset.UTC);
        OffsetDateTime to = date.plusDays(1).atStartOfDay().atOffset(ZoneOffset.UTC);

        try (EntityManager em = HibernateUtil.createEntityManager()) {
            List<Object[]> results = em.createQuery("""
                    SELECT
                        f.company.companyName,
                        f.performance.topic,
                        f.volunteer.lastName,
                        f.volunteer.firstName,
                        f.volunteer.task,
                        f.address,
                        f.date
                    FROM Fest f
                    LEFT JOIN f.volunteer
                    LEFT JOIN f.performance
                    WHERE f.date BETWEEN :from AND :to ORDER BY f.date
                    """, Object[].class).setParameter("from", from)
                    .setParameter("to", to).getResultList();

            System.out.printf("     %-15s %-25s %-15s %-10s %-25s %-25s %-20s%n",
                    "Компания", "Тема выступления", "Волонтер (фам)", "Имя", "Задание", "Адрес", "Время");
            System.out.println("     " + "─".repeat(135));

            if (results.isEmpty()) {
                System.out.println("На указанную дату мероприятий не найдено");
            } else {
                for (Object[] row : results) {
                    System.out.printf("     %-15s %-25s %-15s %-10s %-25s %-25s %-20s%n",
                            row[0], row[1],
                            row[2] != null ? row[2] : "—",
                            row[3] != null ? row[3] : "—",
                            row[4] != null ? row[4] : "—",
                            row[5], ((OffsetDateTime) row[6]).toLocalTime());
                }
            }
        }
        printDivider();
    }

    public void unUsedVolunteers() {
        printHeader("Волонтеры, не закрепленные ни за одним мероприятием");
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            List<Object[]> results = em.createQuery("""
                    SELECT
                        v.id,
                        v.lastName,
                        v.firstName,
                        v.patronymic,
                        v.task
                    FROM Volunteer v
                    LEFT JOIN Fest f ON v.id = f.volunteer.id
                    WHERE f.volunteer.id IS NULL
                    ORDER BY v.id
                    """, Object[].class).getResultList();

            System.out.printf("     %-5s %-15s %-10s %-15s %-25s%n",
                    "ID", "Фамилия", "Имя", "Отчество", "Задание");
            System.out.println("     " + "─".repeat(70));
            for (Object[] row : results) {
                System.out.printf("     %-5s %-15s %-10s %-15s %-25s%n", row[0], row[1], row[2], row[3], row[4]);
            }
        }
        printDivider();
    };

    public void shopsAreaUpAvg() {
        printHeader("Компании-стенды с площадью выше средней");
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            List<Object[]> results = em.createQuery("""
                    SELECT
                        c.id,
                        c.companyName,
                        c.area
                    FROM Company c
                    WHERE c.area > (SELECT AVG(c.area) FROM Company c)
                    ORDER BY c.area DESC
                    """, Object[].class).getResultList();

            System.out.printf("     %-5s %-15s %-8s%n", "ID", "Компания", "Площадь");
            System.out.println("     " + "─".repeat(28));
            for (Object[] row : results) {
                System.out.printf("     %-5s %-15s %-8s%n", row[0], row[1], row[2]);
            }
        }
        printDivider();
    };

    public void companyWithEvents() {
        printHeader("Статистика, насколько задействована компания");
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            List<Object[]> results = em.createQuery("""
                    SELECT
                    c.companyName,
                    COUNT(f.id),
                    COUNT(p.id),
                    COUNT(v.id)
                FROM Company c
                LEFT JOIN Fest f ON c.id = f.company.id
                LEFT JOIN Performance p ON f.performance.id = p.id
                LEFT JOIN Volunteer v ON f.volunteer.id = v.id
                GROUP BY c.id, c.companyName
                ORDER BY COUNT(f.id) DESC
                """, Object[].class).getResultList();

            System.out.printf("     %-15s %-12s %-12s %-12s%n",
                    "Компания", "Мероприятий", "Выступлений", "Волонтеров");
            System.out.println("     " + "─".repeat(51));
            for (Object[] row : results) {
                System.out.printf("     %-15s %-12s %-12s %-12s%n", row[0], row[1], row[2], row[3]);
            }
        }
        printDivider();
    };

    public void runAll() {
        top5MembersByMarks();
        festByDate(LocalDate.now(ZoneOffset.UTC));
        unUsedVolunteers();
        shopsAreaUpAvg();
        companyWithEvents();
    }

    public void printHeader(String title) {
        System.out.println();
        System.out.println(" " + "─".repeat(title.length() + 4) + " ");
        System.out.println("|  " + title + "  |");
        System.out.println(" " + "─".repeat(title.length() + 4) + " ");
    }

    private void printDivider() {
        System.out.println("─".repeat(150));
    }
}
