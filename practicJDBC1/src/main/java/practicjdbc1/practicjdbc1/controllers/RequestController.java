package practicjdbc1.practicjdbc1.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import practicjdbc1.practicjdbc1.Model.ApplicationRequest;
import practicjdbc1.practicjdbc1.Model.Course;
import practicjdbc1.practicjdbc1.Service.CourseService;
import practicjdbc1.practicjdbc1.Service.RequestService;

@Controller
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;
    private final CourseService courseService;
//  ----------ВСЕ ЗАЯВКИ----------
    @GetMapping("/requests")
    public String requestsPage(Model model) {
        model.addAttribute("requests", requestService.getAllRequests());

        return "requests";
    }
//  ---------ОБРАБОТАННЫЕ ЗАЯВКИ--------
    @GetMapping("/processed_requests")
    public String processedRequestsPage(Model model) {
        model.addAttribute("requests", requestService.getAllRequests());
        return "processed-requests";
    }
//   ---------НОВЫЕ ЗАЯВКИ--------
    @GetMapping("/new_requests")
    public String newRequestsPage(Model model) {
        model.addAttribute("requests", requestService.getAllRequests());

        return "new-requests";
    }
//  ---------ДОБАВЛЕНИЕ ЗАЯВОК--------
    @GetMapping("/add_request")
    public String addRequestPage(Model model,
                                 @RequestParam(name = "error", required = false) Integer errorCode) {
        model.addAttribute("courses", courseService.getAllCourses());
        if (errorCode != null) {
            model.addAttribute("error", errorCode);
        }
        return "add-request";
    }
//  ---------СОХРАНЕНИЕ ЗАЯВОК--------
    @PostMapping("/save_request")
    public String addRequest(@RequestParam(name = "user_name") String userName,
                             @RequestParam(name = "commentary") String commentary,
                             @RequestParam(name = "phone_number") String phone,
                             @RequestParam(name = "course_id") Long courseId) {
        Course course = courseService.getCourseById(courseId);
        if(course != null) {
            ApplicationRequest newRequest = ApplicationRequest
                    .builder()
                    .id(null)
                    .userName(userName)
                    .commentary(commentary)
                    .phone(phone)
                    .handled(false)
                    .course(course)
                    .build();
            boolean status = requestService.addRequest(newRequest);

            if (status) {
                return "redirect:/requests";
            } else {
                return "redirect:/add_request?error=1";
            }
        } else {
            return "redirect:/add_request?error=2";
        }
    }
//  ---------ОБРАБОТКА ЗАЯВКА--------
    @GetMapping("/update_request")
    public String updateRequestPage(Model model,
                                    @RequestParam(name = "request_id") Long id,
                                    @RequestParam(name = "error", required = false) Integer errorCode) {
        ApplicationRequest request = requestService.getRequestById(id);
        if(request != null){
            model.addAttribute("request", request);
            model.addAttribute("courses", courseService.getAllCourses());

            if (errorCode != null) {
                model.addAttribute("error", errorCode);
            }

            return "update-request";
        } else {
            return "redirect:/404";
        }
    }
//  ----------ИЗМЕНЕНИЕ ЗАЯВКИ----------
    @PostMapping("/update_request")
    public String updateRequest(@RequestParam(name = "request_id") Long id,
                                @RequestParam(name = "user_name") String userName,
                                @RequestParam(name = "commentary") String commentary,
                                @RequestParam(name = "phone_number") String phone,
                                @RequestParam(name = "course_id") Long courseId){
        Course course = courseService.getCourseById(courseId);

        if(course != null) {
            ApplicationRequest updatedApplicationRequest = ApplicationRequest
                    .builder()
                    .id(id)
                    .userName(userName)
                    .commentary(commentary)
                    .phone(phone)
                    .handled(true)
                    .course(course)
                    .build();

            boolean status = requestService.updateRequest(updatedApplicationRequest);

            if (status) {
                return "redirect:/update_request?request_id=" + id + "&success";
            } else {
                return "redirect:/update_request?request_id=" + id + "&error=1";
            }
        } else {
            return "redirect:/update_request?request_id=" + id + "&error=2";
        }
    }
//  ----------УДАЛЕНИЕ ЗАЯВКИ----------
    @PostMapping("/delete_request")
    public String deleteRequest(@RequestParam(name = "request_id") Long id) {
        boolean status = requestService.deleteRequest(id);

        if(status){
            return "redirect:/requests";
        } else {
            return "redirect:/requests?id=" + id + "&error";
        }
    }
//  ----------ОШИБКА----------
    @GetMapping("/404")
    public String notFoundedPage(){
        return "404";
    }
}