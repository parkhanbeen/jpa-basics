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

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            // 엔티티 프로젝션
            List<Member> result = em.createQuery("select m from Member m ", Member.class)
                    .getResultList(); // 컬렉션 반환

            Member findMember = result.get(0);
            findMember.setAge(20);

            List<Team> resultTeam = em.createQuery("select m.team, from Member m ", Team.class)
                    .getResultList();  // 조인 쿼리가 나감 // 쿼리랑 문법이 달라서 권장하지 않음

            //임베디드 타입 프로젝션
            em.createQuery("select o.address from Order o ", Address.class)  // 소속테이블을 명시해줘야한다.
                    .getResultList();

            //스칼라 타입 프로젝션
            em.createQuery("select distinct m.username,m.age from Member m ")
                    .getResultList();


            // Object 배열 타입으로 조회 // Object 배열로 반환됨
            List resultList = em.createQuery("select m.username,m.age from Member m ")
                    .getResultList();

            Object o = resultList.get(0);
            Object[] result1 = (Object[]) o;
            System.out.println("username = " + result1[0]);
            System.out.println("age = " + result1[1]);

            // new 명령어로 조회
            List<MemberDTO> resultList1 = em.createQuery("select new jpql.MemberDTO(m.username, m.age)from Member m ",MemberDTO.class)
                    .getResultList();

            MemberDTO memberDTO = resultList1.get(0);
            System.out.println("username = " + memberDTO.getUsername());
            System.out.println("age = " + memberDTO.getAge());


            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();


    }
}
