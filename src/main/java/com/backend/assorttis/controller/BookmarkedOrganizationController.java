package com.backend.assorttis.controller;

import com.backend.assorttis.service.BookmarkedOrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookmarks/organizations")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BookmarkedOrganizationController {

    private final BookmarkedOrganizationService bookmarkService;

    // The user specified that for now, we assume the current organization ID is 1
    private static final Long CURRENT_ORG_ID = 1L;

    @PostMapping("/{targetOrgId}/toggle")
    public ResponseEntity<?> toggleBookmark(@PathVariable Long targetOrgId) {
        boolean isBookmarked = bookmarkService.toggleBookmark(CURRENT_ORG_ID, targetOrgId);
        return ResponseEntity.ok(Map.of(
                "bookmarked", isBookmarked,
                "message", isBookmarked ? "Organization bookmarked successfully" : "Bookmark removed successfully"
        ));
    }

    @GetMapping("/ids")
    public ResponseEntity<List<Long>> getBookmarkedIds() {
        List<Long> ids = bookmarkService.getBookmarkedOrganizationIds(CURRENT_ORG_ID);
        return ResponseEntity.ok(ids);
    }
}
