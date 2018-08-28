package com.codegym.controller;


import com.codegym.model.Customer;
import com.codegym.model.Province;
import com.codegym.service.CustomerService;
import com.codegym.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProvinceService provinceService;

    @ModelAttribute("provinces")
    public Iterable<Province> provinces() {
        return provinceService.findAll();
    }

    @GetMapping("/customers")
    public ModelAndView listCustomer(Pageable pageable) {
        Page<Customer> customers = customerService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/customer/list");
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @GetMapping("/customer-create")
    public ModelAndView showCreateCustomerForm() {
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }

    @PostMapping("/customer-create")
    public String saveCustomer(
            @ModelAttribute("customer") Customer customer,
            RedirectAttributes redirectAttributes
    ) {
        customerService.save(customer);
        redirectAttributes.addFlashAttribute("message", "New create customer successfully");
        return "redirect:/customers";
    }

    @GetMapping("/customer-edit/{id}")
    public ModelAndView showEditCustomerForm(
            @PathVariable("id") Integer id
    ) {
        Customer customer = customerService.findById(id);
        if (customer != null) {
            ModelAndView modelAndView = new ModelAndView("/customer/edit");
            modelAndView.addObject("customer", customer);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/customer/error-404");
            return modelAndView;
        }
    }

    @PostMapping("customer-edit")
    public String updateCustomer(
            @ModelAttribute("customer") Customer customer,
            RedirectAttributes redirectAttributes
    ) {
        customerService.save(customer);
        redirectAttributes.addFlashAttribute("message", "Customer update successfully");
        return "redirect:/customers";
    }

    @GetMapping("/customer-delete/{id}")
    public ModelAndView showDeleteCustomerForm(
            @PathVariable("id") Integer id
    ) {
        Customer customer = customerService.findById(id);
        if (customer != null) {
            ModelAndView modelAndView = new ModelAndView("/customer/delete");
            modelAndView.addObject("customer", customer);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/customer/error-404");
            return modelAndView;
        }
    }

    @PostMapping("/customer-delete")
    public String removeCustomer(
            @ModelAttribute("customer") Customer customer,
            RedirectAttributes redirectAttributes
    ) {
        customerService.remove(customer.getId());
        redirectAttributes.addFlashAttribute("message", "customer remove successfully");
        return "redirect:/customers";

    }
}
