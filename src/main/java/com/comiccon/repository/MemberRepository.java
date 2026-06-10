package com.comiccon.repository;

import com.comiccon.entity.Member;

public class MemberRepository extends GenericRepository<Member, Integer> {
    public MemberRepository() { super(Member.class); }
}
