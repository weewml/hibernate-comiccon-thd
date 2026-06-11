package com.comiccon.service;

import com.comiccon.entity.*;
import com.comiccon.repository.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class CrudService {

    private final ArtistRepository artistRepository = new ArtistRepository();
    private final CompanyRepository companyRepository = new CompanyRepository();
    private final CriterionRepository criterionRepository = new CriterionRepository();
    private final FestRepository festRepository = new FestRepository();
    private final JuryRepository juryRepository = new JuryRepository();
    private final MarkRepository markRepository = new MarkRepository();
    private final MemberRepository memberRepository = new MemberRepository();
    private final PerformanceRepository performanceRepository = new PerformanceRepository();
    private final VolunteerRepository volunteerRepository = new VolunteerRepository();


    public void runAll() {
        readAllTables();
        createTables();
        updateCompany();
        deleteArtist();
    }

    public void readAllTables() {
        printHeader("=== READ artist ===");
        List<Artist> artists = artistRepository.findAll();
        System.out.printf("     %-5s %-12s %-10s %-12s%n", "ID", "Фамилия", "Имя", "Отчество");
        for (Artist a : artists) {
            System.out.printf("     %-5s %-12s %-10s %-12s%n",
                    a.getId(), a.getLastName(), a.getFirstName(), a.getPatronymic());
        }

        printHeader("=== READ criterion ===");
        List<Criterion> criterions = criterionRepository.findAll();
        System.out.printf("     %-5s %-10s %-10s%n", "ID", "Сложность", "Артистичность");
        for (Criterion c : criterions) {
            System.out.printf("     %-5s %-10s %-10s%n",
                    c.getId(), c.getDifficultMark(), c.getArtisticMark());
        }

        printHeader("=== READ fest ===");
        List<Fest> fests = festRepository.findAllWithDetails();
        System.out.printf("     %-5s %-20s %-25s %-15s %-20s%n",
                "ID", "Компания", "Выступление (тема)", "Волонтер", "Адрес");
        for (Fest f : fests) {
            System.out.printf("     %-5d %-20s %-25s %-15s %-20s%n",
                    f.getId(), f.getCompany().getCompanyName(),
                    f.getPerformance() != null ? f.getPerformance().getTopic() : "—",
                    f.getVolunteer() != null ? f.getVolunteer().getLastName() : "—",
                    f.getAddress());
        }

        printHeader("=== READ jury ===");
        List<Jury> jurys = juryRepository.findAll();
        System.out.printf("     %-5s %-12s %-10s %-12s%n", "ID", "Фамилия", "Имя", "Отчество");
        for (Jury j : jurys) {
            System.out.printf("     %-5s %-12s %-10s %-12s%n",
                    j.getId(), j.getLastName(), j.getFirstName(), j.getPatronymic());
        }

        printHeader("=== READ marks ===");
        List<Mark> marks = markRepository.findAllWithDetails();
        System.out.printf("     %-5s %-25s %-10s %-10s %-15s%n",
                "ID", "Выступление (тема)", "Сложн.", "Артист.", "Жюри");
        for (Mark m : marks) {
            System.out.printf("     %-5d %-25s %-10.2f %-10.2f %-15s%n",
                    m.getId(),
                    m.getPerformance().getTopic(),
                    m.getCriterion().getDifficultMark(),
                    m.getCriterion().getArtisticMark(),
                    m.getJury().getLastName());
        }

        printHeader("=== READ members ===");
        List<Member> members = memberRepository.findAll();
        System.out.printf("     %-5s %-12s %-10s %-12s %-18s %-12s%n",
                "ID", "Фамилия", "Имя", "Отчество", "Герой", "Первоисточник");
        for (Member m : members) {
            System.out.printf("     %-5s %-12s %-10s %-12s %-18s %-12s%n",
                    m.getId(), m.getLastName(), m.getFirstName(), m.getPatronymic(),
                    m.getHero(), m.getOriginalSource());
        }

        printHeader("=== READ performances ===");
        List<Performance> performances = performanceRepository.findAllWithMembers();
        System.out.printf("     %-5s %-15s %-30s%n", "ID", "Номинация", "Участники");
        for (Performance p : performances) {
            String membersNames = p.getMembers().stream()
                    .map(Member::getLastName)
                    .toList().toString();
            System.out.printf("     %-5d %-15s %-30s%n", p.getId(), p.getNomination(), membersNames);
        }

        printHeader("=== READ company ===");
        List<Company> companies = companyRepository.findAllWithArtists();
        System.out.printf("     %-5s %-20s %-30s%n", "ID", "Компания", "Художники");
        for (Company c : companies) {
            String artistsNames = c.getArtists().stream()
                    .map(a -> a.getLastName() + " " + a.getFirstName())
                    .toList().toString();
            System.out.printf("     %-5d %-20s %-30s%n", c.getId(), c.getCompanyName(), artistsNames);
        }

        System.out.println("=== READ volunteers ===");
        List<Volunteer> volunteers = volunteerRepository.findAll();
        System.out.printf("     %-5s %-12s %-10s %-12s %-12s%n", "ID", "Фамилия", "Имя", "Отчество", "Задание");
        for (Volunteer v : volunteers) {
            System.out.printf("     %-5s %-12s %-10s %-12s %-12s%n",
                    v.getId(), v.getLastName(), v.getFirstName(), v.getPatronymic(), v.getTask());
        }
        printDivider();
    }

    public void createTables() {
        printHeader("=== CREATE ===");

        Artist newArtist = new Artist("Тестов", "Тест", "Тестович");
        artistRepository.save(newArtist);
        System.out.printf("Создан художник: id=%d, %s %s%n",
                newArtist.getId(), newArtist.getLastName(), newArtist.getFirstName());

        Company newCompany = new Company("ТестКомпания", (short) 100, (short) 5);
        newCompany.addArtist(newArtist);
        companyRepository.save(newCompany);
        System.out.printf("Создана компания: id=%d, %s, художник добавлен%n",
                newCompany.getId(), newCompany.getCompanyName());

        Member newMember = new Member("Новиков", "Николай", "Петрович",
                "Человек-паук", "Marvel");
        memberRepository.save(newMember);
        System.out.printf("Создан участник: id=%d, %s %s, герой %s%n",
                newMember.getId(), newMember.getLastName(), newMember.getFirstName(), newMember.getHero());

        Performance newPerf = new Performance("Косплей", "Тестовое выступление");
        newPerf.addMember(newMember);
        performanceRepository.save(newPerf);
        System.out.printf("Создано выступление: id=%d, номинация '%s', участник добавлен%n",
                newPerf.getId(), newPerf.getNomination());

        Jury exJury = juryRepository.findById(2).get();
        Criterion exCrit = criterionRepository.findById(1).get();
        if (exJury != null && exCrit != null) {
            Mark newMark = new Mark(exJury, exCrit, newPerf);
            markRepository.save(newMark);
            System.out.printf("Создана оценка: id=%d, жюри %s, критерий id=%d, выступление id=%d%n",
                    newMark.getId(), exJury.getLastName(), exCrit.getId(), newPerf.getId());
        }

        Volunteer anyVolunteer = volunteerRepository.findAll().stream().findFirst().orElse(null);
        Fest newFest = new Fest(newCompany, newPerf, anyVolunteer,
                "Тестовый адрес, 1", OffsetDateTime.now(ZoneOffset.UTC));
        festRepository.save(newFest);
        System.out.printf("Создано мероприятие: id=%d, адрес %s%n",
                newFest.getId(), newFest.getAddress());

        printDivider();
    }

    public void updateCompany() {
        printHeader("=== UPDATE ===");
        companyRepository.findById(4).ifPresentOrElse(company -> {
            String oldName = company.getCompanyName();
            company.setCompanyName("Озон-tech");
            companyRepository.update(company);
            System.out.printf("Обновлено название компании id=1: '%s' → '%s'%n", oldName, company.getCompanyName());
        }, () -> System.out.println("Компания с id=4 не найдена"));
    }

    public void deleteArtist() {
        printHeader("=== DELETE ===");
        Artist tempArtist = new Artist("Удаляемый", "Тест", "Удаленович");
        artistRepository.save(tempArtist);
        System.out.printf("Создан временный художник: id=%d%n", tempArtist.getId());
        boolean deleted = artistRepository.deleteById(tempArtist.getId());
        System.out.printf("Удалён художник id=%d (успех=%b)%n", tempArtist.getId(), deleted);
        printDivider();
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
