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
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("관리자");
            member.setAge(20);
            member.setTeam(team);
            member.setType(MemberType.ADMIN);

            em.persist(member);

            em.flush();
            em.clear();

            // 경로 표현식
            // 상태 필드 : 경로의 탐색 끝 탐색X
            String query = "select m.username from Member m ";

            // 단일 연관 경로 : 묵시적 내부 조인(inner join)발생 탐색O
            String query1 = "select m.team.name from Member m ";

            // 컬렉션 값 연관 경로 : 묵시적 내부 조인(inner join)발생 탐색X
            String query2 = "select t.members from Team t ";

            // from절에서 명시적인 조인을 통해 별칭을 얻으면 별칭을 통해 탐색 가능
            String query3 = "select m.username from Team t join t.members m";


            Collection resultList = em.createQuery(query2, Collection.class)
                    .getResultList();

            for (Object s : resultList) {
                System.out.println("s = " + s);

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
