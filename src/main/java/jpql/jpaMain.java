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

            String query = "select m from Member m ";

            List<Member> resultList = em.createQuery(query, Member.class)
                    .getResultList();

            for (Member member : resultList) {
                System.out.println("Member = " + member.getUsername() + ", " + member.getTeam().getName());
                //회원1, 팀A(SQL)
                //회원2, 팀A(1차캐시)
                //회원3, 팀B(SQL)
            }

            em.flush();
            em.clear();

            // join fetch
            // 페치 조인으로 회원과 팀을 함께 조회해서 지연 로딩X
            // 다대일계 관계
            String query1 = "select m from Member m join fetch m.team";

            List<Member> resultList1 = em.createQuery(query1, Member.class)
                    .getResultList();

            for (Member member : resultList1) {
                System.out.println("Member = " + member.getUsername() + ", " + member.getTeam().getName());
            }

            // 일대다 관계
            String query2 = "select t from Team t join fetch t.members";

            List<Team> resultList2 = em.createQuery(query2, Team.class)
                    .getResultList();

            for (Team team : resultList2) {
                System.out.println("team = " + team.getName() + ", members = " + team.getMembers().size());
                for(Member member : team.getMembers()){
                    System.out.println("-> member = " + member);
                }
            }

            // distinct
            // distinct를 하더라도 sql에서는 데이터가 완전히 똑같아야 중복 제거
            // JPA에선 같은 식별자를 가진 엔티티를 제거
            String query3 = "select distinct t from Team t join fetch t.members";

            List<Team> resultList3 = em.createQuery(query3, Team.class)
                    .getResultList();

            for (Team team : resultList3) {
                System.out.println("team = " + team.getName() + ", members = " + team.getMembers().size());
                for(Member member : team.getMembers()){
                    System.out.println("-> member = " + member);
                }
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
