package me.sungbin.demobootweb;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName : me.sungbin.demobootweb
 * fileName : PersonRepository
 * author : rovert
 * date : 2022/01/31
 * description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 2022/01/31       rovert         최초 생성
 */

public interface PersonRepository extends JpaRepository<Person, Long> {
}
