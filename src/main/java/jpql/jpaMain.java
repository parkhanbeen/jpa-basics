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

            // JPQL 기본 함수
            String query = "select 'a' || 'b' from Member m ";   // 하이버네이트에서 지원하는 문법

            String concatQuery = "select concat('a', 'b') from Member m "; // concat 데이터베이스 표준 문법

            String subQuery = "select substring(m.username, 2, 3) from Member m "; // 2번째 부터 3개를 짤라라

            String locateQuery = "select locate('de','abcdefg') from Member m "; // de가 몇번째인지 찾아라

            String sizeQuery = "select size(t.members) from Team t "; // 컬렉션의 크기를 돌려준다

            @OrderColumn
            String indexQuery = "select index(t.members) from Team t "; // 컬렉션의 위치 값을 구할 때 사용 거의 안쓴다


            // 사용자 정의 함수 호출
            String functionQuery = "select function('grop_concat', m.username) from Member m "; // 직접 함수를 등록하여 사용가능하다


            List<String> functionList = em.createQuery(functionQuery,String.class)
                    .getResultList();

            for (String s : functionList) {
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
