package at.ac.htlstp.et.sj.webserver_dynamisch.controller;

import at.ac.htlstp.et.sj.webserver_dynamisch.model.DreieckFormDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DreieckController {

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("dreieckForm", new DreieckFormDto());
        return "index";
    }

    @PostMapping("/analyze")
    public String analyzeTriangle(@ModelAttribute DreieckFormDto dreieckForm, Model model) {
        double seiteA = dreieckForm.getSeiteA();
        double seiteB = dreieckForm.getSeiteB();
        double seiteC = dreieckForm.getSeiteC();

        double area = 0.5 * seiteA * seiteB; // Example
        double perimeter = seiteA + seiteB + seiteC;

        model.addAttribute("seiteA", seiteA);
        model.addAttribute("seiteB", seiteB);
        model.addAttribute("seiteC", seiteC);
        model.addAttribute("area", area);
        model.addAttribute("perimeter", perimeter);

        return "result";
    }

    @PostMapping("/determine")
    @ResponseBody
    public String determineTriangle(@RequestBody DreieckFormDto dreieckForm) {
        double seiteA = dreieckForm.getSeiteA();
        double seiteB = dreieckForm.getSeiteB();
        double seiteC = dreieckForm.getSeiteC();

        if (seiteA == seiteB && seiteB == seiteC) {
            return "gleichseitig";
        } else if (seiteA == seiteB || seiteB == seiteC || seiteA == seiteC) {
            return "gleichschenkelig";
        } else if (seiteA == 0 || seiteB == 0 || seiteC == 0) {
            return "falsche Eingabe";
        } else {
            return "allgemeines Dreieck";
        }
    }
}