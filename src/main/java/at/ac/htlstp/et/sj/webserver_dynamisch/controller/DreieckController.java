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

        if (seiteA <= 0 || seiteB <= 0 || seiteC <= 0) {
            model.addAttribute("error", "The given sides do not form a valid triangle.");
            return "index";
        }

        double s = (seiteA + seiteB + seiteC) / 2;
        double area = Math.sqrt(s * (s - seiteA) * (s - seiteB) * (s - seiteC));
        double perimeter = seiteA + seiteB + seiteC;

        double winkelA = Math.toDegrees(Math.acos((seiteB * seiteB + seiteC * seiteC - seiteA * seiteA) / (2 * seiteB * seiteC)));
        double winkelB = Math.toDegrees(Math.acos((seiteA * seiteA + seiteC * seiteC - seiteB * seiteB) / (2 * seiteA * seiteC)));
        double winkelC = 180 - winkelA - winkelB;

        model.addAttribute("seiteA", seiteA);
        model.addAttribute("seiteB", seiteB);
        model.addAttribute("seiteC", seiteC);
        model.addAttribute("area", area);
        model.addAttribute("perimeter", perimeter);
        model.addAttribute("winkelA", winkelA);
        model.addAttribute("winkelB", winkelB);
        model.addAttribute("winkelC", winkelC);

        return "result";
    }

    @PostMapping("/determine")
    @ResponseBody
    public String determineTriangle(@RequestBody DreieckFormDto dreieckForm) {
        double seiteA = dreieckForm.getSeiteA();
        double seiteB = dreieckForm.getSeiteB();
        double seiteC = dreieckForm.getSeiteC();

        if (seiteA <= 0 || seiteB <= 0 || seiteC <= 0) {
            return "kein Dreieck";
        }

        boolean isRightAngled = (seiteA * seiteA + seiteB * seiteB == seiteC * seiteC) ||
                (seiteA * seiteA + seiteC * seiteC == seiteB * seiteB) ||
                (seiteB * seiteB + seiteC * seiteC == seiteA * seiteA);

        if (seiteA == seiteB && seiteB == seiteC) {
            return "gleichseitig";
        } else if (seiteA == seiteB || seiteB == seiteC || seiteA == seiteC) {
            if (isRightAngled) {
                return "gleichschenkelig rechtwinkelig";
            }
            return "gleichschenkelig";
        } else if (isRightAngled) {
            return "rechtwinkelig";
        } else {
            return "allgemeines Dreieck";
        }
    }
}