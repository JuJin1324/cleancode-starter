package com.starter.cleancode.chapter1;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 문제 2: 사용자 권한 확인 로직 리팩토링
 * 다음 코드는 사용자 역할(role)과 특정 액션(action)에 대한 권한 여부를 확인하는 로직이야.
 * 문자열 비교, 조건문의 가독성, 함수의 명확성을 개선해보자.
 */
public class UserPermissionChecker {
    // Before
    // role: "ADMIN", "EDITOR", "VIEWER"
    // action: "CREATE", "EDIT", "DELETE", "VIEW"
    // isOwner: 사용자가 해당 컨텐츠의 소유자인지 여부
    public boolean checkPerm(String role, String action, boolean isOwner) {
        boolean perm_ok = false;
        if (role.equals("ADMIN")) {
            perm_ok = true; // Admin has all permissions
        } else if (role.equals("EDITOR")) {
            if (action.equals("CREATE") || action.equals("EDIT") || action.equals("VIEW")) {
                perm_ok = true;
            } else if (action.equals("DELETE")) {
                if (isOwner) { // Editor can delete only their own content
                    perm_ok = true;
                }
            }
        } else if (role.equals("VIEWER")) {
            if (action.equals("VIEW")) {
                perm_ok = true;
            }
        }
        return perm_ok;
    }

    // After
    // boolean isOwner 를 UserAction 의 OWNER_DELETE 로 변경 구현
    public boolean hasRightPermission(UserRole role, UserAction action/*, boolean isOwner*/) {
        // 값은 할 수 있으면 변하지 않도록 불변 상태 유지를 위해서 값이 변경될 수 있는 변수인
        // perm_ok 와 같은 변수들은 사용 자제
//        boolean perm_ok = false;

        // UserRole 안에 allowedActions 를 정의하여 매개변수로 받은 액션이 
        // 각 롤에 맞춰 허용된 액션 목록에 없다면 false 를 반환한다.
        return role.isAllowing(action);
    }

    @AllArgsConstructor
    @Getter
    public enum UserRole {
        ADMIN(List.of(UserAction.CREATE, UserAction.EDIT, UserAction.DELETE, UserAction.VIEW, UserAction.OWNER_DELETE)),
        EDITOR(List.of(UserAction.CREATE, UserAction.EDIT, UserAction.VIEW, UserAction.OWNER_DELETE)),
        VIEWER(List.of(UserAction.VIEW)),
        ;

        private final List<UserAction> allowedActions;

        public boolean isAllowing(UserAction action) {
            return allowedActions.contains(action);
        }
    }

    public enum UserAction {
        CREATE,
        EDIT,
        DELETE,
        VIEW,
        OWNER_DELETE
    }
}