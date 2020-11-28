package jpql;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;


public class jpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            Team teamA = new Team();
            teamA.setName("teamA");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setAge(21);
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setAge(23);
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setAge(33);
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            // 벌크 연산
            // 벌크 연산은 영속성 컨텍스트를 무시하고 데이터베이스에 직접 쿼리함
            // flush 자동 호출
            int resultCount = em.createQuery("update Member m set m.age = 20")
                    .executeUpdate();

            // 벌크 연산 수행후 영속성 컨텍스트를 초기화
            em.clear();

            Member findMember1 = em.find(Member.class, member1.getId());
            Member findMember2 = em.find(Member.class, member2.getId());
            Member findMember3 = em.find(Member.class, member3.getId());

            System.out.println("resultCount = " + resultCount);

            System.out.println("findMember1 = " + findMember1.getAge());
            System.out.println("findMember2 = " + findMember2.getAge());
            System.out.println("findMember3 = " + findMember3.getAge());

            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();


    }
}
