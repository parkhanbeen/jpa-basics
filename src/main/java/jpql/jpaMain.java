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

            // JPQL 타입 표현
            // ENUM 타입은 패키지명을 모두 포함한다.
            String query = "select m.username, 'HELLO', true From Member m " +
                    "where m.type = :userType";
            List<Object[]> result = em.createQuery(query)
                    .setParameter("userType",MemberType.ADMIN)
                    .getResultList();

            for (Object[] objects : result) {
                System.out.println("objects[0] = " + objects[0]);
                System.out.println("objects[0] = " + objects[1]);
                System.out.println("objects[0] = " + objects[2]);

            }

            // 조건식 - case식
            String caseQuery =
                    "select " +
                            "case when m.age <= 10 then '학생요금' " +
                            "     when m.age >= 60 then '경로요금' " +
                            "     else '일반요금'" +
                            "end " +
                            "from Member m";
            List<String> resultList = em.createQuery(caseQuery,String.class)
                    .getResultList();

            for (String s : resultList) {
                System.out.println("s = " + s);

            }

            // 모든 db에서 사용가능.
            // COALESCE: 하나씩 조회해서 null이 아니면 반환
            String coalQuery = "select coalesce(m.username, '이름 없는 회원') as username from Member m ";
            List<String> coalList = em.createQuery(coalQuery,String.class)
                    .getResultList();


            // NULLIF: 두 값이 같으면 null 반환, 다르면 첫번째 값 반환
            String nullifQuery = "select nullif(m.username, '관리자') as username from Member m ";
            List<String> nullifList = em.createQuery(nullifQuery,String.class)
                    .getResultList();

            for (String s : nullifList) {
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
