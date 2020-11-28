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

            // 엔티티 직접 사용 - 기본 키 값
            // sql에서 식별자 값을 꺼내서 동작하게됨
            String query = "select m from Member m where m = :member";

            Member findMember = em.createQuery(query, Member.class)
                    .setParameter("member",member1)
                    .getSingleResult();

            System.out.println("findMember = " + findMember);

            // 엔티티 직접 사용 - 외래 키 값
            // sql에서 식별자 값을 꺼내서 동작하게됨
            String query1 = "select m from Member m where m.team = :team";

            List<Member> findMember1 = em.createQuery(query1, Member.class)
                    .setParameter("team",teamA)
                    .getResultList();

            for (Member member : findMember1) {
                System.out.println("member = " + member);
            }



            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();


    }
}
