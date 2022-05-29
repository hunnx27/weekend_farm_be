package com.wefarm.modules.account.application.response;

import com.wefarm.modules.common.type.YN;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class AccountInfo {

    private Long id;
    private String name;
    private String email;
    private String age;
    private String location;
    private YN isDelete;
    private Set<Educations> educations;

    @Getter
    @Setter
    private static class Educations {

        private Long id;
        private String name;
        private String subject;
        private YN isDelete = YN.N;
    }
}
