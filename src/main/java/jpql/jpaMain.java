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

            // TypedQuery 반환타입이 명확할 때 사용한다.
            TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
            TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);

            // Query 반환타입이 명확하지 않을때 사용한다.
            Query query3 = em.createQuery("select m.username, m.age from Member m");


            List<Member> resultList = query.getResultList(); // 컬렉션 반환

            for (Member member1 : resultList) {
                System.out.println("member1 = " + member1);
            }

            TypedQuery<Member> query4 = em.createQuery("select m from Member m where m.id=10", Member.class);

            // 단일 객체 반환 결과가 없으면 javax.persistence.NoResultException
            // 결과가 둘이상이면 javax.persistence.NonUniqueResultException
            Member result = query4.getSingleResult();

            System.out.println("result = " + result);

            Member resultMember = em.createQuery("select m from Member m where m.username= :username", Member.class)
                                        .setParameter("username", "member1")
                                        .getSingleResult();

            System.out.println("singleResult = " + resultMember.getUsername());


            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();


    }
}
