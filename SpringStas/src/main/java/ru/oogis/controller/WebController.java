package ru.oogis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.oogis.model.Filter;
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
     * Opens a page listing the id of all available students.
     */
    @GetMapping()
    public String getIdStudent(Model model) {

        model.addAttribute("setIdStudent", studentService.getIdStydens());
        return "listIdStudent";
    }

    /**
     * Opens a page where information about the student will be displayed.
     * In the request, you enter the student's id.
     * If there is no such student, you will be taken to the error window.
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
     * Opens a page with fields for creating a new student.
     * The id will be assigned automatically.
     */
    @GetMapping("/new")
    public String formNewStudent(Model model) {

        model.addAttribute("newStudent", new Student());
        return "postStudent";
    }

    /**
     * Gets the student that it writes to the server. Then it redirects to the page of the created student.
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



      /*  if (student.getFirstName() != null && student.getLastName() != null && student.getYears() >= 0) {
            studentService.postStudent(student);
            model.addAttribute("student", student);
            return "student";
        }
        model.addAttribute("warning"
                , "You have not completed all the fields or entered a negative number of years.");
        return "error";*/
    }

    /**
     * Opens a page with the ability to add new grades to the student.
     * in the fields you enter the subject and grades.
     * The number of grades to be entered is selected in the student's window.
     * All student grade fields are optional.
     *
     * @param idStudent student id
     * @param kolString the number of grades you wish to assign to the student.
     */


    // как написать try cathe
    // название koll
    @GetMapping("/setValue/{id}")
    public String formSetValue(@PathVariable("id") long idStudent, @RequestParam(value = "koll") String kolString, Model model) {
        Optional<Student> optionalStudent = studentService.getStudById(idStudent);
        try {
            Student student = optionalStudent.orElseThrow(IllegalStateException::new);

            try {
                int kol = Integer.parseInt(kolString);
                if (kol > 0) {
                    model.addAttribute("listRating", new FormListMarks());
                    int[] massNumber = new int[kol];
                    for (int i = 0; i < kol; i++) {
                        massNumber[i] = i + 1;
                    }
                    model.addAttribute("student", student);
                    model.addAttribute("mass", massNumber);
                    model.addAttribute("arraysPredmet", Predmet.values());

                    return "setValue";
                }
                model.addAttribute("student", student);
                model.addAttribute("warning", "You have requested a negative number of fields to fill out grades. ");
                return "student";
            } catch (NumberFormatException e) {
                // e.printStackTrace();
                model.addAttribute("student", student);
                model.addAttribute("warning", "koll должен быть integer != 0");
                return "student";
            }
        } catch (IllegalStateException ex) {
            model.addAttribute("warning"
                    , "not Student");
            return "error";
        }

    }

    /**
     * Gets an object with grades, which will later be transferred to the server for recording.
     *
     * @param idStudent  student id.
     * @param listRating Object with grades and item name.
     */

    // SSSSSooooooSSSSS
    // NullPointerException  || IllegalAccessError
    @PostMapping("/setValue/{idStudent}")
    public String setValue(@PathVariable("idStudent") long idStudent,
                           @Valid @ModelAttribute("listRating") FormListMarks listRating
            , BindingResult bindingResult, Model model) {

        Optional<Student> optionalStudent = studentService.getStudById(idStudent);

        try {
            Student student = optionalStudent.orElseThrow(IllegalAccessError::new);
            if (bindingResult.hasErrors()) {

                model.addAttribute("listRating", listRating);
                int[] massNumber = new int[listRating.getLengthArraysMarks()];
                for (int i = 0; i < listRating.getLengthArraysMarks(); i++) {
                    massNumber[i] = i + 1;
                }
                model.addAttribute("student", student);
                model.addAttribute("mass", massNumber);
                model.addAttribute("arraysPredmet", Predmet.values());
                return "setValue";
            }
            studentService.setMarksByIdStudentsAndPredmet(student.getIdStudent(), listRating.getPredmet(), listRating.getList());
            return "redirect:/student/" + student.getIdStudent();
        } catch (IllegalAccessError | NullPointerException ex) {
            System.out.println(ex);
            model.addAttribute("warning"
                    , "not Student");
            return "error";
        }
    }


    @GetMapping("/filter")
    public String formFilter(Model model) {
        model.addAttribute("filterAverage", new FormParametersForFilter());
        model.addAttribute("filterYears", new FormParametersForFilter());
        model.addAttribute("enum", Predmet.values());
        return "filter";
    }

    /**
     * A filter that makes a request to the server to receive students whose average score meets the conditions.
     * Conditions are accepted as an object with filtering boundaries.
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
        model.addAttribute("setIdStudent", studentService.getIdStudentsUsingFilter(formParametersForFilter, Filter.AVERAGE_MARKS));
        return "listIdStudent";
    }

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
        model.addAttribute("setIdStudent", studentService.getIdStudentsUsingFilter(formParametersForFilter, Filter.YEARS));
        return "listIdStudent";
    }

    @DeleteMapping("/delete/{idStudent}")
    public String deleteStudent(@PathVariable long idStudent, Model model) {
        if (studentService.deletStudentById(idStudent))
            return "redirect:/student";
        model.addAttribute("warning"
                , "delete failed");
        return "error";
    }

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