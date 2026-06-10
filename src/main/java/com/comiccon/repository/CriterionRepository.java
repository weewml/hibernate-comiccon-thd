package com.comiccon.repository;

import com.comiccon.entity.Criterion;

public class CriterionRepository extends GenericRepository<Criterion, Integer> {
    public CriterionRepository() { super(Criterion.class); }
}
