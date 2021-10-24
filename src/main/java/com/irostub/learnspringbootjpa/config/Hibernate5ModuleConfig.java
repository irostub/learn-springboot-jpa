package com.irostub.learnspringbootjpa.config;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//더이상 사용하지 않기 위해 주석처리
//@Configuration
public class Hibernate5ModuleConfig {

    //엔티티를 직접 노출 시킬 때 lazy 로딩 상태의 proxy 객체를 json 으로 변환하는 과정에서 생기는 문제를 해결해주는 라이브러리
    //허나, 애초에 엔티티를 직접 반환하거나 받지 않는게 훨씬 좋은 방법이다. 그러므로 사용하지 않는 것이 좋다.
    @Bean
    public Hibernate5Module hibernate5Module() {
        Hibernate5Module hibernate5Module = new Hibernate5Module();
        //아래는 hibernate5Module 의 옵션 중 한가지로, lazy load 상태의 프록시 객체를 무조건 조회하는 방식을 on 한 것
        //기본 전략은 lazy loading 상태의 모든 엔티티를 ignore 하도록 되어있다.
//        hibernate5Module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true);
        return hibernate5Module;
    }
}
