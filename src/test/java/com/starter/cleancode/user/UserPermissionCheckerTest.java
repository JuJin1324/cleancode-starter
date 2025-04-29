package com.starter.cleancode.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.starter.cleancode.user.UserPermissionChecker.UserAction.*;
import static com.starter.cleancode.user.UserPermissionChecker.UserRole.*;
import static org.junit.jupiter.api.Assertions.*;

class UserPermissionCheckerTest {
    UserPermissionChecker checker;

    @BeforeEach
    void setUp() {
        checker = new UserPermissionChecker();
    }

    @Test
    void testAdminPermissions() {
        // Admin should have all permissions
        assertTrue(checker.hasRightPermission(ADMIN, CREATE));
        assertTrue(checker.hasRightPermission(ADMIN, EDIT));
        assertTrue(checker.hasRightPermission(ADMIN, DELETE));
        assertTrue(checker.hasRightPermission(ADMIN, VIEW));
        assertTrue(checker.hasRightPermission(ADMIN, OWNER_DELETE));
    }

    @Test
    void testEditorPermissions() {
        // Editor should have specific permissions
        assertTrue(checker.hasRightPermission(EDITOR, CREATE));
        assertTrue(checker.hasRightPermission(EDITOR, EDIT));
        assertTrue(checker.hasRightPermission(EDITOR, VIEW));
        assertTrue(checker.hasRightPermission(EDITOR, OWNER_DELETE));
        assertFalse(checker.hasRightPermission(EDITOR, DELETE));
    }

    @Test
    void testViewerPermissions() {
        // Viewer should only have VIEW permission
        assertTrue(checker.hasRightPermission(VIEWER, VIEW));
        assertFalse(checker.hasRightPermission(VIEWER, CREATE));
        assertFalse(checker.hasRightPermission(VIEWER, EDIT));
        assertFalse(checker.hasRightPermission(VIEWER, DELETE));
        assertFalse(checker.hasRightPermission(VIEWER, OWNER_DELETE));
    }

    @Test
    void testAdminPermissions_before() {
        // Admin should have all permissions
        assertTrue(checker.checkPerm("ADMIN", "CREATE", false));
        assertTrue(checker.checkPerm("ADMIN", "EDIT", false));
        assertTrue(checker.checkPerm("ADMIN", "DELETE", false));
        assertTrue(checker.checkPerm("ADMIN", "VIEW", false));
        assertTrue(checker.checkPerm("ADMIN", "DELETE", true));
    }

    @Test
    void testEditorPermissions_before() {
        // Editor should have specific permissions
        assertTrue(checker.checkPerm("EDITOR", "CREATE", false));
        assertTrue(checker.checkPerm("EDITOR", "EDIT", false));
        assertTrue(checker.checkPerm("EDITOR", "VIEW", false));
        assertTrue(checker.checkPerm("EDITOR", "DELETE", true));
        assertFalse(checker.checkPerm("EDITOR", "DELETE", false));
    }

    @Test
    void testViewerPermissions_before() {
        // Viewer should only have VIEW permission
        assertTrue(checker.checkPerm("VIEWER", "VIEW", false));
        assertFalse(checker.checkPerm("VIEWER", "CREATE", false));
        assertFalse(checker.checkPerm("VIEWER", "EDIT", false));
        assertFalse(checker.checkPerm("VIEWER", "DELETE", false));
        assertFalse(checker.checkPerm("VIEWER", "DELETE", true));
    }
}