package com.codegym.controller;


import com.codegym.model.Customer;
import com.codegym.model.Province;
import com.codegym.service.CustomerService;
import com.codegym.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProvinceController {

    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private CustomerService customerService;

//    @ModelAttribute("customers")
//    public Iterable<Customer>customers() {
//        return customerService.findAllByProvince(province);
//    }

    @GetMapping("/province")
    public ModelAndView listProvince() {
        Iterable<Province> provinces = provinceService.findAll();
        ModelAndView modelAndView = new ModelAndView("/province/list");
        modelAndView.addObject("province", provinces);
        return modelAndView;
    }

    @GetMapping("/create-province")
    public ModelAndView showCreateFrom() {
        ModelAndView modelAndView = new ModelAndView("/province/create");
        modelAndView.addObject("province", new Province());
        return modelAndView;
    }

    @PostMapping("/create-province")
    public String saveProvince(
            @ModelAttribute("province") Province province,
            RedirectAttributes redirectAttributes
    ) {
        provinceService.save(province);
        redirectAttributes.addFlashAttribute("message", "New create province successfully");
        return "redirect:/province";
    }

    @GetMapping("/edit-province/{id}")
    public ModelAndView showEditFrom(
            @PathVariable("id") Integer id
    ) {
        Province province = provinceService.findById(id);
        if (province != null) {
            ModelAndView modelAndView = new ModelAndView("/province/edit");
            modelAndView.addObject("province", province);
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("/province/error-404");
            return modelAndView;
        }
    }

    @PostMapping("/edit-province")
    public String updateProvince(
            @ModelAttribute("province") Province province,
            RedirectAttributes redirectAttributes
    ) {
        provinceService.save(province);
        redirectAttributes.addFlashAttribute("message", "Province update successfully");
        return "redirect:/province";
    }

    @GetMapping("/delete-province/{id}")
    public ModelAndView showDeleteFrom(
            @PathVariable("id") Integer id
    ) {
        Province province = provinceService.findById(id);
        if (province != null) {
            ModelAndView modelAndView = new ModelAndView("/province/delete");
            modelAndView.addObject("province", province);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/province/error-404");
            return modelAndView;
        }
    }

    @PostMapping("/delete-province")
    public String removeProvince(
            @ModelAttribute("province") Province province,
            RedirectAttributes redirectAttributes
    ) {
        provinceService.remove(province.getId());
        redirectAttributes.addFlashAttribute("message", "Province remove successfully");
        return "redirect:/province";
    }

    @GetMapping("view-province/{id}")
    public ModelAndView viewProvince(
            @PathVariable("id") Integer id
    ) {
        Province province = provinceService.findById(id);
        if (province == null) {
            ModelAndView modelAndView = new ModelAndView("/province/error-404");
            return modelAndView;
        }else {
            Iterable<Customer> customers = customerService.findAllByProvince(province);
            ModelAndView modelAndView = new ModelAndView("/province/view");
            modelAndView.addObject("province", province);
            modelAndView.addObject("customers", customers);
            return modelAndView;
        }
    }
}
