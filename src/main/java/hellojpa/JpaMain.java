package hellojpa;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

//        // 이걸 만드는 순간 데이터베이스랑 연결이 됬다고 보면 된다.
//        // application loading 시점에 딱 하나만 만들어야 한다.
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
//
//        // 트랜잭션 단위에서 항상 늘 만들어야 함.
//        EntityManager em = emf.createEntityManager();
//
//        // 트랜잭션 시작
//        EntityTransaction tx = em.getTransaction();
//        tx.begin();
//
//        // 여기에 데이터 베이스 접근 코드를 작성하면 된다.
//        // 데이터 베이스 접근 코드는 항상 트랜잭션 내에서 작성해야 한다.
//
//        try {
//
//            // member 저장
//            /*
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("helloB");
//            em.persist(member);
//            */
//
//            /**
//             * member 단건 조회
//             */
//            /*
//            Member findMember = em.find(Member.class, 1L);
//            System.out.println("findMember.id = " + findMember.getId());
//            System.out.println("findMember.name = " + findMember.getName());
//            */
//
//            /**
//             * member 전체 조회
//             * 디비에 쿼리를 날리는게 아니라, 객체에 쿼리는 날린다. 테이블이 아닌 객체가 대상이 된다.
//             * 그럼 이게 무슨 메리트가 있지 ?
//             * 사실은 큰 메리트가 있다. 페이징을 한다고 쳐보면...
//             */
//            List<Member> findMembers = em.createQuery("select m from Member as m", Member.class)
//                    .setFirstResult(1)
//                    .setMaxResults(5)
//                    .getResultList();
//
//            for (Member findMember : findMembers) {
//                System.out.println("findMember.id = " + findMember.getId());
//                System.out.println("findMember.name = " + findMember.getName());
//            }
//
//            /**
//             * member 삭제
//             */
//            /*
//            em.remove(findMember);
//            */
//
//            /**
//             * member 수정
//             *
//             * jpa를 통해서 객체를 가져오면, 이제부터 이 객체는 jpa가 관리하는 객체이다.
//             * 이게 변경이 됬는지 안됬는지 트랜잭션이 커밋하는 시점에서 다 체크한다.
//             * 뭐가 바뀌면 커밋 직전에 업데이트 쿼리가 나감.
//             */
//            /*
//            Member member = em.find(Member.class, 1L);
//            member.setName("HelloJPA");
//            */
//
//            /**
//             * 쿼리 나감
//             */
//            tx.commit();
//
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            // entity manager를 닫아주는게 중요하다.
//            // 이게 디비 트랜잭션을 물고 작동한다.
//            em.close();
//        }
//
//        // 실제 application이 끝나면 이 팩토리를 닫아줘야 한다.
//        emf.close();


        main4();
    }

    public static void main2() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction entityTransaction = em.getTransaction();

        entityTransaction.begin();

        try {

//            // 비영속
//            Member member = new Member();
//            member.setId(101L);
//            member.setName("helloJpa");
//
//            //영속화 (1차 캐시 저장)
//            System.out.println("=== before persist ===");
//            em.persist(member);
//            System.out.println("=== after persist ===");

            System.out.println("=== before find ===");
            Member findMember1 = em.find(Member.class, 101L);
            Member findMember2 = em.find(Member.class, 101L);
            System.out.println("member1.id => " + findMember1.getId());
            System.out.println("member1.name =>" + findMember1.getName());
            System.out.println("member2.id => " + findMember2.getId());
            System.out.println("member2.name =>" + findMember2.getName());

            if (findMember1 == findMember2) {
                System.out.println("same1");
            }
            if (findMember1.equals(findMember2)) {
                System.out.println("same2");
            }
            if (findMember2.equals(findMember1)) {
                System.out.println("same3");
            }

            System.out.println("=== after find ===");

            //DB insert(1차 캐시 푸시)
            System.out.println("=== before commit ===");
//            entityTransaction.commit();
            System.out.println("=== after commit ===");
        } catch (Exception e) {
            entityTransaction.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    public static void main3() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {

            Member member1 = new Member(150L, "A");
            Member member2 = new Member(160L, "B");

            em.persist(member1);
            em.persist(member2);

            System.out.println("===========");

            transaction.commit();


        } catch (Exception e) {
            transaction.rollback();

        } finally {
            em.close();
        }
        emf.close();
    }

    public static void main4() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {

            Member member = em.find(Member.class, 150L);

            member.setName("ZZZZZ");
            transaction.commit();


        } catch (Exception e) {
            transaction.rollback();

        } finally {
            em.close();
        }
        emf.close();
    }
}
