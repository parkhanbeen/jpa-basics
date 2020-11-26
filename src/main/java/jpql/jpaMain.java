package jpql;

import javax.persistence.*;
import java.util.List;

public class jpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            for(int i =0; i<100; i++){

                Team team = new Team();
                team.setName("team"+i);
                em.persist(team);

                Member member = new Member();
                member.setUsername("member"+i);
                member.setAge(i+1);
                member.setTeam(team);

                em.persist(member);
            }

            em.flush();
            em.clear();

            // 페이징 쿼리 // 각각 데이터 베이스의 방언에 맞춰 쿼리가 나간다.
            List<Member> result = em.createQuery("select m from Member m inner join m.team t")
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();

            System.out.println("result.size = " + result.size());
            for (Member member1 : result) {
                System.out.println("member1 = " + member1);
            }

            // 조인
            // 내부 조인
            String innerQuery = "select m from Member m inner join m.team t";
            List<Member> innerResult = em.createQuery(innerQuery,Member.class)
                    .getResultList();

            // 외부 조인
            // outer 생략가능
            String outerQuery = "select m from Member m left outer join m.team t";
            List<Member> outerResult = em.createQuery(outerQuery,Member.class)
                    .getResultList();

            // 세타 조인
            // cross join 으로 나옴
            String query = "select m from Member m, Team t where m.username = t.name";
            List<Member> joinResult = em.createQuery(query,Member.class)
                    .getResultList();

            System.out.println("joinResult.size() = " + joinResult.size());

            // on절
            String onQuery = "select m from Member m left join m.team t on t.name = 'team20'";
            List<Member> onResult = em.createQuery(onQuery,Member.class)
                    .getResultList();




            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();


    }
}
