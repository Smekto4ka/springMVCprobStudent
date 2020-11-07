package ru.oogis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.oogis.model.FilterCriterion;
import ru.oogis.model.Predmet;
import ru.oogis.model.Student;
import ru.oogis.model.form.FormListMarks;
import ru.oogis.model.form.FormParametersForFilter;
import ru.oogis.service.StudentService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/student")
public class WebController {

    private final StudentService studentService;

    @Autowired
    public WebController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Показывает страницу со id всех студентов, которые являются ссылками
     */
    @GetMapping()
    public String getIdStudent(Model model) {

        model.addAttribute("setIdStudent", studentService.getIdStydens());
        return "listIdStudent";
    }

    /**
     * Открывает страницу с данными о студенте
     * Есть возможность изменить его имя , фамилию и возраст, так же можно добавить оценки или удалить студента вовсе.
     *
     * @param idStudent student id
     */
    @GetMapping("/{idStudent}")
    public String getStudentById(@PathVariable("idStudent") long idStudent, Model model) {

        Optional<Student> optionalStudent = studentService.getStudById(idStudent);
        try {
            Student student = optionalStudent.orElseThrow(IllegalStateException::new);
            model.addAttribute("student", student);

            return "student";
        } catch (IllegalStateException ex) {
            model.addAttribute("warning", "No student with this id was found.");
            return "error";
        }
    }

    /**
     * Открывает страницу для созания нового студента .
     * id будет дано после получения объекта в запросе POST и проверки введенных данных.
     */
    @GetMapping("/new")
    public String formNewStudent(Model model) {

        model.addAttribute("newStudent", new Student());
        return "postStudent";
    }

    /**
     * Получает объект студента , который записывается в studentService.
     * Также производится проверка. Если есть ошибки , то выбрасывает на прошлую страницу (заполнение данных нового пользователя).
     *
     * @param student student object.
     */
    @PostMapping("/new")
    public String postStudent(@Valid @ModelAttribute("newStudent") Student student, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("newStudent", student);
            return "postStudent";
        }
        studentService.postStudent(student);
        model.addAttribute("student", student);
        return "student";

    }

    /**
     * Открывает страницу с заполнением оценок пользователю под определенном id.
     * <p>
     * Если пользователь не найден , то будет IllegalStateException и вы перенесетесь на окно ошибки.
     * Если вы количество полей будет заданно неверно (пример: "") то NumberFormatException и вас направят на страницу студента.
     *
     * @param idStudent         student id
     * @param numberMarksString the number of grades you wish to assign to the student.
     */


    // как написать try cathe
    @GetMapping("/setMarks/{id}")
    public String formSetMarks(@PathVariable("id") long idStudent, @RequestParam(value = "numberMarks") String numberMarksString, Model model) {
        Optional<Student> optionalStudent = studentService.getStudById(idStudent);
        try {
            Student student = optionalStudent.orElseThrow(IllegalStateException::new);

            try {
                int numberMarks = Integer.parseInt(numberMarksString);
                if (numberMarks > 0) {
                    model.addAttribute("listRating", new FormListMarks());
                    int[] numberingOfMarks = new int[numberMarks];
                    for (int i = 0; i < numberMarks; i++) {
                        numberingOfMarks[i] = i + 1;
                    }
                    model.addAttribute("student", student);
                    model.addAttribute("numberingOfMarks", numberingOfMarks);
                    model.addAttribute("arraysPredmet", Predmet.values());

                    return "setValue";
                }
                model.addAttribute("student", student);
                model.addAttribute("warning", "You have requested a negative number of fields to fill out grades. ");
                return "student";
            } catch (NumberFormatException e) {
                // e.printStackTrace();
                model.addAttribute("student", student);
                model.addAttribute("warning", "numberMarks должен быть числом");
                return "student";
            }
        } catch (IllegalStateException ex) {
            model.addAttribute("warning"
                    , "no Student");
            return "error";
        }

    }

    /**
     * Получает объект с оценками и предметом для записи в studentService.
     * <p>
     * Если по каким либо причинам студента не окажется , то IllegalAccessError при проверке в контроллере
     * или NullPointerException из самого studentService если не будет найден студент.
     * Если при заполнении вы введете несоответствующие данные , то вас переместит на страницу заполнения оценок с замечаниями.
     *
     * @param idStudent  student id.
     * @param listRating Object with grades and item name.
     */

    // SSSSSooooooSSSSS
    // NullPointerException  | IllegalAccessError
    @PostMapping("/setMarks/{idStudent}")
    public String setMarks(@PathVariable("idStudent") long idStudent,
                           @Valid @ModelAttribute("listRating") FormListMarks listRating
            , BindingResult bindingResult, Model model) {

        Optional<Student> optionalStudent = studentService.getStudById(idStudent);

        try {
            Student student = optionalStudent.orElseThrow(IllegalAccessError::new);
            if (bindingResult.hasErrors()) {

                model.addAttribute("listRating", listRating);
                int[] numberingOfMarks = new int[listRating.getLengthArraysMarks()];
                if (numberingOfMarks.length == 0)
                    numberingOfMarks = new int[]{1};
                for (int i = 0; i < listRating.getLengthArraysMarks(); i++) {
                    numberingOfMarks[i] = i + 1;
                }
                model.addAttribute("student", student);
                model.addAttribute("numberingOfMarks", numberingOfMarks);
                model.addAttribute("arraysPredmet", Predmet.values());
                return "setValue";
            }
            studentService.setMarksByIdStudentsAndPredmet(student.getIdStudent(), listRating.getPredmet(), listRating.getList());
            return "redirect:/student/" + student.getIdStudent();
        } catch (IllegalAccessError | NullPointerException ex) {
            System.out.println(ex);
            model.addAttribute("warning"
                    , "no Student");
            return "error";
        }
    }

    /**
     * Открывает страницу возможных фильтровю
     */
    @GetMapping("/filter")
    public String formFilter(Model model) {
        model.addAttribute("filterAverage", new FormParametersForFilter());
        model.addAttribute("filterYears", new FormParametersForFilter());
        model.addAttribute("enum", Predmet.values());
        return "filter";
    }

    /**
     * Производит фильтрацию по среднему баллу определенного предмета и возвращает страницу со списком id студентов , прошедших критерии.
     * <p>
     * Если будут ошибки в введенных данных , то перешлет на страницу , где эти данные были введены.
     *
     * @param formParametersForFilter An object with filtering boundaries.
     */
    @GetMapping("/filter/minmax")
    public String filterAverageMarks(@Valid @ModelAttribute("filterAverage") FormParametersForFilter
                                             formParametersForFilter, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("filterAverage", formParametersForFilter);
            model.addAttribute("filterYears", new FormParametersForFilter());
            model.addAttribute("enum", Predmet.values());
            return "filter";
        }
        System.out.println(formParametersForFilter);
        model.addAttribute("setIdStudent", studentService.getIdStudentsUsingFilter(formParametersForFilter, FilterCriterion.AVERAGE_MARKS));
        return "listIdStudent";
    }

    /**
     * Производит фильтрацию по возрасту предмета и возвращает страницу со списком id студентов , прошедших критерии.
     * <p>
     * Если будут ошибки в введенных данных , то перешлет на страницу , где эти данные были введены.
     *
     * @return
     */
    @GetMapping("/filter/years")
    public String filterYears(@Valid @ModelAttribute("filterYears") FormParametersForFilter
                                      formParametersForFilter, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("filterYears", formParametersForFilter);
            model.addAttribute("filterAverage", new FormParametersForFilter());
            model.addAttribute("enum", Predmet.values());
            return "filter";
        }
        System.out.println(formParametersForFilter);
        model.addAttribute("setIdStudent", studentService.getIdStudentsUsingFilter(formParametersForFilter, FilterCriterion.YEARS));
        return "listIdStudent";
    }


    /**
     * Удаляет студента по id
     * Вызывается из страницы самого студента
     * Если удаление не произойдет , то выдаст ошибку
     */
    @DeleteMapping("/delete/{idStudent}")
    public String deleteStudent(@PathVariable long idStudent, Model model) {
        if (studentService.deletStudentById(idStudent))
            return "redirect:/student";
        model.addAttribute("warning"
                , "error while deleting");
        return "error";
    }

    /**
     * Выдается страница для изменений данных студента.
     */
    @GetMapping("/update/{idStudent}")
    public String formUpdateStudent(@PathVariable long idStudent, Model model) {
        Optional<Student> optionalStudent = studentService.getStudById(idStudent);
        try {
            Student student = optionalStudent.orElseThrow(IllegalStateException::new);
            model.addAttribute("student", student);
            return "upStudent";
        } catch (IllegalStateException ex) {
            model.addAttribute("warning", "No student with this id was found.");
            return "error";
        }
    }

    /**
     * Происходит получение объекта студента с измененными значениями,
     * которые проверяются и после присваиваются существующему объекту в studentService.
     */
    @PutMapping("/update")
    public String updateStudent(@Valid @ModelAttribute("student") Student student, BindingResult
            bindingResult, Model model) {
        System.out.println(student);
        if (bindingResult.hasErrors()) {
            model.addAttribute("student", student);
            return "upStudent";
        }
        studentService.updateStudent(student);
        return "redirect:/student/" + student.getIdStudent();

    }

    @GetMapping("/prob")
    public @ResponseBody
    String prob() {
        return "Hello world";
    }

}
//HttpServletRequest
//@RequestParam