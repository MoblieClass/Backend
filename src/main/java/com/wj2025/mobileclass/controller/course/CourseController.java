package com.wj2025.mobileclass.controller.course;

import com.wj2025.mobileclass.model.course.CourseModel;
import com.wj2025.mobileclass.model.course.Course_UserModel;
import com.wj2025.mobileclass.service.Service.CourseService;
import com.wj2025.mobileclass.service.Service.PermissionService;
import com.wj2025.mobileclass.service.Service.UserService;
import com.wj2025.mobileclass.utils.TimeUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/course")
@Tag(
        name = "课程管理",
        description = "课程管理相关接口"
)
public class CourseController {
    private final CourseService courseService;
    private final PermissionService permissionService;
    private final UserService userService;
    public CourseController(CourseService courseService, PermissionService permissionService, UserService userService) {
        this.courseService = courseService;
        this.permissionService = permissionService;
        this.userService = userService;
    }

    @GetMapping("/all")
    @Operation(summary = "获取全部课程")
    public ResponseEntity<?> getAllCourses(@RequestParam int page, @RequestParam int size) {
        page-=1; // 页码从0开始
        if(page < 0 || size <= 0 || size > 100) {
            return ResponseEntity.badRequest().body("error parameter");
        }
        return ResponseEntity.ok(courseService.getAllCourses(page,size));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取课程详情")
    public ResponseEntity<?> getCourseById(@PathVariable int id) {
        var course = courseService.getCourseById(id);
        if(course.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @GetMapping("/search")
    @Operation(summary = "搜索课程")
    public ResponseEntity<?> searchCourse(@RequestParam String keyWord,@RequestParam int page, @RequestParam int size) {
        page-=1;
        if(page < 0 || size <= 0 || size > 100) {
            return ResponseEntity.badRequest().body("error parameter");
        }
        var courses = courseService.getCoursesByCourseName(keyWord,page,size);
        return ResponseEntity.ok(courses);
    }

    @PostMapping("/add")
    @Operation(summary = "添加课程，需要权限 course:create")
    public ResponseEntity<?> addCourse(@RequestBody CourseModel courseModel) {
        courseModel.setId(null);

        // 校验时间
        if(!TimeUtils.isValidTime(courseModel.getStartTime()) || !TimeUtils.isValidTime(courseModel.getEndTime())) {
            return ResponseEntity.badRequest().body("error time");
        }
        // 校验上课周
        try{
            Integer.parseInt(courseModel.getStartWeek());
            Integer.parseInt(courseModel.getEndWeek());
        }catch (NumberFormatException e){
            return ResponseEntity.badRequest().body("error week");
        }

        // 权限验证
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(permissionService.checkHasPermission(username,"course:create") || permissionService.checkHasPermission(username,"root")){
            return ResponseEntity.ok(courseService.addCourse(courseModel));
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("permission denied");
        }
    }

    @PostMapping("/modify")
    @Operation(summary = "修改课程，需要权限 course:modify")
    public ResponseEntity<?> updateCourse(@RequestBody CourseModel courseModel) {
        var course = courseService.getCourseById(courseModel.getId());
        if(course.isEmpty()) {
            return ResponseEntity.badRequest().body("course not found");
        }
        // 校验时间
        if(!TimeUtils.isValidTime(courseModel.getStartTime()) || !TimeUtils.isValidTime(courseModel.getEndTime())) {
            return ResponseEntity.badRequest().body("error time");
        }
        // 校验上课周
        try{
            Integer.parseInt(courseModel.getStartWeek());
            Integer.parseInt(courseModel.getEndWeek());
        }catch (NumberFormatException e){
            return ResponseEntity.badRequest().body("error week");
        }
        // 权限验证
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(permissionService.checkHasPermission(username,"course:modify") || permissionService.checkHasPermission(username,"root")){
            courseService.modifyCourse(courseModel);
            return ResponseEntity.ok("success");
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("permission denied");
        }
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除课程，需要权限 course:delete")
    public ResponseEntity<?> deleteCourse(@RequestParam int id) {
        var course = courseService.getCourseById(id);
        if(course.isEmpty()) {
            return ResponseEntity.badRequest().body("course not found");
        }
        // 权限验证
        var currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if(permissionService.checkHasPermission(currentUser,"course:delete") || permissionService.checkHasPermission(currentUser,"root")){
            courseService.deleteCourseById(course.get().getId());
            return ResponseEntity.ok("success");
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("permission denied");
        }
    }

    @PostMapping("/addUser")
    @Operation(summary = "添加学生到课堂，需要权限 courseUser:create")
    public ResponseEntity<?> addCourseUser(@RequestParam int courseId, @RequestParam String username) {
        var course = courseService.getCourseById(courseId);
        if(course.isEmpty()) {
            return ResponseEntity.badRequest().body("course not found");
        }
        var user = userService.getUserByUsername(username);
        if(user.isEmpty()) {
            return ResponseEntity.badRequest().body("course user not found");
        }
        // 权限验证
        if(permissionService.checkHasPermission(SecurityContextHolder.getContext().getAuthentication().getName(),"courseUser:create") || permissionService.checkHasPermission(username,"root")){
            var courseUser = new Course_UserModel();
            courseUser.setId(null);
            courseUser.setCourseId(course.get().getId());
            courseUser.setUserId(user.get().getId());
            return ResponseEntity.ok(courseService.addCourseUser(courseUser));
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("permission denied");
        }
    }

    @DeleteMapping("/removeUser")
    @Operation(summary = "从课堂移除学生，需要权限 courseUser:delete")
    public ResponseEntity<?> removeCourseUser(@RequestParam int id, @RequestParam String username) {
        var course = courseService.getCourseById(id);
        if(course.isEmpty()) {
            return ResponseEntity.badRequest().body("course not found");
        }
        var user = userService.getUserByUsername(username);
        if(user.isEmpty()) {
            return ResponseEntity.badRequest().body("user not found");
        }
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if(permissionService.checkHasPermission(currentUser, "courseUser:delete") ||
                permissionService.checkHasPermission(currentUser, "root")) {
            courseService.deleteCourseUserByUserIdAndCourseId(user.get().getId(),course.get().getId());
            return ResponseEntity.ok("user removed from course successfully");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("permission denied");
        }
    }


}
