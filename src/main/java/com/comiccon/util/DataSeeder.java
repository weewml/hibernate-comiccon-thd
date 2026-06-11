package com.comiccon.util;

import com.comiccon.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

public final class DataSeeder {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private DataSeeder() {}

    public static void seed() {
        EntityManager em = HibernateUtil.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Long artistsCount = em.createQuery("SELECT COUNT(a) FROM Artist a", Long.class).getSingleResult();
            if (artistsCount > 0) {
                tx.commit();
                log.info("Начальные данные уже есть, заполнение пропущено");
                return;
            }

            // Художники (artist)
            Artist artist1 = new Artist("Алешина", "Анна", "Матвеевна");
            Artist artist2 = new Artist("Парфенова", "Ангелина", "Данииловна");
            Artist artist3 = new Artist("Князев", "Платон", "Яковлевич");
            Artist artist4 = new Artist("Попова", "Полина", "Матвеевна");
            Artist artist5 = new Artist("Ковалев", "Артур", "Робертович");
            Artist artist6 = new Artist("Новикова", "Екатерина", "Алексеевна");
            em.persist(artist1);
            em.persist(artist2);
            em.persist(artist3);
            em.persist(artist4);
            em.persist(artist5);
            em.persist(artist6);
            Map<Integer, Artist> artistMap = Map.of(1, artist1, 2, artist2, 3, artist3, 4, artist4, 5, artist5, 6, artist6);

            // Компании (company)
            Company company1 = new Company("Яндекс", (short) 30, (short) 10);
            Company company2 = new Company("Сбербанк", (short) 15, (short) 13);
            Company company3 = new Company("Авито", (short) 40, (short) 15);
            Company company4 = new Company("Озон", (short) 60, (short) 11);
            Company company5 = new Company("Альфа-банк", (short) 55, (short) 12);
            company1.addArtist(artistMap.get(1));
            company1.addArtist(artistMap.get(3));
            company1.addArtist(artistMap.get(6));
            company2.addArtist(artistMap.get(2));
            company2.addArtist(artistMap.get(4));
            company3.addArtist(artistMap.get(3));
            company3.addArtist(artistMap.get(5));
            company4.addArtist(artistMap.get(3));
            company5.addArtist(artistMap.get(5));
            em.persist(company1);
            em.persist(company2);
            em.persist(company3);
            em.persist(company4);
            em.persist(company5);
            Map<Integer, Company> companyMap = Map.of(1, company1, 2, company2, 3, company3, 4, company4, 5, company5);

            // Участники (member)
            Member member1 = new Member("Иванов", "Иван", "Иванович", "Человек-паук", "Marvel");
            Member member2 = new Member("Петров", "Игорь", "Викторович", "Железный человек", "Marvel");
            Member member3 = new Member("Смирнова", "Анастасия", "Андреевна", "Харли-Квинн", "DC Comics");
            Member member4 = new Member("Григорьев", "Данил", "Васильевич", "Халк", "Marvel Comics");
            Member member5 = new Member("Воронин", "Петр", "Алексеевич", "Джокер", "DC Comics");
            Member member6 = new Member("Козлова", "Елена", "Игоревна", "Чудо-женщина", "DC Comics");
            em.persist(member1);
            em.persist(member2);
            em.persist(member3);
            em.persist(member4);
            em.persist(member5);
            em.persist(member6);
            Map<Integer, Member> memberMap = Map.of(1, member1, 2, member2, 3, member3, 4, member4, 5, member5, 6, member6);

            // Выступления (performance)
            Performance perf1 = new Performance("Одиночная", "Трюковое представление");
            Performance perf2 = new Performance("Парная", "Первая встреча героев");
            Performance perf3 = new Performance("Одиночная", "Перевоплощение Халка");
            Performance perf4 = new Performance("Парная", "Первая встреча героев");
            Performance perf5 = new Performance("Парная", "Сценка битва-танец");
            Performance perf6 = new Performance("Парная", "Сценка битва-танец");
            perf1.addMember(memberMap.get(1));
            perf2.addMember(memberMap.get(2));
            perf2.addMember(memberMap.get(3));
            perf2.addMember(memberMap.get(1));
            perf3.addMember(memberMap.get(4));
            perf4.addMember(memberMap.get(2));
            perf5.addMember(memberMap.get(3));
            perf5.addMember(memberMap.get(5));
            perf6.addMember(memberMap.get(5));
            perf6.addMember(memberMap.get(6));
            em.persist(perf1);
            em.persist(perf2);
            em.persist(perf3);
            em.persist(perf4);
            em.persist(perf5);
            em.persist(perf6);
            Map<Integer, Performance> performanceMap = new HashMap<>();
            performanceMap.put(1, perf1);
            performanceMap.put(2, perf2);
            performanceMap.put(3, perf3);
            performanceMap.put(4, perf4);
            performanceMap.put(5, perf5);
            performanceMap.put(6, perf6);

            // Критерии (criterion)
            Criterion crit1 = new Criterion(new BigDecimal("2.00"), new BigDecimal("1.75"));
            Criterion crit2 = new Criterion(new BigDecimal("5.50"), new BigDecimal("2.00"));
            Criterion crit3 = new Criterion(new BigDecimal("7.10"), new BigDecimal("5.50"));
            Criterion crit4 = new Criterion(new BigDecimal("8.00"), new BigDecimal("7.85"));
            Criterion crit5 = new Criterion(new BigDecimal("10.00"), new BigDecimal("10.00"));
            em.persist(crit1);
            em.persist(crit2);
            em.persist(crit3);
            em.persist(crit4);
            em.persist(crit5);
            Map<Integer, Criterion> criterionMap = Map.of(1, crit1, 2, crit2, 3, crit3, 4, crit4, 5, crit5);

            // Жюри (jury)
            Jury jury1 = new Jury("Соловьева", "Лиана", "Георгиевна");
            Jury jury2 = new Jury("Михайлов", "Павел", "Артёмович");
            Jury jury3 = new Jury("Павлова", "Алиса", "Максимовна");
            Jury jury4 = new Jury("Медведев", "Денис", "Романович");
            Jury jury5 = new Jury("Андреев", "Егор", "Макарович");
            em.persist(jury1);
            em.persist(jury2);
            em.persist(jury3);
            em.persist(jury4);
            em.persist(jury5);
            Map<Integer, Jury> juryMap = Map.of(1, jury1, 2, jury2, 3, jury3, 4, jury4, 5, jury5);

            // Волонтеры (volunteer)
            Volunteer vol1 = new Volunteer("Гончаров", "Руслан", "Яромирович", "Пополнять запасы напитков");
            Volunteer vol2 = new Volunteer("Титова", "Ярослава", "Данииловна", "Следить за оборудованием");
            Volunteer vol3 = new Volunteer("Суворов", "Всеволод", "Михайлович", "Настраивать свет");
            Volunteer vol4 = new Volunteer("Парамонов", "Андрей", "Макарович", "Рекламировать столы");
            Volunteer vol5 = new Volunteer("Никитин", "Тимур", "Евгеньевич", "Помогать участникам");
            em.persist(vol1);
            em.persist(vol2);
            em.persist(vol3);
            em.persist(vol4);
            em.persist(vol5);
            Map<Integer, Volunteer> volunteerMap = Map.of(1, vol1, 2, vol2, 3, vol3, 4, vol4, 5, vol5);

            // Оценки (mark)
            em.persist(new Mark(juryMap.get(1), criterionMap.get(4), performanceMap.get(1)));
            em.persist(new Mark(juryMap.get(2), criterionMap.get(5), performanceMap.get(2)));
            em.persist(new Mark(juryMap.get(2), criterionMap.get(5), performanceMap.get(4)));
            em.persist(new Mark(juryMap.get(3), criterionMap.get(2), performanceMap.get(3)));
            em.persist(new Mark(juryMap.get(4), criterionMap.get(3), performanceMap.get(5)));
            em.persist(new Mark(juryMap.get(5), criterionMap.get(3), performanceMap.get(6)));

            // Мероприятия Fest
            LocalDate today = LocalDate.now(ZoneOffset.UTC);

            OffsetDateTime date1 = today.atTime(18, 30, 0).atOffset(ZoneOffset.UTC);
            OffsetDateTime date2 = today.atTime(18, 0, 0).atOffset(ZoneOffset.UTC);
            OffsetDateTime date3 = today.atTime(17, 0, 0).atOffset(ZoneOffset.UTC);
            OffsetDateTime date4 = today.atTime(17, 30, 0).atOffset(ZoneOffset.UTC);
            OffsetDateTime date5 = today.atTime(19, 0, 0).atOffset(ZoneOffset.UTC);

            em.persist(new Fest(companyMap.get(1), performanceMap.get(1), volunteerMap.get(5), "Ул. Образцова 9", date1));
            em.persist(new Fest(companyMap.get(1), performanceMap.get(3), volunteerMap.get(5), "Ул. Образцова 9", date2));
            em.persist(new Fest(companyMap.get(3), performanceMap.get(5), volunteerMap.get(3), "Ул. Образцова 10", date3));
            em.persist(new Fest(companyMap.get(3), performanceMap.get(6), volunteerMap.get(3), "Ул. Образцова 11", date4));
            em.persist(new Fest(companyMap.get(2), null, volunteerMap.get(2), "Ул. Образцова 8", date5));

            tx.commit();
            log.info("Начальные данные для Comiccon добавлены");
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            log.error("Ошибка при заполнении начальных данных Comiccon", e);
            throw e;
        } finally {
            em.close();
        }
    }
}